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
import java.util.HashSet;
import thoenluk.adventofcode2020.Ut;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner23 {
    public static void runFirstChallenge(String input) {
        ArrayList<Integer> cups = new ArrayList<>();
        ArrayList<Integer> pickedUpCups = new ArrayList<>();
        int currentCup = 0;
        int currentCupValue;
        int destinationCup;
        int destinationCupValue;
        int i, j;
        for(char cup : input.toCharArray()) {
            cups.add(Character.getNumericValue(cup));
        }
        for(i = 0; i < 100; i++) {
            currentCupValue = cups.get(currentCup);
            destinationCupValue = currentCupValue;
            currentCup++;
            for(j = 0; j < 3; j++) {
                currentCup %= cups.size();
                pickedUpCups.add(cups.remove(currentCup));
            }
            do {
                destinationCupValue--;
                if(destinationCupValue == 0) {
                    destinationCupValue = 9;
                }
            } while(pickedUpCups.contains(destinationCupValue));
            destinationCup = cups.indexOf(destinationCupValue);
            for(j = 0; j < 3; j++) {
                cups.add(destinationCup + 1 + j, pickedUpCups.remove(0));
            }
            currentCup = (cups.indexOf(currentCupValue) + 1) % cups.size();
        }
        currentCup = cups.indexOf(1);
        for(i = 1; i < 9; i++) {
            System.out.print(cups.get((currentCup + i) % cups.size()));
        }
    }
    
    public static void runSecondChallenge(String input) {
        ArrayList<Cup> originalCups = new ArrayList<>(1000000);
        Cup[] grabbedCups = new Cup[3];
        HashMap<Integer, Cup> cups = new HashMap();
        Cup currentCup;
        Cup targetCup;
        int targetCupLabel;
        Cup targetChild;
        int i, j;
        for(char cup : input.toCharArray()) {
            originalCups.add(new Cup(Character.getNumericValue(cup)));
        }
        for(i = 10; i <= 1000000; i++) {
            originalCups.add(new Cup(i));
        }
        currentCup = originalCups.get(0);
        for(Cup current : originalCups) {
            current.detectAncestry(originalCups);
            cups.put(current.getLabel(), current);
        }
        for(i = 0; i < 10000000; i++) {
            grabbedCups[0] = currentCup.getChild();
            grabbedCups[1] = grabbedCups[0].getChild();
            grabbedCups[2] = grabbedCups[1].getChild();
            targetCupLabel = currentCup.getLabel();
            do {
                targetCupLabel--;
                if(targetCupLabel == 0) {
                    targetCupLabel = 1000000;
                }
            } while(grabbedCups[0].getLabel() == targetCupLabel
                    || grabbedCups[1].getLabel() == targetCupLabel
                    || grabbedCups[2].getLabel() == targetCupLabel);
            targetCup = cups.get(targetCupLabel);
            targetChild = targetCup.getChild();
            currentCup.setChild(grabbedCups[2].getChild(), true);
            targetCup.setChild(grabbedCups[0], true);
            grabbedCups[2].setChild(targetChild, true);
            currentCup = currentCup.getChild();
        }
        long firstLabel = cups.get(1).getChild().getLabel();
        long secondLabel = cups.get(1).getChild().getChild().getLabel();
        System.out.println("My horoscope says it is " + firstLabel + " * " + secondLabel + " making " + firstLabel * secondLabel);
    }
}

class Cup {
    private Cup parent;
    private Cup child;
    private final int value;
    
    public Cup(int value) {
        this.value = value;
    }
    
    public void detectAncestry(ArrayList<Cup> allCups) {
        int index = this.value < 10 ? allCups.indexOf(this) : this.value - 1;
        parent = Ut.betterGet(allCups, index - 1);
        child = Ut.betterGet(allCups, index + 1);
    }
    
    public Cup getParent() {
        return parent;
    }
    
    public void setParent(Cup parent, boolean firstCalled) {
        this.parent = parent;
        if(firstCalled) {
            parent.setChild(this, false);
        }
    }
    
    public Cup getChild() {
        return child;
    }
    
    public void setChild(Cup child, boolean firstCalled) {
        this.child = child;
        if(firstCalled) {
            child.setParent(this, false);
        }
    }
    
    public int getLabel() {
        return value;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Labelled ").append(value).append(" with parent ").append(parent.getLabel())
                .append(" and child ").append(child.getLabel());
        return sb.toString();
    }
}