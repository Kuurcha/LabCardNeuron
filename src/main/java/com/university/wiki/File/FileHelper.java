package com.university.wiki.File;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHelper {


    public static URL getResourcePath(String resourcePath) throws IOException {
        if (!resourcePath.startsWith("/")) {
            resourcePath = "/" + resourcePath;
        }


        var resourceURL = FileHelper.class.getResource(resourcePath);
        if (resourceURL == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }
        return  resourceURL;
    }

    /**
     * Retrieves a resource as a File.
     *
     * @param resourcePath the path to the resource (relative to the resources folder, e.g., "/keys/private.pem").
     * @return a File object pointing to the resource, copied to a temporary file.
     * @throws IOException if the resource cannot be read or copied.
     */
    public static File getResourceAsFile(String resourcePath) throws IOException {

        URL resourceURL = getResourcePath(resourcePath);
        // Copy resource to a temporary file
        Path tempFile = Files.createTempFile("resource-", "-" + Path.of(resourcePath).getFileName());
        Files.copy(resourceURL.openStream(), tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        return tempFile.toFile();
    }
}