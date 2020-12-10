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
import java.util.TreeMap;
import thoenluk.adventofcode2020.Ut;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner10 {
    
    public static void runFirstChallenge(String input) {
        ArrayList<Integer> numbers = new ArrayList<>();
        int ones = 0, threes = 0;
        for(String line : input.split("\n")) {
            numbers.add(Ut.cachedParseInt(line));
        }
        numbers.sort(null);
        numbers.add(0, 0); // Yes, I fell into the gotcha of not adding these at first.
        numbers.add(numbers.size(), numbers.get(numbers.size() - 1) + 3);
        for(int i = 1; i < numbers.size(); i++) {
            switch(numbers.get(i) - numbers.get(i - 1)) {
                case 1:
                    ones++;
                    break;
                case 2:
                    break;
                case 3:
                    threes++;
                    break;
                default:
                    System.out.println("what");
                    break;
            }
        }
        System.out.println("I take it the number in question is " + ones + " * " 
                + threes + " = " + (ones * threes));
    }
    
    public static void runSecondChallenge(String input) {
        ArrayList<Integer> numbers = new ArrayList<>();
        TreeMap<Integer, Long> paths = new TreeMap<>();
        int target, current, i, child;
        long pathsFromCurrent;
        for(String line : input.split("\n")) {
            numbers.add(Ut.cachedParseInt(line));
        }
        numbers.sort(null);
        numbers.add(0, 0);
        target = numbers.get(numbers.size() - 1) + 3;
        numbers.add(numbers.size(), target);
        paths.put(target, Long.valueOf(1));
        for(i = numbers.size() - 2; i >= 0; i--) {
            current = numbers.get(i);
            pathsFromCurrent = 0;
            for(child = i + 1; child - i <= 3 && child < numbers.size() && numbers.get(child) - current <= 3; child++) {
                pathsFromCurrent += paths.get(numbers.get(child));
            }
            paths.put(current, pathsFromCurrent);
        }
        System.out.println("I believe there are " + paths.get(0) + " valid paths.");
    }
}
