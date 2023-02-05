// --== CS400 Fall 2022 File Header Information ==--
// Name: <Wenpei>
// Email: <wshao33@wisc.edu>
// Team: <DE>
// TA: <April Roszkowski>
// Lecturer: <Florian Heimerl >
// Notes to Grader: <optional extra notes>
import java.util.List;

public interface IHouseSearcherFrontend {

    /**
     * The constructor that the implementation this interface will
     * provide. It takes the Scanner that will read user input as
     * a parameter as well as the backend and the sortedCollection.
     */
     //IHouseSearcherFrontend(Scanner userInputScanner, IHouseSearcherBackend backend)
     


    /**
     * This method starts the command loop for the frontend, and will
     * terminate when the user exists the app.
     */
    public void runCommandLoop();

    // to help make it easier to test the functionality of this program, 
    // the following helper methods will help support runCommandLoop():

    public void displayMainMenu(); // prints command options to System.out


    public void searchByPriceFE(); // reads price from System.in, displays results
    
    public void addHouse(); // add house from System.in, displays results

    /**
     * this method displays a list of house
     */
    void displayHouses(List<House> house);


}
