package file_io;

import constants.Warnings;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class FileInputOutput {

    public Path inputFilePath (){
        Scanner scanner = new Scanner(System.in);
        Path filePath = Paths.get(scanner.nextLine());
        if (!Files.isRegularFile(filePath)){
            System.out.println(Warnings.FILENOTEXIST);
        }
        return filePath;
    }

    public String fileTextToString(Path filePath) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader buffer = Files.newBufferedReader(filePath)) {
            String fullString;
            while ((fullString = buffer.readLine()) != null) {
                builder.append(fullString).append("\n");
            }
        }
        catch (IOException e) {
          //  System.out.println("исключение в fileTextToString - FILE NOT RECOGNIZED");
            throw new IOException(e);
            //e.printStackTrace();
        }
        return builder.toString();
    }

    public void writeToFile(Path pathOfIncomingFile, String textToWrite) throws IOException {
        String fname = pathOfIncomingFile.getFileName().toString();
        int pos = fname.lastIndexOf(".");
        if (pos > 0) {
            fname = fname.substring(0, pos);
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH_mm_ss");
        Path pathOfWriteFile = Files.createFile(Path.of(pathOfIncomingFile.getParent().toString(),fname+"_"
                      +LocalTime.now().format(dtf)+"_proceed.txt"));
        Files.write(pathOfWriteFile, textToWrite.getBytes());
    }

}
