package com.subhash.backend.service.generator.terraform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AutoScalingGenerator {

    public void generate(File terraformDirectory) {

        File file = new File(terraformDirectory, "autoscaling.tf");

        try (FileWriter writer = new FileWriter(file)) {

            writer.write("""
resource "aws_autoscaling_group" "application_asg" {

  desired_capacity = 2

  min_size = 2

  max_size = 5

}
""");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
