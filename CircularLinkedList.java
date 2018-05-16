//Christopher Nelson
//27 January 2018
//Lab04: The Josephus Problem
package circularlinkedlist;
import java.util.Iterator;


public class CircularLinkedList<E> implements Iterable<E> {
    
    private Node<E> head;
    private Node<E> tail;
    private int size;  // BE SURE TO KEEP TRACK OF THE SIZE
    
    
    // Constructor
    public CircularLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
    
    
    private Node<E> getNode(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        
        Node<E> current = head;
        for(int i = 0; i < index; i++) {
            current = current.next;
        }
        
        return current;
        
    }
    
    
    // attach a node to the end of the list
    public boolean add(E item) {
        this.add(size,item);
        return true;
        
    }
    
    public void add(int index, E item){
        // Out of bounds
        if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        
        Node<E> adding = new Node<E>(item);
        
        // Adding to empty list
        if(size == 0){
            this.head = adding;
            this.tail = adding;
            tail.next = head;
        }
        else if(index == 0) { // Adding to front (a new head)
            adding.next = head;
            tail.next = adding;
            head = adding;
        }
        else if(index == size) { // Adding to "end" (a new tail)
            tail.next = adding;
            tail = adding;
            adding.next = head;
        }
        else { // Adding anywhere else
            Node<E> before = this.getNode(index - 1);
            adding.next = before.next;
            before.next = adding;
        }
        
        size++;
    }
    
    public E remove(int index) {
        E toReturn = null;
        // Out of bounds
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        // Removing the only item in the list
        if(size == 1){
            toReturn = head.item;
            head = null;
            tail = null;
        }
        else if(index == 0) { // Removing the first thing in the list
            toReturn = head.item;
            tail.next = head.next;
            head = head.next;
        }
        else if(index == size -1) { // Removing the last thing in the list
            toReturn = tail.item;
            Node<E> before = this.getNode(index - 1);
            tail = before;
            before.next = head;
        }
        else { // Removing from anywhere else
            Node<E> before = this.getNode(index - 1);
            toReturn = before.next.item;
            before.next = before.next.next;
        }
        
        size--;
        return toReturn;
    }
    
    
    
    
    // Turns your list into a string
    // Useful for debugging
    public String toString(){
        Node<E> current =  head;
        StringBuilder result = new StringBuilder();
        if(size == 0){
            return "";
        }
        if(size == 1) {
            return head.item.toString();
            
        }
        else{
            do{
                result.append(current.item);
                result.append(" ==> ");
                current = current.next;
            } while(current != head);
        }
        return result.toString();
    }
    
    
    public Iterator<E> iterator() {
        return new ListIterator<E>();
    }
    
    // provided code
    // read the comments to figure out how this works and see how to use it
    // you should not have to change this
    // change at your own risk!
    // this class is not static because it needs the class it's inside of to survive!
    private class ListIterator<E> implements Iterator<E>{
        
        Node<E> nextItem;
        Node<E> prev;
        int index;
        
        @SuppressWarnings("unchecked")
        //Creates a new iterator that starts at the head of the list
        public ListIterator(){
            nextItem = (Node<E>) head;
            index = 0;
        }
        
        // returns true if there is a next node
        // this is always should return true if the list has something in it
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return size != 0;
        }
        
        // advances the iterator to the next item
        // handles wrapping around back to the head automatically for you
        public E next() {
            // TODO Auto-generated method stub
            prev =  nextItem;
            nextItem = nextItem.next;
            index =  (index + 1) % size;
            return prev.item;
            
        }
        
        // removed the last node was visted by the .next() call
        // for example if we had just created a iterator
        // the following calls would remove the item at index 1 (the second person in the ring)
        // next() next() remove()
        public void remove() {
            int target;
            if(nextItem == head) {
                target = size - 1;
            } else{
                target = index - 1;
                index--;
            }
            CircularLinkedList.this.remove(target); //calls the above class
        }
        
    }
    
    
    private static class Node<E>{
        private E item;
        Node<E> next;
        
        public Node(E item) {
            this.item = item;
        }
        
    }
    
    // Solve the problem in the main method
    // The answer of n = 13,  k = 2 is
    // the 11th person in the ring (index 10)
    public static void main(String[] args) {
        
        CircularLinkedList<Integer> l =  new CircularLinkedList<Integer>();
        int n = 13;
        int k = 1;
        
        
        // add your nodes here!
        for(int i = 1; i <= n; i++){
            l.add(i);
        }
        System.out.println(l.toString());
        // add your nodes before this
        // use the iterator to iterate around the list
        Iterator<Integer> iter = l.iterator();
        while (l.size > 1){
            for(int i = 0; i < k; i ++){
                iter.next();
            }
            iter.remove();
            System.out.println(l.toString());
            
        }
    }
}
