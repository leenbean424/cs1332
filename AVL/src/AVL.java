import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Collection cannot be null");
        }
        for (T element: data) {
            if (element == null) {
                throw new IllegalArgumentException("Data cannot be null");
            }
            add(element);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data");
        }
        root = helperAdd(data, root);
    }

    /**
     *
     * @param data the data we want to find
     * @param curr the node we are looking at
     * @return the current node to reinforce the AVL
     */
    private AVLNode<T> helperAdd(T data, AVLNode<T> curr) {
        if (curr == null) {
            size++;
            return new AVLNode<T>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(helperAdd(data, curr.getLeft()));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(helperAdd(data, curr.getRight()));
        }
        update(curr);
        if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() > -1) {
                curr = rightRotate(curr);
            } else {
                curr.setLeft(leftRotate(curr.getLeft()));
                curr = rightRotate(curr);
            }
        } else if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() < 1) {
                curr = leftRotate(curr);
            } else {
                curr.setRight(rightRotate(curr.getRight()));
                curr = leftRotate(curr);
            }
        }
        return curr;
    }

    /**
     * updates the height & balanceFactor of a passed in node
     * @param curr node we want to update
     */
    private void update(AVLNode<T> curr) {
        int j = -1;
        int k = -1;
        if (curr.getRight() != null) {
            k = curr.getRight().getHeight();
        }
        if (curr.getLeft() != null) {
            j = curr.getLeft().getHeight();
        }
        if (j > k) {
            curr.setHeight(j + 1);
        } else {
            curr.setHeight(k + 1);
        }
        curr.setBalanceFactor(j - k);
    }

    /**
     * helper method that implements the left rotate method
     * @param curr node that we are rotating on
     * @return node that we rotated
     */
    private AVLNode<T> leftRotate(AVLNode<T> curr) {
        AVLNode<T> node = curr.getRight();
        curr.setRight(node.getLeft());
        node.setLeft(curr);
        update(curr);
        update(node);
        return node;
    }

    /**
     * helper method that implements the right rotate method
     * @param curr node that we are rotating on
     * @return the node that we rotated
     */
    private AVLNode<T> rightRotate(AVLNode<T> curr) {
        AVLNode<T> node = curr.getLeft();
        curr.setLeft(node.getRight());
        node.setRight(curr);
        update(curr);
        update(node);
        return node;
    }

    /**
     * Performs rotate functions to the entire tree
     * @param curr node that we are looking at
     */
    private void rotate(AVLNode<T> curr) {
        if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() > -1) {
                curr = rightRotate(curr);
            } else {
                curr.setLeft(leftRotate(curr.getLeft()));
                curr = rightRotate(curr);
            }
        } else if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() < 1) {
                curr = leftRotate(curr);
            } else {
                curr.setRight(rightRotate(curr.getRight()));
                curr = leftRotate(curr);
            }
        }
    }
    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        } else {
            AVLNode<T> dummy = new AVLNode<T>(null);
            root = removeH(root, data, dummy);
            size--;
            return dummy.getData();
        }
    }

    /**
     * recursive remove helper
     * @param curr the node that we are looking at
     * @param data data that we want to remove
     * @param dummy dummy node to store the node that we want to remove
     * @return pointer that we are reinforcing
     */
    private AVLNode<T> removeH(AVLNode<T> curr, T data, AVLNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Data not found in the tree");
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeH(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeH(curr.getRight(), data, dummy));
        } else {
            dummy.setData(curr.getData());
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getRight() == null && curr.getLeft() != null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null && curr.getRight() != null) {
                return curr.getRight();
            } else {
                AVLNode<T> temp = new AVLNode<>(null);
                curr.setLeft(removePredecessor(curr.getLeft(), temp));
                curr.setData(temp.getData());
            }
        }
        update(curr);
        if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() > -1) {
                curr = rightRotate(curr);
            } else {
                curr.setLeft(leftRotate(curr.getLeft()));
                curr = rightRotate(curr);
            }
        } else if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() < 1) {
                curr = leftRotate(curr);
            } else {
                curr.setRight(rightRotate(curr.getRight()));
                curr = leftRotate(curr);
            }
        }
        return curr;
    }

    /**
     * 2nd helper method to remove, takes out the predecessor of the passed in node
     * @param curr node that we are looking at
     * @param node dummy node that stores predecessor data
     * @return pointer that we are reinforcing
     */
    private AVLNode<T> removePredecessor(AVLNode<T> curr, AVLNode<T> node) {
        if (curr.getRight() == null) {
            node.setData(curr.getData());
            return curr.getLeft();
        }
        curr.setRight(removePredecessor(curr.getRight(), node));
        update(curr);
        rotate(curr);
        return curr;
    }
    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        return getH(root, data);
    }

    /**
     * Helper method that returns data in tree that matches parameters passed in
     * using recursion
     * @param curr node used to traverse the tree
     * @param data data we want to find
     * @return the data T that we want to find
     */

    private T getH(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the tree");
        } else {
            int compare = data.compareTo(curr.getData());
            if (compare == 0) {
                return curr.getData();
            } else if (compare < 0) {
                return getH(curr.getLeft(), data);
            } else {
                return getH(curr.getRight(), data);
            }
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        return containsH(data, root);
    }

    /**
     * Helper method that returns if data is contained within a tree
     * @param data data we want to find
     * @param curr node used to traverse the tree
     * @return boolean depending on data is in the tree
     */
    private boolean containsH(T data, AVLNode<T> curr) {
        if (curr == null) {
            return false;
        } else {
            int compare = data.compareTo(curr.getData());
            if (compare == 0) {
                return true;
            } else if (compare < 0) {
                return containsH(data, curr.getLeft());
            } else {
                return containsH(data, curr.getRight());
            }
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with
     * the deepest depth.
     *
     * Should be recursive.
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (root == null) {
            return null;
        } else {
            return deepestNodeH(root);
        }
    }

    /**
     * helper method to recurse and find the maximum deepest node
     * @param curr node used to traverse
     * @return data that the deepest node is holding
     */
    private T deepestNodeH(AVLNode<T> curr) {
        if (curr.getHeight() == 0) {
            return curr.getData();
        } else {
            if (curr.getBalanceFactor() > 0) {
                return deepestNodeH(curr.getLeft());
            } else {
                return deepestNodeH(curr.getRight());
            }
        }
    }

    /**
     * In BSTs, you learned about the concept of the successor: the
     * smallest data that is larger than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the successor of the data
     * passed in. There are 2 cases to consider:
     * 1: The right subtree is non-empty. In this case, the successor is the
     * leftmost node of the right subtree.
     * 2: The right subtree is empty. In this case, the successor is the lowest
     * ancestor of the node whose left subtree contains the data.
     *
     * The second case means the successor node will be one of the node(s) we
     * traversed left from to find data. Since the successor is the SMALLEST element
     * greater than data, the successor node is the lowest/last node
     * we traversed left from on the path to the data node.
     *
     * This should NOT be used in the remove method.
     *
     * Should be recursive.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     * successor(76) should return 81
     * successor(81) should return 90
     * successor(40) should return 76
     *
     * @param data the data to find the successor of
     * @return the successor of data. If there is no larger data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T successor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be empty");
        }
        return successorH(root, data);
    }

    /**
     * helper method to find the successor data of a passed in node
     * @param curr node used for traversal
     * @param data data in the successor
     * @return the data of the successor
     */
    private T successorH(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the tree");
        } else {
            int compare = data.compareTo(curr.getData());
            if (compare == 0) {
                if (curr.getRight() != null) {
                    return rightSuccessor(curr.getRight());
                } else {
                    return null;
                }
            } else if (compare > 0) {
                return successorH(curr.getRight(), data);
            } else {
                T result = successorH(curr.getLeft(), data);
                if (result == null) {
                    return curr.getData();
                } else {
                    return result;
                }
            }
        }
    }

    /**
     * second helper method, similar to successorH, but with
     * the case of a right tree
     * @param curr the node that we are looking at
     * @return the data of the successor
     */
    private T rightSuccessor(AVLNode<T> curr) {
        if (curr.getLeft() == null) {
            return curr.getData();
        } else {
            return rightSuccessor(curr.getLeft());
        }
    }
    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
