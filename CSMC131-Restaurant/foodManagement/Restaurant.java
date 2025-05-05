package foodManagement;

/**
 *  The Restaurant has a name (String), a menu (list of Entrees), an inventory
 *  (list of Food), and an amount of cash on hand, measured in pennies (int)
 * 
 *  This class facilitates orders being placed, deliveries being made to the
 *  inventory, and entrees being added to the menu.
 */
public class Restaurant {
	private String name;
	private SortedListOfImmutables menu;       // A list of Entree objects	
	private SortedListOfImmutables inventory;  // A list of Food objects
	private int cash;

	/*
	 * constructor with a string parameter name and an int parameter
	 * starting cash measured in pennies. 
	 * the menu and inventory both are initialized as empty lists
	 */
	public Restaurant(String nameIn, int startingCash) {
		this.name = nameIn;
		this.cash = startingCash;
		this.menu = new SortedListOfImmutables();
		this.inventory = new SortedListOfImmutables();
	}

	/*
	 * returns a reference to the name of the current object
	 */
	public String getName() {
		return this.name;
	}

	/*
	 * returns a reference to a copy of the menu
	 * 
	 */
	public SortedListOfImmutables getMenu() {
		return new SortedListOfImmutables(menu);
	}

	/*
	 * adds an entree to the menu. the entree that is added is used as 
	 * a parameter entreeToAdd
	 */
	public void addEntree(Entree entreeToAdd) {
		menu.add(entreeToAdd);
	}
	
	/*
	 * returns a reference to a copy of the inventory
	 */
	public SortedListOfImmutables getInventory() {
		return new SortedListOfImmutables(inventory);
	}

	/*
	 * returns the current amount of cash, measured in pennies
	 */
	public int getCash() {
		return this.cash;
	}

	/*
	 * returns a boolean. checks the inventory for a given entree.
	 * returns true if the inventory contains all the list of food items in the 
	 * entree parameter and false otherwise.
	 */
	public boolean checkIfInInventory(Entree entree) {
		return inventory.checkAvailability(entree.getFoodList());
	}
	
	/*
	 * if the total amount of cash on hand is enough to pay for the shipment
	 * this method adds the SortedListOfImmutables parameter to the 
	 * items in the inventory. in this case the cash is reduced by the 
	 * wholesale cost of the shipment. this case would also return true
	 * 
	 * if the total amount of cash on hand is not enough to pay for the 
	 * shipment. it will not add the parameter and will not reduce the cost
	 * this case returns false
	 */
	public boolean addShipmentToInventory(SortedListOfImmutables list) {
		//find total cost
		int totalCost = list.getWholesaleCost();;
		
		//if can pay, buy. if cannot pay, dont buy.
		if(totalCost <= getCash()) {
			inventory.add(list);
			cash = getCash() - totalCost;
			return true;
		}else {
			return false;
		}
	}

	/*
	 * removes the food items in the entree parameter from the inventory if all 
	 * the food items are contained in the inventory. the cash on hand 
	 * will also be increased by the retail value of the entree. will also 
	 * return true;
	 * 
	 * if there is nothing in the inventory or the inventory does not contain
	 * the food items in the entree nothing will be removed. will return false
	 */
	public boolean placeOrder(Entree entree) {
		if (checkIfInInventory(entree) == true) {
			inventory.remove(entree.getFoodList());
			cash += entree.getRetailValue();
			return true;
		}else {
			return false;
		}
	}
}