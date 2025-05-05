package foodManagement;

/**
 * A SortedListOfImmutables represents a sorted collection of immutable objects 
 * that implement the Listable interface.  
 * 
 * An array of references to Listable objects is used internally to represent 
 * the list.  
 * 
 * The items in the list are always kept in alphabetical order based on the 
 * names of the items.  When a new item is added into the list, it is inserted 
 * into the correct position so that the list stays ordered alphabetically
 * by name.
 */
public class SortedListOfImmutables {
	private Listable[] items;

	/*
	 * creates an empty list by creating an internal array of size 0
	 */
	public SortedListOfImmutables() {
		this.items = new Listable[0];
	}

	/*
	 * copy constructor. shallow copy. the current object will
	 * become the object the parameter refers to
	 */
	public SortedListOfImmutables(SortedListOfImmutables other) {
		this.items = new Listable[other.getSize()];
		for(int i = 0; i < other.getSize(); i++) {
			items[i] = other.items[i];
		}
	}

	/*
	 * returns the number of items in the list
	 */
	public int getSize() {
		return this.items.length;
	}
	
	/*
	 * returns a reference to the item in the ith position in the list.
	 * begins at 0
	 */
	public Listable get(int i) {
		return this.items[i];
	}
	
	/*
	 * adds an item to an already sorted in alphabetical order list. will
	 * insert the parameter into the correct position based on 
	 * alphabetical order.
	 */
	public void add(Listable itemToAdd) {
		Listable[] newList = new Listable[this.items.length + 1];
		int whereToGo = 0;
		
		//neg = a before b, 0 = equal, pos = b before a
		if (this.items.length <= 0) {
			whereToGo = 0;
		}else{
			for(int i = 0; i < this.items.length; i++) {
				if (itemToAdd.getName().compareTo(this.items[i].getName()) 
						< 0) {
					whereToGo = i;
					break;
				}
			}
		}
		// itemToAdd goes on the end of the list
		if (items.length > 0 && whereToGo == 0 && itemToAdd.getName()
				.compareTo(this.items[this.items.length - 1].getName()) >= 0) {
			whereToGo = items.length;
		}
		//beginning
		for(int pos = 0; pos < whereToGo; pos++) {
			newList[pos] = this.items[pos];
		}
		//insert item
		newList[whereToGo] = itemToAdd;
		//put in the rest
		for(int pos = whereToGo; pos < items.length; pos++) {
			newList[pos + 1] = this.items[pos];
		}
		
		this.items = newList;
		
		/*
		 * find where it needs to go
		 * put in the beginning
		 * put in the item to add
		 * put in the end
		 */
	}

	/*
	 * adds a list to the current list and maintains alphabetical order.
	 */
	public void add(SortedListOfImmutables listToAdd) {
		Listable indexToAdd;
		for(int i = 0; i < listToAdd.getSize(); i++) {
			//set each element of listToAdd to indexToAdd
			indexToAdd = listToAdd.get(i);
			
			//add each element to newList
			this.add(indexToAdd);
		}
	}
	
	/*
	 * removes an item from the list. will only remove one instance
	 * of the variable. if it does not appear in this list it will do nothing
	 */
	public void remove(Listable itemToRemove) {
		//create new list
		Listable[] newList = new Listable[this.items.length - 1];
		
		//find if itemToRemove is even in the list
		boolean has = false;
		for(int i = 0; i < this.items.length; i++) {
			if (this.items[i] == itemToRemove) {
				has = true;
				break;
			}else {
				has = false;
			}
		}
		
		if (has == true) {
			//find what to remove and remove each individually
			int indexToRemove = 0;
			for(int i = 0; i < this.items.length; i++) {
				if (itemToRemove.getName().equals(this.items[i].getName())) {
					indexToRemove = i;
					break;
				}
			}
			//if remove beginning
			if (indexToRemove == 0) {
				//add the end, but not what needs to be removed. skip it.
				for(int pos = 0; pos < newList.length; pos++) {
					newList[pos] = this.items[pos + 1];
				}
			}else if (indexToRemove == newList.length){//if remove end
				//add the beginning, but not what needs to be removed. skip it.
				for(int pos = 0; pos < newList.length; pos++) {
					newList[pos] = this.items[pos];
				}
			}else {//if remove anything in middle
				newList = new Listable[this.items.length - 1];
				//add the beginning, but not what needs to be removed. skip it.
				for(int pos = 0; pos < indexToRemove; pos++) {
					newList[pos] = this.items[pos];
				}
				//add the end, but not what needs to be removed. skip it.
				for(int pos = indexToRemove + 1; pos < newList.length + 1; 
						pos++) {
					newList[pos - 1] = this.items[pos];
				}
			}
		}else if(has == false){
			newList = this.items;
		}
		this.items = newList;
	}

	/*
	 * removes a list from the current list. any items in the listToRemove
	 * will be removed. only removes the amount that are in the parameter.
	 * anything else will stay.
	 */
	public void remove(SortedListOfImmutables listToRemove) {
		Listable indexToAdd;
		for(int i = 0; i < listToRemove.getSize(); i++) {
			//set each element of listToAdd to indexToAdd
			indexToAdd = listToRemove.get(i);
			
			//add each element to newList
			this.remove(indexToAdd);
		}
	}

	/*
	 * returns the sum of the wholesale costs of all items in the list
	 */
	public int getWholesaleCost() {
		int sum = 0;
		for(int i = 0; i < this.items.length; i++) {
			sum += this.items[i].getWholesaleCost();
		}
		return sum;
	}

	/*
	 * returns the sum of the retail values of all items in the list
	 */
	public int getRetailValue() {
		int sum = 0;
		for(int i = 0; i < this.items.length; i++) {
			sum += this.items[i].getRetailValue();
		}
		return sum;
	}

	/*
	 * checks to see if a particular item is in the list
	 * itemToFind is being looked for
	 * return true if the item is found in the list, false otherwise
	 */
	public boolean checkAvailability(Listable itemToFind) {
		for(int i = 0; i < this.items.length; i++) {
			if(this.items[i].getName().equals(itemToFind.getName())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * checks if the parameter is contained in the current list.
	 * if there is more than one of an element in the parameter the 
	 * current list must have them too.
	 * returns true if all items in the parameter are contained in the current 
	 * list
	 */
	public boolean checkAvailability(SortedListOfImmutables listToCheck) {
		SortedListOfImmutables newList = new SortedListOfImmutables(this);
		for(int i = 0; i < listToCheck.getSize(); i++) {
			if (newList.checkAvailability(listToCheck.get(i)) == true) {
				newList.remove(listToCheck.get(i));
			}else {
				return false;
			}
		}
		return true;
	}

	/*
	 * We'll give you this one for free.  Do not modify this method or you
	 * will fail our tests!
	 */
	public String toString() {
		String retValue = "[ ";
		for (int i = 0; i < items.length; i++) {
			if (i != 0) {
				retValue += ", ";
			}
			retValue += items[i];
		}
		retValue += " ]";
		return retValue;
	}
}
