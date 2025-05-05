package foodManagement;


/**
 * An IMMUTABLE class that represents a list of food and a name.  For example, 
 * a typical entree might be called "Big Bubba Breakfast" and might consist of
 * the list:  2 Egg, 3 Waffle, 1 Bacon, 1 Coffee
 */
public class Entree implements Listable {
	private String name;
	private SortedListOfImmutables foodList;//This list contains Food objects
	
	/*
	 * a constructor that takes a string parameter name and a 
	 * copy of the SortedListOfImmutables parameter foodListIn. 
	 * this constructor creates an immutable entree
	 */
	public Entree(String nameIn, SortedListOfImmutables foodListIn) {
		this.name = nameIn;
		this.foodList = new SortedListOfImmutables(foodListIn);
	}
	
	/*
	 * returns the current objects name
	 */
	public String getName() {
		return this.name;
	}
	
	/*
	 * returns a copy of the current object's foodList
	 */
	public SortedListOfImmutables getFoodList() {
		return new SortedListOfImmutables(this.foodList);
	}

	/*
	 * returns the wholesale cost of a foodList
	 */
	public int getWholesaleCost() {
		return foodList.getWholesaleCost();
	}

	/*
	 * returns the retail value of a foodList
	 */
	public int getRetailValue() {
		return foodList.getRetailValue();
	}
	
	/*
	 * compares the current object to the parameter other. returns
	 * true if they are the same and false if anything else
	 */
	public boolean equals(Entree other) {
		return other.getName().equals(this.getName());
	}
	
	public String toString() {
		String retValue = "< ";
		for (int i = 0; i < foodList.getSize(); i++) {
			if (i != 0) {
				retValue += ", ";
			}
			retValue += foodList.get(i);
		}
		retValue += " >";
		return retValue;
	}
}