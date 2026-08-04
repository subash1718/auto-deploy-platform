package com.subhash.backend.service.generator.terraform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AlbGenerator {

    public void generate(File terraformDirectory) {

        File file = new File(terraformDirectory, "alb.tf");

        try (FileWriter writer = new FileWriter(file)) {

            writer.write("""
resource "aws_lb" "application_lb" {

  name = "application-lb"

  internal = false

  load_balancer_type = "application"

  security_groups = [
    aws_security_group.app_sg.id
  ]

  subnets = [
    aws_subnet.public_subnet.id
  ]

}
""");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
