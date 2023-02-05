import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class HouseLoader implements IHouseLoader {
    public List<House> loadHouses(String filepathToXML) throws FileNotFoundException {
        try {
            List<House> list = new ArrayList<House>();
            // storing the xml file in a File object
            File origFile = new File(filepathToXML);
            // checking if it exists or not
            if (!origFile.exists() || !filepathToXML.substring(filepathToXML.length() - 3).equals("xml")) {
                throw new FileNotFoundException();
            }
            Scanner scanner = new Scanner(origFile);
            String line;
            // to check if we're in a house and should be looking for paramters or not
            boolean inHouse = false;
            int price = 0;
            int numBaths = 0;
            int numBedrooms = 0;
            // going through the xml file line by line
            while (scanner.hasNext()) {
                line = scanner.nextLine();
                line = line.trim();
                // if the line starts with <row> then this is a new house that we need to store
                if (line.length() >= 4 && line.substring(1, 4).equals("row")) {
                    inHouse = true;
                } else if (line.length() >= 5 && line.substring(1, 5).equals("/row")) {// this means that we reached the
                                                                                       // end of our house object and
                                                                                       // should store the data now
                    list.add(new House(price, numBaths, numBedrooms));
                    inHouse = false;
                }
                if (inHouse) {// if we're in a house object
                    if (line.length() >= 6 && line.substring(1, 6).equals("price")) {// if this line is a price field ->
                                                                                     // store it into (String)price
                        String sPrice = "";
                        for (int i = 7; i < line.length(); i++) {
                            if (line.charAt(i) == '<') {
                                break;
                            }
                            sPrice += line.charAt(i);
                        }
			price = Integer.parseInt(sPrice);
                    } else if (line.length() >= 9 && line.substring(1, 9).equals("bedrooms")) {// if this line is a
                                                                                               // bedrooms field ->
                        // store it into (int)numBedrooms
                        numBedrooms = 0;
                        String bedrooms = "";
                        for (int i = 10; i < line.length(); i++) {
                            if (line.charAt(i) == '<') {
                                break;
                            }
                            bedrooms += line.charAt(i);
                        }
                        numBedrooms = Integer.parseInt(bedrooms);
                    } else if (line.length() >= 9 && line.substring(1, 10).equals("bathrooms")) {// if this line is a
                                                                                                 // bathrooms field ->
                        // store it into (int)numBaths
                        numBaths = 0;
                        String baths = "";
                        for (int i = 11; i < line.length(); i++) {
                            if (line.charAt(i) == '<') {
                                break;
                            }
                            baths += line.charAt(i);
                        }
                        numBaths = Integer.parseInt(baths);
                    }
                }
            }
            return list;
        } catch (NullPointerException e) {
            throw new FileNotFoundException("Null path");
        } catch (Exception e) {
            throw new FileNotFoundException("invalid path");
        }
    }
}
