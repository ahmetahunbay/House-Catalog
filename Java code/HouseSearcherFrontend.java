// --== CS400 Fall 2022 File Header Information ==--
// Name: <Wenpei>
// Email: <wshao33@wisc.edu>
// Team: <DE>
// TA: <April Roszkowski>
// Lecturer: <Florian Heimerl >
// Notes to Grader: <optional extra notes>
import java.util.List;
import java.util.Scanner;

public class HouseSearcherFrontend implements IHouseSearcherFrontend{
  Scanner userInputScanner;
  IHouseSearcherBackend backend;
  // Constructor 
  /**
   *     
   * The constructor that the implementation this interface will
   * provide. It takes the Scanner that will read user input as
   * a parameter as well as the backend and the sortedCollection.
   *
   * @param userInputScanner user input
   * @param backend backend method 
   */
  public HouseSearcherFrontend(Scanner userInputScanner, IHouseSearcherBackend backend) {
    this.userInputScanner = userInputScanner;
    this.backend = backend;
  }

  /**
   * The main command loop which calls other methods and
   *    controls the users interactions with the program
   */
  @Override
  public void runCommandLoop() {
    System.out.println("Welcome to the House Searcher Application!");
    System.out.println("x+x+xx+x+xx+x+xx+x+xx+x+xx+x+xx+x+xx+x+x");
    System.out.println();

    displayMainMenu();

    String userSelection = userInputScanner.next().trim();

    while(!userSelection.equals("4")) {

      if(userSelection.equals("1")) { // When user inputs 1 from Main Menu:
        searchByPriceFE();
      } else if(userSelection.equals("2")) { // When user inputs 2 from Main Menu:
        addHouse();
      }else if(userSelection.equals("3")) { // When user inputs 2 from Main Menu:
        removeHouse();
      }  else { // If input is something else, it is invalid
        System.out.println("Please enter a valid input (a number 1-3): ");
      }

      displayMainMenu();
      if(userInputScanner.hasNext())
        userSelection = userInputScanner.next(); // Get next userInput for each while-loop cycle
    } // end of while loop that waits for input '4'

    System.out.println("Goodbye!");
  }

/**
 * this method prints command options to System.out
 */
  @Override
  public void displayMainMenu() {
    System.out.println("You are in the Main Menu:");
    System.out.println("           1) Search for House");
    System.out.println("           2) Add House");
    System.out.println("           3) remove House");
    System.out.println("           4) Exit Application");
    System.out.println();
    
  }
/**
 * this method displays a list of house
 */
  @Override
  public void displayHouses(List<House> house) {
    for(int i = 0; i < house.size(); i++) {
      House curHouse = house.get(i);
      System.out.println(i+1 + ". Price: $" + curHouse.getPrice() + "\n  Bathrooms: " + curHouse.getNumBaths() +
          "\n  Bedrooms: " + curHouse.getNumBedrooms());
      System.out.println();
    }    
  }
/**
 * this method reads price from System.in, displays results
 */
  @Override
  public void searchByPriceFE() {
    boolean filterBaths=false;
    boolean filterBeds=false;
    System.out.println("You are in the Search by House Menu:");
    System.out.println("—-----------------------------------");
    System.out.print("Enter minimum price: ");
    userInputScanner.nextLine();
    int minPrice = userInputScanner.nextInt();
    System.out.print("Enter maximum price: ");
    int maxPrice = userInputScanner.nextInt();
    System.out.println();
    System.out.print("Would you also like to sort by bedrooms? (yes/no) ");
    userInputScanner.nextLine();
    String input = userInputScanner.nextLine();
    while(!input.equals("yes")&&!input.equals("no")) {
      System.out.print("Would you also like to sort by bedrooms? (yes/no) ");
      input = userInputScanner.nextLine();
    }
    if(input.equals("yes")) {
      System.out.print("How many bedrooms would you like your house to have? ");
      filterBaths = true;
      int numBed = userInputScanner.nextInt();
      userInputScanner.nextLine();
      while(numBed<0) {
        System.out.println("ERROR: An invalid amount of bedrooms/bathrooms was specified. ");
        System.out.println("How many bedrooms does your house have? ");
        numBed = userInputScanner.nextInt();
        userInputScanner.nextLine();
      }
      backend.setNumBaths(numBed);
    }
    System.out.print("Would you also like to sort by bathrooms? (yes/no) ");
    //userInputScanner.nextLine();
    input = userInputScanner.nextLine();
    while(!input.equals("yes")&&!input.equals("no")) {
      System.out.print("Would you also like to sort by bathrooms? (yes/no) ");
      input = userInputScanner.nextLine();
    }
    if(input.equals("yes")) {
      filterBaths=true;
      System.out.print("How many bathrooms would you like your house to have? ");
      int numBath = userInputScanner.nextInt();
      userInputScanner.nextLine();
      while(numBath<=0) {
        System.out.println("ERROR: An invalid amount of bedrooms/bathrooms was specified. ");
        System.out.println("How many bathrooms does your house have? ");
        numBath = userInputScanner.nextInt();
        userInputScanner.nextLine();
      }
      backend.setNumBaths(numBath);
    }
    List<House> house = backend.searchByPrice(minPrice,maxPrice,filterBaths,filterBeds);
    if(house.isEmpty()) {
      System.out.println("Nothing found");
    }else
    displayHouses(house);
  }
/**
 * this method add house from System.in, displays results
 */
  @Override
  public void addHouse() {
    System.out.println("You are in the Add House Menu: ");
    System.out.println("—-----------------------------------");
    System.out.print("What is the price of your house? ");   
    int price = userInputScanner.nextInt();
    while(price<0) {
      System.out.print("What is the price of your house? ");   
      price = userInputScanner.nextInt();
    }
    System.out.println("How many bedrooms does your house have? ");
    int numBed = userInputScanner.nextInt();
    while(numBed<0) {
      System.out.println("ERROR: An invalid amount of bedrooms/bathrooms was specified. ");
      System.out.println("How many bedrooms does your house have? ");
      numBed = userInputScanner.nextInt();
    }
    
    System.out.println("How many bathrooms does your house have? ");
    int numBath = userInputScanner.nextInt();
    while(numBath<0) {
      System.out.println("ERROR: An invalid amount of bedrooms/bathrooms was specified. ");
      System.out.println("How many bathrooms does your house have? ");
      numBath = userInputScanner.nextInt();
      System.out.println("Add Success! ");
    }
    House newHourse = new House(price, numBath, numBed);
    backend.addHouse(newHourse);
  }
  public void removeHouse() {
    System.out.println("You are in the remove House Menu: ");
    System.out.println("—-----------------------------------");
    System.out.print("What is the price of the house? ");   
    int price = userInputScanner.nextInt();
    while(price<0) {
      System.out.print("What is the price of the house? ");   
      price = userInputScanner.nextInt();
    }
    System.out.println("How many bedrooms does the house have? ");
    int numBed = userInputScanner.nextInt();
    while(numBed<0) {
      System.out.println("ERROR: An invalid amount of bedrooms/bathrooms was specified. ");
      System.out.println("How many bedrooms does the house have? ");
      numBed = userInputScanner.nextInt();
    }
    System.out.println("How many bathrooms does the house have? ");
    int numBath = userInputScanner.nextInt();
    while(numBath<0) {
      System.out.println("ERROR: An invalid amount of bedrooms/bathrooms was specified. ");
      System.out.println("How many bathrooms does the house have? ");
      numBath = userInputScanner.nextInt();
    }
    House newHourse = new House(price, numBath, numBed);
    backend.removeHouse(newHourse);
    System.out.println("Remove Success! ");
  }
  /**
   * Main method to test runCommandLoop()
   * @param args - unused
   */
  public static void main(String[] args) {
    Scanner scnr = new Scanner(System.in);
    HouseSearcherBackend backend = new HouseSearcherBackend();
    HouseSearcherFrontend frontend = new HouseSearcherFrontend(scnr, backend);
    frontend.runCommandLoop();
  }


}
