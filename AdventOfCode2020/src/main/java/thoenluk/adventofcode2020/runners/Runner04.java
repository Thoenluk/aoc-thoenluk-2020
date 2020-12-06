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

import java.util.HashMap;
import java.util.Set;
import thoenluk.adventofcode2020.Ut;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner04 {
    public static void runFirstChallenge(String input) {
        int validPassports = 0;
        for(String passport : input.split("\n\n")) {
            if(new Passport(passport).isValid()) {
                validPassports++;
            }
        }
        System.out.println("With totally legit new rules, " + validPassports + " passports are legal.");
    }
    
    public static void runSecondChallenge(String input) {
        int validPassports = 0;
        for(String passport : input.split("\n\n")) {
            if(new Passport(passport).isComplete()) {
                validPassports++;
            }
        }
        System.out.println("With totally legit new rules, " + validPassports + " passports are legal.");
    }
}

class Passport {
    private enum Attribute {
        BIRTHYEAR("byr"),
        ISSUEYEAR("iyr"),
        EXPIRATIONYEAR("eyr"),
        HEIGHT("hgt"),
        HAIRCOLOUR("hcl"),
        EYECOLOUR("ecl"),
        PASSPORTID("pid"),
        COUNTRYID("cid");
        
        public final String label;
        
        private Attribute(String label) {
            this.label = label;
        }
    }
    private static final Set<String> allowedEyeColours = Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
    
    private final HashMap<String, String> attributes = new HashMap<>();
    
    public Passport(String input) {
        String[] tokens = input.replaceAll("\n", " ").split(" ");
        String[] labelValue;
        for(String token : tokens) {
            labelValue = token.split(":");
            attributes.put(labelValue[0], labelValue[1]);
        }
    }
    
    public boolean isValid() {
        boolean isValid = true;
        for(Attribute att : Attribute.values()) {
            if(!att.equals(Attribute.COUNTRYID)) {
                isValid &= attributes.containsKey(att.label);
            }
        }
        return isValid;
    }
    
    public boolean isComplete() {
        boolean isComplete = this.isValid();
        if(isComplete) {
            try {
                int birthYear = Ut.cachedParseInt(attributes.get(Attribute.BIRTHYEAR.label));
                isComplete &= 1920 <= birthYear && birthYear <= 2002;

                int issueYear = Ut.cachedParseInt(attributes.get(Attribute.ISSUEYEAR.label));
                isComplete &= 2010 <= issueYear && issueYear <= 2020;

                int expirationYear = Ut.cachedParseInt(attributes.get(Attribute.EXPIRATIONYEAR.label));
                isComplete &= 2020 <= expirationYear && expirationYear <= 2030;
                
                String heightString = attributes.get(Attribute.HEIGHT.label);
                int height = Ut.cachedParseInt(heightString.substring(0, heightString.length() - 2));
                if(heightString.contains("in")) {
                    isComplete &= 59 <= height && height <= 76;
                } else {
                    isComplete &= 150 <= height && height <= 193;
                }
                
                isComplete &= attributes.get(Attribute.HAIRCOLOUR.label).matches("#[\\da-f]{6}");
                
                isComplete &= allowedEyeColours.contains(attributes.get(Attribute.EYECOLOUR.label));
                
                isComplete &= attributes.get(Attribute.PASSPORTID.label).matches("[\\d]{9}");
            } catch (NumberFormatException nfe) {
                return false;
            }
        }
        return isComplete;
    }
}