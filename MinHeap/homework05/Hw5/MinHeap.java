import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Clifford Yao
 * @version 1.0
 * @userid cyao61
 * @GTID 903601722
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
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("You are trying to add data from an ArrayList but the list is null.");
        }
        backingArray = (T[]) new Comparable[data.size() * 2 + 1];
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("You cannot put null data into the heap.");
            }
            backingArray[i + 1] = data.get(i);
            size++;
        }
        T leftChild;
        T rightChild;
        int currentNode;
        T temp;
        for (int j = size / 2; j > 0; j--) {
            currentNode = j;
            while (currentNode * 2 <= size) {
                leftChild = backingArray[currentNode * 2];
                if (currentNode * 2 + 1 <= size) {
                    rightChild = backingArray[currentNode * 2 + 1];
                    if ((rightChild.compareTo(leftChild) > 0) && backingArray[currentNode].compareTo(leftChild) > 0) {
                        temp = backingArray[currentNode];
                        backingArray[currentNode] = backingArray[currentNode * 2];
                        backingArray[currentNode * 2] = temp;
                        currentNode *= 2;
                    } else if (leftChild.compareTo(rightChild) > 0
                            && backingArray[currentNode].compareTo(rightChild) > 0) {
                        temp = backingArray[currentNode];
                        backingArray[currentNode] = backingArray[currentNode * 2 + 1];
                        backingArray[currentNode * 2 + 1] = temp;
                        currentNode = currentNode * 2 + 1;
                    } else {
                        break;
                    }
                } else {
                    if (backingArray[currentNode].compareTo(leftChild) > 0) {
                        temp = backingArray[currentNode];
                        backingArray[currentNode] = backingArray[currentNode * 2];
                        backingArray[currentNode * 2] = temp;
                        currentNode *= 2;
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
     * The order property of the heap must be maintained after adding.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You cannot add null data into the heap.");
        }
        if (size + 1 == backingArray.length) {
            T[] tempArray = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i < backingArray.length; i++) {
                tempArray[i] = backingArray[i];
            }
            backingArray = tempArray;
        }
        size++;
        backingArray[size] = data;
        int back = size;
        while (back > 1 && backingArray[back].compareTo(backingArray[back / 2]) < 0) {
            T temp = backingArray[back];
            backingArray[back] = backingArray[back / 2];
            backingArray[back / 2] = temp;
            back /= 2;
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after adding.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("You cannot remove an element from an empty heap.");

        }
        T store = backingArray[1];
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
                if ((rightChild.compareTo(leftChild) > 0) && backingArray[front].compareTo(leftChild) > 0) {
                    temp = backingArray[front];
                    backingArray[front] = backingArray[front * 2];
                    backingArray[front * 2] = temp;
                    front = front * 2;
                } else if (leftChild.compareTo(rightChild) > 0 && backingArray[front].compareTo(rightChild) > 0) {
                    temp = backingArray[front];
                    backingArray[front] = backingArray[front * 2 + 1];
                    backingArray[front * 2 + 1] = temp;
                    front = front * 2 + 1;
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
        return store;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty and you cannot get the minimum element.");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
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
