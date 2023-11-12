package edu.hw6;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import org.apache.logging.log4j.LogManager;

public class Task4 {
    private Task4() {
    }

    public static void outputStreamComp(Path path) {
        try (OutputStream outputStream = Files.newOutputStream(path);
             CheckedOutputStream checkedOutputStream = new CheckedOutputStream(outputStream, new CRC32());
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream);
             OutputStreamWriter streamWriter = new OutputStreamWriter(bufferedOutputStream, StandardCharsets.UTF_8);
             PrintWriter printWriter = new PrintWriter(streamWriter)) {
            printWriter.print("Programming is learned by writing programs. ― Brian Kernighan");
        } catch (IOException e) {
            LogManager.getLogger().error(e);
        }
    }
}
