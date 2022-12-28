import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Eileen Liu
 * @version 1.0
 * @userid eliu80
 * @GTID 903561092
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Arraylist is null");
        }
        backingArray = (T[]) new Comparable[data.size() * 2 + 1];
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Data cannot be null");
            } else {
                size++;
                backingArray[i + 1] = data.get(i);
            }
        }
        T rightChild;
        T leftChild;
        T temp;
        int current;

        for (int i = size / 2; i > 0; i--) {
            current = i;
            while (current * 2 <= size) {
                leftChild = backingArray[current * 2];
                if ((current * 2) + 1 <= size) {
                    rightChild = backingArray[(current * 2) + 1];
                    if (leftChild.compareTo(rightChild) > 0 && backingArray[current].compareTo(rightChild) > 0) {
                        temp = backingArray[current];
                        backingArray[current] = backingArray[(current * 2) + 1];
                        backingArray[(current * 2) + 1] = temp;
                        current = (current * 2) + 1;
                    } else if ((rightChild.compareTo(leftChild) > 0)
                            && backingArray[current].compareTo(leftChild) > 0) {
                        temp = backingArray[current];
                        backingArray[current] = backingArray[current * 2];
                        backingArray[current * 2] = temp;
                        current = current * 2;
                    } else {
                        break;
                    }
                } else {
                    if (leftChild.compareTo(backingArray[current]) < 0) {
                        temp = backingArray[current];
                        backingArray[current] = backingArray[current * 2];
                        current = current * 2;
                        backingArray[current] = temp;

                    } else {
                        break;
                    }
                }
            }
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (backingArray.length == size + 1) {
            T[] temp = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        }
        size++;
        backingArray[size] = data;

        int end = size;
        while (end > 1 && backingArray[end].compareTo(backingArray[end / 2]) < 0) {
            T dummy = backingArray[end];
            backingArray[end] = backingArray[end / 2];
            end = end / 2;
            backingArray[end] = dummy;
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("No elements to remove");

        }
        T hold = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;

        int front = 1;
        T leftChild;
        T rightChild;
        T temp;
        while (front * 2 <= size) {
            leftChild = backingArray[front * 2];
            if (front * 2 + 1 <= size) {
                rightChild = backingArray[front * 2 + 1];
                if (leftChild.compareTo(rightChild) > 0 && backingArray[front].compareTo(rightChild) > 0) {
                    temp = backingArray[front];
                    backingArray[front] = backingArray[front * 2 + 1];
                    backingArray[front * 2 + 1] = temp;
                    front = front * 2 + 1;
                } else if ((rightChild.compareTo(leftChild) > 0) && backingArray[front].compareTo(leftChild) > 0) {
                    temp = backingArray[front];
                    backingArray[front] = backingArray[front * 2];
                    backingArray[front * 2] = temp;
                    front = front * 2;
                } else {
                    break;
                }
            } else {
                if (backingArray[front].compareTo(leftChild) > 0) {
                    temp = backingArray[front];
                    backingArray[front] = backingArray[front * 2];
                    backingArray[front * 2] = temp;
                    front = front * 2;
                } else {
                    break;
                }
            }
        }
        return hold;
    }


    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("To contain minimum element, heap cannot be empty");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }
    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        size = 0;
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
