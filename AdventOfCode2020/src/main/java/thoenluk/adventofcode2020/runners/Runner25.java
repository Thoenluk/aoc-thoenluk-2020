/*
 * Copyright (C) 2021 Lukas Thöni lukas.thoeni@gmx.ch
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

import thoenluk.adventofcode2020.Ut;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner25 {
    public static void runFirstChallenge(String input) {
        String[] lines = input.split("\n");
        long cardPublicKey = Ut.cachedParseLong(lines[0]);
        int cardLoopSize;
        long doorPublicKey = Ut.cachedParseLong(lines[1]);
        long subjectNumber = 1;
        int modulus = 20201227;
        for(cardLoopSize = 0; subjectNumber != cardPublicKey; cardLoopSize++) {
            subjectNumber *= 7;
            subjectNumber %= modulus;
        }
        subjectNumber = 1;
        for(int i = 0; i < cardLoopSize; i++) {
            subjectNumber *= doorPublicKey;
            subjectNumber %= modulus;
        }
        System.out.println("A fortune teller told me the encryption key is " + subjectNumber);
    }
    
    public static void runSecondChallenge(String input) {
        
    }
}
