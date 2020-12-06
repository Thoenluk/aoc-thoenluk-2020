package thoenluk.adventofcode2020;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class AOC20Runner {
    private static final Scanner USER = new Scanner(System.in);
    
    public static void main(String[] args) {
        // What I'm doing below is some unholy JavaScript-esque voodoo.
        // For your own sanity, forget what is here and stick to a switch-case for this
        // kind of use case.
        // This is the result of a bored evening before the advent actually started.
        // We learn: Only code under time constraints because that will keep you doing
        // the things actually asked of you.
        TreeMap<String, Method> solutions = new TreeMap<>();
        Class<?> runner;
        try {
            // Get all names of folders containing input files
            String[] folders = (new File(".")).list((File dir, String name) -> !name.equals("src")
                    && !name.equals("target")
                    && new File(dir, name).isDirectory());
            // Get the class corresponding to them and gather the static run methods
            // from them. For the moment they are all named after a scheme and leave
            // instantation to the running methods.
            // I could do otherwise, but it would be insane.
            for (String folder : folders) {
                runner = Class.forName("thoenluk.adventofcode2020.runners.Runner" + folder);
                solutions.put(folder + "0", runner.getMethod("runFirstChallenge", String.class));
                solutions.put(folder + "1", runner.getMethod("runSecondChallenge", String.class));
            }
        } catch (NoSuchMethodException nsme) {
            System.out.println("Failed loading methods. One of those methods doesn't exist. Get good son.\n" + nsme.getMessage());
            System.exit(1);
        } catch (ClassNotFoundException ex) {
            System.out.println("Failed loading classes. One of those runner classes doesn't exist. Do better.\n" + ex.getMessage());
        }
        System.out.println("Welcome to the magniflorious Advent of Code solution runner.");
        System.out.println("Type the day and index of the challenge you want to see solved.\n"
                + "Pad values less than 10 with a zero and remember indices start at 0.\n"
                + "So, the first challenge of day 3 would be 030. The second of day 17 would be 171.\n"
                + "Type anything else to be berated and quit the program.");
        String solutionIdentifier = USER.next();
        try {
            if(!solutions.containsKey(solutionIdentifier)) {
                System.out.println("That's not a valid solution, dummy!\n"
                        + "Number of day, with a 0 before it if need be, followed by 0 or 1. Three digits. That's what you shall type.");
                return;
            } else {
                // Read input from corresponding file
                String actualDay = solutionIdentifier.substring(0, 2);
                String input = Files.readString(Paths.get(actualDay, "\\input.txt"));
                // Run the solution on the provided input. What it does to generate output is not specified.             
                solutions.get(solutionIdentifier).invoke(null, input);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            System.out.println("I don't even know how this happened.\n"
                    + "Maybe you can make sense of this " + e.getClass().getName() + "\n"
                    + e.getMessage());
        } catch (IOException ex) {
            System.out.println("Couldn't find input. Are you sure it's in " + solutionIdentifier.substring(0, 2) + "\\input.txt?");
        }
    }
    
    public Scanner getScanner() {
        return USER;
    }
}