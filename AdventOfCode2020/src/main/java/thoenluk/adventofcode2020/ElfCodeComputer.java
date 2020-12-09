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
package thoenluk.adventofcode2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.BiConsumer;

enum Instruction {
    nop((int[] args, ElfCodeComputer target) -> {}),
    acc((int[] args, ElfCodeComputer target) -> {
        target.setAcc(target.getAcc() + args[0]);
    }),
    jmp((int[] args, ElfCodeComputer target) -> {
        target.setIp(target.getIp() + args[0] - 1);
    });

    public final BiConsumer<int[], ElfCodeComputer> code;

    private Instruction(BiConsumer<int[], ElfCodeComputer> code) {
        this.code = code;
    }
}

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 * For the record, the implementation worked perfectly first try, though I did spend
 * an hour coding getting nowhere before finding the right approach. I am as surprised
 * as you are.
 */
public class ElfCodeComputer {    
    private int ip = 0;
    private long acc = 0;
    int[] args = new int[1];
    private final ArrayList<Instruction> program = new ArrayList<>();
    private final ArrayList<String[]> inputs = new ArrayList<>();
    private final HashSet<Integer> visitedLines = new HashSet<>();
    
    public ElfCodeComputer(String[] programDescription) {
        String[] instructionSet;
        for(String line : programDescription) {
            instructionSet = line.split(" ");
            program.add(Instruction.valueOf(instructionSet[0]));
            inputs.add(Arrays.copyOfRange(instructionSet, 1, instructionSet.length));
        }
    }
    
    /**
     * Run the program within this ECC until it terminates (IP goes out of bounds),
     * regardless of if it might loop. Since the Halting Problem is what it is, this
     * method may have you stuck in an infinite loop, but we consciously don't check.
     */
    public void runProgram() {
        String[] input;
        int i;
        while(ipIsValid()) {
            input = inputs.get(ip);
            for(i = 0; i < input.length; i++) {
                args[i] = resolve(input[i]);
            }
            program.get(ip).code.accept(args, this);
            ip++;
        }
    }
    
    /**
     * Run the program within this ECC until a line is repeated, which may indicate a loop.
     * @return true if the program terminated (IP went beyond program bounds), false if it looped.
     * Eat your heart out, Turing!
     */
    public boolean runProgramUntilLoop() {
        String[] input;
        int i;
        while(!isLooping() && ipIsValid()) {
            input = inputs.get(ip);
            for(i = 0; i < input.length; i++) {
                args[i] = resolve(input[i]);
            }
            program.get(ip).code.accept(args, this);
            ip++;
        }
        return !ipIsValid();
    }
    
    private boolean ipIsValid() {
        return 0 <= ip && ip < program.size();
    }
    
    private boolean isLooping() {
        return !visitedLines.add(ip);
    }
    
    private int resolve(String directOrReference) {
        // At the moment, this does nothing more than parse a direct int.
        // But this ain't my first advent calendar and I know register references will come.
        return Ut.cachedParseInt(directOrReference);
    }
    
    public long getAcc() {
        return acc;
    }
    
    public void setAcc(long acc) {
        this.acc = acc;
    }

    public int getIp() {
        return ip;
    }

    public void setIp(int ip) {
        this.ip = ip;
    }
}