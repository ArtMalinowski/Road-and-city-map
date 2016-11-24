import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a settlement with a name, population, type and with connected
 * roads.
 * 
 * @author Artur Malinowski
 * @version 1.0 (15th March 2016)
 *
 */
public class Road {
	private String name;
	private Classification classification;
	private Settlement sourceSettlement;
	private Settlement destinationSettlement;
	private double length;

	public Road() {

	}

	/**
	 * Constructor to build road between two settlements. This fulfills the
	 * class diagram constraint that every road must be connected to two
	 * settlements. We are not checking whether it's the same settlement, but
	 * that's okay: you drive out and arrive back again at the same place!
	 * 
	 * @param nm
	 *            The road name
	 * @param classifier
	 *            The class of road, e.g. 'A'
	 * @param source
	 *            The source settlement
	 * @param destination
	 *            The destination settlement (can be the same as the source!)
	 */
	public Road(String nm, Classification classifier, Settlement source, Settlement destination, double len) {

		// This is from worksheet 8. We now also have a road length
		name = nm;
		classification = classifier;
		sourceSettlement = source;
		source.add(this);
		destinationSettlement = destination;
		destination.add(this);
		length = len;
	}

	public Road(String nm) {
		name = nm;
	}

	/**
	 * The name of the road
	 * 
	 * @return The road's name
	 */
	public String getName() {
		return name;
	}

	public void setName(String nm) {
		name = nm;
	}

	public void setLength(double len) {
		length = len;
	}

	public double getLength() {
		return length;
	}

	/**
	 * The road's class
	 * 
	 * @return The class of the road, e.g. A
	 */
	public Classification getClassification() {
		return classification;
	}

	public void setClassification(Classification classification) {
		this.classification = classification;
	}

	/**
	 * The source settlement
	 * 
	 * @return One end of the road we call source
	 */
	public Settlement getSourceSettlement() {
		return sourceSettlement;
	}

	/**
	 * The destination settlement
	 * 
	 * @return One end of the road we call destination
	 */
	public Settlement getDestinationSettlement() {
		return destinationSettlement;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Road other = (Road) obj;
		if (destinationSettlement == null) {
			if (other.destinationSettlement != null)
				return false;
		} else if (!destinationSettlement.equals(other.destinationSettlement))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sourceSettlement == null) {
			if (other.sourceSettlement != null)
				return false;
		} else if (!sourceSettlement.equals(other.sourceSettlement))
			return false;
		return true;
	}

	/**
	 * Some useful info about the state of this object. Notice how this also
	 * returns the names of the connected settlements
	 * 
	 * @return The state of the object
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString() return string with information about
	 * road with destination Settlement and source Settlement
	 */
	public String toString() {
		String result = "Road name=" + name + ", length=" + length + ", sourceSettlement=" + sourceSettlement.getName()
				+ ", destinationSettlement=" + destinationSettlement.getName() + ", classification=" + classification;
		return result;
	}

	/**
	 * @param s1
	 * @return return us extra string only with information about roads
	 *         connected to settlement
	 */
	public String extraStrig(Settlement s1) {
		String result = "- ";
		if (sourceSettlement == s1) {
			result += name + " to the Settlment of the name " + destinationSettlement.getName();
		} else {
			result += name + " to the Settlment of the name " + sourceSettlement.getName();
		}

		return result;
	}
}
