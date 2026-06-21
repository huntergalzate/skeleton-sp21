package bstmap;

import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V>, Iterable<K> {

    //instance variables for BSTMap
    private Node<K,V> root; //root of BST
    private Node<K,V> sentinel; //sentinel of BST
    private V deletedValue = null; //not thread safe?

    private static class Node<K extends Comparable<K>,V> {
        private K key;
        private V value;
        private Node<K,V> left;
        private Node<K,V> right;
        //private Node<K,V> parent;
        private int size;

        public Node(Node<K,V> left, Node<K,V> right, K key, V value, int size) {
            //this.parent = parent;
            this.left = left;
            this.key = key;
            this.value = value;
            this.right = right;
            this.size = size;
        }
    }

    public BSTMap() {
        sentinel = new Node<>(null, null, null, null, 0);
        sentinel.left = sentinel;
        sentinel.right = sentinel;
        this.root = sentinel;
    }
    /** Removes all of the mappings from this map. */
    public void clear() {
        this.root = sentinel;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        if (root == sentinel) {
            return false;
        } else {
            Node<K,V> findNode = get(root, key);
            return findNode != sentinel;
        }
    }


    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        Node<K,V> findNode = get(root, key);
        return findNode.value;
    }

    /*
    returns the node found with the specified key, searching/starting at node treeNode
     */
    private Node<K,V> get(Node<K,V> treeNode, K searchKey) {
        if (searchKey == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (treeNode == sentinel) {
            return sentinel;
        }
        /*
         0 -> equals
         < 0 -> searchKey is less than treeNode.key
         > 0 -> searchKey is greater than treeNode.key
         */
        int compareKeyValue = searchKey.compareTo(treeNode.key);

        if (compareKeyValue == 0) {
            return treeNode;
        } else if (compareKeyValue < 0) {
            return get(treeNode.left, searchKey);
        } else {
            return get(treeNode.right, searchKey);
        }
    }



    /* Returns the number of key-value mappings in this map. */
    public int size() {
        //the size field contains 1 (for itself) + size of subtree beneath that particular node
        return size(root);
    }

    private int size(Node<K,V> node) {
        if (node == null) {
            return 0;
        } else {
            return node.size;
        }
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        this.root = put(this.root, key, value);
    }

    private Node<K,V> put(Node<K,V> sNode, K insertKey, V insertValue) {
        if (sNode == sentinel) {
            Node<K,V> insertNode = new Node<>(sentinel, sentinel, insertKey, insertValue, 1);
            return insertNode;
        }
        int compareKeyValue = insertKey.compareTo(sNode.key);
        if (compareKeyValue < 0) {
            sNode.left = put(sNode.left, insertKey, insertValue);
        } else if (compareKeyValue > 0) {
            sNode.right = put(sNode.right, insertKey, insertValue);
        } else {
            //could update if wanted to here. but don't per the assignment specs
            //sNode.value = insertValue;
        }
        sNode.size = sNode.left.size + sNode.right.size + 1;
        return sNode;
    }


    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    public Set<K> keySet() {
        Set<K> llSet = new LinkedHashSet<>();

        //using my iterator on this (BSTMap)
        for (K keyValue : this) {
            llSet.add(keyValue);
        }
        return llSet;
    }

    public String printInOrder() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (K keyValues : this) {
            sb.append(keyValues);
            sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        this.root = remove(this.root, key);
        return this.deletedValue;
    }

    private Node<K,V> remove(Node<K,V> sNode, K searchKey) {
        if (sNode == sentinel) {
            return sentinel;
        }
        int compareKeyValue = searchKey.compareTo(sNode.key);
        if (compareKeyValue < 0) {
            sNode.left = remove(sNode.left, searchKey);

        } else if (compareKeyValue > 0) {
            sNode.right = remove(sNode.right, searchKey);

        } else { //we found it. we need to delete. deletion phase
            deletedValue = sNode.value; //store the soon to be deleted value before swapping pointers
            if (sNode.left == sentinel) {
                return sNode.right;
            } else if (sNode.right == sentinel) {
                return sNode.left;
            } else {
                //deleting with two children
                Node<K,V> successor = sNode.right;
                while (successor.left != sentinel) {
                    successor  = successor .left;
                }
                //copy the successor <key, value> data into the node we are deleting
                sNode.key = successor.key;
                sNode.value = successor.value;

                //delete the original successor node
                sNode.right = remove(sNode.right, successor.key);
            }
         }
        sNode.size = sNode.left.size + sNode.right.size + 1;
        return sNode;
    }


    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value) {
        Node<K,V> foundNode = get(this.root, key);
        if (foundNode == sentinel || !foundNode.value.equals(value)) {
            return null;
        }
        return remove(key);
    }


    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }
    private class BSTMapIterator implements Iterator<K> {
        private Deque<Node<K,V>> stack;


        //go as far left as possible, pushing nodes onto the stack as we visit them
        public BSTMapIterator() {
            stack = new ArrayDeque<>();
            Node<K,V> itCurrent = root;
            while (itCurrent != sentinel) {
                stack.push(itCurrent);
                itCurrent = itCurrent.left;
            }
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        //once the constructor has finished, we are done going left from that particular subtree
        //pop off the most far-left ('smallest') node, store its key value in a return variable,
        //and then go to the right sub-tree, then go as far left as possible again
        //repeating this process will always do the InOrder Traversal of left-root-right
        public K next() {
            Node<K,V> nodePopped = stack.pop();
            K returnKeyValue = nodePopped.key;
            Node<K,V> itCurrent = nodePopped.right;
            while(itCurrent != sentinel) {
                stack.push(itCurrent);
                itCurrent = itCurrent.left;
            }
            return returnKeyValue;
        }
    }
}
