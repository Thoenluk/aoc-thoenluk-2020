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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import thoenluk.adventofcode2020.Ut;

/**
 * This code is garbage product of extremely limited time and there are many more elegant implementations.
 * But given it works and I have to move on, I'm leaving it as such.
 * It should probably be broken into several functions even under the current runner model.
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner16 {
    public static void runFirstChallenge(String input) {
        TreeMap<String, int[]> restrictions = new TreeMap<>();
        String[] blocks = input.split("\n\n");
        String[] current;
        int[] limits;
        int i;
        
        for(String line : blocks[0].split("\n")) {
            current = line.split(": ");
            limits = new int[4];
            restrictions.put(current[0], limits);
            current = current[1].replaceAll("[^\\d]+", ",").split(",");
            for(i = 0; i < 4; i++) {
                limits[i] = Ut.cachedParseInt(current[i]);
            }
        }
        
        int sum = 0, currentValue;
        Collection<int[]> ranges = restrictions.values();
        boolean matchedAny;
        
        current = blocks[2].split("\n");
        for(i = 1; i < current.length; i++) {
            for(String number : current[i].split(",")) {
                currentValue = Ut.cachedParseInt(number);
                matchedAny = false;
                for(int[] range : ranges) {
                    matchedAny |= (currentValue >= range[0] && currentValue <= range[1])
                        || (currentValue >= range[2] && currentValue <= range[3]);
                }
                if(!matchedAny) {
                    sum += currentValue;
                }
            }
        }
        System.out.println("TSER appears to be " + sum);
    }
    
    public static void runSecondChallenge(String input) {
        TreeMap<String, int[]> restrictions = new TreeMap<>();
        String[] blocks = input.split("\n\n");
        String[] current;
        int[] limits;
        int[] currentRange;
        int i, j;
        
        for(String line : blocks[0].split("\n")) {
            current = line.split(": ");
            limits = new int[4];
            restrictions.put(current[0], limits);
            current = current[1].replaceAll("[^\\d]+", ",").split(",");
            for(i = 0; i < 4; i++) {
                limits[i] = Ut.cachedParseInt(current[i]);
            }
        }
        
        int currentValue;
        Collection<int[]> ranges = restrictions.values();
        boolean matchedAny, valid;
        Set<String> fields = restrictions.keySet();
        ArrayList<String>[] possibleFields = new ArrayList[fields.size()];
        ArrayList<String> validTickets = new ArrayList<>();
        Iterator<String> it;
        
        for(i = 0; i < possibleFields.length; i++) {
            possibleFields[i] = new ArrayList<>(fields);
        }
        
        current = blocks[2].split("\n");
        for(i = 1; i < current.length; i++) {
            valid = true;
            for(String number : current[i].split(",")) {
                currentValue = Ut.cachedParseInt(number);
                matchedAny = false;
                for(int[] range : ranges) {
                    matchedAny |= (currentValue >= range[0] && currentValue <= range[1])
                        || (currentValue >= range[2] && currentValue <= range[3]);
                }
                valid &= matchedAny;
            }
            if(valid) {
                validTickets.add(current[i]);
            }
        }
        
        
        for(String ticket : validTickets) {
            for(i = 0; i < possibleFields.length; i++) {
                current = ticket.split(",");
                currentValue = Ut.cachedParseInt(current[i]);
                it = possibleFields[i].iterator();

                while(it.hasNext()) {
                    currentRange = restrictions.get(it.next());
                    if(!((currentValue >= currentRange[0] && currentValue <= currentRange[1])
                    || (currentValue >= currentRange[2] && currentValue <= currentRange[3]))) {
                        it.remove();
                    }
                }
            }
        }
        // At this point, you could really just print out the contents of possibleFields
        // and solve it yourself. To do this properly, you would need to implement
        // backtracking search or a similar algorithm, to deal with one field having
        // multiple plausible labels with all but one solution being eventually, but
        // not immediately, impossible.
        // But they were lazy and made one field unique in its label, the next have
        // only that label and another as possibilities, and so on. So we abuse that.        
        ArrayList<List<String>> sorted = new ArrayList<>(Arrays.asList(possibleFields));
        sorted.sort((List e1, List e2) -> {
            return e1.size() - e2.size();
        });
        
        for(i = 0; i < sorted.size(); i++) {
            for(j = i + 1; j < sorted.size(); j++) {
                sorted.get(j).remove(sorted.get(i).get(0));
            }
        }
        
        String[] myTicket = blocks[1].split(",");
        long product = 1;
        
        for(i = 0; i < myTicket.length; i++) {
            if(possibleFields[i].get(0).contains("departure")) {
                product *= Ut.cachedParseInt(myTicket[i]);
            }
        }
        
        System.out.println("The desired product seems to be " + product);
    }
}
