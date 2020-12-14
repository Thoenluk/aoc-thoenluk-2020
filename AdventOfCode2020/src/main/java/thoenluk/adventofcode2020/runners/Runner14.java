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
import java.util.HashMap;
import java.util.TreeMap;
import thoenluk.adventofcode2020.Ut;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner14 {
    
    public static void runFirstChallenge(String input) {
        HashMap<Integer, Long> memory = new HashMap<>();
        long ORMask = 0, ANDMask = 0;
        int address;
        String[] splitLine;
        String command, value;
        for(String line : input.split("\n")) {
            splitLine = line.split(" = ");
            command = splitLine[0];
            value = splitLine[1];
            if(command.equals("mask")) {
                ORMask = Ut.cachedParseLong(value.replaceAll("X", "0"), 2);
                ANDMask = Ut.cachedParseLong(value.replaceAll("X", "1"), 2);
            } else {
                address = Ut.cachedParseInt(command.replaceAll("[^\\d]", ""));
                memory.put(address, (Ut.cachedParseLong(value) & ANDMask) | ORMask);
            }
        }
        long sum = 0;
        for(Long val : memory.values()) {
            sum += val;
        }
        System.out.println("The total value seems to be " + sum);
    }
    
    public static void runSecondChallenge(String input) {
        TreeMap<Long, Long> memory = new TreeMap<>();
        char[] mask = new char[0];
        ArrayList<Integer> xIndices = new ArrayList<>();
        long address, maskAddress, quantumState, valueNumber;
        String[] splitLine;
        String command, value;
        int i;
        for(String line : input.split("\n")) {
            splitLine = line.split(" = ");
            command = splitLine[0];
            value = splitLine[1];
            if(command.equals("mask")) {
                mask = value.toCharArray();
                xIndices.clear();
                for(i = 0; i < mask.length; i++) {
                    if(mask[i] == 'X') {
                        xIndices.add(mask.length - (i + 1));
                        mask[i] = '0';
                    }
                }
            } else {
                maskAddress = Ut.cachedParseLong(String.valueOf(mask), 2);
                address = Ut.cachedParseLong(command.replaceAll("[^\\d]", ""));
                address = maskAddress | address;
                valueNumber = Ut.cachedParseLong(value);
                for(quantumState = Math.round(Math.pow(2, xIndices.size())) - 1;
                        quantumState >= 0; quantumState--) {
                    for(i = 0; i < xIndices.size(); i++) {
                        // Pitfall on which I spent two hours and you might not have to:
                        // A number literal in Java with no identifiers is an int.
                        // A 32 bit int.
                        // You might see a slight issue when leftshifting a 32 bit int
                        // by 32 or more. Thus it has to be 1l, which is the literal
                        // for a 64 bit long with the value of 1.
                        if((quantumState & (1l << i)) > 0) {
                            address |= (1l << xIndices.get(i));
                        } else {
                            address &= ~(1l << xIndices.get(i));
                        }
                    }
                    memory.put(address, valueNumber);
                }
            }
        }
        long sum = 0;
        for(Long val : memory.values()) {
            sum += val;
        }
        System.out.println("The total value seems to be " + sum);
    }
}
