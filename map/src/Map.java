import java.util.Scanner;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Represents a settlement with a name, population, type and with connected
 * roads.
 * 
 * @author Artur Malinowski
 * @version 1.0 (15th March 2016)
 *
 */

public class Map {
	private static final String saveSettlement = "savesettlement.txt";
	private static final String saveRoad = "saveroad.txt";
	private Scanner scan;
	private ArrayList<Settlement> Settlements;
	// this my second array with roads which store only roads
	private ArrayList<Road> generallyroads;

	public Map() {
		scan = new Scanner(System.in);
		Settlements = new ArrayList<Settlement>();
		generallyroads = new ArrayList<Road>();
	}

	/**
	 * function make our arrays with roads and settlements empty i created this
	 * function if someone doesn't want to delete arrays and roads one by one
	 * 
	 */
	public void emptyarrays() {
		Settlements = new ArrayList<Settlement>();
		generallyroads = new ArrayList<Road>();
	}

	/**
	 * @throws FileNotFoundException
	 * this function is used to loadSSettlement and loadRoads verry
	 *             simmilar to our function from monster
	 */
	public void load() throws FileNotFoundException {
		// STEP 6: INSERT CODE HERE
		Settlements.clear();
		Scanner loadsettlement = new Scanner(new FileReader(saveSettlement));
		loadsettlement.useDelimiter(":|\r?\n|\r");
		int numofSettlement = Integer.parseInt(loadsettlement.nextLine());
		for (int i = 0; i < numofSettlement; i++) {
			String name = loadsettlement.next();
			int pop = loadsettlement.nextInt();
			SettlementType t = SettlementType.valueOf(loadsettlement.next().toUpperCase());
			Settlement s = new Settlement(name, t, pop);
			Settlements.add(s);
		}
		loadsettlement.close();

		generallyroads.clear();
		Scanner loadroads = new Scanner(new FileReader(saveRoad));
		loadroads.useDelimiter(":|\r?\n|\r");
		// I have very big problem with loading part
		// my friend help my by showing and
		// explanation how work function (parsInt, parseDouble)

		int numRoad = Integer.parseInt(loadroads.nextLine());
		for (int i = 0; i < numRoad; i++) {
			String nm = loadroads.nextLine();
			double len = Double.parseDouble(loadroads.nextLine());
			Classification classifier = Classification.valueOf(loadroads.nextLine().toUpperCase());
			Settlement source = findSettlement(loadroads.nextLine());
			Settlement destination = this.findSettlement(loadroads.nextLine());
			Road road = new Road(nm, classifier, source, destination, len);
			generallyroads.add(road);
		}
		loadsettlement.close();
	}

	/**
	 * @throws IOException
	 * Function which save our settlements( and connected to them
	 *             roads) and roads as roads
	 */
	public void save() throws IOException {

		// both functions very simmilar to save from worksheets about monster
		PrintWriter mySavesettlement = new PrintWriter(new FileWriter(saveSettlement));
		mySavesettlement.println(Settlements.size());
		for (Settlement settlement : Settlements) {
			mySavesettlement.println(settlement.getName());
			mySavesettlement.println(settlement.getPopulation());
			mySavesettlement.println(settlement.getKind());
		}
		mySavesettlement.close();

		PrintWriter mySaveroad = new PrintWriter(new FileWriter(saveRoad));
		mySaveroad.println(generallyroads.size());
		for (Road road : generallyroads) {
			mySaveroad.println(road.getName());
			mySaveroad.println(road.getLength());
			mySaveroad.println(road.getClassification());
			mySaveroad.println(road.getSourceSettlement().getName());
			mySaveroad.println(road.getDestinationSettlement().getName());
		}
		mySaveroad.close();
	}

	/**
	 * @param road
	 *            add roads to our array with roads
	 */
	public void addroad(Road road) {
		generallyroads.add(road);
	}

	/**
	 * display our roads and settlements
	 */
	public void display() {
		System.out.println("***ROADS*** \n");
		for (Road r : generallyroads) {
			System.out.println(r.toString());
		}
		System.out.println("***SETLEMENTS*** \n");
		for (Settlement s : Settlements) {
			System.out.println(s);
		}
	}

	/**
	 * @return Return us number (integer) of arrays with settlements
	 */
	public int settlementsSize() {
		int settlementsSize;
		settlementsSize = Settlements.size();
		return settlementsSize;
	}

	/**
	 * @return Return us number (integer) of arrays with roads
	 */
	public int roadSize() {
		int roadSize;
		roadSize = generallyroads.size();
		return roadSize;
	}

	/**
	 * @param delete
	 *            Settlements and conected to them roads also delete roads from
	 *            array of road also delete the same road from other settlements
	 */

	public void deleteSettlement(Settlement setttodelete) {
		ArrayList<Road> arrayroadstoDelete = new ArrayList<Road>();
		Settlement destinationSettlement = new Settlement();
		Settlement sourceSettlement = new Settlement();
		for (Settlement s : Settlements) {
			if (s.getName().toUpperCase().equals(setttodelete.getName().toUpperCase())) {
				arrayroadstoDelete = s.getAllRoads();
				for (Road roadTOdelate : arrayroadstoDelete) {
					destinationSettlement = roadTOdelate.getDestinationSettlement();
					destinationSettlement.delete(roadTOdelate);
					sourceSettlement = roadTOdelate.getSourceSettlement();
					sourceSettlement.delete(roadTOdelate);

					generallyroads.remove(roadTOdelate);

				}

			}

		}
		Settlements.remove(setttodelete);
	}

	/**
	 * @param roadtodelete
	 */
	public void deleteroads(Road roadtodelete) {

		roadtodelete.getSourceSettlement().delete(roadtodelete);
		roadtodelete.getDestinationSettlement().delete(roadtodelete);
		generallyroads.remove(roadtodelete);
	}

	/**
	 * @param newSettlement
	 * @throws IllegalArgumentException
	 */
	public void addSettlements(Settlement newSettlement) throws IllegalArgumentException {
		Settlement foundSettlement = null;

		foundSettlement = findSettlement(newSettlement.getName());
		if (foundSettlement == null)
			Settlements.add(newSettlement);
		else {
			System.err.println("Settlement of the name " + foundSettlement.getName() + " allready exist");
		}
	}

	/**
	 * function which searching our arrays by loop.funcitn check all elements
	 * and if they are equal to our previous provided name it will return
	 * foundSettlement.
	 * 
	 * @param name
	 * @return This function return our find Settlement or if it will not find
	 *         it will return null
	 */
	public Settlement findSettlement(String name) {
		/*
		 * generally all my find function based on our find function from
		 * worksheets about monsters , there are only very small diffrence
		 */

		Settlement foundSettlement = null;
		for (Settlement s : Settlements) {
			if (s.getName().toUpperCase().equals(name.toUpperCase())) {
				foundSettlement = s;
				break;
			}
		}
		return foundSettlement;
	}

	/**
	 * Function which searching our arrays by loop.funcitn check all elements
	 * and if they are equal to our previous provided name, source settlement
	 * and destination settlement it will return foundRoad. diffrence is that we
	 * checking 3 parameters
	 * 
	 * @param Name
	 * @param source
	 * @param destination
	 * @return It return us our find road or if it will not find it will return
	 *         null
	 */
	public Road findRoad(String Name, Settlement source, Settlement destination) {
		for (Road r : generallyroads) {
			if (r.getName().equals(Name) && r.getSourceSettlement().equals(source)
					&& r.getDestinationSettlement().equals(destination)) {
				return r;
			}
		}
		return null;
	}

	/**
	 * Function which searching our arrays by loop.funcitn check all elements
	 * and if they are equal to our previous provided name it will return
	 * foundRoad.
	 * 
	 * @param Name
	 * @return It return us our find road or if it will not find it will return
	 *         null
	 */
	public Road findNameroad(String Name) {
		for (Road r : generallyroads) {
			if (r.getName().equals(Name)) {
				return r;
			}
		}
		return null;
	}

	public String toString() {
		String result = "";
		return result;
	}
}
