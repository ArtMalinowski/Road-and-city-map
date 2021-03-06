import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class to test the Road and Settlement classes
 * 
 * @author Artur Malinowski
 * @version 1.0 (15th March 2016)
 *
 */
public class Application {
	private Scanner scan;
	private Map map;


	public Application() {
		scan = new Scanner(System.in);
		map = new Map();
	}

	/**
	 * @throws IOException
	 *             enter choice to pick one from following option case 1 enter
	 *             information about settlement and create this settlement case
	 *             2 find created settlement and delete this settlement case 3
	 *             enter information about road and crate this road case 4 find
	 *             created road and delete this road case 5 display roads ,
	 *             settlements and roads connected to settlement case 6 save
	 *             roads and settlements case 7 make our arrays empty (we need
	 *             do that if we want to create new settlements and road without
	 *             previous one) case q to quit the program
	 */
	private void runMenu() throws IOException {
		String choice;
		do {
			Scanner in = new Scanner(System.in);
			printMenu();
			choice = in.next().toUpperCase();
			switch (choice) {
			case "1":
				String result = null;
				System.out.println("Enter info about Settlement ");
				System.out.println("enter Name: ");
				String name = in.next().toUpperCase();
				System.out.println("enter population: ");
				int population = in.nextInt();
				SettlementType type = askForRoadtTYPE();
				Settlement newSettlement = new Settlement(name, type, population);
				map.addSettlements(newSettlement);
				break;
			case "2":
				if (map.settlementsSize() < 1) {
					System.err.println("we dont have any city do delete");
					break;
				} else if (map.settlementsSize() >= 1) {
					System.out.println("Enter name of Settlement to delete ");
					String nameofSetToDelete = in.next().toUpperCase();
					Settlement settlementToDelete = new Settlement(nameofSetToDelete);
					map.deleteSettlement(settlementToDelete);
					System.err.println("*** done ***");
				} else {
					System.err.println("wrong name of the city ");
					break;
				}
				// }
				break;
			case "3":
				Settlement foundSourceSetlement = null;
				Settlement foundDestinationSetlement = null;
				if (map.settlementsSize() < 2) {
					System.err.println("we need more than 2 cities ");
					break;
				} else if (map.settlementsSize() >= 2) {
					System.out.println("Enter info about Road ");
					System.out.println("enter Name: ");
					String nm = in.next();
					System.out.println("enter Length: ");
					Double length = in.nextDouble();
					Classification classifier = askForRoadClassifier();
					System.out.println("enter source Settlement: ");
					String nameSourceSettlement = in.next();
					Settlement roadSource = new Settlement(nameSourceSettlement);
					foundSourceSetlement = map.findSettlement(roadSource.getName());
					if (foundSourceSetlement != null) {
						System.out.println("enter destination Settlement: ");
						String nameDestinationSettlement = in.next();
						Settlement roadDestination = new Settlement(nameDestinationSettlement);
						foundDestinationSetlement = map.findSettlement(roadDestination.getName());

						if (foundDestinationSetlement != null) {
							Road newRoad = new Road(nm, classifier, foundSourceSetlement, foundDestinationSetlement,
									length);
							map.addroad(newRoad);
						} else {
							System.err.println("city of name" + nameDestinationSettlement
									+ " doesn't exist  please choose option 1 and create settlement ");
						}
					} else {
						System.err.println("city of name" + nameSourceSettlement
								+ " doesn't exist  please choose option 1 and create settlement ");
					}
				}

				break;
			case "4":
				if (map.roadSize() < 1) {
					System.err.println("we dont have any roads do delete");
					break;
				} else if (map.roadSize() >= 1) {
					System.out.println("Enter name of Road to delete ");
					String nameS = in.next();
					Road findNameroad = map.findNameroad(nameS);
					if (findNameroad == null) {
						System.err.println("Road of name " + nameS + " doesn't exsist");
						break;
					} else {
						System.out.println("Enter source to delete ");
						String source = in.next();
						Settlement sourceS = map.findSettlement(source);
						if (sourceS == null) {
							System.err.println("Source Settlement of name " + source + " doesn't exsist");
							break;
						} else {
							System.out.println("Enter destination to delete ");
							String destination = in.next();
							Settlement destinationS = map.findSettlement(destination);
							if (destinationS == null) {
								System.err.println(
										"destination Settlemen" + "t of name " + destinationS + " doesn't exsist");
								break;
							} else {
								Road toDelete = null;
								toDelete = map.findRoad(nameS, sourceS, destinationS);
								map.deleteroads(toDelete);
								System.err.println("*** done ***");
							}
						}
					}
				}
				break;
			case "5":
				map.display();
				break;
			case "6":
				map.save();
				System.out.println("Saved");
				break;
			case "7":
				map.emptyarrays();
				System.out.println("All settlements and raods are dlete");
				break;
			case "Q":
				System.err.println("*** THANK YOU FOR USING MY PROGRAM ***");
				break;
			default:
				System.err.println("not a valid choice, PLEASE ENTER AGAIN ");
			}
		} while (!choice.equals("Q"));
	}

	/**
	 * ask user to enter one from 4 road Classifications
	 * 
	 * @return return road classification
	 * 
	 */
	private Classification askForRoadClassifier() {
		Classification result = null;
		boolean valid;
		do {
			valid = false;
			System.out.print("Enter a road classification: ");
			for (Classification cls : Classification.values()) {
				System.out.print(cls + " ");
			}
			String choice = scan.next().toUpperCase();
			try {
				result = Classification.valueOf(choice);
				valid = true;
			} catch (IllegalArgumentException iae) {
				System.out.println(choice + " is not one of the options. Try again.");
			}
		} while (!valid);
		return result;
	}

	/**
	 * ask user to enter one of settlement types
	 * 
	 * @return return settlement type
	 */
	private SettlementType askForRoadtTYPE() {
		SettlementType result = null;
		boolean valid;
		do {
			valid = false;
			System.out.print("Enter a road Settlement Type: ");
			for (SettlementType cls : SettlementType.values()) {
				System.out.print(cls + " ");
			}
			String choice = scan.next().toUpperCase();
			try {
				result = SettlementType.valueOf(choice);
				valid = true;
			} catch (IllegalArgumentException iae) {
				System.out.println(choice + " is not one of the options. Try again.");
			}
		} while (!valid);
		return result;
	}


	/**
	 * @throws FileNotFoundException
	 *  load settlements from savesettlement load roads from
	 *             saveroads
	 */
	private void load() throws FileNotFoundException {
		map.load();
		System.out.println("loaded");

	}

	/**
	 * Print meni with possible choices
	 */
	public void printMenu() {
		System.out.println("\n MENU \n 1 - Create Setlment  \n 2 - Delete settlement \n "
				+ "3 - Create road \n 4 - delete road \n 5 - Display map \n 6 - Save \n 7 - remove previous loaded roads and settlements "
				+ " \n Q = quit");
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 *             load our data from files saveSettlement and saveroad run our
	 *             runMenu
	 */
	public static void main(String args[]) throws IOException, FileNotFoundException {
		Application app = new Application();
		app.load();
		app.runMenu();
	}

}
