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

import java.util.Arrays;
import java.util.TreeMap;
import thoenluk.adventofcode2020.Ut;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner19 {
    
    private static String[] rules = new String[0];
    private static TreeMap<Integer, String> parsedRules = new TreeMap<>();
    
    public static void runFirstChallenge(String input) {
        String[] blocks = input.split("\n\n");
        String[] lines = blocks[0].split("\n");
        rules = new String[lines.length];
        String[] current;
        for(String line : lines) {
            current = line.split(": ");
            rules[Ut.cachedParseInt(current[0])] = current[1];
        }
        // I could waste time putting this together as a check inside the program.
        // I COULD.
        // Or I could be certain that every unparsed rule is made only of references
        // and | signs.
        parsedRules.put(39, "b");
        parsedRules.put(45, "a");
        System.out.println(parse(0));
        int count = 0;
        for(String line : blocks[1].split("\n")) {
            count = line.matches(parsedRules.get(0)) ? count + 1 : count;
        }
        System.out.println("There seem to be " + count + " messages that match.");
    }
    
    public static void runSecondChallenge(String input) {
        String[] blocks = input.split("\n\n");
        String[] lines = blocks[0].split("\n");
        rules = new String[lines.length];
        String[] current;
        for(String line : lines) {
            current = line.split(": ");
            rules[Ut.cachedParseInt(current[0])] = current[1];
        }
        // I could waste time putting this together as a check inside the program.
        // I COULD.
        // Or I could be certain that every unparsed rule is made only of references
        // and | signs.
        parsedRules.put(39, "b");
        parsedRules.put(45, "a");
        System.out.println(parseWithLoops(0));
        int count = 0;
        for(String line : blocks[1].split("\n")) {
            count = line.matches(parsedRules.get(0)) ? count + 1 : count;
        }
        System.out.println("There seem to be " + count + " messages that match.");
    }
    
    private static String parse(int index) {
        if(!parsedRules.containsKey(index)) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for(String token : rules[index].split(" ")) {
                sb.append(token.equals("|") ? token : parse(Ut.cachedParseInt(token)));
            }
            sb.append(")");
            parsedRules.put(index, sb.toString());
        }
        return parsedRules.get(index);
    }
    
    private static String parseWithLoops(int index) {
        if(!parsedRules.containsKey(index)) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            if(index == 11) {
                // Determined by the age old method of "Increase it until the output
                // stops changing."
                // You could do this programmatically quite easily; Parse the rules,
                // leave a placeholder that you can find in place of rule 11,
                // then take the final regex and string replace this expression for
                // the placeholder into a new string, then match with that. You can
                // incrementally increase the number of expressions added until the
                // output stabilises.
                // Why didn't I? Because I got raid in two hours and I can't be bothered.
                for(int i = 1; i <= 4; i++) {
                    sb.append(parseWithLoops(42));
                    sb.append("{").append(i).append("}");
                    sb.append(parseWithLoops(31));
                    sb.append("{").append(i).append("}|");
                }
                sb.deleteCharAt(sb.length() - 1);
            } else {
                for(String token : rules[index].split(" ")) {
                    sb.append(token.equals("|") ? token : parseWithLoops(Ut.cachedParseInt(token)));
                }
            }
            sb.append(")");
            if(index == 8) {
                sb.append("+");
            }
            parsedRules.put(index, sb.toString());
        }
        return parsedRules.get(index);
    }
}
