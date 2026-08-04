package com.subhash.backend.service.download;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileDownloadService {

    private static final String UPLOADS_DIR = "uploads";

    public Resource getSingleFileResource(String repository, String fileName) {
        Path filePath = resolveRepositoryPath(repository).resolve(fileName);
        if (!Files.exists(filePath)) {
            throw new RuntimeException("Requested file '" + fileName + "' does not exist for repository: " + repository);
        }
        return new FileSystemResource(filePath.toFile());
    }

    public Resource getTerraformZipResource(String repository) {
        Path repoPath = resolveRepositoryPath(repository);
        Path terraformDir = repoPath.resolve("terraform");

        if (!Files.exists(terraformDir)) {
            // Check if terraform files are directly under repo root
            try (var stream = Files.list(repoPath)) {
                boolean hasTf = stream.anyMatch(p -> p.toString().endsWith(".tf"));
                if (hasTf) {
                    terraformDir = repoPath;
                } else {
                    throw new RuntimeException("No Terraform configuration found for repository: " + repository);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to scan directory for Terraform files", e);
            }
        }

        try {
            File zipFile = File.createTempFile("terraform-", ".zip");
            zipFile.deleteOnExit();

            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
                Path sourceDir = terraformDir;
                Files.walk(sourceDir)
                        .filter(path -> !Files.isDirectory(path))
                        .filter(path -> path.toString().endsWith(".tf"))
                        .forEach(path -> {
                            ZipEntry zipEntry = new ZipEntry(sourceDir.relativize(path).toString());
                            try {
                                zos.putNextEntry(zipEntry);
                                Files.copy(path, zos);
                                zos.closeEntry();
                            } catch (IOException e) {
                                throw new RuntimeException("Failed to add terraform file to zip", e);
                            }
                        });
            }

            return new FileSystemResource(zipFile);

        } catch (IOException e) {
            throw new RuntimeException("Failed to package Terraform zip file", e);
        }
    }

    private Path resolveRepositoryPath(String repository) {
        if (repository == null || repository.isBlank()) {
            throw new IllegalArgumentException("Repository parameter cannot be empty.");
        }
        Path path = Path.of(UPLOADS_DIR, repository).normalize();
        if (!path.startsWith(Path.of(UPLOADS_DIR).normalize())) {
            throw new SecurityException("Invalid repository path traversal attempt.");
        }
        if (!Files.exists(path)) {
            throw new RuntimeException("Repository workspace not found: " + repository);
        }
        return path;
    }
}
