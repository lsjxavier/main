package seedu.foodiebot.model.randomize;

import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Randomize a canteen, stall and food choice for the user
 */
public class Randomize {

    private String selectedCanteen = "";
    private String selectedStall = "";

    private Random rand = new Random();

    public Randomize(){
    }

    /**
     * This method return the name of the selected canteen.
     * @return String of the canteen name.
     */
    public String selectCanteen() {
        return selectedCanteen;
    }

    //public void getSelectedCanteen(ObservableList<Canteen> list) {
    //    listSize = list.size();
    //    index = rand.nextInt(listSize);
    //    List<String> lst = new ArrayList<String>();
    //    selectedCanteen = list.get(index).toString();
    //}

    /**
     * This function select a canteen from the jsonfile (foodiebot.json).
     * @param file which is provided from the RandomizeCommand
     */
    public void getSelectedCanteen(FileReader file) {
        int listSize;
        int index;
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) parser.parse(file);
            JSONArray canteenList = (JSONArray) obj.get("canteens");
            //System.out.println((canteenList));

            listSize = canteenList.size();
            index = rand.nextInt(listSize);

            JSONObject canteen = (JSONObject) canteenList.get(index);
            selectedCanteen = (String) canteen.get("name");

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    //public void parseCanteenObject (JSONObject canteen) {
    //    JSONObject canteenObject = (JSONObject) canteen.get("canteen");
    //    String canteenName = (String) canteenObject.get("name");
    //    System.out.println(canteenName);
    //    canteens.add(canteenName);
    //}

}
