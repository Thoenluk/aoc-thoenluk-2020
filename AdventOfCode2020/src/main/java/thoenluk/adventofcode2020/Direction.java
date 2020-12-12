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

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public enum Direction {
    UPLEFT(-1, -1),
    UP(-1, 0),
    UPRIGHT(-1, 1),
    LEFT(0, -1),
    RIGHT(0, 1),
    DOWNLEFT(1, -1),
    DOWN(1, 0),
    DOWNRIGHT(1, 1);
    
    public final int y, x;

    private Direction(int y, int x) {
        this.y = y;
        this.x = x;
    }
}
