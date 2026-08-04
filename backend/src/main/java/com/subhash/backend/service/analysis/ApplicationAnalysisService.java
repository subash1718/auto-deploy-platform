package com.subhash.backend.service.analysis;

import com.subhash.backend.model.analysis.ApplicationAnalysis;
import com.subhash.backend.model.metadata.RepositoryMetadata;
import org.springframework.stereotype.Service;

@Service
public class ApplicationAnalysisService {

    public ApplicationAnalysis analyze(RepositoryMetadata metadata) {

        ApplicationAnalysis analysis = new ApplicationAnalysis();

        analysis.setDockerPresent(!metadata.getDockerFiles().isEmpty());
        analysis.setTerraformPresent(!metadata.getTerraformFiles().isEmpty());

        analysis.setKubernetesPresent(!metadata.getKubernetesFiles().isEmpty());

        analysis.setDatabaseRequired(true);

        analysis.setDatabase("MySQL");

        analysis.setPort(8080);

        analysis.setRestApi(true);

        analysis.setMicroservice(true);

        return analysis;
    }

}
