package com.subhash.backend.service.generator;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class DockerfileGeneratorService {

    public void generateDockerfile(String projectPath) {

        try {

            Path dockerfile =
                    Path.of(projectPath, "backend", "Dockerfile");

            String content = """
                    FROM eclipse-temurin:21-jdk

                    WORKDIR /app

                    COPY target/*.jar app.jar

                    EXPOSE 8080

                    ENTRYPOINT ["java","-jar","app.jar"]
                    """;

            Files.writeString(dockerfile, content);

            System.out.println("\nDockerfile generated successfully:");
            System.out.println(dockerfile);

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Dockerfile", e);
        }

    }
}
