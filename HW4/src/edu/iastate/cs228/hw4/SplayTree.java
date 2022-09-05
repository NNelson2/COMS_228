package edu.iastate.cs228.hw4;


import com.sun.source.tree.BinaryTree;

import java.util.*;


/**
 * 
 * @author Noah Nelson
 *
 */


/**
 * 
 * This class implements a splay tree.  Add any helper methods or implementation details 
 * you'd like to include.
 *
 */


public class SplayTree<E extends Comparable<? super E>> extends AbstractSet<E>
{
	protected Node root; 
	protected int size;
	protected StringBuilder theTree;
	protected boolean hasNoKids;

	public class Node  // made public for grading purpose
	{
		public E data;
		public Node left;
		public Node parent;
		public Node right;

		public Node(E data) {
			this.data = data;
		}

		@Override
		public Node clone() {
			return new Node(data);
		}

		public boolean hasNokids()
		{
			if (left == null && right == null) {
				return true;
			} else {
				return false;
			}
		}
	}

	
	/**
	 * Default constructor constructs an empty tree. 
	 */
	public SplayTree() 
	{
		size = 0;
	}
	
	
	/**
	 * Needs to call addBST() later on to complete tree construction. 
	 */
	public SplayTree(E data) 
	{
		// TODO
		size = 1;
		root = new Node(data);
	}

	
	/**
	 * Copies over an existing splay tree. The entire tree structure must be copied.  
	 * No splaying. Calls cloneTreeRec(). 
	 * 
	 * @param tree
	 */
	public SplayTree(SplayTree<E> tree)
	{
		size = tree.size();
		root = cloneTreeRec(tree.root);
	}

	
	/**
	 * Recursive method called by the constructor above. 
	 * 
	 * @param subTree
	 * @return
	 */
	public Node cloneTreeRec(Node subTree)
	{
		Node current = new Node(null);
		if (subTree != null) {
			if (subTree.left != null) {
				current.left = cloneTreeRec(subTree.left);
			}
			if (subTree.right != null) {
				current.right = cloneTreeRec(subTree.right);
			}
			current.data = subTree.data;
			current.parent = subTree.parent;
		}
		return current;
	}
	
	
	/**
	 * This function is here for grading purpose. It is not a good programming practice.
	 * 
	 * @return element stored at the tree root 
	 */
	public E getRoot()
	{
		return root.data;
	}
	
	
	@Override 
	public int size()
	{
		return size;
	}
	
	
	/**
	 * Clear the splay tree. 
	 */
	@Override
	public void clear() 
	{
		root = new Node(null);
		size = 0;
	}
	
	
	// ----------
	// BST method
	// ----------
	
	/**
	 * Adds an element to the tree without splaying.  The method carries out a binary search tree
	 * addition.  It is used for initializing a splay tree. 
	 * 
	 * Calls link(). 
	 * 
	 * @param data
	 * @return true  if addition takes place  
	 *         false otherwise (i.e., data is in the tree already)
	 */
	public boolean addBST(E data)
	{
		Boolean searching = true;
		Node adderNode = new Node(data);
		if (root == null) {
			root = adderNode;
			size++;
			return true;
		}
		if (helperContains(root, new Node(data))) {
			return false;
		} else {
			Node current = root;
			while (searching) {
				int comp = current.data.compareTo(data);
				if (comp > 0) {
					if (current.left != null) {
						current = current.left;

					} else {
						link(current, adderNode);
						size++;
						return true;
					}
				} else {
					if (current.right != null) {
						current = current.right;

					} else {
						link(current, adderNode);
						size++;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	// ------------------
	// Splay tree methods 
	// ------------------
	
	/**
	 * Inserts an element into the splay tree. In case the element was not contained, this  
	 * creates a new node and splays the tree at the new node. If the element exists in the 
	 * tree already, it splays at the node containing the element. 
	 * 
	 * Calls link(). 
	 * 
	 * @param  data  element to be inserted
	 * @return true  if addition takes place 
	 *         false otherwise (i.e., data is in the tree already)
	 */
	@Override 
	public boolean add(E data)
	{
		if (findEntry(data).data.compareTo(data) == 0 ) {
			splay(findEntry(data));
			return false;

		} else {
			addBST(data);
			splay(findEntry(data));
			size++;
			return true;
		}
	}
	
	
	/**
	 * Determines whether the tree contains an element.  Splays at the node that stores the 
	 * element.  If the element is not found, splays at the last node on the search path.
	 * 
	 * @param  data  element to be determined whether to exist in the tree
	 * @return true  if the element is contained in the tree 
	 *         false otherwise
	 */
	public boolean contains(E data)
	{
		Node dataNode = new Node(data);
		splay(findEntry(data));
		return helperContains(root, dataNode);
	}

	/**
	 * Recursive method to determine if the tree contains an element
	 * @param current the current node
	 * @param dataNode node containing the data
	 * @return true if element is found
	 */
	private boolean helperContains(Node current, Node dataNode)
	{
		if (current == null) {
			return false;

		} else if (current.data.compareTo(dataNode.data) == 0) {
			return true;

		} else if (current.data.compareTo(dataNode.data) > 0) {
			return helperContains(current.left, dataNode);

		} else {
			return helperContains(current.right, dataNode);

		}
	}
	
	
	/**
	 * Finds the node that stores the data and splays at it.
	 *
	 * @param data
	 */
	public void splay(E data) 
	{
		splay(findEntry(data));
	}

	
	/**
	 * Removes the node that stores an element.  Splays at its parent node after removal
	 * (No splay if the removed node was the root.) If the node was not found, the last node 
	 * encountered on the search path is splayed to the root.
	 * 
	 * Calls unlink(). 
	 * 
	 * @param  data  element to be removed from the tree
	 * @return true  if the object is removed 
	 *         false if it was not contained in the tree 
	 */
	public boolean remove(E data)
	{
		Node temp = findEntry(data);

		if (temp == root && temp.hasNokids()) {
			clear();
			return true;
		}
		if (temp.hasNokids()) {
			if(temp.data.compareTo(temp.parent.data) < 0) {
				temp.parent.left = null;

			} else {
				temp.parent.right = null;
			}
			splay(temp.parent);
			return true;
		}

		if (!helperContains(root, temp)) {
			splay(findEntry(data));
			return false;
		} else {
			if (findEntry(data) == root) {
				unlink(findEntry(data));
			} else {
				unlink(findEntry(data));
				if (temp.parent != null) {
					splay(temp.parent);
				}
			}

			return true;
		}
	}


	/**
	 * This method finds an element stored in the splay tree that is equal to data as decided 
	 * by the compareTo() method of the class E.  This is useful for retrieving the value of 
	 * a pair <key, value> stored at some node knowing the key, via a call with a pair 
	 * <key, ?> where ? can be any object of E.   
	 * 
	 * Calls findEntry(). Splays at the node containing the element or the last node on the 
	 * search path. 
	 * 
	 * @param  data
	 * @return element such that element.compareTo(data) == 0
	 */
	public E findElement(E data) 
	{
		Node current = findEntry(data);
		return current.data;
	}

	
	/**
	 * Finds the node that stores an element. It is called by methods such as contains(), add(), remove(), 
	 * and findElement(). 
	 * 
	 * No splay at the found node. 
	 *
	 * @param  data  element to be searched for 
	 * @return node  if found or the last node on the search path otherwise
	 *         null  if size == 0. 
	 */
	public Node findEntry(E data)
	{
		if (size == 0) {
			return null;
		}
		Boolean searching = true;
		Node current = root;
		while (searching) {
			if (current.data.compareTo(data) == 0) {
				return current;
			}
			int comp = current.data.compareTo(data);
			if (comp > 0) {
				if (current.left != null) {
					current = current.left;

				} else {
					return current;
				}
			} else {
				if (current.right != null) {
					current = current.right;

				} else {
					return current;
				}
			}
		}
		return null; 
	}
	
	
	/** 
	 * Join the two subtrees T1 and T2 rooted at root1 and root2 into one.  It is 
	 * called by remove(). 
	 * 
	 * Precondition: All elements in T1 are less than those in T2. 
	 * 
	 * Access the largest element in T1, and splay at the node to make it the root of T1.  
	 * Make T2 the right subtree of T1.  The method is called by remove(). 
	 * 
	 * @param root1  root of the subtree T1 
	 * @param root2  root of the subtree T2 
	 * @return the root of the joined subtree
	 */
	public Node join(Node root1, Node root2)
	{
		SplayTree<E> tree1 = new SplayTree<E>();
		tree1.root = root1;
		Node root1largest = tree1.root;
		tree1.root.parent = null;
		while (root1largest.right != null){
			root1largest = root1largest.right;
		}
		tree1.splay(root1largest);
		root1largest.right = root2;
		root2.parent = root1largest;
		root1largest.parent = null;
		return root1largest;
	}

	
	/**
	 * Splay at the current node.  This consists of a sequence of zig, zigZig, or zigZag 
	 * operations until the current node is moved to the root of the tree.
	 * 
	 * @param current  node to splay
	 */
	public void splay(Node current)
	{
		while (current != root) {
			if (current.parent.parent != null && current.parent.parent.left == current.parent && current.parent.left == current) {
				zigZig(current);

			} else if (current.parent.parent != null &&current.parent.parent.right == current.parent && current.parent.right == current) {
				zigZig(current);

			} else if (current.parent.parent != null &&current.parent.parent.left == current.parent && current.parent.right == current) {
				zigZag(current);

			} else if (current.parent.parent != null &&current.parent.parent.right == current.parent && current.parent.left == current) {
				zigZag(current);

			} else {
				zig(current);
			}
		}
		root.parent = null;
	}

	

	/**
	 * This method performs the zig operation on a node. Calls leftRotate() 
	 * or rightRotate().
	 * 
	 * @param current  node to perform the zig operation on
	 */
	protected void zig(Node current)
    {
		if (current == current.parent.left) {
			rightRotate(current);
		} else {
			leftRotate(current);
		}
	}

	
	/**
	 * This method performs the zig-zig operation on a node. Calls leftRotate() 
	 * or rightRotate().
	 * 
	 * @param current  node to perform the zig-zig operation on
	 */
	protected void zigZig(Node current)
	{
		if (current == current.parent.left) {
			rightRotate(current.parent);
			rightRotate(current);
		} else {
			leftRotate(current.parent);
			leftRotate(current);
		}
	}

	
    /**
	 * This method performs the zig-zag operation on a node. Calls leftRotate() 
	 * and rightRotate().
	 * 
	 * @param current  node to perform the zig-zag operation on
	 */
	protected void zigZag(Node current)
	{
		if (current == current.parent.left) {
			rightRotate(current);
			leftRotate(current);
		} else {
			leftRotate(current);
			rightRotate(current);
		}
	}	
	
	
	/**
	 * Carries out a left rotation at a node such that after the rotation 
	 * its former parent becomes its left child. 
	 * 
	 * Calls link(). 
	 * 
	 * @param current
	 */
	public void leftRotate(Node current)
	{
		if (current.parent != root) {
			Node temp = current.left;
			Node temp2 = current.parent.parent;
			link(current, current.parent);
			if (temp == null) {
				current.left.right = null;
			} else {
				link(current.left, temp);
			}
			link(temp2, current);

		} else {
			Node temp = current.left;
			link(current, current.parent);
			if (temp == null) {
				current.left.right = null;
			} else {
				link(current.left, temp);
			}
			root = current;
			current.parent = null;
		}

	}

	
	/**
	 * Carries out a right rotation at a node such that after the rotation 
	 * its former parent becomes its right child. 
	 * 
	 * Calls link(). 
	 * 
	 * @param current
	 */
	public void rightRotate(Node current)
	{

		if (current.parent != root) {
			Node temp = current.right;
			Node temp2 = current.parent.parent;
			link(current, current.parent);
			if (temp == null) {
				current.right.left = null;
			} else {
				link(current.right, temp);
			}
			link(temp2, current);

		} else {
			Node temp = current.right;
			link(current, current.parent);
			if (temp == null) {
				current.right.left = null;
			} else {
				link(current.right, temp);
			}
			root = current;
		}

	}	
	
	
	/**
	 * Establish the parent-child relationship between two nodes. 
	 * 
	 * Called by addBST(), add(), leftRotate(), and rightRotate(). 
	 * 
	 * @param parent
	 * @param child
	 */
	private void link(Node parent, Node child) 
	{
		if (child.data.compareTo(parent.data) >= 0) {
			parent.right = child;

		} else {
			parent.left = child;
		}
		child.parent = parent;
	}
	
	
	/** 
	 * Removes a node n by replacing the subtree rooted at n with the join of the node's
	 * two subtrees.
	 * 
	 * Called by remove().   
	 * 
	 * @param n
	 */
	private void unlink(Node n) 
	{
		Node nParent = n.parent;
		Node joinedRoot = join(n.left, n.right);
		if (nParent != null) {
			if (joinedRoot.data.compareTo(nParent.data) < 0) {
				nParent.left = joinedRoot;

			} else {
				nParent.right = joinedRoot;
			}
			joinedRoot.parent = nParent;

		} else {
			root = joinedRoot;
		}

	}
	
	
	/**
	 * Perform BST removal of a node. 
	 * 
	 * Called by the iterator method remove(). 
	 * @param n
	 */
	public void unlinkBST(Node n)
	{
		if (n == root) {
			unlink(n);
			return;
		}
		if (n.hasNokids() && n.parent == null) {
			clear();
		} else if (n.hasNokids()) {
			if(n.data.compareTo(n.parent.data) < 0) {
				n.parent.left = null;

			} else {
				n.parent.right = null;
			}

		} else if (n.right == null || n.left == null) {
			if (n.right != null) {
				link(n.parent, n.right);

			} else {
				link(n.parent,n.left);

			}

		} else {
			Node searchStart = n.right;
			while (searchStart.left != null) {
				searchStart = searchStart.left;

			}
			unlinkBST(searchStart);
			n.data = searchStart.data;
		}
	}
	
	
	/**
	 * Called by unlink() and the iterator method next(). 
	 * 
	 * @param n
	 * @return successor of n 
	 */
	public Node successor(Node n)
	{
		return n.parent;
	}

	
	@Override
	public Iterator<E> iterator()
	{
	    return new SplayTreeIterator();
	}

	
	/**
	 * Write the splay tree according to the format specified in Section 2.2 of the project 
	 * description.
	 * 	
	 * Calls toStringRec(). 
	 *
	 */
	@Override 
	public String toString()
	{
		theTree = new StringBuilder();
		hasNoKids = false;
		theTree.append(root.data);
		toStringRecNoReturn(root.left, 1);
		toStringRecNoReturn(root.right, 1);
		return theTree.toString();
	}


	private void toStringRecNoReturn(Node n, int depth)
	{
		if (n == null && hasNoKids) {
			hasNoKids = false;
			return;
		} else if (n == null) {
			theTree.append("\n");
			for(int x = 0; x < 4 * depth; x++) {
				theTree.append(" ");
			}
			theTree.append("null");
			return;
		}
		theTree.append("\n");
		for(int x = 0; x < 4 * depth; x++) {
			theTree.append(" ");
		}
		theTree.append(n.data);
		if(n.hasNokids()){
			hasNoKids = true;
		}
		toStringRecNoReturn(n.left, depth + 1);
		if(n.hasNokids()){
			hasNoKids = true;
		}
		toStringRecNoReturn(n.right, depth + 1);
	}



	/**
	   *
	   * Iterator implementation for this splay tree.  The elements are returned in 
	   * ascending order according to their natural ordering.  The methods hasNext()
	   * and next() are exactly the same as those for a binary search tree --- no 
	   * splaying at any node as the cursor moves.  The method remove() behaves like 
	   * the class method remove(E data) --- after the node storing data is found.  
	   *  
	   */
	private class SplayTreeIterator implements Iterator<E>
	{
		Node cursor;
		Node pending;
		ArrayList<Node> passed = new ArrayList<Node>();
		boolean leftOfRoot;

		public SplayTreeIterator()
	    {
			Node current = root;
			while (current.left != null) {
				current = current.left;
			}

			if (current.data.compareTo(root.data) < 0) {
				leftOfRoot = true;
			} else {
				leftOfRoot = false;
			}

			cursor = null;
			pending = current;
	    }

	    @Override
	    public boolean hasNext()
	    {
			if (pending == null) {
				return false;
			} else {
				return true;
			}
	    }

	    @Override
	    public E next()
	    {
			cursor = pending;
			if (pending.right != null) {
				pending = pending.right;
				while (pending.left != null){
					pending = pending.left;
				}

			} else {
				pending = pending.parent;
			}
			return cursor.data;
		}

	    /**
	     * This method will join the left and right subtrees of the node being removed, 
	     * and then splay at its parent node.  It behaves like the class method 
	     * remove(E data) after the node storing data is found.  Place the cursor at the 
	     * parent (or the new root if removed node was the root).
	     * 
	     * Calls unlinkBST(). 
	     * 
	     */
	    @Override
	    public void remove()
	    {
	      // TODO

	    }
	}
}
