import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Tester {

    private static final String ACTUAL_FILENAME_PREFIX = "output_actual_";
    private static final String EXPECTED_FILENAME_PREFIX = "output_expected_";

    // Put input filenames to test here
    private static final String[] INPUT_FILES = new String[] {
            "input1",
            "input2"
    };

    public static void main(String[] args) {
        PrintStream originalOut = new PrintStream(System.out);

        for (String inputFilename : INPUT_FILES) {
            System.out.println("-----------------------------------");
            System.out.println("Input file:\t\t\t\t\t" + inputFilename + ".txt");
            System.out.println("Output file for expected:\t" + EXPECTED_FILENAME_PREFIX + inputFilename + ".txt");
            System.out.println("Output file for actual:\t\t" + ACTUAL_FILENAME_PREFIX + inputFilename + ".txt");
            System.out.println();

            FileInputStream input1 = null;
            FileInputStream input2 = null;
            try {
                input1 = new FileInputStream(inputFilename + ".txt");
                input2 = new FileInputStream(inputFilename + ".txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            PrintStream actualOut = null;
            PrintStream expectedOut = null;
            try {
                actualOut = new PrintStream(ACTUAL_FILENAME_PREFIX + inputFilename + ".txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                expectedOut = new PrintStream(EXPECTED_FILENAME_PREFIX + inputFilename + ".txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            System.setIn(input1);
            System.setOut(actualOut);
            BruteForce.main(null);

            System.setIn(input2);
            System.setOut(expectedOut);
            Solution.main(null);

            System.setOut(originalOut);

            BufferedReader actual = null;
            BufferedReader expected = null;
            try {
                actual = new BufferedReader(new FileReader(ACTUAL_FILENAME_PREFIX + inputFilename + ".txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                expected = new BufferedReader(new FileReader(EXPECTED_FILENAME_PREFIX + inputFilename + ".txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (actual == null || expected == null) {
                System.out.println("Problem accessing actual/expected files");
                System.exit(-1);
            }

            String lineFromActual = null, lineFromExpected = null;
            int lineNum = 1;

            while (true) {
                try {
                    lineFromActual = actual.readLine();
                    lineFromExpected = expected.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (lineFromActual != null && lineFromExpected != null) {
                    if (!lineFromActual.equals(lineFromExpected)) {
                        System.out.println("Difference in line " + lineNum + ":");
                        System.out.println("\tExpected:\t" + lineFromExpected);
                        System.out.println("\tActual:\t\t" + lineFromActual);
                    }
                }
                else if (lineFromActual == null && lineFromExpected == null) {
                    System.out.println("Outputs are equal!");
                    break;
                }
                else {
                    System.out.println("One of the files are shorter than the other.");
                    break;
                }
            }

            try {
                actual.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                expected.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
