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
package thoenluk.adventofcode2020;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner01 {
    // Keep expenses in a sortable list to optimise of second challenge and make iteration easier.
    private static final ArrayList<Integer> expenseReport = new ArrayList<>();
    // Also write all numbers to set for O(logn) .contains calls.
    private static final TreeSet<Integer> presentNumbers = new TreeSet<>();
    // Either one could be used alone to do the challenge, but I felt like golfing.
    
    public static void runFirstChallenge(String input) {
        parseInput(input);
        for(Integer expense : expenseReport) {
            if(presentNumbers.contains(2020 - expense)) {
                System.out.println("The answer shall be " + expense * (2020 - expense));
                return;
            }
        }
    }
    
    public static void runSecondChallenge(String input) {
        parseInput(input);
        expenseReport.sort(null);
        int firstIndex = 0;
        // ArrayList.get is O(1) time, so there is not much point to caching the result.
        for(Integer expense : expenseReport) {
            for(int secondIndex = firstIndex + 1;
                    secondIndex < expenseReport.size() && expense + expenseReport.get(secondIndex) < 2020;
                    secondIndex++) {
                if(presentNumbers.contains(2020 - expense - expenseReport.get(secondIndex))) {
                    System.out.println("The answer shall be " + expense 
                            * expenseReport.get(secondIndex) 
                            * (2020 - expense - expenseReport.get(secondIndex)));
                    return;
                }
            }
        }
    }
    
    private static void parseInput(String input) {
        for(String expenseLine : input.split("\n")) {
            expenseReport.add(Ut.cachedParseInt(expenseLine));
        }
        presentNumbers.addAll(expenseReport);
    }
}
