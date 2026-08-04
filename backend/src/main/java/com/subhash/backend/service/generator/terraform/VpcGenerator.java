package com.subhash.backend.service.generator.terraform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VpcGenerator {

    public void generate(File terraformDirectory) {

        File file = new File(terraformDirectory, "vpc.tf");

        try (FileWriter writer = new FileWriter(file)) {

            writer.write("""
resource "aws_vpc" "main" {

  cidr_block = "10.0.0.0/16"

  tags = {
    Name = "auto-deploy-vpc"
  }

}
""");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
