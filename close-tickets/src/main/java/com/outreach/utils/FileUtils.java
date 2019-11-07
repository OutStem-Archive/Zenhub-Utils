package com.outreach.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtils {

    private static Logger log = Logger.getLogger(FileUtils.class.getName());

    public static void writeFile(String fileName, String content) throws IOException {
        if (fileName == null || fileName.isEmpty()) {
            log.log(Level.WARNING, "Cannot supply an empty file name, will not write out file");
            return;
        }
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(content);
            fw.flush();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to write to file " + fileName, e);
        }
    }
}