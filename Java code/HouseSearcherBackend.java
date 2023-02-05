// --== CS400 Fall 2022 File Header Information ==--
// Name: Aidan Carrig
// Email: acarrig@wisc.edu
// Team: DE
// TA: April Roszkowski
// Lecturer: Florian Heimerl
// Notes to Grader: N/A

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implements the search and filter functionality for house objects
 */
public class HouseSearcherBackend implements IHouseSearcherBackend {

  private RBTree<House> tree;
  private int numBaths;
  private int numBedrooms;

  // Constructor
  public HouseSearcherBackend() {
    tree = new RBTree<>();
  }

  /**
   * Loop through the list of houses brought in from the data wrangler
   * and add them to our RBTree
   * @param houseList - A list of houses read in from the data wrangler/xml file
   */
  @Override
  public void loadData(List<House> houseList) {
    for (House house : houseList) {
      this.addHouse(house);
    }
  }

  /**
   * Removes a house from the red-black tree
   * @param house - house to be removed
   */
  @Override
  public void removeHouse(House house) {
      tree.remove(house);
    }

  /**
   * Adds a house to the red black tree
   * @param house - House object to be added to tree
   */
  @Override
  public void addHouse(House house) {
    tree.insert(house);
  }

  /**
   * Sets the filter for the number of baths a house must have
   * @param numBaths - num of baths a house must have
   */
  @Override
  public void setNumBaths(int numBaths) {
    this.numBaths = numBaths;
  }

  /**
   * Sets the filter for the number of bedrooms a house will need
   * @param numBedrooms - # of bedrooms in a house
   */
  @Override
  public void setNumBedrooms(int numBedrooms) {
    this.numBedrooms = numBedrooms;
  }

  /**
   * Returns the number of baths filter for a house
   * @return - # of baths a house needs as an int
   */
  @Override
  public int getNumBaths() {
    return numBaths;
  }

  /**
   * Returns the number of bedrooms a house must have
   * @return - int # of bedrooms a house must have to match filter
   */
  @Override
  public int getNumBedrooms() {
    return numBedrooms;
  }

  /**
   * Returns the number of houses in our red black tree
   * @return - # of houses as an int in our redBlackTree
   */
  @Override
  public int getNumberOfHouses() {
    return tree.size();
  }

  /**
   * Resets the numBaths Filter (to 0)
   */
  @Override
  public void resetNumBaths() {
    numBaths = 0;
  }

  /**
   * Resets the numBedrooms filter (to 0)
   */
  @Override
  public void resetNumBedrooms() {
    numBedrooms = 0;
  }

  /**
   * Search for house with specific criteria
   * This method will use the above numBaths and numBedrooms filters as well as
   *    the price parameters to only add houses with all the criteria
   *    to a list of House objects
   *
   * @param minPrice - min price of house to look for
   * @param maxPrice - max price of house to look for
   * @param filterBaths - do we also want to filter by # baths?
   * @param filterBeds - do we also want to filter by # bedrooms?
   * @return - A list of houses meeting ALL the criteria
   */
  @Override
  public List<House> searchByPrice(int minPrice, int maxPrice, boolean filterBaths, boolean filterBeds) {
    ArrayList<House> houseList = new ArrayList<>();

    Iterator houseIterator = tree.iterateAtRange(minPrice, maxPrice);
    while(houseIterator.hasNext()) {
      House currentHouse = (House) houseIterator.next();
      if(currentHouse.price >= minPrice && currentHouse.price <= maxPrice) {
        if (filterBaths && !filterBeds) { // just filter by # baths
          if (currentHouse.getNumBaths() == this.numBaths) houseList.add(currentHouse);
        } else if (filterBeds && !filterBaths) { // just filter by # beds
          if (currentHouse.getNumBedrooms() == numBedrooms) houseList.add(currentHouse);
        } else if (filterBaths && filterBeds) { // filter by # beds and # baths
          if (currentHouse.getNumBaths() == numBaths && currentHouse.getNumBedrooms() == numBedrooms)
            houseList.add(currentHouse);
        } else { // dont filter by # beds or # baths
          houseList.add(currentHouse);
        }
      }
    }

    return houseList;
  }

}

