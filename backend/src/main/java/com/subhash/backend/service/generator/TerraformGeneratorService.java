package com.subhash.backend.service.generator;

import com.subhash.backend.model.infrastructure.InfrastructureRecommendation;
import com.subhash.backend.service.generator.terraform.*;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class TerraformGeneratorService {

    private final ProviderGenerator providerGenerator = new ProviderGenerator();
    private final VariablesGenerator variablesGenerator = new VariablesGenerator();
    private final VpcGenerator vpcGenerator = new VpcGenerator();
    private final SubnetGenerator subnetGenerator = new SubnetGenerator();
    private final SecurityGroupGenerator securityGroupGenerator = new SecurityGroupGenerator();
    private final Ec2Generator ec2Generator = new Ec2Generator();
    private final AlbGenerator albGenerator = new AlbGenerator();
    private final AutoScalingGenerator autoScalingGenerator = new AutoScalingGenerator();
    private final OutputsGenerator outputsGenerator = new OutputsGenerator();

    public void generateTerraform(
            String projectPath,
            InfrastructureRecommendation recommendation) {

        File terraformDirectory = new File(projectPath, "terraform");

        if (!terraformDirectory.exists()) {
            terraformDirectory.mkdirs();
        }

        providerGenerator.generate(terraformDirectory);
        variablesGenerator.generate(terraformDirectory);
        vpcGenerator.generate(terraformDirectory);
        subnetGenerator.generate(terraformDirectory);
        securityGroupGenerator.generate(terraformDirectory);
        ec2Generator.generate(terraformDirectory, recommendation);
        albGenerator.generate(terraformDirectory);
        autoScalingGenerator.generate(terraformDirectory);
        outputsGenerator.generate(terraformDirectory);

        System.out.println("\nTerraform project generated successfully.");
    }

}
