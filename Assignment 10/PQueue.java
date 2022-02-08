/*  Student information for assignment:
 *
 *  On <MY> honor, <Tyler Meador>, this programming assignment is <MY> own work
 *  and <I> have not provided this code to any other student.
 *
 *  Number of slip days used: 0
 *
 *  Student 1 (Student whose turnin account is being used)
 *  UTEID: ttm553
 *  email address: meador.tyler0@gmail.com
 *  Grader name: Nina
 */

import java.util.ArrayList;

public class PQueue<E extends Comparable<? super E>> {
    private ArrayList<E> queue;
    private int size;
    
    public PQueue() {
        queue = new ArrayList<>();
    }
    
    /*
     * Adds the value to the priority queue, adding it 
     * fairly if there are values of the same priority
     * pre: val != null
     * post: new value is added to queue based on its priority
     */
    public void add(E val) {
        if (val == null) {
            throw new IllegalArgumentException("val cannot be null");
        }
        
        if (size == 0) {
            queue.add(val);
        } else {
            int index = 0;
            while (index < size && queue.get(index).compareTo(val) <= 0) {
                index++;
            }
            queue.add(index, val);
        }
        size++;
    }
    
    /*
     * pre: none
     * post: removes all elements from the queue
     */
    public void clear() {
        queue.clear();
        size = 0;
    }
    
    /*
     * pre: none
     * post: returns true if element is in the queue, and false otherwise
     */
    public boolean contains(Object element) {
        return queue.contains(element);
    }
    
    /*
     * pre: size > 0
     * post: returns the first element in the queue
     */
    public E peek() {
        if (size <= 0) {
            throw new IllegalArgumentException("PQueue cannot be empty");
        }
        
        return queue.get(0);
    }
    
    /*
     * pre: size > 0
     * post: returns the first element in the queue and removes if from the queue
     */
    public E poll() {
        if (size <= 0) {
            throw new IllegalArgumentException("PQueue cannot be empty");
        }
        
        size--;
        return queue.remove(0);
    }

    /*
     * pre: element != null
     * post: removes the element from the queue
     */
    public void remove(Object element) {
        if (element == null) {
            throw new IllegalArgumentException("val cannot be null");
        }
        
        if (queue.remove(element)) {
            size--;
        }
    }

    /*
     * pre: none
     * post: returns the size
     */
    public int size() {
        return size;
    }
    
    /*
     * *Meant for debugging purposes*
     * pre: none
     * post: returns a string representation fo the queue
     */
    public String toString() {
        return queue.toString();
    }
}
