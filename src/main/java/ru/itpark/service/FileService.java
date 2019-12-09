package ru.itpark.service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class FileService {
    private final String uploadPath;

    public FileService() throws IOException {
        String path = System.getenv("UPLOAD_PATH");
        if (path == null) {
            uploadPath = Files.createTempDirectory("upload").toAbsolutePath().toString();
            return;
        }
        uploadPath = path;
        if (Files.notExists(Paths.get(uploadPath))) {
            Files.createDirectories(Paths.get(uploadPath));
        }
    }
    public void readFiles(String id, ServletOutputStream os) throws IOException {
        var path = Paths.get(uploadPath).resolve(id);
        Files.copy(path, os);
    }
    public String writeFiles( Part part) throws IOException {
        var id = UUID.randomUUID().toString();
        part.write (Paths.get(uploadPath).resolve(id).toString());
        return id;
    }
}