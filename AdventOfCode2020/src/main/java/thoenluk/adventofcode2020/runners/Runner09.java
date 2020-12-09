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
import thoenluk.adventofcode2020.Ut;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner09 {
    
    public static void runFirstChallenge(String input) {
        ArrayList<Long> numbers = new ArrayList<>();
        for(String line : input.split("\n")) {
            numbers.add(Ut.cachedParseLong(line));
        }
        long current, firstSummand;
        int currentIndex, firstSummandIndex, secondSummandIndex = 0;
        for(currentIndex = 25; currentIndex < numbers.size(); currentIndex++) {
            current = numbers.get(currentIndex);
            checkSum: // Aren't I clever.
            for(firstSummandIndex = currentIndex - 25; firstSummandIndex < currentIndex; firstSummandIndex++) {
                firstSummand = numbers.get(firstSummandIndex);
                for(secondSummandIndex = firstSummandIndex + 1; secondSummandIndex < currentIndex; secondSummandIndex++) {
                    if(firstSummand + numbers.get(secondSummandIndex) == current) {
                        break checkSum;
                    }
                }
            }
            if(firstSummandIndex == secondSummandIndex) {
                System.out.println("I believe the first non-sum number is " + current);
            }
        }
    }
    
    public static void runSecondChallenge(String input) {
        // The invalid number is 400480901. The answer to the crossword puzzle is FRIES.
        // I was a little concerned this approach would take a while. It took milliseconds.
        // Computers flop fast these days.
        ArrayList<Long> numbers = new ArrayList<>();
        for(String line : input.split("\n")) {
            numbers.add(Ut.cachedParseLong(line));
        }
        long target = 400480901;
        int currentIndex, nextIndex;
        long least, most, sum, next;
        for(currentIndex = 0; currentIndex < numbers.size(); currentIndex++) {
            least = Long.MAX_VALUE;
            most = Long.MIN_VALUE;
            sum = 0;
            for(nextIndex = currentIndex; sum < target; nextIndex++) {
                next = numbers.get(nextIndex);
                sum += next;
                least = next < least ? next : least;
                most = next > most ? next : most;
            }
            if(sum == target) {
                System.out.println("The least value is " + least + " and the most is "
                        + most + " which sums up to " + (least + most));
                System.out.println("It's not asked for, but for the record: The sequence"
                        + " is line " + (currentIndex + 1) + " to " + nextIndex);
                break;
            }
        }
    }
    
}
