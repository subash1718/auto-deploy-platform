package com.subhash.backend.service.generator.terraform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SubnetGenerator {

    public void generate(File terraformDirectory) {

        File file = new File(terraformDirectory, "subnets.tf");

        try (FileWriter writer = new FileWriter(file)) {

            writer.write("""
resource "aws_subnet" "public_subnet" {

  vpc_id = aws_vpc.main.id

  cidr_block = "10.0.1.0/24"

  availability_zone = "eu-west-1a"

  map_public_ip_on_launch = true

}
""");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
