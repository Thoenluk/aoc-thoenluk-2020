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

import java.util.Arrays;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner03 {
    
    public static void runFirstChallenge(String input) {
        String[] forest = input.split("\n");
        int width = forest[0].length();
        int currentColumn = 0;
        int trees = 0;
        for(String row : Arrays.copyOfRange(forest, 1, forest.length)) {
            currentColumn += 3;
            currentColumn %= width; // Behold: Infinite forest
            trees = row.charAt(currentColumn) == '#' ? trees + 1 : trees;
        }
        System.out.println("I have hit " + trees + " trees on my way. Ow.");
    }
    
    public static void runSecondChallenge(String input) {
        String[] forest = input.split("\n");
        int width = forest[0].length();
        int currentColumn = 0;
        long totalTrees = 1;
        int trees = 0;
        int row;
        int[][] slopes = new int[][]{new int[]{1, 1}, new int[]{3, 1}, new int[]{5, 1}, new int[]{7, 1}, new int[]{1, 2}};
        for(int[] slope : slopes) {
            trees = 0;
            currentColumn = 0;
            for(row = slope[1]; row < forest.length; row += slope[1]) {
                currentColumn += slope[0];
                currentColumn %= width;
                trees = forest[row].charAt(currentColumn) == '#' ? trees + 1 : trees;
            }
            System.out.println(trees);
            totalTrees *= trees > 0 ? trees : 1;
        }
        System.out.println("I have hit " + totalTrees + " trees on my way. Ow.");
    }
}
