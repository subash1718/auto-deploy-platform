package com.subhash.backend.service.deployment;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Service
public class TerraformExecutionService {

    public void terraformInit(String terraformPath) {

        execute(terraformPath, "terraform", "init");

    }

    public void terraformPlan(String terraformPath) {

        execute(terraformPath, "terraform", "plan");

    }

    public void terraformApply(String terraformPath) {

        execute(terraformPath,
                "terraform",
                "apply",
                "-auto-approve");

    }

    private void execute(String directory, String... command) {

        try {

            ProcessBuilder builder = new ProcessBuilder(command);

            builder.directory(new File(directory));

            builder.redirectErrorStream(true);

            Process process = builder.start();

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(process.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            process.waitFor();

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

}
