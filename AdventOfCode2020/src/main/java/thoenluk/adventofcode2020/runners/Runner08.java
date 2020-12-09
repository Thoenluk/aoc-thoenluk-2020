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

import thoenluk.adventofcode2020.ElfCodeComputer;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner08 {
    public static void runFirstChallenge(String input) {
        ElfCodeComputer comp = new ElfCodeComputer(input.split("\n"));
        comp.runProgramUntilLoop();
        System.out.println("The acc was " + comp.getAcc());
    }
    
    public static void runSecondChallenge(String input) {
        String[] lines = input.split("\n");
        String line;
        ElfCodeComputer comp = null;
        for(int i = 0; i < lines.length; i++) {
            line = lines[i];
            if(!line.substring(0, 3).equals("acc")) {
                lines[i] = (line.substring(0, 3).equals("jmp") ? "nop" : "jmp") + line.substring(3);
                comp = new ElfCodeComputer(lines);
                if(comp.runProgramUntilLoop()) {
                    break;
                }
                lines[i] = line;
            }
        }
        System.out.println("The accumulator was " + comp.getAcc());
    }
}