package main.java;

import main.kotlin.TailKt;
import org.kohsuke.args4j.*;
import java.util.List;


public class Launcher {
    @Option(name = "-o", usage = "Output File")
    String outFile;

    @Option(name = "-с", usage = "How much symbols will earned", forbids = {"-n"})
    Integer symbols;

    @Option(name = "-n", usage = "How much strings will earned", forbids = {"-c"})
    Integer strings;

    @Argument
    List<String> arguments;

    public static void main(String[] args) {
        new Launcher().parse(args);
    }

    private void parse(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            if (strings == null & symbols == null ) strings = 10;
            if (arguments.isEmpty() || (!arguments.get(0).equals("tail"))) {
                System.err.println("Error entering arguments (for correct input, see the example)");
                System.err.println("tail [options...] arguments...");
                parser.printUsage(System.err);
                System.err.println("\nExample: tail [-c num|-n num] [-o outputname.txt] inputname1.txt inputname2.txt");
                throw new IllegalArgumentException("");
            }
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("tail [options...] arguments...");
            parser.printUsage(System.err);
            System.err.println("\nExample: tail [-c num|-n num] [-o outputname.txt] inputname1.txt inputname2.txt");
        }
        if (arguments.size() == 1 && arguments.get(0).equals("tail")) {
            TailKt.tail4CmdInput(symbols); // т.к. в таком случае на вход только 1 строка
        }
        else {
            List<String> inputFiles = arguments.subList(1, arguments.size());
            TailKt.tail(strings, symbols, outFile, inputFiles);
        }
    }

}
