package thoenluk.adventofcode2020;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class AOC20Runner {    
    public static void main(String[] args) {
        // What I'm doing below is some unholy JavaScript voodoo. But this is fancy
        // though not really any more compact. A switch-case would likely be better
        // and I am literally only doing this because I can (and to avoid using a switch-case)
        // It would most certainly be possible to fill the map automatically with a set
        // method from each folder's file. But that would be so mad that I am pulling
        // the cord for my own sanity now.
        TreeMap<String, Method> solutions = new TreeMap<>();
        Scanner user = new Scanner(System.in);
        try {
            solutions.put("000", AOC20Runner.class.getMethod("testMethod", String.class));
            solutions.put("010", AOC20Runner.class.getMethod("testMethod", String.class));
        } catch (NoSuchMethodException nsme) {
            System.out.println("Failed loading methods. One of those methods doesn't exist. Get good son.\n" + nsme.getMessage());
            System.exit(1);
        }
        System.out.println("Welcome to the magniflorious Advent of Code solution runner.");
        System.out.println("Type the day and index of the challenge you want to see solved.\n"
                + "Pad values less than 10 with a zero and remember indices start at 0.\n"
                + "So, the first challenge of day 3 would be 030. The second of day 17 would be 171.\n"
                + "Type anything else to be berated and quit the program.");
        String solutionIdentifier = user.next();
        try {
            if(!solutions.containsKey(solutionIdentifier)) {
                System.out.println("That's not a valid solution, dummy!\n"
                        + "Number of day, with a 0 before it if need be, followed by 0 or 1. That's what you shall type.");
            } else {
                // Read input from corresponding file
                String actualDay = solutionIdentifier.substring(0, 2);
                String input = Files.readString(Paths.get(actualDay, "\\input.txt"));
                // Run the solution on the provided input What it does to generate output is not specified.             
                solutions.get(solutionIdentifier).invoke(null, input);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            System.out.println("I don't even know how this happened.\n" + e.getMessage());
        } catch (IOException ex) {
            System.out.println("Couldn't find input. Are you sure it's in " + solutionIdentifier.substring(0, 2) + "\\input.txt?");
        }
    }
    
    public static void testMethod(String input) {
        System.out.println("This is the test method called with input " + input);
    }
}