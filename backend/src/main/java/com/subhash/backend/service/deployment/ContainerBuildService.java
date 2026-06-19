package com.subhash.backend.service.deployment;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;

@Service
public class ContainerBuildService {

    public void buildDockerImage(String projectPath) {

        try {

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "docker",
                    "build",
                    "-t",
                    "pet-shop-api",
                    "."
            );

            // projectPath is already the application root
            processBuilder.directory(new File(projectPath));

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(process.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("\nDocker image built successfully!");
            } else {
                System.out.println("\nDocker build failed.");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
