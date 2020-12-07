package thoenluk.adventofcode2020;


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
