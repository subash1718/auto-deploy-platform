package com.subhash.backend.service.analysis;

import com.subhash.backend.model.AnalysisRequest;
import com.subhash.backend.model.analysis.ResourceRequirement;
import com.subhash.backend.model.technology.TechnologyStack;
import org.springframework.stereotype.Service;

@Service
public class ResourceRequirementService {

    public ResourceRequirement analyze(
            TechnologyStack stack,
            AnalysisRequest request) {

        ResourceRequirement requirement = new ResourceRequirement();

        requirement.setCpu(2);
        requirement.setMemoryGb(4);
        requirement.setStorageGb(20);

        requirement.setDatabaseRequired(true);
        requirement.setDatabaseType("MySQL");

        requirement.setApplicationPort(8080);

        requirement.setContainerizationRequired(true);

        requirement.setLoadBalancerRequired(
                request.isHighAvailability());

        requirement.setAutoScalingRequired(
                request.isHighAvailability());

        return requirement;
    }
}
