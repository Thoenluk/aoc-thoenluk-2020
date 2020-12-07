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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import thoenluk.adventofcode2020.Ut;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner07 {
    public static void runFirstChallenge(String input) {
        HashMap<String, List<String>> containedIn = new HashMap<>();
        String[] relation;
        String outerBag;
        String[] innerBags;
        for(String line : input.split("\n")) {
            line = line.replaceAll("bags?\\.?|\\d*", ""); // Any problem can be solved with sufficient regex.
            relation = line.split(" contain ");
            outerBag = relation[0].trim();
            innerBags = relation[1].split(", ");
            for(String innerBag : innerBags) {
                innerBag = innerBag.trim();
                if(!containedIn.containsKey(innerBag)) {
                    containedIn.put(innerBag, new LinkedList<>());
                }
                containedIn.get(innerBag).add(outerBag);
            }
        }
        Set<String> containingBags = new HashSet<>();
        String currentColour;
        Iterator<String> current;
        Stack<Iterator<String>> iterators = new Stack<>();
        iterators.push(containedIn.get("shiny gold").iterator());
        while(!iterators.empty()) {
            current = iterators.peek();
            if(current.hasNext()) {
                currentColour = current.next();
                containingBags.add(currentColour);
                if(containedIn.containsKey(currentColour)) {
                    iterators.push(containedIn.get(currentColour).iterator());
                }
            } else {
                iterators.pop();
            }
        }
        System.out.println("I have " + containingBags.size() + " different colour options for my shiny gold bag.");
    }
    
    public static void runSecondChallenge(String input) {
        /*
        Beware: Though the methods look similar - because they are 90% the same code
        - they are distinct in an important way. In the first challenge I build my
        graph bottom up (keyed from the contained bag to the containing) and count
        distinct colours, because that's all I need to do there.
        
        On this one, I go from the containing bag to the contained one and count each
        occurence of a contained bag, embracing redundancy because that's the point.
        
        Note the trick I did to avoid having to track two things in the DFS: Putting
        redundant copies of each contained bag string in the lists, such that a bag
        which must be contained three times is actually three times in the list.
        This is a horrific idea wasting space which will certainly crater performance
        on anything but this most tiny of data sets.
        But it keeps me from having to track the amount of bags contained.
        I like that.
        
        Methinks I want to isolate the Depth-First Search code part of this to the Ut
        class with a lambda function for what to do on each element, but it's hard to
        think of what could be done besides looking for a particular element. Could
        I even make this function if all I can control is calling a lambda where 
        bagsNeeded++ stands?
        
        But I already made the Node class which I didn't end up actually using, so
        I might as well add a DFS for a given value at least.
        */
        HashMap<String, List<String>> contains = new HashMap<>();
        String[] relation;
        String outerBag;
        String[] innerBags;
        int amount;
        List<String> currentList;
        for(String line : input.split("\n")) {
            line = line.replaceAll("bags?\\.?", ""); // Any problem can be solved with sufficient regex.
            relation = line.split(" contain ");
            outerBag = relation[0].trim();
            innerBags = relation[1].split(", ");
            for(String innerBag : innerBags) {
                if(innerBag.equals("no other ")) {
                    break;
                }
                amount = Ut.cachedParseInt(innerBag.replaceAll("[^\\d]", ""));
                innerBag = innerBag.replaceAll("\\d", "").trim();
                if(!contains.containsKey(outerBag)) {
                    contains.put(outerBag, new LinkedList<>());
                }
                currentList = contains.get(outerBag);
                for(;amount > 0; amount--) {
                    currentList.add(innerBag);
                }
            }
        }
        int bagsNeeded = 0;
        String currentColour;
        Iterator<String> current;
        Stack<Iterator<String>> iterators = new Stack<>();
        iterators.push(contains.get("shiny gold").iterator());
        while(!iterators.empty()) {
            current = iterators.peek();
            if(current.hasNext()) {
                currentColour = current.next();
                bagsNeeded++;
                if(contains.containsKey(currentColour)) {
                    iterators.push(contains.get(currentColour).iterator());
                }
            } else {
                iterators.pop();
            }
        }
        System.out.println("I would need to buy " + bagsNeeded + " bags!");
    }
}
