package ru.vsu.csf.corporatelearningsite.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.vsu.csf.corporatelearningsite.model.Material;
import ru.vsu.csf.corporatelearningsite.payload.UploadFileResponse;
import ru.vsu.csf.corporatelearningsite.services.MaterialService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/materials")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping("/uploadMaterial/{lessonName}")
    public UploadFileResponse uploadMaterial(@PathVariable("lessonName") String lessonName, @RequestParam("material") MultipartFile material) {
        String materialName = materialService.storeMaterial(material, lessonName).getName();
        String materialDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadMaterial/")
                .path(materialName)
                .toUriString();
        log.info("the material was uploaded");
        return new UploadFileResponse(materialName, materialDownloadUri,
                material.getContentType(), material.getSize());
    }

    @GetMapping("/downloadMaterial/{materialName:.+}")
    public ResponseEntity<Resource> downloadMaterial(@PathVariable String materialName, HttpServletRequest request) {
        Resource resource = materialService.loadMaterialAsResource(materialName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; materialname=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("")
    public Iterable<Material> getMaterials(){
        log.info("materials were received");
        return materialService.listAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Material> deleteMaterial(@PathVariable("id") Long id) {
        materialService.delete(id);
        log.info("the material {} was deleted", id);
        return ResponseEntity.ok().build();
    }
}
