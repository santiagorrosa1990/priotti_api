package com.santiago.priotti_api.Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;


public class FileReader {

    public String convert_file_to_string_apache_commons(String filename) {

        try {
            String filePath = new File("").getAbsolutePath();
            filePath = filePath.concat(filename);
            File file = new File(filePath);
            return FileUtils.readFileToString(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";


    }

    private String getContent(String fileName) throws IOException {
        File file = getFileFromResources(fileName);
        return new String(Files.readAllBytes(file.toPath()));
    }

    private File getFileFromResources(String fileName) {
        final ClassLoader classLoader = this.getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }


    public String getFile(String file) {
        String fileName = file;

        try {
            String content = getContent(fileName);

            //normalizamos el file por si tiene tabs o new lines
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readValue(content, JsonNode.class);

            return jsonNode.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}