// --== CS400 Project Two File Header ==--
// Name: Spencer Ball / Ahmet Ahunbay
// CSL Username: sball / ahunbay
// Email: sjball@wisc.edu / aahunbay@wisc.edu
// Lecture #: 003
// Notes to Grader: none

import java.util.Iterator;

/**
 * Interface for a Red-Black Tree general implementation.
 * Extends the base methods from SortedCollectionInterface, and adds functionality for removal and constrained iteration.
 *
 * @param T - comparable type of data stored in tree
 */
public interface IRBTree<T extends Comparable<T>> extends SortedCollectionInterface<T>, Iterable<T> {

  /**
   * Inserts a provided object into the tree at the correct location, 
   * then repairs the tree according to Red-Black properties.
   * @param data, the ValueType object to insert
   * @return whether data was inserted
   * @throws IllegalArgumentException if data is of an invalid type to be added to the tree
   * @throws NullPointerException if the data reference has a null pointer
   */
  public boolean insert(T data) throws NullPointerException, IllegalArgumentException;

  /**
   * @param data, the ValueType object to check
   * @return whether the tree contains the object specified in data
   */
  public boolean contains(T data);

  /**
   * @return the number of objects in the tree
   */
  public int size();

  /**
   * @return whether the tree contains any objects (false) or is empty (true)
   */
  public boolean isEmpty();
  
  /**
   * Removes the provided ValueType object from the tree if it exists, 
   * then repairs the tree according to Red-Black properties.
   * @param data, the ValueType object to remove
   * @return the removed object if it was removed, null if nothing was removed
   */
  public T remove(T data); 
  
  /**
   * Returns an iterator through the Red-Black Tree.
   * @param min: any object < min in the tree should be omitted from the iterator.
   * @param max: any object > max in the tree should be omitted from the iterator.
   * min and max are inclusive.
   * min and/or max can be null, signifying no constraint
   * For instance, for a tree with values 1, 2, and 3:
   *    iterateAtRange(null, null) returns an iterator over 1, 2, and 3
   *    iterateAtRange(2, null) returns an iterator over 2 and 3
   *    iterateAtRange(1, 2) returns an iterator over 1 and 2
   * @return Iterator<T> of T objects within the range
   */
  public Iterator<T> iterateAtRange(Integer min, Integer max);

  
  

}
