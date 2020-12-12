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
public enum CardinalDirection {
    NORTH(1, 0, 0),
    EAST(0, 1, 1),
    SOUTH(-1, 0, 2),
    WEST(0, -1, 3);
    
    public final int latitude, longitude, index;
    private static final CardinalDirection[] list = CardinalDirection.values();
    
    private CardinalDirection(int latitude, int longitude, int index) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.index = index;
    }
    
    public static CardinalDirection get(int index) {
        return list[index];
    }
}
