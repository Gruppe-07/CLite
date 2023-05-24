package org.example.codegeneration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class TerminalRunner {
    public void runProgram() {
        try {
            // Specify the working directory
            File workingDirectory = new File("assembly/");

            // Command 1: Assemble and compile the assembly file
            String command1 = "\"make\"";
            executeCommand(command1, workingDirectory);

            // Command 2: Link the object file and generate the executable
            String command2 = "ls";
            executeCommand(command2, workingDirectory);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void executeCommand(String command, File workingDirectory) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("zsh", "-c", command);
        processBuilder.directory(workingDirectory);
        // Redirect error stream to output stream
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        System.out.println("Command executed with exit code: " + exitCode);
    }
}
