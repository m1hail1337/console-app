import kotlin.Pair;
import main.kotlin.TailKt;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Launcher {
    @Option(name = "-o", usage = "Output File")
    File outFile;

    @Option(name = "-c", usage = "How much symbols will earned", forbids = {"-n"})
    Integer symbols;

    @Option(name = "-n", usage = "How much strings will earned", forbids = {"-c"})
    Integer strings;

    @Argument
    List<File> inputFiles;

    public static void main(String[] args) {
        new Launcher().parse(args);
    }


    public void parse(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getLocalizedMessage());
            System.err.println("\nExample: tail [-c num|-n num] [-o outputname.txt] inputname1.txt inputname2.txt");
            System.exit(-1);
        }

        if (strings == null && symbols == null ) strings = 10;

        List<Pair<BufferedReader, String>> inputs = new ArrayList<>();

        if (inputFiles == null || inputFiles.isEmpty()) {
            inputs.add(new Pair<>(new BufferedReader(new InputStreamReader(System.in)), null));
        } else {
            // проверка на существование входных файлов
            for (File file: inputFiles) {
                if (!file.isFile()) {
                    System.err.println(file.getPath() + " does not exist!");
                    System.exit(-1);
                }

                try {
                    inputs.add(new Pair<>(new BufferedReader(new FileReader(file)), file.getName()));
                } catch (FileNotFoundException e) {
                    System.err.println(e.getLocalizedMessage());
                    System.exit(-1);
                }
            }
            if (inputs.size() == 1) inputs.set(0, new Pair<>(inputs.get(0).component1(), null));
        }

        BufferedWriter writer = null;
        try {
            writer = outFile == null ? new BufferedWriter(new OutputStreamWriter(System.out)) : new BufferedWriter(new FileWriter(outFile));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
            System.exit(-1);
        }

        TailKt.tail(strings, symbols, writer, inputs);

        if (outFile != null) {
            try {
                writer.close();
            } catch (IOException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }

        if (inputFiles != null && !inputFiles.isEmpty()) {
            try {
                for (Pair<BufferedReader, String> input : inputs) {
                    input.component1().close();
                }
            } catch (IOException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
    }
}
