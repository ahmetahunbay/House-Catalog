import java.io.FileNotFoundException;
import java.util.List;

/**
 * Instances of this interface can be used to load house data from a house file.
 */
public interface IHouseLoader {

    /**
     * This method loads the list of houses from an XML file.
     * @param filepathToXML path to the XML file relative to the executable
     * @return a list of house objects
     * @throws FileNotFoundException
     */
    List<House> loadHouses(String filepathToXML) throws FileNotFoundException;

}

