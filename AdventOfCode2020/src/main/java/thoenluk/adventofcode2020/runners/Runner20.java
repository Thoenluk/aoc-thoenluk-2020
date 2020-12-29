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
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import thoenluk.adventofcode2020.Ut;

/**
 *
 * @author Lukas Thöni lukas.thoeni@gmx.ch
 */
public class Runner20 {
    public static void runFirstChallenge(String input) {
        String[] unprocessedTiles = input.split("\n\n");
        ArrayList<Tile> tiles = new ArrayList<>(unprocessedTiles.length);
        for(String current : unprocessedTiles) {
            tiles.add(new Tile(current));
        }
        int i, j;
        long total = 1;
        for(i = 0; i < tiles.size(); i++) {
            for(j = 0; j < tiles.size(); j++) {
                if(i != j) {
                    tiles.get(i).addPossibleNeighbour(tiles.get(j));
                }
            }
            if(tiles.get(i).getNeighbourCount() == 2) {
                total *= tiles.get(i).getID();
            }
        }
        System.out.println("I have a hunch the product of IDs is " + total);
    }
    
    public static void runSecondChallenge(String input) {
        String[] unprocessedTiles = input.split("\n\n");
        ArrayList<Tile> tiles = new ArrayList<>(unprocessedTiles.length);
        ArrayList<Tile> neighbours;
        Tile currentTile, startingCorner = null;
        int i, j, x = 0, y = 0, currentX, currentY, direction, firstDirection, secondDirection;
        boolean possibleStarter;
        
        for(String current : unprocessedTiles) {
            currentTile = new Tile(current);
            tiles.add(currentTile);
        }
        
        for(i = 0; i < tiles.size(); i++) {
            currentTile = tiles.get(i);
            for(j = 0; j < tiles.size(); j++) {
                if(i != j) {
                    currentTile.addPossibleNeighbour(tiles.get(j));
                }
            }
            if(currentTile.getNeighbourCount() == 2 && null == startingCorner) {
                neighbours = currentTile.getNeighbours();
                for(direction = 0; direction < 8; direction++) {
                    possibleStarter = true;
                    currentTile.setOrientation(direction);
                    currentTile.fitOther(neighbours.get(0));
                    currentTile.fitOther(neighbours.get(1));
                    firstDirection = currentTile.getActualPositionOfNeighbour(neighbours.get(0));
                    secondDirection = currentTile.getActualPositionOfNeighbour(neighbours.get(1));
                    possibleStarter &= firstDirection == 1 || firstDirection == 2;
                    possibleStarter &= secondDirection == 1 || secondDirection == 2;
                    if(possibleStarter) {
                        startingCorner = currentTile;
                        break;
                    }
                }
            }
        }
        
        if(null == startingCorner) {
            System.out.println("You dun bad son. Couldn't find a starting tile!");
            return;
        }
        
        startingCorner.setPosition(20, 20);
        
        LinkedList<Tile> nextTiles = new LinkedList<>();
        HashSet<Tile> visitedPieces = new HashSet<>();
        nextTiles.add(startingCorner);
        visitedPieces.add(startingCorner);
        
        while(!nextTiles.isEmpty()) {
            currentTile = nextTiles.pop();
            for(Tile neighbour : currentTile.getNeighbours()) {
                if(visitedPieces.add(neighbour)) {
                    nextTiles.add(neighbour);
                    currentTile.fitOther(neighbour);
                    direction = currentTile.getActualPositionOfNeighbour(neighbour);
                    x = currentTile.getX() + ((2 - direction) % 2) * 8;
                    y = currentTile.getY() + ((direction - 1) % 2) * 8;
                    neighbour.setPosition(x, y);
                }
            }
        }
        
        Boolean[][] currentBits;
        int side = 12 * 8 + 2 * 20;
        int totalRoughness = 0;
        Boolean[][] image = new Boolean[side][side];
        for(Boolean[] row : image) {
            Arrays.fill(row, false);
        }
        
        for(Tile current : tiles) {
            currentBits = current.getBits();
            currentX = current.getX();
            currentY = current.getY();
            for(y = 0; y < 8; y++) {
                for(x = 0; x < 8; x++) {
                    image[currentY + y][currentX + x] = currentBits[y][x];
                    if(currentBits[y][x]) {
                        totalRoughness++;
                    }
                }
            }
        }
        
        
        Boolean[][] monsterShape = new Boolean[][]{
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false},
            {true, false, false, false, false, true, true, false, false, false, false, true, true, false, false, false, false, true, true, true},
            {false, true, false, false, true, false, false, true, false, false, true, false, false, true, false, false, true, false, false, false}
        };
        
        Boolean[][][] images = new Boolean[8][side][side];
        int monsters = 0;
        images[0] = image;
        images[1] = Ut.flipVertically(image, Boolean.class);
        images[2] = Ut.flipHorizontally(image, Boolean.class);
        images[3] = Ut.flipHorizontally(images[1], Boolean.class);
        images[4] = Ut.rotateClockwise(image, Boolean.class);
        images[5] = Ut.flipVertically(images[4], Boolean.class);
        images[6] = Ut.flipHorizontally(images[4], Boolean.class);
        images[7] = Ut.flipHorizontally(images[5], Boolean.class);
        for(Boolean[][] orientedImage : images) {
            monsters = findSeaMonsters(orientedImage, monsterShape, 20);
            if(monsters > 0) {
                System.out.println("The waters appear to be around " + (totalRoughness - monsters * 15) + " rough, cap'n.");
                break;
            }
        }
    }
    
    private static int findSeaMonsters(Boolean[][] image, Boolean[][] filter, int padding) {
        int monsters = 0;
        int m = image.length - padding;
        int n = image[0].length - padding;
        for(int r = padding; r < m; r++) {
            for(int c = padding; c < n; c++) {
                if(convolveSinglePixel(image, filter, r, c)) {
                    monsters++;
                }
            }
        }
        return monsters;
    }
    
    private static boolean convolveSinglePixel(Boolean[][] image, Boolean[][] filter, int startingY, int startingX) {
        boolean matches = true;
        int m = filter.length;
        int n = filter[0].length;
        for(int r = 0; r < m; r++) {
            for(int c = 0; c < n; c++) {
                if(filter[r][c]) {
                    matches &= image[r + startingY][c + startingX];
                }
            }
        }
        return matches;
    }
}

class Tile {    
    private final String[] borders;
    private final int id;
    private int orientation;
    private final Tile[] neighbours;
    private final String[] content;
    private int x, y, neighbourCount;
    
    public Tile(String unprocessed) {
        borders = new String[32];
        String[] lines = unprocessed.split("\n");
        String[] reverse = new String[4];
        int i;
        
        id = Ut.cachedParseInt(lines[0].replaceAll("[^\\d]", ""));
        
        content = Arrays.copyOfRange(lines, 1, lines.length);
        
        // regular borders in 0-3
        borders[0] = lines[1];
        borders[2] = Ut.lastElement(lines);
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();
        for(String line : content) {
            left.append(line.charAt(0));
            right.append(line.charAt(line.length() - 1));
        }
        borders[1] = right.toString();
        borders[3] = left.toString();
        
        for(i = 0; i < 4; i++) {
            reverse[i] = new StringBuilder(borders[i]).reverse().toString();
        }
                
        // flipped vertically in 4-7: Swap top and bottom, mirror left and right
        borders[4] = borders[2];
        borders[5] = reverse[1];
        borders[6] = borders[0];
        borders[7] = reverse[3];
        
        // flipped horizontally in 8-11: Swap left and right, mirror top and bottom
        borders[8] = reverse[0];
        borders[9] = borders[3];
        borders[10] = reverse[2];
        borders[11] = borders[1];
        
        // flipped both ways in 12-15: Swap and mirror all.
        borders[12] = reverse[2];
        borders[13] = reverse[3];
        borders[14] = reverse[0];
        borders[15] = reverse[1];
        
        // rotated once clockwise in 16-31: Must mirror left and right as they are now
        // read the other way.
        borders[16] = reverse[3];
        borders[17] = borders[0];
        borders[18] = reverse[1];
        borders[19] = borders[2];
        
        borders[20] = reverse[1];
        borders[21] = reverse[0];
        borders[22] = reverse[3];
        borders[23] = reverse[2];
        
        borders[24] = borders[3];
        borders[25] = borders[2];
        borders[26] = borders[1];
        borders[27] = borders[0];
        
        borders[28] = borders[1];
        borders[29] = reverse[2];
        borders[30] = borders[3];
        borders[31] = reverse[0];
        
        orientation = 0;
        neighbours = new Tile[4];
        neighbourCount = 0;
        x = 0;
        y = 0;
    }
    
    public boolean addPossibleNeighbour(Tile other) {
        for(int i = 0; i < 4; i++) {
            if(other.hasBorder(borders[i]) > -1) {
                neighbours[i] = other;
                neighbourCount++;
                return true;
            }
        }
        return false;
    }
    
    public void fitOther(Tile other) {
        int originalPosition = getOriginalIndexOfNeighbour(other);
        int actual = getOrientedSidePosition(originalPosition);
        for(int i = 0; i < 8; i++) {
            other.setOrientation(i);
            if(getOrientedBorder(actual).equals(other.getOrientedBorder((2 + actual) % 4))) {
                return;
            }
        }
    }
    
    public int getOriginalIndexOfNeighbour(Tile other) {
        for(int i = 0; i < neighbours.length; i++) {
            if(neighbours[i] == other) {
                return i;
            }
        }
        return -1;
    }
    
    public int getActualPositionOfNeighbour(Tile other) {
        return getOrientedSidePosition(getOriginalIndexOfNeighbour(other));
    }
    
    public String getOrientedBorder(int side) {
        return borders[orientation * 4 + side];
    }
    
    public int getOrientedSidePosition(int side) {
        switch(orientation) {
            case 0:
                return side;
            case 1:
                return (6 - side) % 4;
            case 2:
                return (4 - side) % 4;
            case 3:
                return (2 + side) % 4;
            case 4:
                return (1 + side) % 4;
            case 5:
                return (5 - side) % 4;
            case 6:
                return 3 - side;
            case 7:
                return (3 + side) % 4;
        }
        return side;
    }
    
    public Boolean[][] getBits() {
        int r, c;
        int m = content.length - 2;
        int n = content[0].length() - 2;
        Boolean[][] bits = new Boolean[m][n];
        
        for(r = 0; r < m; r++) {
            for(c = 0; c < n; c++) {
                bits[r][c] = content[r + 1].charAt(c + 1) == '#';
            }
        }
        
        // rotate clockwise
        if(orientation >= 4) {
            bits = Ut.rotateClockwise(bits, Boolean.class);
        }
        
        // flip vertically
        if(orientation % 2 == 1) {
            bits = Ut.flipVertically(bits, Boolean.class);
        }
        
        // flip horizontally
        if(orientation % 4 >= 2) {
            bits = Ut.flipHorizontally(bits, Boolean.class);
        }
        
        return bits;
    }
    
    public int getID() {
        return id;
    }
    
    public ArrayList<Tile> getNeighbours() {
        ArrayList<Tile> actualNeighbours = new ArrayList<>(neighbourCount);
        for(Tile current : neighbours) {
            if(null != current) {
                actualNeighbours.add(current);
            }
        }
        return actualNeighbours;
    }
    
    public int getNeighbourCount() {
        return neighbourCount;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getOrientation() {
        return orientation;
    }
    
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    private int hasBorder(String otherBorder) {
        for(int i = 0; i < borders.length; i++) {
            if(borders[i].equals(otherBorder)) {
                return i;
            }
        }
        return -1;
    }
}