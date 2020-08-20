package com.github.service;

import org.apache.commons.io.input.ReversedLinesFileReader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class LogService {


    /**
     * function to retrive file content for last n lines
     * @param filePath
     * @return
     */
    public String getLastNLines(String filePath,int noOfLines) throws IOException {
        ReversedLinesFileReader fileObject = new ReversedLinesFileReader(new File(filePath), Charset.defaultCharset());
        StringBuilder result=new StringBuilder("");
        for(int i=0;i<noOfLines;i++){
            String line=fileObject.readLine();
            if(line==null)
                break;
            result.append(line+"\n");
        }
        return result.toString();
    }
}
