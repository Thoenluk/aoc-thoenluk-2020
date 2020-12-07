/*
 * Copyright (C) 2020 Lukas Thöni lukas.thoeni@gmx.ch
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package thoenluk.adventofcode2020.runners;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner06 {
    public static void runFirstChallenge(String input) {
        int YES = 0;
        for(String group : input.split("\n\n")) {
            YES += group.replaceAll("\n", "").replaceAll("(.)(?=.*\\1)", "").length();
            // Regex: The cause of, and solution to, all of life's problems.
        }
        System.out.println("There appear to be a total of " + YES + " yes answers. Why are we on a plane filling an English survey while no one speaks English?");
    }
    
    public static void runSecondChallenge(String input) {
        int YES = 0;
        long newlines;
        for(String group : input.split("\n\n")) {
            newlines = group.chars().filter(ch -> ch == '\n').count();
            YES += group.replaceAll("\n", "").replaceAll("(.)(?!(.*\\1){" + newlines + "})", "").length();
            // Regex: The cause of, and solution to, all of life's problems. Again.
        }
        System.out.println("There appear to be a total of " + YES + " shared yes answers. It appears I don't speak English either. How unfortunate, truly.");
    }
}
