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
public class Runner13 {
    public static void runFirstChallenge(String input) {
        // For the record, I almost did this with a calculator. You don't need code.
        String[] lines = input.split("\n");
        int departure = Ut.cachedParseInt(lines[0]);
        int lowestWaitTime = Integer.MAX_VALUE;
        int ID, result = 0, waitTime;
        for(String busID : lines[1].split(",")) {
            if(!busID.equals("x")) {
                ID = Ut.cachedParseInt(busID);
                waitTime = ID - (departure % ID);
                if(waitTime < lowestWaitTime) {
                    lowestWaitTime = waitTime;
                    result = ID * waitTime;
                }
            }
        }
        System.out.println("I am best off taking a bus in a way such that the result is "
                + result);
    }
    
    public static void runSecondChallenge(String input) {
        /*
        https://en.wikipedia.org/wiki/Chinese_remainder_theorem
        Essentially, what we want is a number n such that for each bus, the departure
        time from n equals the bus's index in the list.
        
        Gotcha trap here: n mod ID equals the time SINCE departure. We want time
        TO departure = index, so we must set the remainder equal to ID - index
        
        After that, it's simply solving the CRT. Find the smallest possible number
        such that it has been (for the example) 7 minutes since 7's departure, 12
        since 13's, etc.
        */
        int ID;
        long product = 1, sum = 0;
        String[] busses = input.split("\n")[1].split(",");
        ArrayList<Integer> IDs = new ArrayList<>(), remainders = new ArrayList<>();
        long partial;
        for(int i = 0; i < busses.length; i++) {
            if(!busses[i].equals("x")) {
                ID = Ut.cachedParseInt(busses[i]);
                IDs.add(ID);
                remainders.add(ID - i);
                product *= ID;
            }
        }
        for(int i = 0; i < IDs.size(); i++) {
            partial = product / IDs.get(i);
            sum += partial * computeInverse(partial, IDs.get(i)) * remainders.get(i);
        }
        System.out.println("T appears to equal " + (sum % product));
    }
    
    // Yes, I copied this code. Shame me. The algorithm is ages old, like ancient Greeks old.
    // I feel that trying to reinvent it wouldn't be productive.
    private static long computeInverse(long a, long b){
        long m = b, t, q;
        long x = 0, y = 1; 
        if (b == 1){
            return 0;
        }
        // Apply extended Euclid Algorithm
        while (a > 1){
            // q is quotient
            q = a / b;
            t = b;
            // now proceed same as Euclid's algorithm
            b = a % b;
            a = t;
            t = x;
            x = y - q * x;
            y = t;
        }
        // Make x1 positive
        if (y < 0) {
            y += m;
        }
        return y;
    }
}
