import java.util.*;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize an empty BST.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("Cannot add null data");
            }
            add(element);
        }
    }
    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
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
     * Helper method that traverses a Bst and returns the node where the data should be added
     * @param data the data to add
     * @param curr Current node to compare data to
     * @return BSTNode Node where the data should be added
     */
    private BSTNode<T> helperAdd(T data, BSTNode<T> curr) {
        if (curr == null) {
            size++;
            return new BSTNode<T>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(helperAdd(data, curr.getLeft()));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(helperAdd(data, curr.getRight()));
        }
        return curr;
    }


    /**
     * Removes and returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data");
        }
        BSTNode<T> dummy = new BSTNode<T>(null);
        root = helperRemove(data, root, dummy);
        return dummy.getData();
    }

    /**
     * Helper method that returns the node we want removed
     * @param data The data to be removed
     * @param current Current node to compare data to
     * @param dummy Node to store removed data
     * @return BSTNode Node that we want to remove
     */
    private BSTNode<T> helperRemove(T data, BSTNode<T> current, BSTNode<T> dummy) {
        if (current == null) {
            throw new NoSuchElementException("Element does not exist in the tree.");
        }
        if (data.compareTo(current.getData()) < 0) {
            current.setLeft(helperRemove(data, current.getLeft(), dummy));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(helperRemove(data, current.getRight(), dummy));
        } else {
            dummy.setData(current.getData());
            size--;
            if (current.getLeft() == null && current.getRight() == null) {
                return null;
            } else if (current.getRight() == null) {
                return current.getLeft();
            } else if (current.getLeft() == null) {
                return current.getRight();
            } else {
                BSTNode<T> dummy2 = new BSTNode<>(null);
                current.setRight(removeSucessor(current.getRight(), dummy2));
                current.setData(dummy2.getData());
            }
        }
        return current;
    }

    /**
     * Helper method that removes a successor from a BST
     * @param current current node to compare data to
     * @param dummy Node to store removed data
     * @return BSTNode Node with removed successor
     */
    private BSTNode<T> removeSucessor(BSTNode<T> current, BSTNode<T> dummy) {
        if (current.getLeft() == null) {
            dummy.setData(current.getData());
            return current.getRight();
        } else {
            current.setLeft(removeSucessor(current.getLeft(), dummy));
        }
        return current;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        return getH(data, root);
    }

    /**
     * Helper get method that returns the data we want to get
     * @param data data we want to get
     * @param current Node to compare data to
     * @return T data from the tree that we wanted to get
     */

    private T getH(T data, BSTNode<T> current) {
        if (current == null) {
            throw new NoSuchElementException("Data does not exist in the BST.");
        } else if (data.compareTo(current.getData()) < 0) {
            return getH(data, current.getLeft());
        } else if (data.compareTo(current.getData()) > 0) {
            return getH(data, current.getRight());
        }
        return current.getData();
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
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
     * Helper method that traverses a BST recursively and returns if a BST contains a data element
     * @param data Data that we want to find
     * @param current Node to compare data to
     * @return boolean. Returns true if BST contains the data and false otherwise.
     */
    private boolean containsH(T data, BSTNode<T> current) {
        if (current == null) {
            return false;
        } else if (data.compareTo(current.getData()) < 0) {
            return containsH(data, current.getLeft());
        } else if (data.compareTo(current.getData()) > 0) {
            return containsH(data, current.getRight());
        }
        return true;
    }

    /**
     * Generate a pre-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        List<T> returnArr = new ArrayList<T>();
        preorderH(returnArr, root); // this will add into returnArr
        return returnArr;
    }

    /**
     * helper method that returns a list sorted with preorder traversal
     * @param returnArr array we want to change
     * @param current node to compare data with
     */
    private void preorderH(List<T> returnArr, BSTNode<T> current) {
        if (current == null) {
            return;
        }
        returnArr.add(current.getData());
        preorderH(returnArr, current.getLeft());
        preorderH(returnArr, current.getRight());
    }
    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        List<T> returnArr = new ArrayList<T>();
        inorderH(returnArr, root); // this will add into returnArr
        return returnArr;
    }

    /**
     * helper method that returns a list sorted with inorderH traversal
     * @param returnArr array we want to change
     * @param current node to compare data with
     */
    private void inorderH(List<T> returnArr, BSTNode<T> current) {
        if (current == null) {
            return;
        }
        inorderH(returnArr, current.getLeft());
        returnArr.add(current.getData());
        inorderH(returnArr, current.getRight());
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        List<T> returnArr = new ArrayList<T>();
        postorderH(returnArr, root); // this will add into returnArr
        return returnArr;
    }

    /**
     * helper method that returns a list sorted with postorderH traversal
     * @param returnArr array that we want to change
     * @param current node to compare data with
     */

    private void postorderH(List<T> returnArr, BSTNode<T> current) {
        if (current == null) {
            return;
        }
        postorderH(returnArr, current.getLeft());
        postorderH(returnArr, current.getRight());
        returnArr.add(current.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> result = new ArrayList<T>();
        Queue<BSTNode<T>> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> current = queue.remove();
            if (current != null) {
                result.add(current.getData());
                if (current.getLeft() != null) {
                    queue.add(current.getLeft());
                }
                if (current.getRight() != null) {
                    queue.add(current.getRight());
                }
            }
        }
        return result;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return heightH(0, root);
    }

    /**
     * helper method that returns the height of a given node (in this case the root)
     * @param height int to keep track of the height
     * @param current node to compare data to
     * @return int height of the root
     */

    private int heightH(int height, BSTNode<T> current) {
        if (current.getLeft() == null && current.getRight() == null) {
            return height;

        } else if (current.getLeft() == null) {
            return heightH(height + 1, current.getRight());
        } else if (current.getRight() == null) {
            return heightH(height + 1, current.getLeft());
        } else {
            return Math.max(heightH(height + 1, current.getRight()), heightH(height + 1, current.getLeft()));
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     * 
     * This must be done recursively.
     *
     * This list should contain the last node of each level.
     *
     * If the tree is empty, an empty list should be returned.
     *
     * Ex:
     * Given the following BST composed of Integers
     *      2
     *    /   \
     *   1     4
     *  /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     *
     * Ex:
     * Given the following BST composed of Integers
     *               50
     *           /        \
     *         25         75
     *       /    \
     *      12    37
     *     /  \    \
     *   11   15   40
     *  /
     * 10
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 10] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     *
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        List<T> result = new ArrayList<T>();
        result.add(root.getData());
        return maxDataH(result, root);
    }

    /**
     * helper method that traverses a list to find the max data of each level
     * @param list list of max data per level
     * @param current node to compare data to
     * @return list of max data per level
     */
    private List<T> maxDataH(List<T> list, BSTNode<T> current) {
        if (current.getRight() != null) {
            list.add(current.getRight().getData());
            if (current.getRight().getRight() != null) {
                maxDataH(list, current.getRight());
            } else if (current.getRight().getLeft() != null) {
                maxDataH(list, current.getRight());
            } else if (current.getLeft() != null) {
                maxDataH(list, current.getLeft());
            }
        } else if (current.getLeft() != null) {
            list.add(current.getLeft().getData());
            maxDataH(list, current.getLeft());
        }
        return list;
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
