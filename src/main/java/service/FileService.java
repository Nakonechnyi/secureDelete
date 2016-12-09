package service;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @autor A_Nakonechnyi
 * @date 09.12.2016.
 */
public class FileService {
    static private final String newline = "\n";

    public static String overrideDir(File directory) throws IOException {
        String result = "";
        for (File file : directory.listFiles()){
            if (file.isFile()) {
                int length = (int) file.length();
                String newContent = "Overwritten_overwritten_overwritten_overwritten_overwritten_overwritten_!";
                FileWriter writer = new FileWriter(file);
                for (int iter= 0; iter < length; iter = iter+newContent.length()) {
                    writer.write(newContent);
                }
                writer.close();
                result = result.concat("File " + file.getName() + " overwritten." + newline);
            } else {
                result = result.concat(
                        overrideDir(file));
            }
        }
        return result;
    }

    public static String deleteContent(File directory) {
        String result = "";
        for (File file : directory.listFiles()){
            try {
                FileUtils.forceDelete(file);
                result = result.concat(file.getName() + " is deleted!" + newline);
            } catch (IOException e) {
                result = result.concat("Delete operation " + file.getName() + " is failed." + newline);
            }
        }
        return result;
    }

    public static String printDirContent(File directory) {
        String result = "";
        for (File file : directory.listFiles()){
            if (file.isDirectory()) {
                result = result.concat(file.getPath() + newline);
                result = result.concat(
                        printDirContent(file));
            } else {
                result = result.concat(file.getPath() + newline);
            }
        }
        return result;
    }
}
