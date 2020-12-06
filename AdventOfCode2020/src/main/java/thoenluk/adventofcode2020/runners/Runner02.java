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

import java.util.HashSet;
import thoenluk.adventofcode2020.Ut;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner02 {
    public static void runFirstChallenge(String input) {
        HashSet<PasswordBundle> bundles = parseInput(input);
        int occurences;
        int validPasswords = 0;
        for(PasswordBundle current : bundles) {
            occurences = current.getPassword().replaceAll("[^" + current.getLetter() + "]", "").length();
            if(current.getLowerLimit() <= occurences && occurences <= current.getUpperLimit()) {
                validPasswords++;
            }
        }
        System.out.println("There are " + validPasswords + " valid passwords");
    }
    
    public static void runSecondChallenge(String input) {
        HashSet<PasswordBundle> bundles = parseInput(input);
        int validPasswords = 0;
        for(PasswordBundle current : bundles) {
            if(current.getPassword().charAt(current.getLowerLimit() - 1) == current.getLetter()
                ^ current.getPassword().charAt(current.getUpperLimit() - 1) == current.getLetter()) {
                validPasswords++;
            }
        }
        System.out.println("There are " + validPasswords + " valid passwords");
    }
    
    private static HashSet<PasswordBundle> parseInput(String input) {
        HashSet<PasswordBundle> bundles = new HashSet<>();
        for(String line : input.split("\n")) {
            bundles.add(new PasswordBundle(line));
        }
        return bundles;
    } 
}

class PasswordBundle {
    private final String password;
    private final int lowerLimit, upperLimit;
    private final char letter;
    
    public PasswordBundle(String specification) {
        String[] policyPassword, limitsLetter, limits;
        policyPassword = specification.split(": ");
        password = policyPassword[1];
        limitsLetter = policyPassword[0].split(" ");
        letter = limitsLetter[1].charAt(0);
        limits = limitsLetter[0].split("-");
        lowerLimit = Ut.cachedParseInt(limits[0]);
        upperLimit = Ut.cachedParseInt(limits[1]);
    }

    public String getPassword() {
        return password;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public int getUpperLimit() {
        return upperLimit;
    }
    
    public char getLetter() {
        return letter;
    }
}