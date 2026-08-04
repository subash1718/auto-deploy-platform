package com.subhash.backend.service.generator;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class JenkinsfileGeneratorService {

    public void generateJenkinsfile(String projectPath) {

        File jenkinsfile = new File(projectPath, "Jenkinsfile");

        if (jenkinsfile.exists()) {

            System.out.println("\nJenkinsfile already exists.");
            return;
        }

        String content = """
pipeline {

    agent any

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t pet-shop-api .'
            }
        }

    }

}
""";

        try (FileWriter writer = new FileWriter(jenkinsfile)) {

            writer.write(content);

            System.out.println("\nJenkinsfile generated successfully.");

        } catch (IOException e) {

            throw new RuntimeException(e);

        }

    }

}
