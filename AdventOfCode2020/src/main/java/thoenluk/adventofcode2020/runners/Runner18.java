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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import thoenluk.adventofcode2020.Ut;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner18 {
    public static void runFirstChallenge(String input) {
        int start, end;
        long result, total = 0;
        for(String line : input.split("\n")) {
            while((start = line.lastIndexOf("(")) > -1) {
                for(end = start + 1; end < line.length() && line.charAt(end) != ')'; end++);
                result = compute(line.substring(start + 1, end));
                line = line.substring(0, start) + result + line.substring(end + 1);
            }
            total += compute(line);
        }
        System.out.println("Methinks the total is " + total);
    }
    
    public static void runSecondChallenge(String input) {
        int start, end;
        long result, total = 0;
        for(String line : input.split("\n")) {
            while((start = line.lastIndexOf("(")) > -1) {
                for(end = start + 1; end < line.length() && line.charAt(end) != ')'; end++);
                result = computeAdditionFirst(line.substring(start + 1, end));
                line = line.substring(0, start) + result + line.substring(end + 1);
            }
            total += computeAdditionFirst(line);
        }
        System.out.println("Methinks the total is " + total);
    }
    
    private static long compute(String instructions) {
        long result;
        String[] parameters = instructions.split(" ");
        result = Ut.cachedParseLong(parameters[0]);
        for(int i = 1; i < parameters.length; i += 2) {
            if(parameters[i].equals("+")) {
                result += Ut.cachedParseLong(parameters[i + 1]);
            } else {
                result *= Ut.cachedParseLong(parameters[i + 1]);
            }
        }
        return result;
    }
    
    private static long computeAdditionFirst(String instructions) {
        long result;
        String[] parameters = instructions.split(" ");
        result = Ut.cachedParseLong(parameters[0]);
        ArrayList<String> asList = new ArrayList<>(Arrays.asList(parameters));
        int plusIndex, i;
        while((plusIndex = asList.indexOf("+")) > -1) {
            // There are no parentheses in the instructions string as they are extracted
            // in runSecondChallenge. Thus any operator is surrounded by two literals.
            result = Ut.cachedParseLong(asList.get(plusIndex - 1)) + Ut.cachedParseLong(asList.get(plusIndex + 1));
            // Set first argument to result
            asList.set(plusIndex - 1, Long.toString(result));
            // Remove the + and second argument (which slides to plusIndex after removal
            asList.remove(plusIndex);
            asList.remove(plusIndex);
        }
        result = 1;
        for(i = 0; i < asList.size(); i+= 2) {
            result *= Ut.cachedParseLong(asList.get(i));
        }
        return result;
    }
}
