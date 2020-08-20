package com.github.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileService {

    /**
     * function to retrive file content
     * @param filePath
     * @return
     */
    public String getFileContent(String filePath) throws IOException {
        String fileContent= FileUtils.readFileToString(new File(filePath), Charset.defaultCharset());
        return Base64.getEncoder().encodeToString(fileContent.getBytes());
    }



    /**
     * function to save file content
     * @param filePath
     * @param Data
     * @return
     */
    public String saveFile(String filePath,String Data) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(Data);
        String decodedString = new String(decodedBytes);
        FileUtils.writeStringToFile(new File(filePath),decodedString,Charset.defaultCharset());
        return this.getFileContent(filePath);
    }

    /**
     * function to list the files with full path
     * @param filePath
     * @return
     */
    public List<String> getFiles(String filePath) throws IOException {
        List<File> filesInFolder = Files.walk(Paths.get(filePath))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
        List<String> filePaths=new ArrayList<>();
        filesInFolder.forEach(file -> filePaths.add(file.getAbsolutePath()));
        return filePaths;
    }
}
