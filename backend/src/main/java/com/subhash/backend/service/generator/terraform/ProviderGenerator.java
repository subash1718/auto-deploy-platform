package com.subhash.backend.service.generator.terraform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ProviderGenerator {

    public void generate(File terraformDirectory) {

        File provider = new File(terraformDirectory, "provider.tf");

        try (FileWriter writer = new FileWriter(provider)) {

            writer.write("""
provider "aws" {
  region = "eu-west-1"
}
""");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
