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

import java.util.function.BiConsumer;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public enum Instruction {
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
