package com.github.controller;


import com.github.service.FileService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;


@RestController
public class FileController {

    @Autowired
    private FileService fileService;


    @GetMapping("/getFilePaths")
    ResponseEntity<List<String>> getFilePaths(@RequestParam("path") String filePath) throws IOException {
        return ResponseEntity.ok().body(fileService.getFiles(filePath));
    }

    @GetMapping("/getFileContent")
    ResponseEntity<String> getFileContent(@RequestParam("path") String filePath) throws IOException {
        return ResponseEntity.ok().body(fileService.getFileContent(filePath));
    }

    @PostMapping(value = "/saveFileContent")
    ResponseEntity<String> saveFileContent(@RequestBody String data,@RequestParam("path") String filePath) throws IOException {
        return ResponseEntity.ok().body(fileService.saveFile(filePath,data));
    }


    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getDownloadData(@RequestParam("path") String filePath) throws Exception {

        File file=new File(filePath);

        String fileData = FileUtils.readFileToString(file, Charset.defaultCharset());
        byte[] output = fileData.getBytes();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("charset", "utf-8");
        responseHeaders.setContentType(MediaType.MULTIPART_MIXED);
        responseHeaders.setContentLength(output.length);
        responseHeaders.set("Content-disposition", "attachment; filename="+file.getName());

        return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
    }

}
