// --== CS400 Fall 2022 File Header Information ==--
// Name: Aidan Carrig
// Email: acarrig@wisc.edu
// Team: DE
// TA: April Roszkowski
// Lecturer: Florian Heimerl
// Notes to Grader: N/A

public class House implements Comparable<House>, HouseADT {

  // Instance variables/fields for a House
  int price;
  int numBaths;
  int numBedrooms;

  // Constructor
  public House(int price, int numBaths, int numBedrooms) {
    this.price = price;
    this.numBaths = numBaths;
    this.numBedrooms = numBedrooms;
  }

  /**
   * Returns the price of the house object
   * @return - the price as an int
   */
  public int getPrice() {
    return this.price;
  }

  /**
   * The number of bathrooms in the house -- assumes there are no half baths
   * @return - The number of bathrooms as an int
   */
  public int getNumBaths() {
    return this.numBaths;
  }

  /**
   * Gets the number of bedrooms in the house
   * @return - The number of bedrooms in the house as an int
   */
   public int getNumBedrooms() {
     return this.numBedrooms;
   }

  /**
   * Compares to House objects.
   * @param o - A house object that will be compared to the THIS.compareTo(House obj)
   * @return - -1 if this is cheaper than o
   *                or if same price this has fewer baths, or
   *                    if same price, same # of baths if this has fewer bedrooms than o
   *             0 if the price, # baths, # of beds between this and o is the same
   *             1 if this is more expensive than o
   *                or if same price this has more baths than o
   *                    or if same price, same # baths, this has more bedrooms than o
   */
  public int compareTo(House o) {
    if(price < (o).price) { // if price of o greater than HOUSE return -1
      return -1;
    } else if(price> (o).price) { // If price of o less than HOUSE.compareTo(o) return 1
      return 1;
    } else { // prices are equal? compare # of baths
      if(this.numBaths < (o).numBaths) {
        return -1;
      } else if(this.numBaths > (o).numBaths) {
        return 1;
      } else { // price and # baths is equal? compare beds
        if(this.numBedrooms < (o).numBedrooms) {
          return -1;
        } else if(this.numBedrooms > (o).numBedrooms) {
          return 1;
        } else {
          return 0;
        }
      }
    }
  }

}

