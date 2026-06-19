package com.subhash.backend.service.git;

import org.eclipse.jgit.api.Git;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class GitCloneService {

    public String cloneRepository(String githubUrl) {

        try {

            String repoName = githubUrl.substring(githubUrl.lastIndexOf("/") + 1)
                    .replace(".git", "");

            File directory = new File("uploads/" + repoName);

            if (!directory.exists()) {

                Git.cloneRepository()
                        .setURI(githubUrl)
                        .setDirectory(directory)
                        .call();

            }

            return directory.getAbsolutePath();

        } catch (Exception e) {

            throw new RuntimeException("Failed to clone repository", e);

        }

    }

}
