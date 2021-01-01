package thoenluk.adventofcode2020;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lukas Th�ni lukas.thoeni@gmx.ch
 */
public class Ut {
    private static final HashMap<String, Integer> numberCache = new HashMap<>();
    private static final HashMap<String, Long> longCache = new HashMap<>();
    
    public static int cachedParseInt(String stringRepresentation) throws NumberFormatException {
        if(!numberCache.containsKey(stringRepresentation)) {
            numberCache.put(stringRepresentation, Integer.parseInt(stringRepresentation));
        }
        return numberCache.get(stringRepresentation);
    }
    
    public static int cachedParseInt(String stringRepresentation, int radix) throws NumberFormatException {
        if(!numberCache.containsKey(stringRepresentation)) { // Does not distinguish radix. Probably fine in my use case.
            numberCache.put(stringRepresentation, Integer.parseInt(stringRepresentation, radix));
        }
        return numberCache.get(stringRepresentation);
    }
    
    public static long cachedParseLong(String stringRepresentation) throws NumberFormatException {
        if(!longCache.containsKey(stringRepresentation)) {
            longCache.put(stringRepresentation, Long.parseLong(stringRepresentation));
        }
        return longCache.get(stringRepresentation);
    }
    
    public static long cachedParseLong(String stringRepresentation, int radix) throws NumberFormatException {
        if(!longCache.containsKey(stringRepresentation)) { // Does not distinguish radix. Probably fine in my use case.
            longCache.put(stringRepresentation, Long.parseLong(stringRepresentation, radix));
        }
        return longCache.get(stringRepresentation);
    }
    
    public static <T> T lastElement(T[] array) {
        return array[array.length - 1];
    }
    
    /**
     * Return the element at the specified index, wrapping around both sides if necessary.
     * If 0 <= index, return list.get(index);
     * Otherwise index < 0, return list.get(list.size() - index); The indexth element from the end of the list.
     * In either case, the index is shrunk to fit into the list's boundaries.
     * @param list The list to be gotten from.
     * @param index The index from the front (or back, if negative) the element is located at.
     * @return The element of the list located as described.
     */
    public static <T> T betterGet(List<T> list, int index) {
        index %= list.size();
        if(index < 0) {
            return list.get(list.size() + index);
        } else {
            return list.get(index);
        }
    }
    
    public static <T> T[][] flipVertically(T[][] matrix, Class<T> type) {
        int m = matrix.length;
        int n = matrix[0].length;
        T[][] ret = (T[][]) Array.newInstance(type, m, n);
        for(int r = 0; r < m; r++) {
            ret[r] = matrix[m - 1 - r];
            ret[m - 1 - r] = matrix[r];
        }
        return ret;
    }
    
    public static <T> T[][] flipHorizontally(T[][] matrix, Class<T> type) {
        int m = matrix.length;
        int n = matrix[0].length;
        T[][] ret = (T[][]) Array.newInstance(type, m, n);
        for(int r = 0; r < m; r++) {
            for(int c = 0; c < n; c++) {
                ret[r][c] = matrix[r][n - 1 - c];
                ret[r][n - 1 - c] = matrix[r][c];
            }
        }
        return ret;
    }
    
    public static <T> T[][] rotateClockwise(T[][] matrix, Class<T> type) {
        int m = matrix.length;
        int n = matrix[0].length;
        T[][] ret = (T[][]) Array.newInstance(type, m, n);
        for(int r = 0; r < m; r++) {
            for(int c = r; c < n; c++) {
                ret[r][c] = matrix[n - 1 - c][r];
                ret[n - 1 - c][r] = matrix[m - 1 - r][n - 1 - c];
                ret[m - 1 - r][n - 1 - c] = matrix[c][n - 1 - r];
                ret[c][n - 1 - r] = matrix[r][c];
            }
        }
        return ret;
    }
    
    public class Node<T> {
        private List<Node<T>> children = new ArrayList<>();
        private Node<T> parent = null;
        private T data;
        
        public Node(T data) {
            this.data = data;
        }
        
        public Node(T data, Node<T> parent) {
            this.data = data;
            this.parent = parent;
        }
        
        public List<Node<T>> getChildren() {
            return children;
        }
        
        public Node<T> getParent() {
            return parent;
        }
        
        public void setParent(Node<T> parent) {
            this.parent = parent;
        }
        
        public void addChild(T data) {
            this.children.add(new Node<>(data, this));
       }
        
        public void addChild(Node<T> node) {
            node.setParent(this);
            this.children.add(node);
        }
        
        public T getData() {
            return data;
        }
        
        public boolean isRoot() {
            return this.parent == null;
        }
        
        public boolean isLeaf() {
            return this.children.size() == 0;
        }
    }
}
