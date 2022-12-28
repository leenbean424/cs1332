import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index cannot be outside the bounds of the Linked List");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        DoublyLinkedListNode<T> node = new DoublyLinkedListNode<T>(data);
        if (head == null) {
            head = node;
            tail = node;
            size++;
        } else if (index == size) {
            addToBack(data);
        } else if (index == 0) {
            addToFront(data);
        } else if (index < size / 2) {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            node.setNext(curr.getNext());
            node.setPrevious(curr);
            curr.getNext().setPrevious(node);
            curr.setNext(node);
            size++;

        } else if (index >= size / 2) {
            DoublyLinkedListNode<T> curr = tail;
            for (int i = 0; i < size - index; i++) {
                curr = curr.getPrevious();
            }
            node.setNext(curr.getNext());
            node.setPrevious(curr);
            curr.getNext().setPrevious(node);
            curr.setNext(node);
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        DoublyLinkedListNode<T> node = new DoublyLinkedListNode<T>(data);
        node.setNext(head);
        node.setPrevious(null);
        if (size == 1) {
            tail.setPrevious(node);
        }
        if (head != null) {
            head.setPrevious(node);
        }
        head = node;
        if (size == 0) {
            tail = head;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        DoublyLinkedListNode<T> node = new DoublyLinkedListNode<T>(data);

        if (tail != null) {
            tail.setNext(node);
            node.setPrevious(tail);
            tail = node;
        }
        if (head == null) {
            head = node;
            tail = node;
        }
        if (size == 1) {
            tail = node;
            head.setNext(tail);
            tail.setPrevious(head);
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index cannot be outside the bounds of the Linked List");
        }
        DoublyLinkedListNode<T> current = head;
        if (index == 0) {
            removeFromFront();
        } else if (index == size - 1) {
            current = tail;
            removeFromBack();
        } else if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());
            size--;
        } else if (index >= size / 2) {
            current = tail;
            for (int i = 0; i >= size - index; i++) {
                current = current.getPrevious();
            }
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());
            size--;
        }

        return current.getData();
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("List cannot be empty");
        }
        T data = head.getData();
        head = head.getNext();
        head.getPrevious().setNext(null);
        head.setPrevious(null);
        size--;
        return data;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("List cannot be empty");
        }
        T data = tail.getData();
        tail = tail.getPrevious();
        tail.setNext(null);
        size--;
        return data;
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index cannot be outside the bounds of Linked List");
        }
        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else if (index < size / 2) {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            return curr.getData();
        } else if (index >= size / 2) {
            DoublyLinkedListNode<T> curr = tail;
            for (int i = 0; i < size - index - 1; i++) {
                curr = curr.getPrevious();
            }
            return curr.getData();
        }
        return null;
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (head == null && size == 0);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        head = null;
        tail = null;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        DoublyLinkedListNode<T> curr = tail;
        for (int i = 0; i < size - 1; i++) {
            if (curr.getData().equals(data)) {
                if (curr != head) {
                    curr.getPrevious().setNext(curr.getNext());
                }
                if (curr != tail) {
                    curr.getNext().setPrevious(curr.getPrevious());
                }
                size--;
                return curr.getData();
            } else {
                curr = curr.getPrevious();
            }
        }
        size--;
        return curr.getData();
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] dArr = new Object[size];
        DoublyLinkedListNode<T> curr = head;
        if (this.isEmpty()) {
            return dArr;
        }
        for (int i = 0; i < size; i++) {
            dArr[i] = curr.getData();
            curr = curr.getNext();
        }
        return dArr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
