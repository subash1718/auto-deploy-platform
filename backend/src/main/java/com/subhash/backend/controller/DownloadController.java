package com.subhash.backend.controller;

import com.subhash.backend.service.download.FileDownloadService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/download")
@CrossOrigin(origins = "http://localhost:5173")
public class DownloadController {

    private final FileDownloadService fileDownloadService;

    public DownloadController(FileDownloadService fileDownloadService) {
        this.fileDownloadService = fileDownloadService;
    }

    @GetMapping("/dockerfile")
    public ResponseEntity<Resource> downloadDockerfile(@RequestParam("repository") String repository) {
        Resource resource = fileDownloadService.getSingleFileResource(repository, "Dockerfile");
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Dockerfile\"")
                .body(resource);
    }

    @GetMapping("/jenkinsfile")
    public ResponseEntity<Resource> downloadJenkinsfile(@RequestParam("repository") String repository) {
        Resource resource = fileDownloadService.getSingleFileResource(repository, "Jenkinsfile");
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Jenkinsfile\"")
                .body(resource);
    }

    @GetMapping("/terraform")
    public ResponseEntity<Resource> downloadTerraform(@RequestParam("repository") String repository) {
        Resource resource = fileDownloadService.getTerraformZipResource(repository);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + repository + "-terraform.zip\"")
                .body(resource);
    }

    @GetMapping("/report")
    public ResponseEntity<Resource> downloadReport(@RequestParam("repository") String repository) {
        Resource resource = fileDownloadService.getSingleFileResource(repository, "deployment-report.txt");
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"deployment-report.txt\"")
                .body(resource);
    }
}
