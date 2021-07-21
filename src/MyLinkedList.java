import java.util.Iterator;

/**
 * Create by Kannika Armstrong
 * TCSS342(Spring 2021): April 27, 2021
 * Assignment 3 - Word Search (MyLinkedList class)
 * Professor. Christopher Paul Marriott
 */

public class MyLinkedList<Type extends Comparable<Type>> {

    private Node first; // head node
    private Node last; // tail node
    int count = 0; // use to count how many time contain() called

    // the initial constructor
    public MyLinkedList() {
        this.first = null;
        this.last = null;
    }

    // add an item to the end of the list
    public void add(Type item) {
        if (first == null) {
            first = new Node(item);
            last = first;
        } else {
            last = first;
            while (last.next != null) {
                last = last.next;
            }
            last.next = new Node(item);
        }
    }

    // add an item to the given index
    public void add(Type item, int index) {
        try {
            if (index == size()) {
                add(item);
                return;
            }
            if (index == 0) {
                first = new Node(item, first);
            } else {
                Node current = first;
                for (int i = 0; i < index - 1; i++) {
                    current = current.next;
                }
                // current is now the node before the index where we want to add the item
                // we can add the item to the node that we want to add
                current.next = new Node(item, current.next);
            }
        } catch (Exception e){
            System.out.println("Index out of bound!");
        }
    }

    // remove an item at the given index
    public void remove(int index) {
        try {
            if (index == 0) {
                first = first.next;
            } else {
                Node current = this.first;
                for (int i = 0; i < index - 1; i++) {
                    current = current.next;
                }
                if (current.next.next != null) {
                    current.next = current.next.next;
                } else {
                    current.next = null;
                }
            }
        } catch (Exception e){
            System.out.println("Index out of bound!");
        }

    }

    // returns the value at the given index in the list
    public Type get(int index) {
        if (index < 0 || index > size()){
            throw new IndexOutOfBoundsException();
        }
        if (index < size() - 1){ // the last node of the list
            Node current = first;
            for (int i = 0; i < index; i++){
                current = current.next;
            }
            return current.item;
        }
        return last.item;
    }

    // replaces the value at the given index with the given value
    public void set(int index, Type item) {
        Node current = this.first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.item = item;
    }

    // check if our LinkedList contain the specify element
    // check over every single element in our object
    public boolean contain(Type item){
        Node current = first;
        while (current != null) {
            if (current.item.equals(item)) {
                return true;
            }
            current = current.next;
            count++;
        }
        count++;
        return false;
    }

    // t=return the number of elements in the list
    public int size() {
        int count = 0;
        Node current = first;
        while (current != null) {
            current = current.next;
            count++;
        }
        return count;
    }

    // check if the size is 0 -- return true
    public boolean isEmpty() {
        return this.first == null;
    }

    //
    public Iterator<Type> iterator() {
        return new Iterator<>() {
            private Node current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Type next() {
                Node next = current;
                current = current.next;
                return next.item;
            }
        };
    }

    public void sort() {
        Node firstNode = first;
        Node nextNode = first.next;
        for (int i = 0; i < size() - 1; i++) {
            boolean swap = false;
            for (int j = 0; j < size() - i - 1; j++) {
                while (nextNode != null) {
                    if (firstNode.item.compareTo(nextNode.item) > 0) {
                        swap = true;
                        Type tItem = firstNode.item;
                        firstNode.item = nextNode.item;
                        nextNode.item = tItem;
                    }
                    firstNode = nextNode;
                    nextNode = nextNode.next;
                }
            }
            if (!swap) {
                break;
            }
        }
    }

    public String toString() {
        String result = "";
        Node current = first;
        while (current != null) {
            result += current.toString();
            current = current.next;
        }
        return result;
    }

    // Node class store a single node of the linked list.
    // This Node class is for the list of Type values.
    private class Node {
        public Type item; // item store in this node
        public Node next; // link to next node in the list

        public Node(Type item) {
            this.item = item;
            this.next = null;
        }

        public Node(Type item, Node next) {
            this.item = item;
            this.next = next;
        }

        @Override
        public String toString() {
            return item.toString();
        }
    }
}

