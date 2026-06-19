package com.subhash.backend.service.scanner;

import com.subhash.backend.model.metadata.RepositoryMetadata;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class RepositoryScannerService {

    public RepositoryMetadata scanRepository(String projectPath) {

        RepositoryMetadata metadata = new RepositoryMetadata();
        metadata.setProjectRoot(projectPath);

        System.out.println("========== Repository Scanner ==========");

        try {

            Files.walk(Path.of(projectPath))
                    .forEach(path -> {

                        String fileName = path.getFileName().toString();

                        // Maven
                        if (fileName.equals("pom.xml")) {
                            metadata.getPomFiles().add(path.toString());
                        }

                        // Node.js
                        if (fileName.equals("package.json")) {
                            metadata.getPackageJsonFiles().add(path.toString());
                        }

                        // Docker
                        if (fileName.equals("Dockerfile")) {
                            metadata.getDockerFiles().add(path.toString());
                        }

                        // Jenkins
                        if (fileName.equals("Jenkinsfile")) {
                            metadata.getJenkinsFiles().add(path.toString());
                        }

                        // Terraform
                        if (fileName.endsWith(".tf")) {
                            metadata.getTerraformFiles().add(path.toString());
                        }

                        // Kubernetes
                        if (fileName.equals("deployment.yaml")
                                || fileName.equals("service.yaml")
                                || fileName.equals("ingress.yaml")) {

                            metadata.getKubernetesFiles().add(path.toString());
                        }

                    });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return metadata;
    }
}
