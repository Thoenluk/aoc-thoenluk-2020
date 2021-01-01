package thoenluk.adventofcode2020.runners;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import thoenluk.adventofcode2020.Ut;

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

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner22 {
    public static void runFirstChallenge(String input) {
        LinkedList<Integer> myDeck = new LinkedList<>();
        LinkedList<Integer> wilsonsDeck = new LinkedList<>();
        LinkedList<Integer> winningDeck;
        String[] decks = input.split("\n\n");
        String[] myInput = decks[0].split("\n");
        String[] wilsonsInput = decks[1].split("\n");
        int myCard, wilsonsCard, multiplier;
        int score = 0;
        for(int i = 1; i < myInput.length; i++) {
            myDeck.add(Ut.cachedParseInt(myInput[i]));
            wilsonsDeck.add(Ut.cachedParseInt(wilsonsInput[i]));
        }
        while(!myDeck.isEmpty() && !wilsonsDeck.isEmpty()) {
            myCard = myDeck.pop();
            wilsonsCard = wilsonsDeck.pop();
            if(myCard > wilsonsCard) {
                myDeck.add(myCard);
                myDeck.add(wilsonsCard);
            } else {
                wilsonsDeck.add(wilsonsCard);
                wilsonsDeck.add(myCard);
            }
        }
        winningDeck = myDeck.isEmpty() ? wilsonsDeck : myDeck;
        multiplier = winningDeck.size();
        for(Integer card : winningDeck) {
            score += card * multiplier;
            multiplier--;
        }
        System.out.println("The winning score is forecast to be " + score + ". " + (myDeck.isEmpty() ? "Can't believe Wilson beat me." : "Knew I was smarter than that crab."));
        
    }
    
    public static void runSecondChallenge(String input) {
        ArrayList<Integer> myDeck = new ArrayList<>();
        ArrayList<Integer> wilsonsDeck = new ArrayList<>();
        ArrayList<Integer> winningDeck;
        String[] decks = input.split("\n\n");
        String[] myInput = decks[0].split("\n");
        String[] wilsonsInput = decks[1].split("\n");
        int multiplier;
        int score = 0;
        for(int i = 1; i < myInput.length; i++) {
            myDeck.add(Ut.cachedParseInt(myInput[i]));
            wilsonsDeck.add(Ut.cachedParseInt(wilsonsInput[i]));
        }
        winningDeck = runRecursiveCombat(myDeck, wilsonsDeck) ? myDeck : wilsonsDeck;
        multiplier = winningDeck.size();
        for(Integer card : winningDeck) {
            score += card * multiplier;
            multiplier--;
        }
        System.out.println("The winning score is forecast to be " + score + ". " + (myDeck.isEmpty() ? "Can't believe Wilson beat me." : "Knew I was smarter than that crab."));
    }
    
    private static boolean runRecursiveCombat(List<Integer> myDeck, List<Integer> wilsonsDeck) {
        HashSet<List> myPreviousDeckStates = new HashSet<>();
        List<Integer> winningDeck;
        int myCard, wilsonsCard, winningCard, losingCard;
        boolean IWIN = true;
        while(!myDeck.isEmpty() && !wilsonsDeck.isEmpty()) {
            for(List current : myPreviousDeckStates) {
                if(myDeck.equals(current)) {
                    return true;
                }
            }
            myPreviousDeckStates.add(new ArrayList(myDeck));
            myCard = myDeck.remove(0);
            wilsonsCard = wilsonsDeck.remove(0);
            if(myCard <= myDeck.size() && wilsonsCard <= wilsonsDeck.size()) {
                IWIN = runRecursiveCombat(new ArrayList<>(myDeck.subList(0, myCard)), new ArrayList(wilsonsDeck.subList(0, wilsonsCard)));
            } else {
                IWIN = myCard > wilsonsCard;
            }
            winningDeck = IWIN ? myDeck : wilsonsDeck;
            winningCard = IWIN ? myCard : wilsonsCard;
            losingCard = IWIN ? wilsonsCard : myCard;
            winningDeck.add(winningCard);
            winningDeck.add(losingCard);
        }
        return IWIN;
    }
}
