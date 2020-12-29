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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner21 {
    public static void runFirstChallenge(String input) {
        String[] lines = input.split("\n");
        String[] ingredientsAndAllergens, allergens;
        HashSet<String> ingredients = new HashSet<>();
        HashMap<String, Integer> allIngredients = new HashMap<>();
        HashMap<String, HashSet<String>> possibleIngredients = new HashMap<>();
        int safeOccurences = 0;
        for(String line : lines) {
            ingredientsAndAllergens = line.split("\\(contains ");
            ingredients.clear();
            ingredients.addAll(Arrays.asList(ingredientsAndAllergens[0].split(" ")));
            for(String ingredient : ingredients) {
                if(!allIngredients.containsKey(ingredient)) {
                    allIngredients.put(ingredient, 0);
                }
                allIngredients.put(ingredient, allIngredients.get(ingredient) + 1);
            }
            allergens = ingredientsAndAllergens[1].substring(0, ingredientsAndAllergens[1].length() - 1).split(", ");
            for(String allergen : allergens) {
                if(!possibleIngredients.containsKey(allergen)) {
                    possibleIngredients.put(allergen, new HashSet<>());
                    possibleIngredients.get(allergen).addAll(ingredients);
                } else {
                    possibleIngredients.get(allergen).removeIf((String element) -> {
                        return !ingredients.contains(element);
                    });
                }
            }
        }
        for(HashSet<String> possibleHazards : possibleIngredients.values()) {
            for(String hazard : possibleHazards) {
                allIngredients.remove(hazard);
            }
        }
        for(Integer occurences : allIngredients.values()) {
            safeOccurences += occurences;
        }
        System.out.println("According to this FDA pamphlet, there are " + safeOccurences + " occurences of safe ingredients.");
    }
    
    public static void runSecondChallenge(String input) {
        String[] lines = input.split("\n");
        String[] ingredientsAndAllergens, allergens;
        HashSet<String> ingredients = new HashSet<>();
        HashMap<String, Integer> allIngredients = new HashMap<>();
        HashMap<String, HashSet<String>> possibleIngredients = new HashMap<>();
        
        for(String line : lines) {
            ingredientsAndAllergens = line.split("\\(contains ");
            ingredients.clear();
            ingredients.addAll(Arrays.asList(ingredientsAndAllergens[0].split(" ")));
            for(String ingredient : ingredients) {
                if(!allIngredients.containsKey(ingredient)) {
                    allIngredients.put(ingredient, 0);
                }
                allIngredients.put(ingredient, allIngredients.get(ingredient) + 1);
            }
            allergens = ingredientsAndAllergens[1].substring(0, ingredientsAndAllergens[1].length() - 1).split(", ");
            for(String allergen : allergens) {
                if(!possibleIngredients.containsKey(allergen)) {
                    possibleIngredients.put(allergen, new HashSet<>());
                    possibleIngredients.get(allergen).addAll(ingredients);
                } else {
                    possibleIngredients.get(allergen).removeIf((String element) -> {
                        return !ingredients.contains(element);
                    });
                }
            }
        }
        
        ArrayList<Entry<String, HashSet<String>>> actualIngredients = new ArrayList<>(possibleIngredients.entrySet());
        Entry<String, HashSet<String>> currentEntry;
        boolean madeChanges = true;
        int i, j;
        
        while(madeChanges) {
            madeChanges = false;
            actualIngredients.sort((Entry<String, HashSet<String>> e1, Entry<String, HashSet<String>> e2) -> {
                return e1.getValue().size() - e2.getValue().size();
            });
            for(i = 0; i < actualIngredients.size(); i++) {
                currentEntry = actualIngredients.get(i);
                if(currentEntry.getValue().size() == 1) {
                    for(j = i + 1; j < actualIngredients.size(); j++) {
                        madeChanges |= actualIngredients.get(j).getValue().removeAll(currentEntry.getValue());
                    }
                }
            }
        }
        
        actualIngredients.sort((Entry<String, HashSet<String>> e1, Entry<String, HashSet<String>> e2) -> {
            return e1.getKey().compareTo(e2.getKey());
        });
        
        StringBuilder canonicalList = new StringBuilder();
        for(Entry<String, HashSet<String>> current : actualIngredients) {
            for(String ingredient : current.getValue()) {
                canonicalList.append(ingredient).append(",");
            }
        }
        canonicalList.deleteCharAt(canonicalList.length() - 1);
        System.out.println("The canonical list shall be " + canonicalList.toString() + " and no further shall the list be.");
    }
}
