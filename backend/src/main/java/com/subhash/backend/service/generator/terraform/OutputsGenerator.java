package com.subhash.backend.service.generator.terraform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputsGenerator {

    public void generate(File terraformDirectory) {

        File file = new File(terraformDirectory, "outputs.tf");

        try (FileWriter writer = new FileWriter(file)) {

            writer.write("""
output "instance_id" {

  value = aws_instance.application.id

}
""");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
