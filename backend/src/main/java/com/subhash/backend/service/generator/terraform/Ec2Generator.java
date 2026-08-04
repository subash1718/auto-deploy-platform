package com.subhash.backend.service.generator.terraform;

import com.subhash.backend.model.infrastructure.InfrastructureRecommendation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ec2Generator {

    public void generate(File terraformDirectory,
                         InfrastructureRecommendation recommendation) {

        File file = new File(terraformDirectory, "ec2.tf");

        try (FileWriter writer = new FileWriter(file)) {

            writer.write("""
resource "aws_instance" "application" {

  ami = "ami-0c1c30571d2dae5c9"

  instance_type = "%s"

  subnet_id = aws_subnet.public_subnet.id

  vpc_security_group_ids = [
    aws_security_group.app_sg.id
  ]

  tags = {
    Name = "ApplicationServer"
  }

}
""".formatted(recommendation.getInstanceType()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
