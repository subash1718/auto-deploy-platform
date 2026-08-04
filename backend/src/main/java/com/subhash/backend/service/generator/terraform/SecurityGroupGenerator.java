package com.subhash.backend.service.generator.terraform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SecurityGroupGenerator {

    public void generate(File terraformDirectory) {

        File file = new File(terraformDirectory, "security-group.tf");

        try (FileWriter writer = new FileWriter(file)) {

            writer.write("""
resource "aws_security_group" "app_sg" {

  name = "app-security-group"

  vpc_id = aws_vpc.main.id

  ingress {

    from_port = 8080
    to_port = 8080
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]

  }

}
""");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
