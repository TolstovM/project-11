package ru.vsu.csf.corporatelearningsite.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.csf.corporatelearningsite.config.MaterialStorageProperties;
import ru.vsu.csf.corporatelearningsite.exceptions.MaterialStorageException;
import ru.vsu.csf.corporatelearningsite.exceptions.ResourceNotFoundException;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.model.Material;
import ru.vsu.csf.corporatelearningsite.repositories.LessonRepository;
import ru.vsu.csf.corporatelearningsite.repositories.MaterialRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class MaterialService {

    private static final String RESOURCE_NAME = "Material";
    private static final String DEFAULT_UPLOAD_DIR = "/home/maksfox/tmp/project";
    private static final String FIELD_NAME_ID = "id";
    public static final String BAD_PATH_EXCEPTION_MASSAGE = "Filename contains invalid path sequence";
    public static final String FILE_SIZE_EXCEPTION_MASSAGE = "The file size exceeds the allowed value";
    public static final Long MAX_FILE_SIZE = 1048776L;
    private final MaterialRepository materialRepository;
    private final LessonRepository lessonRepository;
    private final Path materialStorageLocation;

    public MaterialService(MaterialRepository materialRepository,
                           MaterialStorageProperties materialStorageProperties,
                           LessonRepository lessonRepository) {
        if(materialStorageProperties.getUploadDir() != null) {
            this.materialStorageLocation = Paths.get(materialStorageProperties.getUploadDir())
                    .toAbsolutePath().normalize();
        }
        else {
            this.materialStorageLocation = Paths.get(DEFAULT_UPLOAD_DIR)
                    .toAbsolutePath().normalize();
        }
        try {
            Files.createDirectories(this.materialStorageLocation);
        } catch (IOException ex) {
            log.error("Could not create the directory {} where the uploaded materials will be stored",
                    materialStorageProperties.getUploadDir());
            throw new MaterialStorageException("Could not create the directory where the uploaded materials will be stored.", ex);
        }

        this.materialRepository = materialRepository;
        this.lessonRepository = lessonRepository;
    }

    public Material storeMaterial(MultipartFile material, Long lessonId) {
        String materialName = "Урок_" + lessonId.toString() + "_" + StringUtils.cleanPath(material.getOriginalFilename());
        if(materialRepository.findByName(materialName).isPresent()) {
            delete(materialRepository.findByName(materialName).get().getId());
        }
        // for a unique name

        try {
            if (materialName.contains("..")) {
                throw new MaterialStorageException(BAD_PATH_EXCEPTION_MASSAGE);
            }

            Material dbMaterial = new Material(materialName, material.getContentType(),
                    this.materialStorageLocation.toString());

            if(lessonRepository.findById(lessonId).isPresent())
                dbMaterial.setLesson(lessonRepository.findById(lessonId).get());

            Path targetLocation = this.materialStorageLocation.resolve(materialName);
            Files.copy(material.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            materialRepository.save(dbMaterial);
            return dbMaterial;
        } catch (IOException ex) {
            log.error("Could not store material {}", materialName);
            throw new MaterialStorageException("Could not store material " + materialName + ". Please try again!", ex);
        }
    }

    public Material getMaterial(Long materialId) {
        return materialRepository.findById(materialId)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, FIELD_NAME_ID, materialId));
    }

    public Resource loadMaterialAsResource(String materialName) {
        try {
            Path materialPath = this.materialStorageLocation.resolve(materialName).normalize();
            Resource resource = new UrlResource(materialPath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                log.error("Material not found {}", materialName);
                throw new ResourceNotFoundException(RESOURCE_NAME, FIELD_NAME_ID, materialName);
            }
        } catch (MalformedURLException ex) {
            log.error("Material not found {}", materialName);
            throw new ResourceNotFoundException(RESOURCE_NAME, FIELD_NAME_ID, materialName);
        }
    }

    public List<Material> listAll() {
        List<Material> res = (List<Material>) materialRepository.findAll();

        res.sort(Comparator.comparing(Material::getId));

        return res;
    }

    public void delete(Long id) {
        try {
            Material dbMaterial = getMaterial(id);
            Files.delete(Paths.get(dbMaterial.getUrl() + "\\" + dbMaterial.getName()));
        } catch (IOException ex) {
            log.error("Could not delete the material");
        }
        finally {
            materialRepository.deleteById(id);
        }
    }

    public void deleteByLessonId(Long id){
        for(Material material: getMaterialsByLessonId(id)){
            delete(material.getId());
        }
    }

    public List<Material> getMaterialsByLessonId(Long id) {
        if(lessonRepository.findById(id).isPresent()) {
            return lessonRepository.findById(id).get().getMaterials();
        }
        else
            return new ArrayList<>();
    }
}
