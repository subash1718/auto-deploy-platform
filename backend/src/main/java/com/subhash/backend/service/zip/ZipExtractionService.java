package com.subhash.backend.service.zip;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class ZipExtractionService {

    public String extractZip(File zipFile) {

        try {

            String fileName = zipFile.getName().replace(".zip", "");

            Path outputPath = Path.of("uploads", fileName);

            if (Files.exists(outputPath)) {
                deleteDirectory(outputPath.toFile());
            }

            Files.createDirectories(outputPath);

            ZipInputStream zis = new ZipInputStream(
                    new FileInputStream(zipFile)
            );

            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {

                File newFile = new File(
                        outputPath.toFile(),
                        entry.getName()
                );

                if (entry.isDirectory()) {

                    newFile.mkdirs();

                } else {

                    new File(newFile.getParent()).mkdirs();

                    FileOutputStream fos =
                            new FileOutputStream(newFile);

                    byte[] buffer = new byte[4096];

                    int len;

                    while ((len = zis.read(buffer)) > 0) {

                        fos.write(buffer, 0, len);

                    }

                    fos.close();

                }

                zis.closeEntry();

            }

            zis.close();

            return outputPath.toAbsolutePath().toString();

        } catch (Exception e) {

            throw new RuntimeException("Failed to extract ZIP", e);

        }

    }

    private void deleteDirectory(File file) {

        if (file.isDirectory()) {

            for (File child : file.listFiles()) {

                deleteDirectory(child);

            }

        }

        file.delete();

    }

}