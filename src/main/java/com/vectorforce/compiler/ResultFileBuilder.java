package main.java.com.vectorforce.compiler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ResultFileBuilder {
    public static void generate(String context, String methods) throws IOException {
        String mainClass = "public class Main {";
        mainClass += "\n\tpublic static void main(String[] args) {";

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Main.java"));

        /*
        * Create file and write main form
        * */
        bufferedWriter.write(mainClass);

        /*
        * Write context
        * */
        bufferedWriter.append(context);
        bufferedWriter.append("\n\t}");

        /*
        * Write methods
        * */
        bufferedWriter.append("\n");
        bufferedWriter.append(methods);

        bufferedWriter.append("\n}");

        bufferedWriter.close();
    }
}
