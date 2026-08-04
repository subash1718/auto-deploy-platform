package com.subhash.backend.service.git;

import org.eclipse.jgit.api.Git;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class GitCloneService {

    public String cloneRepository(String githubUrl) {

        try {

            String repositoryName = githubUrl
                    .substring(githubUrl.lastIndexOf("/") + 1)
                    .replace(".git", "");

            File directory = new File("uploads/" + repositoryName);

            if (directory.exists()) {
                deleteDirectory(directory);
            }

            Git.cloneRepository()
                    .setURI(githubUrl)
                    .setDirectory(directory)
                    .call();

            return directory.getAbsolutePath();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteDirectory(File file) {

        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                deleteDirectory(child);
            }
        }

        file.delete();
    }
}
