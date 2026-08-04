package com.subhash.backend.service.generator.terraform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VariablesGenerator {

    public void generate(File terraformDirectory) {

        File variables = new File(terraformDirectory, "variables.tf");

        try (FileWriter writer = new FileWriter(variables)) {

            writer.write("""
variable "instance_type" {
  default = "t3.micro"
}
""");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
