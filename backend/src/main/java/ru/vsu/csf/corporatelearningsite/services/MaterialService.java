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
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class MaterialService {

    private static final String RESOURCE_NAME = "Material";
    private static final String FIELD_NAME_ID = "id";
    private final MaterialRepository materialRepository;
    private final LessonRepository lessonRepository;
    private final Path materialStorageLocation;

    public MaterialService(MaterialRepository materialRepository,
                           MaterialStorageProperties materialStorageProperties,
                           LessonRepository lessonRepository) {
        this.materialStorageLocation = Paths.get(materialStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
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

    public Material storeMaterial(MultipartFile material, String lessonName) {
        String materialName = StringUtils.cleanPath(material.getOriginalFilename());

        try {
            if (materialName.contains("..")) {
                log.error("Filename contains invalid path sequence {}", materialName);
                throw new MaterialStorageException("Sorry! Filename contains invalid path sequence " + materialName);
            }

            Material dbMaterial = new Material(materialName, material.getContentType(),
                    this.materialStorageLocation.toString());

            if(lessonRepository.findByName(lessonName).isPresent())
                dbMaterial.setLesson(lessonRepository.findByName(lessonName).get());
            else
                throw new MaterialStorageException("Lesson name not found");

            Path targetLocation = this.materialStorageLocation.resolve(materialName);
            Files.copy(material.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return materialRepository.save(dbMaterial);
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
            materialRepository.deleteById(id);
        } catch (IOException ex) {
            log.error("Could not delete the material");
            throw new MaterialStorageException("Could not delete the material", ex);
        }
    }
}
