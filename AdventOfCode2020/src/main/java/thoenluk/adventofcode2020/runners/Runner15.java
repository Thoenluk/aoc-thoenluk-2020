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

import java.util.Set;
import java.util.TreeMap;
import thoenluk.adventofcode2020.Ut;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner15 {
    public static void runFirstChallenge(String input) {
        TreeMap<Long, Long> lastIndex = new TreeMap<>();
        Set<Long> keySet = lastIndex.keySet();
        long current = 0, next = 0, index = 0;
        for(String number : input.split(",")) {
            current = Ut.cachedParseLong(number);
            lastIndex.put(current, index);
            index++;
        }
        for(; index < 2020; index++) {
            current = next;
            if(keySet.contains(current)) {
                next = index - lastIndex.get(current);
            } else {
                next = 0;
            }
            lastIndex.put(current, index);
        }
        System.out.println("It's probably " + current);
    }
    
    public static void runSecondChallenge(String input) {
        TreeMap<Long, Long> lastIndex = new TreeMap<>();
        Set<Long> keySet = lastIndex.keySet();
        long current = 0, next = 0, index = 0;
        for(String number : input.split(",")) {
            current = Ut.cachedParseLong(number);
            lastIndex.put(current, index);
            index++;
        }
        for(; index < 30000000; index++) {
            current = next;
            if(keySet.contains(current)) {
                next = index - lastIndex.get(current);
            } else {
                next = 0;
            }
            lastIndex.put(current, index);
        }
        System.out.println("It's probably " + current);
    }
}
