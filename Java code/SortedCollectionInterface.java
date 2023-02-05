/**
 * An interface to do basic operations that involve our Red-Black Tree data structure
 * @param <T>
 */
public interface SortedCollectionInterface<T extends Comparable<T>>{

  /**
   * Inserts a houseADT into our Red-Black Tree
   * @param house - a House ADT
   * @return - true if inserted successfully, false otherwise
   * @throws NullPointerException - If houseADT param is null
   * @throws IllegalArgumentException - If parameter is not a HouseADT
   */
  public boolean insert(T house) throws NullPointerException, IllegalArgumentException;

  /**
   * Check if our Red-Black tree contains a certain house
   * @param house - House to be searched for in the tree
   * @return - True if the tree already contains this house
   *           False otherwise
   */
  public boolean contains(T house);

  /**
   * Returns the number of House objects in our Red-Black Tree
   * @return - an int representing # of houses in our tree
   */
  public int size();

  /**
   * Check if our Red-Black Tree is empty
   * @return - True if the tree is empty (size 0)
   *           False otherwise
   */
  public boolean isEmpty();

}
