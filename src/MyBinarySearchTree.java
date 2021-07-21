import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Create by Kannika Armstrong
 * TCSS342(Spring 2021): April 27, 2021
 * Assignment 3 - Word Search (MyBinarySearchTree class)
 * Professor. Christopher Paul Marriott
 */
public class MyBinarySearchTree<Type extends Comparable<Type>> {

    private Node root;  // the root of the binary search tree
    private int size;   // the number of items stored in the tree
    private boolean balancing;  // balancing - determines if the tree rebalanced itself
    // true - AVL tree
    // false - binary search tree
    int count = 0; // use to count how many time contain() called
    int rotateCount = 0; // use to count how many time of the rotation

    public MyBinarySearchTree() {
        this.root = null;
        this.size = 0;
    }
    public MyBinarySearchTree(boolean balancing) {
        this.root = null;
        this.size = 0;
        this.balancing = balancing;
    }

    /**
     * The public method calls the private method on the root.
     */
    public void add(Type item) {
        root = add(item, root);
        size++;
    }

    /**
     * The private method is recursive and will add the item to the tree rooted at subTree.
     * It returns the root of the new subtree.
     */
    private Node add(Type item, Node subtree) {
        // Empty tree or end of a leaf
        if (subtree == null) {
            subtree = new Node(item);
        }
        // If item is less than current node then move to left child node
        else if (item.compareTo(subtree.item) < 0) {
            // Set the node's left child to the left subtree with item added
            subtree.left = add(item, subtree.left);
        }
        // If item is greater than current node then traverse to right child node
        else {
            // Set the node's right child to the right subtree with item added
            subtree.right = add(item, subtree.right);
        }

        if (balancing) {
        updateStats(subtree);
//        System.out.println("Update Stats Height: " + subtree.height);
//        System.out.println("Update Stats Balance Factor: " + subtree.balanceFactor);
        subtree = rebalance(subtree);
        }
        return subtree;
    }

    /**
     * The public method calls the private method on the root.
     */
    public void remove(Type item) {
        root = remove(item, root);
        size--;
    }

    /**
     * The private method is recursive and will remove the item from the tree rooted at subTree.
     * It returns the root of the new subtree.
     */

    private Node remove(Type item, Node subtree) {
        // If item is less than node's item, continue to left subtree
        if (item.compareTo(subtree.item) < 0) {
            subtree.left = remove(item, subtree.left);
        }
        // If item is less than node's item, continue to right subtree
        else if (item.compareTo(subtree.item) > 0) {
            subtree.right = remove(item, subtree.right);
        }
        // found node containing object with same item, so delete it
        else {
            // if node is a leaf,return null
            if (subtree.left == null && subtree.right == null) {
                subtree = null;
            }
            // if node has a single right child node, then return a reference to the right child node
            else if (subtree.left == null) {
                subtree = subtree.right;
            }
            // if node has a single left child node, then return a reference to the left child node
            else if (subtree.right == null) {
                subtree = subtree.left;
            }
            // if the node has two child nodes
            else {
                subtree = this.remove(subtree);
            }
        }
        if (balancing) {
            updateStats(subtree);
//        System.out.println("Update Stats Height: " + subtree.height);
//        System.out.println("Update Stats Balance Factor: " + subtree.balanceFactor);
            subtree = rebalance(subtree);
        }
        return subtree;
    }

    private Node remove(Node node) {
        // if node is a leaf,return null
        if (node.left == null && node.right == null) {
            node = null;
        }
        // if node has a single right child node then return a reference to the right child node
        else if (node.left == null) {
            node = node.right;
        }
        // if node has a single left child node, then return a reference to the left child node
        else if (node.right == null) {
            node = node.left;
        }
        // if the node has two child nodes
        else {
            // get next Smaller Item, which is Largest Item in Left Subtree
            // The next Smaller Item is stored at the rightmost node in the left subtree.
            Node current = node.left;
            Type largestItem;
            if (current.right == null) {
                largestItem = current.item;
            } else {
                largestItem = current.right.item;
            }
            // replace the node's item with this item
            node.item = largestItem;
            // delete the rightmost node in the left subtree
            node.left = removeLargestItem(node.left);
        }
        return node;
    }

    private Node removeLargestItem(Node node) {
        // if no right child, then this node contains the largest item
        // so replace it with its left child
        if (node.right == null) {
            node = node.left;
        }
        // if not, keep looking on the right
        else {
            node.right = removeLargestItem(node.right);
        }
        return node;
    }

    /**
     * The public method calls the private method on the root.
     */
    public boolean contain(Type item) {
        return contain(item, root);
    }

    /**
     * The private method is recursive and will search for the item in the tree rooted at subTree.
     */
    private boolean contain(Type item, Node subtree) {
        Node temp = subtree;
        while (temp != null) {
            if (item.compareTo(temp.item) == 0) {
                count++;
                return true;
            } else if (item.compareTo(temp.item) < 0) {
                count++;
                temp = temp.left;
            } else {
                count++;
                temp = temp.right;
            }
        }
 //       count++;
        return false;
    }

    /**
     * Use to returns the (0 based) height of the tree
     * https://www.techiedelight.com/calculate-height-binary-tree-iterative-recursive/
     */
    public int height() {
        // empty tree has a height of 0
        if (root == null) {
            return 0;
        }
        // create an empty queue and enqueue the root node
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);
        Node front = null;
        int height = 0;
        // loop till queue is empty
        while (!queue.isEmpty()) {
            // calculate the total number of nodes at the current level
            int size = queue.size();
            // process each node of the current level and enqueue their
            // non-empty left and right child
            while (size-- > 0) {
                front = queue.poll();
                if (front.left != null) {
                    queue.add(front.left);
                }
                if (front.right != null) {
                    queue.add(front.right);
                }
            }
            // increment height by 1 for each level
            height++;
        }
        root.height = height - 1; // It is the 0 based
        return root.height;
    }

    /**
     * Use to returns the number of items in the tree
     * https://www.geeksforgeeks.org/write-program-calculate-size-tree-iterative/
     */
    public int size() {
        if (root == null) {
            size = 0;
        }
        // Using level order Traversal.
        Queue<Node> q = new ArrayDeque<>();
        q.offer(root);
        size = 1;
        while (!q.isEmpty()) {
            Node tmp = q.poll();
            // when the queue is empty:
            // the poll() method returns null.
            if (tmp != null) {
                if (tmp.left != null) {
                    // Increment count
                    size++;
                    // Enqueue left child
                    q.offer(tmp.left);
                }
                if (tmp.right != null) {
                    // Increment count
                    size++;
                    // Enqueue left child
                    q.offer(tmp.right);
                }
            }
        }
        return size;
    }

    /**
     * Use to returns true of the tree is empty
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    /**
     * Use to updates the height and balance factor of the node
     */
    private void updateStats(Node node) {
        int leftNodeHeight;
        int rightNodeHeight;
        if (node.left == null){
            leftNodeHeight = -1;
        } else {
            leftNodeHeight = node.left.height;
        }
        if (node.right == null){
            rightNodeHeight = -1;
        } else {
            rightNodeHeight = node.right.height;
        }
        // Update this node's height.
        node.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);
        //System.out.println("Update Stats Height: " + node.height);
        // Update balance factor.
        node.balanceFactor = rightNodeHeight - leftNodeHeight;
        //System.out.println("Update Stats Balance Factor: " + node.balanceFactor);
    }

    /**
     * Use to performs the a right rotation on the node
     */
    private Node rotateRight(Node node) {
        Node newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        updateStats(node);
        updateStats(newRoot);
        rotateCount++;
        return newRoot;
    }

    /**
     * Use to performs the a left rotation on the node
     */
    private Node rotateLeft(Node node) {
        Node newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        updateStats(node);
        updateStats(newRoot);
        rotateCount++;
        return newRoot;
    }

    /**
     * Use to checks the node for imbalance and corrects it if found
     */
    private Node rebalance(Node node) {
        // Imbalanced on the left
        if (node.balanceFactor <= -2) {
            // Left-Left case.
            if (node.left.balanceFactor <= 0) {
                return rotateRight(node);
                // Left-Right case.
            } else {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }

            // Imbalanced on the right
        } else if (node.balanceFactor >= 2) {
            // Right-Right case.
            if (node.right.balanceFactor >= 0) {
                return rotateLeft(node);
                // Right-Left case.
            } else {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }
        // Balanced node (-1,0,1)
        return node;
    }


    /**
     * outputs the contents of the tree using an in-order traversal of the tree
     */
    public String toString() {
        return inOrder(root);
    }

    private String inOrder(Node node) {
        String inorderNodes = "";
        if (node != null) {
            inorderNodes = inorderNodes + inOrder(node.left);
            inorderNodes = inorderNodes + node.toString() + " ";
            inorderNodes = inorderNodes + inOrder(node.right);
        }
        return inorderNodes;
    }

    /**
     * preOrder traversal
     */
    public String preOrder() {
        return this.preOrder(root);
    }

    private String preOrder(Node node) {
        String preOrderNodes = "";
        if (node != null) {
            preOrderNodes = preOrderNodes + node.toString() + " ";
            preOrderNodes = preOrderNodes + preOrder(node.left);
            preOrderNodes = preOrderNodes + preOrder(node.right);
        }
        return preOrderNodes;
    }

    /**
     * postOrder traversal
     */
    public String postOrder() {
        return this.postOrder(root);
    }

    private String postOrder(Node node) {
        String postOrderNodes = "";
        if (node != null) {
            postOrderNodes = postOrderNodes + postOrder(node.left);
            postOrderNodes = postOrderNodes + postOrder(node.right);
            postOrderNodes = postOrderNodes + node.toString() + " ";
        }
        return postOrderNodes;
    }

    // Binary Node class: a private inner class of MyBinarySearchTree
    private class Node {
        public Type item; //data stored in this node
        public Node left; // left child node
        public Node right; // right child node
        public int height; // the depth of tree based 0
        public int balanceFactor; //balance factor

        public Node(Type item) {
            this.item = item;
            this.left = null;
            this.right = null;
        }
        public String toString() {
            return item.toString();
        }
    }

}
