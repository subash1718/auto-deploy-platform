package com.subhash.backend.service.recommendation;

import com.subhash.backend.model.AnalysisRequest;
import com.subhash.backend.model.infrastructure.InfrastructureRecommendation;
import com.subhash.backend.model.technology.TechnologyStack;
import org.springframework.stereotype.Service;

@Service
public class InfrastructureRecommendationService {

    public InfrastructureRecommendation recommend(
            TechnologyStack stack,
            AnalysisRequest request) {

        InfrastructureRecommendation recommendation =
                new InfrastructureRecommendation();

        recommendation.setCloudProvider("AWS");
        recommendation.setComputeService("EC2");

        // Default values
        recommendation.setVpc(true);
        recommendation.setInternetGateway(true);
        recommendation.setPublicSubnets(2);
        recommendation.setPrivateSubnets(2);
        recommendation.setStorageGb(20);

        // Instance recommendation based on expected users
        if (request.getExpectedUsers() <= 100) {

            recommendation.setInstanceType("t3.micro");
            recommendation.setCpu(1);
            recommendation.setMemoryGb(2);

        } else if (request.getExpectedUsers() <= 1000) {

            recommendation.setInstanceType("t3.medium");
            recommendation.setCpu(2);
            recommendation.setMemoryGb(4);

        } else {

            recommendation.setInstanceType("t3.large");
            recommendation.setCpu(4);
            recommendation.setMemoryGb(8);

        }

        // High Availability
        if (request.isHighAvailability()) {

            recommendation.setLoadBalancer(true);
            recommendation.setAutoScaling(true);
            recommendation.setMinInstances(2);
            recommendation.setMaxInstances(5);

        } else {

            recommendation.setLoadBalancer(false);
            recommendation.setAutoScaling(false);
            recommendation.setMinInstances(1);
            recommendation.setMaxInstances(1);

        }

        return recommendation;
    }
}
