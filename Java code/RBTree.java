
// --== CS400 Role Code Project ==--
// Name: Ahmet Ahunbay
// CSL Username: ahunbay
// Email: aaahunbay@wisc.edu
// Lecture #: 003 @2:25pm
// Notes to Grader: N/A
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Stack;


/**
 * Red-Black Tree implementation that is meant to store objects of type house within HouseNodes
 */
public class RBTree<HouseADT> implements IRBTree {

    protected HouseNode root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree\
    
    /**
     * Method to check if the tree is empty (does not contain any node).
     * @return true of this.size() return 0, false if this.size() > 0
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    /**
     * Maintains RBTree properties upon the insertion of a new node
     * @param node is the new red node being inserted
     */
    protected void enforceRBTreePropertiesAfterInsert(HouseNode node) {
    	//checks if new node is root, sets to black if is
    	if(node.parent == null) {
    		node.blackHeight = 1;
    		return;
    	}
    	//checks if parent is black, returns if true
    	if(node.parent.blackHeight == 1) {
    		return;
    	}
    	//checks if node has a black aunt/uncle regardless of side
    	if((node.parent.isLeftChild() && (node.parent.parent.rightChild == null || node.parent.parent.rightChild.blackHeight == 1 )) 
    			|| (!node.parent.isLeftChild() && (node.parent.parent.leftChild == null || node.parent.parent.leftChild.blackHeight == 1 ))) {
    		//rotates for alignment and recurses
    		if(node.isLeftChild() != node.parent.isLeftChild()) {
    			HouseNode stored = node.parent;
    			rotate(node, node.parent);
    			enforceRBTreePropertiesAfterInsert(stored);
    			return;
    		}
    		//rotates
    		rotate(node.parent, node.parent.parent);
    		//recolors
    		node.parent.blackHeight = 1;
    		node.parent.leftChild.blackHeight = 0;
    		node.parent.rightChild.blackHeight = 0;
    		//returns
    		return;
    	}
    	//checks if node has a red aunt or uncle regardless of side
    	if((node.parent.isLeftChild() && node.parent.parent.rightChild.blackHeight == 0 ) 
    			|| (!node.parent.isLeftChild() && node.parent.parent.leftChild.blackHeight == 0 )) {
    		//recolors
    		node.parent.parent.blackHeight = 0;
    		node.parent.parent.leftChild.blackHeight = 1;
    		node.parent.parent.rightChild.blackHeight = 1;
    		//recurses up tree to check for RBTree property violations
    		enforceRBTreePropertiesAfterInsert(node.parent.parent);
    	}
	
    }
   

    /**
     * Recursive helper method to find the subtree with a null reference in the
     * position that the newNode should be inserted, and then extend this tree
     * by the newNode in that position.
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which the 
     *      newNode should be inserted as a descenedent beneath
     * @return true is the value was inserted in subtree, false if not
     */
    private boolean insertHelper(HouseNode newNode, HouseNode subtree) {
        int compare = newNode.data.compareTo(subtree.data);
        // do not allow duplicate values to be stored within this tree
        if(compare == 0) return false;

            // store newNode within left subtree of subtree
        else if(compare < 0) {
            if(subtree.leftChild == null) { // left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
                // otherwise continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.leftChild);
        }

        // store newNode within the right subtree of subtree
        else {
            if(subtree.rightChild == null) { // right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                enforceRBTreePropertiesAfterInsert(newNode);
                return true;
                // otherwise continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.rightChild);
        }
    }
    
    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * rightChild of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    private void rotate(HouseNode child, HouseNode parent) throws IllegalArgumentException {
    	try {
    		//checks for nonexistent parent/child
			if(parent == null || child == null){
				throw new IllegalArgumentException();
			}
			//checks for left rotation about parent
			if(parent.rightChild != null && parent.rightChild.equals(child)){
				//saves potentially important nodes
				HouseNode a = child.leftChild;
		        HouseNode b = child.rightChild;
	
		        //sets new child parent
		        child.parent = parent.parent;	
		        //sets grandparent child(if not null)
		        if(parent.parent != null) {		      
		        	//checks if parent is leftChild of grandparent
		        	if(parent.parent.leftChild != null && parent.parent.leftChild.equals(parent)) {		  
		        		//sets
		        		parent.parent.leftChild = child;		                	
		        	}	
		        	//checks if parent is leftChild of grandparent
		        	if(parent.parent.rightChild != null && parent.parent.rightChild.equals(parent)) {
		        		//sets
		        		parent.parent.rightChild = child;		                	
		        	}		             
		        }		 
		        //sets parent's parent
		        parent.parent = child;
		        //sets parent's right child
		        parent.rightChild = a;
		        //changes relevant child's child
		        if(a != null){		            
		        	a.parent = parent;		             
		        }
		        //sets child's child        		               
		        child.leftChild = parent;
		        //checks for root switch
		        if(child.parent == null) {		        
		        	this.root = child;		        
		        }
		    //checks for right rotation about parent    
			} else if(parent.leftChild != null && parent.leftChild.equals(child)){
				//saves potentially important nodes
				HouseNode a = child.leftChild;
		        HouseNode b = child.rightChild;
	
		        //sets new child parent
		        child.parent = parent.parent;	
		        //sets grandparent child(if not null)
		        if(parent.parent != null) {		      
		        	//checks if parent is leftChild of grandparent
		        	if(parent.parent.leftChild != null && parent.parent.leftChild.equals(parent)) {		  
		        		//sets
		        		parent.parent.leftChild = child;		                	
		        	}	
		        	//checks if parent is leftChild of grandparent
		        	if(parent.parent.rightChild != null && parent.parent.rightChild.equals(parent)) {
		        		//sets
		        		parent.parent.rightChild = child;		                	
		        	}		             
		        }		 
		        //sets parent's parent
				parent.parent = child;
				//sets leftChild of parent
				parent.leftChild = b;
				//changes relevant node
				if(b != null){
					b.parent = parent;
				}
				//sets child's right child
				child.rightChild = parent;
				//checks for rootswitch
				if(child.parent == null) {
					this.root = child;
				}
					
			}else{
				//if neither case works, arguments lack relationship and exception is thrown
				throw new IllegalArgumentException();
			}
    	}catch(Exception e) {
    		//backup exception throw
    		throw new IllegalArgumentException();
    	}

    }
    /**
     * Get the size of the tree (its number of nodes).
     * @return the number of nodes in the tree
     */
    public int size() {
    	return size;
    }


    /**
     * Recursive helper method that recurses through the tree and looks
     * for the value *data*.
     * @param data the data value to look for
     * @param subtree the subtree to search through
     * @return true of the value is in the subtree, false if not
     */
    private boolean containsHelper(House data, HouseNode subtree) {
        if (subtree == null) {
            // we are at a null child, value is not in tree
            return false;
        } else {
            int compare = data.compareTo(subtree.data);
            if (compare < 0) {
                // go left in the tree
                return containsHelper(data, subtree.leftChild);
            } else if (compare > 0) {
                // go right in the tree
                return containsHelper(data, subtree.rightChild);
            } else {
                // we found it :)
                return true;
            }
        }
    }

    /**
     * Inserts a provided object into the tree at the correct location, 
     * then repairs the tree according to Red-Black properties.
     * @param data, the ValueType object to insert
     * @return whether data was inserted
     * @throws IllegalArgumentException if data is of an invalid type to be added to the tree
     * @throws NullPointerException if the data reference has a null pointer
     */
	@Override
	public boolean insert(Comparable data) throws NullPointerException, IllegalArgumentException {
		House real = (House)data;
		// null references cannot be stored within this tree
        if(data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");
        
        HouseNode newNode = new HouseNode(real);
        // add first node to an empty tree
        if(root == null) {
        	root = newNode; size++; root.blackHeight = 1; return true; 
		} else{
            boolean returnValue = insertHelper(newNode,root); // recursively insert into subtree
            if (returnValue) size++;
            else throw new IllegalArgumentException(
                    "This RedBlackTree already contains that value.");
            
            root.blackHeight = 1;
            return returnValue;
		}
	}
	
	/**
	   * Removes the provided ValueType object from the tree if it exists, 
	   * then repairs the tree according to Red-Black properties.
	   * @param data, the ValueType object to remove
	   * @return the removed object if it was removed, null if nothing was removed
	   */
	@Override
	public House remove(Comparable data) {
		//casts the comparable data argument
		House real = (House)data;
		//finds its node with helper method
		HouseNode replaced = findHouseNode(real, root);
		//checks if no node exists
		if(replaced == null) {
		return null;
		}
		//uses removal helper method
		removeHelper(replaced);
		size--;	
		return real;
	}
	
	/**
	 * helper method for remove method. Checks basic repair operations
	 * @param replaced node being removed
	 */
	private void removeHelper(HouseNode replaced) {	
		//uses helper method to find replacement
		HouseNode replacement = makeReplacement(replaced);
		
		//remove only node
		if (replaced.parent == null && replacement == null) {	
			root = null;
			return;
		}
		
		//remove red leaf or red with red replacement
		if(replaced.blackHeight == 0 
				&& (replacement == null || replacement.blackHeight == 0)) {
			replace(replaced, replacement);
			return;
		}
		
		//remove red node with black replacement
		if(replaced.blackHeight == 0 && replacement.blackHeight == 1) {
			//recurses through to perform operation on removed black node
			tempReplace(replaced, replacement);
			removeHelper(replacement);
			return;
		}
	
		//replacing black node
		if(replaced.blackHeight == 1) {
			//with null
			HouseNode weighted;			
			boolean storeBool = replaced.isLeftChild();
			HouseNode storeParent = replaced.parent;
			if(replacement == null) {
				
				replace(replaced, replacement);
				//creates weighted node
				weighted = new HouseNode(storeParent, storeBool);
				//runs through repair enforcement operations
				removeRepair(weighted);
				return;
			}
			//with black
			if(replacement.blackHeight == 1) {
				//recurses and removes node being replaced
				tempReplace(replaced, replacement);
				removeHelper(replacement);
				return;
			}			
			//with red
			if(replacement.blackHeight == 0) {
				//simple operation gets completed and returns
				replace(replaced, replacement);
				replacement.blackHeight = 1;
				
				return;
			}
		}
		return;
	}
	
	/**
	 * helper method to enforce repair after removal
	 * @param weighted the weighted black node
	 */
	private void removeRepair(HouseNode weighted) {
		//checks if the weighted node is the root
		if(root.equals(weighted)) {
			return;
		}
		//checks first repair operation on document
		if((weightedLeft(weighted) && weighted.parent.rightChild.blackHeight == 1 
				&& (weighted.parent.rightChild.leftChild != null && weighted.parent.rightChild.leftChild.blackHeight == 0)
				&& (weighted.parent.rightChild.leftChild.leftChild == null || weighted.parent.rightChild.leftChild.leftChild.blackHeight == 1))
				||(weightedRight(weighted) && weighted.parent.leftChild.blackHeight == 1 
				&& (weighted.parent.leftChild.rightChild != null && weighted.parent.leftChild.rightChild.blackHeight == 0)
				&& (weighted.parent.leftChild.rightChild.rightChild == null || weighted.parent.leftChild.rightChild.rightChild.blackHeight == 1))) {
			//checks if weighted node is on right or left, rotates and recolors accordingly
			if(weightedLeft(weighted)) {
				rotate(weighted.parent.rightChild.leftChild, weighted.parent.rightChild);
				weighted.parent.rightChild.blackHeight = 1;
				weighted.parent.rightChild.rightChild.blackHeight = 0;
			}
			if(weightedRight(weighted)) {
				rotate(weighted.parent.leftChild.rightChild, weighted.parent.leftChild);
				weighted.parent.leftChild.blackHeight = 1;
				weighted.parent.leftChild.leftChild.blackHeight = 0;
			}
		}
		//checks second repair operation on document
		if((weightedLeft(weighted) && weighted.parent.rightChild.blackHeight == 1 
				&& (weighted.parent.rightChild.rightChild != null && weighted.parent.rightChild.rightChild.blackHeight == 0))
				||((weightedRight(weighted)) && weighted.parent.leftChild.blackHeight == 1 
				&& (weighted.parent.leftChild.leftChild != null && weighted.parent.leftChild.leftChild.blackHeight == 0))) {
			//stores color of parent
			int bColor = weighted.parent.blackHeight;
			//checks if weighted node is on right or left, rotates and recolors accordingly
			if(weightedLeft(weighted)) {
				rotate(weighted.parent.rightChild, weighted.parent);
				weighted.parent.parent.rightChild.blackHeight = 1;
			}
			if(weightedRight(weighted)) {
				rotate(weighted.parent.leftChild, weighted.parent);
				weighted.parent.parent.leftChild.blackHeight = 1;
			}
			//does consistent recolor
			weighted.parent.blackHeight = 1;
			weighted.parent.parent.blackHeight = bColor;
			//checks if weighted node should exist
			if(weighted.data == null) {
				if(weightedLeft(weighted)) {
					weighted.parent.leftChild = null;
				}else if(weightedRight(weighted)) {
					weighted.parent.rightChild = null;
				}
			}
			return;
		}
		//checks third repair operation on document
		if((weightedLeft(weighted) && weighted.parent.rightChild.blackHeight == 1 
				&& (weighted.parent.rightChild.rightChild == null || weighted.parent.rightChild.rightChild.blackHeight == 1)
				&& (weighted.parent.rightChild.leftChild == null || weighted.parent.rightChild.leftChild.blackHeight == 1))
				||(weightedRight(weighted) && weighted.parent.leftChild.blackHeight == 1 
				&& (weighted.parent.leftChild.rightChild == null || weighted.parent.leftChild.rightChild.blackHeight == 1)
				&& (weighted.parent.leftChild.leftChild == null || weighted.parent.leftChild.leftChild.blackHeight == 1))) {
			//checks if weighted node is on right or left, rotates and recolors accordingly
			if(weightedLeft(weighted)) {
				weighted.parent.rightChild.blackHeight = 0;
			}
			if(weightedRight(weighted)) {
				weighted.parent.leftChild.blackHeight = 0;
			}
			//checks if it is red parent variation
			if(weighted.parent.blackHeight == 0) {
				weighted.parent.blackHeight = 1;
				//checks if weighted should be deleted
				if(weighted.data == null) {
					if(weightedLeft(weighted)) {
						weighted.parent.leftChild = null;
					}else if(weightedRight(weighted)) {
						weighted.parent.rightChild = null;
					}
				}
				return;
			}else {
				//recurses if it is black parent variation
				removeRepair(weighted.parent);
				if(weighted.data == null) {
					//checks if weighted should be deleted
					if(weightedLeft(weighted)) {
						weighted.parent.leftChild = null;
					}else if(weightedRight(weighted)) {
						weighted.parent.rightChild = null;
					}
				}
				return;
			}
		}
		
		//checks last case on document
		if(weighted.parent.blackHeight == 1 
				&& ((weightedLeft(weighted) && weighted.parent.rightChild.blackHeight == 0)
				|| (weightedRight(weighted) && weighted.parent.rightChild.blackHeight == 0))) {
			//checks if left or right weighted, rotates accordingly
			if(weightedLeft(weighted)) {
				rotate(weighted.parent.rightChild, weighted.parent);
			} else if(weightedRight(weighted)) {
				rotate(weighted.parent.leftChild, weighted.parent);
			}
			//recolors
			weighted.parent.blackHeight = 0;
			weighted.parent.parent.blackHeight = 1;
			//recurses
			removeRepair(weighted);
			//checks if weighted should be deleted
			if(weighted.data == null) {
				if(weightedLeft(weighted)) {
					weighted.parent.leftChild = null;
				}else if(weightedRight(weighted)) {
					weighted.parent.rightChild = null;
				}
			}
			return;
		}	

	}
	
	
	/**
	* @param data, the ValueType object to check
	* @return whether the tree contains the object specified in data
	*/	
	@Override
	public boolean contains(Comparable data) {
		return this.containsHelper((House)data, root);
	}


	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return new HouseIterator(root);
	}

	@Override
	public Iterator iterateAtRange(Integer min, Integer max) {

		return new HouseIterator(root, min, max);
	}
	
	/**
	 * Prints RBT in text file for visualization
	 */
	public void treeDisplay() {
		//in stores number of levels
		int in = (int)(Math.log(size)/Math.log(2));
		ArrayList<HouseNode> topLevel = new ArrayList<HouseNode>();
		topLevel.add(root);
		Path textFile = Path.of("C:\\Users\\14146\\eclipse-workspace\\P2Clean\\tree.txt");
		try {
			Files.writeString(textFile,treeDisplayHelper(topLevel, new StringBuilder(),in));
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}
	
	/**
	 * Recursive helper method
	 * @param level row of RBT
	 * @param sb stringbuilder for the txt file
	 * @param in number of indents for each row
	 * @return string representation of RBT in which black nodes are surrounded in stars
	 */
	private String treeDisplayHelper(ArrayList<HouseNode> level, StringBuilder sb , int in) {
		StringBuilder spacing = new StringBuilder();
		
		//use logarithmic function for determining indentation(not perfect)
		for(int i = 0;i< Math.pow(in, 2) +1;i++) {
			spacing.append("  ");
		}
		String indent = spacing.toString();
		sb.append(indent);

		ArrayList<HouseNode> nextLevel = new ArrayList<HouseNode>();
		for(int i = 0;i<level.size();i++) {
			
			if(level.get(i) != null) {
				
				sb.append((level.get(i).blackHeight == 0) ? level.get(i).data.price : "*" + level.get(i).data.price + "*" );
				sb.append(indent);
				nextLevel.add(level.get(i).leftChild);
				nextLevel.add(level.get(i).rightChild);				
			}else {
				if(i%2 == 0) sb.append(indent);
				sb.append(indent);
				
				nextLevel.add(null);
				nextLevel.add(null);
			}
		}
		
		sb.append("\n\n");
		
		if(in == 0) {
			return sb.toString();
		}
		
		return treeDisplayHelper(nextLevel,sb, in-1);
	}
	
	/**
	 * helper method to tell if weighted node is right or left
	 * @param weighted node being checked
	 * @return true if left
	 */
	private boolean weightedLeft(HouseNode weighted) {
		if(weighted.isLeftChild()) {
			return true;
		}
		if(weighted.parent.leftChild != null && weighted.parent.leftChild.blackHeight == 2) {
			return true;
		}
		return false;
	}
	/**
	 * helper method to tell if weighted node is right or left
	 * @param weighted node being checked
	 * @return true if right
	 */
	private boolean weightedRight(HouseNode weighted) {
		if(weighted.isRightChild()) {
			return true;
		}
		if(weighted.parent.rightChild != null && weighted.parent.rightChild.blackHeight == 2) {
			return true;
		}
		return false;
	}
	
	/**
	 * Helper method that replaces a node with another
	 * @param n node being replaced
	 * @param o node replacing
	 */
	private void replace(HouseNode n, HouseNode o) {
		//sets n's parent's configurations
		if(n.isLeftChild()) {			
			n.parent.leftChild = o;
		}
		if(n.isRightChild()) {		
			n.parent.rightChild = o;
		}
		//checks if o exists
		if(o==null) {
			return;
		}
		//sets o's parent's configurations
		if(o.isLeftChild()) {
			HouseNode replace = makeReplacement(o);
			o.parent.leftChild = replace;
		}
		if(o.isRightChild()) {
			HouseNode replace = makeReplacement(o);
			o.parent.rightChild = replace;
		}
		//sets o's parent and children
		o.parent = n.parent;
		if(n.leftChild == null || !n.leftChild.equals(o)) {
			
			o.leftChild = n.leftChild;
		}
		if(n.rightChild == null || !n.rightChild.equals(o)) {
			
			o.rightChild = n.rightChild;
		}
		//checks for new root
		if (o.parent == null) {
			root = o;
			
		}

	}
	/**
	 * copy-replacing helper method for recursion
	 * @param n
	 * @param o
	 */
	private void tempReplace(HouseNode n, HouseNode o) {
		n.data = o.data;
	}
	/**
	 * finds node recursively for data
	 * @param data data of node
	 * @param subtree node searched
	 * @return node of data
	 */
	private HouseNode findHouseNode(House data, HouseNode subtree) {

          int compare = data.compareTo(subtree.data);
          if (compare < 0) {
              // go left in the tree
              return findHouseNode(data, subtree.leftChild);
          } else if (compare > 0) {
              // go right in the tree
              return findHouseNode(data, subtree.rightChild);
          } else if(compare == 0) {
              // we found it :)
              return subtree;
          } else {
          	return null;
          }
//      }
	}
	
	/**
	 * finds replacement of node
	 * @param subtree node needing replacement
	 * @return node doing replacing
	 */
	private HouseNode makeReplacement(HouseNode subtree) {
		if(subtree.leftChild == null && subtree.rightChild == null) {
			return null;
		}
		if(subtree.leftChild == null && subtree.rightChild != null) {
			return subtree.rightChild;
		}
		if(subtree.leftChild != null && subtree.rightChild == null) {
			
			return subtree.leftChild;
		}
		
		HouseNode minisub = subtree.rightChild;
		
		while(minisub.leftChild != null) {
			minisub = minisub.leftChild;
		}
		
		return minisub;
			
		
	}

	
}
