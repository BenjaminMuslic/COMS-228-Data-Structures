package graph;

import java.util.NoSuchElementException;

/**
 * A Queue based on a LinkedList.
 * 
 * This class is complete and should not be modified
 * 
 * @author Xiaoqiu Huang
 */
public class LinkedQueue<E> {

    /**
     * Private helper linked list node
     */
    private class QNode {
        public E data;
        public QNode link;
    }

    // Node at the front of the queue
    private QNode front;

    // Node at the back of the queue
    private QNode back;

    // Number of elements in the queue
    private int numItems;

    /**
     * Constructs an empty queue
     */
    public LinkedQueue() {
        front = back = null;
        numItems = 0;
    }

    /**
     * Gets the number of elements in the queue
     */
    public int size() {
        return numItems;
    }

    /**
     * Tests if this queue has elements
     */
    public boolean isEmpty() {
        return (numItems == 0);
    }

    /**
     * Adds a new element to the back of the queue
     * 
     * @param element
     * - the item to add
     */
    public void enqueue(E element) {
        QNode toAdd = new QNode();
        toAdd.data = element;
        toAdd.link = null;
        if (back != null)
            back.link = toAdd;
        else
            front = toAdd;
        back = toAdd;
        numItems++;
    }

    /**
     * Removes the front of the queue and returns its data
     * 
     * @return the front node's data
     */
    public E dequeue() {
        if (front == null)
            throw new NoSuchElementException();
        E returnVal = front.data;
        front = front.link;
        if (front == null)
            back = null;
        numItems--;
        return returnVal;
    }

    /**
     * Gets the front node's data without removing
     * 
     * @return the front node's data
     */
    public E front() {
        if (front == null)
            throw new NoSuchElementException();
        return front.data;
    }
}
