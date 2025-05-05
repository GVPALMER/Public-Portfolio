import static org.junit.Assert.*;
import org.junit.Test;
import foodManagement.*;

public class PublicTests {

	private static final Food BACON = Food.FOOD_OBJECTS[0];
	private static final Food WAFFLE = Food.FOOD_OBJECTS[1];
	private static final Food EGG = Food.FOOD_OBJECTS[2];
	private static final Food OJ = Food.FOOD_OBJECTS[3];
	private static final Food MILK = Food.FOOD_OBJECTS[4];
	
	@Test
	public void testDefaultConstructorAndGetSize() {
		SortedListOfImmutables list = new SortedListOfImmutables();
		assertTrue(list.getSize() == 0);
		assertEquals("[  ]", list.toString());
	}
	
	@Test
	public void testListCopyConstructor() {
		SortedListOfImmutables list = new SortedListOfImmutables();
		for (int i = Food.FOOD_OBJECTS.length - 1; i >= 0; i--) {
			list.add(Food.FOOD_OBJECTS[i]);
		}
		assertEquals(Food.FOOD_OBJECTS.length, list.getSize());
		assertEquals("[ Bacon, Cereal, Coffee, Croissant, Donut, Egg, " + 
					 "Hashbrowns, Melon, Milk, Orange Juice, Pancakes, "
					 + "Pie, Toast, Waffle ]",
				list.toString());
		
		SortedListOfImmutables newList = new SortedListOfImmutables(list);
		
		assertEquals(newList.getSize(), list.getSize());
		assertEquals(newList.toString(), list.toString());
	}
	
	@Test
	public void testListSimpleAdd() {
		SortedListOfImmutables list = new SortedListOfImmutables();
		for (int i = Food.FOOD_OBJECTS.length - 1; i >= 0; i--) {
			list.add(Food.FOOD_OBJECTS[i]);
		}
		assertEquals(Food.FOOD_OBJECTS.length, list.getSize());
		assertEquals("[ Bacon, Cereal, Coffee, Croissant, Donut, Egg, " + 
					 "Hashbrowns, Melon, Milk, Orange Juice, Pancakes, "
					 + "Pie, Toast, Waffle ]",
				list.toString());
		
		list.add(BACON);
		list.add(WAFFLE);
		list.add(EGG);
		list.add(MILK);
		list.add(EGG);
		list.add(OJ);
		assertEquals(20, list.getSize());
		assertEquals("[ Bacon, Bacon, Cereal, Coffee, Croissant, Donut, "
					 + "Egg, Egg, Egg, Hashbrowns, Melon, Milk, Milk, " + 
					 "Orange Juice, Orange Juice, Pancakes, Pie, Toast, "
					 + "Waffle, Waffle ]" , list.toString());
	}	
	
	@Test
	public void testListAddList() {
		SortedListOfImmutables list = new SortedListOfImmutables();
		SortedListOfImmutables list2 = new SortedListOfImmutables();
		for (int i = Food.FOOD_OBJECTS.length - 1; i >= 0; i--) {
			list.add(Food.FOOD_OBJECTS[i]);
		}
		for (int i = Food.FOOD_OBJECTS.length - 1; i >= 0; i--) {
			list2.add(Food.FOOD_OBJECTS[i]);
		}
		assertEquals(Food.FOOD_OBJECTS.length, list.getSize());
		assertEquals("[ Bacon, Cereal, Coffee, Croissant, Donut, Egg, " + 
					 "Hashbrowns, Melon, Milk, Orange Juice, Pancakes, "
					 + "Pie, Toast, Waffle ]",
				list.toString());
		
		assertEquals(Food.FOOD_OBJECTS.length, list2.getSize());
		assertEquals("[ Bacon, Cereal, Coffee, Croissant, Donut, Egg, " + 
					 "Hashbrowns, Melon, Milk, Orange Juice, Pancakes, "
					 + "Pie, Toast, Waffle ]",
				list2.toString());
		
		list.add(list2);
		
		assertEquals(28, list.getSize());
		assertEquals("[ Bacon, Bacon, Cereal, Cereal, Coffee, Coffee, "
					 + "Croissant, Croissant, Donut, Donut, Egg, Egg, " + 
					 "Hashbrowns, Hashbrowns, Melon, Melon, Milk, Milk, "
					 + "Orange Juice, Orange Juice, Pancakes, Pancakes, "
					 + "Pie, Pie, Toast, Toast, Waffle, Waffle ]",
				list.toString());
		
		//second case
		SortedListOfImmutables list3 = new SortedListOfImmutables();
		SortedListOfImmutables list4 = new SortedListOfImmutables();
		for (int i = Food.FOOD_OBJECTS.length - 1; i >= 0; i--) {
			list3.add(Food.FOOD_OBJECTS[i]);
		}
		list4.add(BACON);
		list4.add(OJ);
		list4.add(BACON);
		list4.add(WAFFLE);
		list4.add(OJ);


		assertEquals(Food.FOOD_OBJECTS.length, list3.getSize());
		assertEquals("[ Bacon, Cereal, Coffee, Croissant, Donut, Egg, " + 
					 "Hashbrowns, Melon, Milk, Orange Juice, Pancakes, "
					 + "Pie, Toast, Waffle ]",
					 list3.toString());
		
		assertEquals(5, list4.getSize());
		assertEquals("[ Bacon, Bacon, Orange Juice, Orange Juice, Waffle ]",
					 list4.toString());
		
		list3.add(list4);
		
		assertEquals(19, list3.getSize());
		assertEquals("[ Bacon, Bacon, Bacon, Cereal, Coffee, Croissant, "
					 + "Donut, Egg, " + 
				 	 "Hashbrowns, Melon, Milk, Orange Juice, Orange Juice, "
				 	 + "Orange Juice, Pancakes, "
				 	 + "Pie, Toast, Waffle, Waffle ]",
					 list3.toString());
		
		//third case
		SortedListOfImmutables list5 = new SortedListOfImmutables();
		SortedListOfImmutables list6 = new SortedListOfImmutables();
		
		for (int i = Food.FOOD_OBJECTS.length - 1; i >= 0; i--) {
			list6.add(Food.FOOD_OBJECTS[i]);
		}
		assertEquals(0, list5.getSize());
		assertEquals("[  ]",
				list5.toString());
		
		assertEquals(Food.FOOD_OBJECTS.length, list6.getSize());
		assertEquals("[ Bacon, Cereal, Coffee, Croissant, Donut, Egg, " + 
					 "Hashbrowns, Melon, Milk, Orange Juice, Pancakes, "
					 + "Pie, Toast, Waffle ]",
					 list6.toString());
		
		list5.add(list6);
		
		assertEquals(14, list5.getSize());
		assertEquals("[ Bacon, Cereal, Coffee, "
					 + "Croissant, Donut, Egg, " + 
					 "Hashbrowns, Melon, Milk, "
					 + "Orange Juice, Pancakes, "
					 + "Pie, Toast, Waffle ]",
					 list5.toString());
	}
	
	@Test
	public void testListRemove() {
		SortedListOfImmutables list = new SortedListOfImmutables();
		for (int i = Food.FOOD_OBJECTS.length - 1; i >= 0; i--) {
			list.add(Food.FOOD_OBJECTS[i]);
		}
		assertEquals(Food.FOOD_OBJECTS.length, list.getSize());
		assertEquals("[ Bacon, Cereal, Coffee, Croissant, Donut, Egg, "
					 + "Hashbrowns, Melon, Milk, Orange Juice, Pancakes, "
					 + "Pie, Toast, Waffle ]",
				list.toString());
		
		list.remove(BACON);
		list.remove(WAFFLE);
		list.remove(OJ);
		list.remove(WAFFLE);
		assertEquals(11, list.getSize());
		assertEquals("[ Cereal, Coffee, Croissant, Donut, Egg, "
					 + "Hashbrowns, Melon, Milk, Pancakes, Pie, "
					 + "Toast ]" , list.toString());	
	}	
	
	@Test
	public void testListRemoveList() {
		SortedListOfImmutables list = new SortedListOfImmutables();
		SortedListOfImmutables list2 = new SortedListOfImmutables();
		for (int i = Food.FOOD_OBJECTS.length - 1; i >= 0; i--) {
			list.add(Food.FOOD_OBJECTS[i]);
		}
		for (int i = Food.FOOD_OBJECTS.length - 1; i >= 0; i--) {
			list2.add(Food.FOOD_OBJECTS[i]);
		}
		assertEquals(Food.FOOD_OBJECTS.length, list.getSize());
		assertEquals("[ Bacon, Cereal, Coffee, Croissant, Donut, Egg, "
					 + "Hashbrowns, Melon, Milk, Orange Juice, Pancakes, "
					 + "Pie, Toast, Waffle ]", list.toString());
		
		assertEquals(Food.FOOD_OBJECTS.length, list2.getSize());
		assertEquals("[ Bacon, Cereal, Coffee, Croissant, Donut, Egg, "
					 + "Hashbrowns, Melon, Milk, Orange Juice, Pancakes, "
					 + "Pie, Toast, Waffle ]", list2.toString());
			
		list.remove(list2);
		assertEquals(0, list.getSize());
		assertEquals("[  ]" , list.toString());
		
		//second case
		SortedListOfImmutables list3 = new SortedListOfImmutables();
		SortedListOfImmutables list4 = new SortedListOfImmutables();
		for (int i = Food.FOOD_OBJECTS.length - 1; i >= 0; i--) {
			list3.add(Food.FOOD_OBJECTS[i]);
		}
		list4.add(BACON);
		list4.add(OJ);
		list4.add(BACON);
		list4.add(WAFFLE);
		list4.add(OJ);
		
		assertEquals(Food.FOOD_OBJECTS.length, list3.getSize());
		assertEquals("[ Bacon, Cereal, Coffee, Croissant, Donut, Egg, "
					 + "Hashbrowns, Melon, Milk, Orange Juice, Pancakes, "
					 + "Pie, Toast, Waffle ]", list3.toString());
		
		assertEquals(5, list4.getSize());
		assertEquals("[ Bacon, Bacon, Orange Juice, Orange Juice, Waffle ]",
					 list4.toString());
			
		list3.remove(list4);
		
		assertEquals(11, list3.getSize());
		assertEquals("[ Cereal, Coffee, Croissant, Donut, Egg, "
					 + "Hashbrowns, Melon, Milk, Pancakes, "
					 + "Pie, Toast ]", list3.toString());
	}
	
	@Test
	public void testListAvailability() {
		SortedListOfImmutables list = new SortedListOfImmutables();
		for (int i = Food.FOOD_OBJECTS.length - 1; i >= 0; i--) {
			list.add(Food.FOOD_OBJECTS[i]);
		}
		assertEquals(Food.FOOD_OBJECTS.length, list.getSize());
		assertEquals("[ Bacon, Cereal, Coffee, Croissant, Donut, Egg, "
					 + "Hashbrowns, Melon, Milk, Orange Juice, "
					 + "Pancakes, Pie, Toast, Waffle ]",
				list.toString());
		
		list.remove(BACON);
		list.remove(MILK);
		list.remove(OJ);
		
		boolean case1 = list.checkAvailability(BACON);
		boolean case2 = list.checkAvailability(WAFFLE);
		boolean case3 = list.checkAvailability(EGG);
		boolean case4 = list.checkAvailability(MILK);
		boolean case5 = list.checkAvailability(EGG);
		boolean case6 = list.checkAvailability(OJ);
		assertTrue(case1 == false);
		assertTrue(case2 == true);
		assertTrue(case3 == true);
		assertTrue(case4 == false);
		assertTrue(case5 == true);
		assertTrue(case6 == false);
		
	}	
	
	@Test
	public void testListAvailabilityOfList() {
		SortedListOfImmutables list = new SortedListOfImmutables();
		SortedListOfImmutables list2 = new SortedListOfImmutables();
		for (int i = Food.FOOD_OBJECTS.length - 1; i >= 0; i--) {
			list.add(Food.FOOD_OBJECTS[i]);
		}
		for (int i = Food.FOOD_OBJECTS.length - 1; i >= 0; i--) {
			list2.add(Food.FOOD_OBJECTS[i]);
		}
		assertEquals(Food.FOOD_OBJECTS.length, list.getSize());
		assertEquals("[ Bacon, Cereal, Coffee, Croissant, Donut, Egg, "
					 + "Hashbrowns, Melon, Milk, Orange Juice, "
					 + "Pancakes, Pie, Toast, Waffle ]",
				list.toString());
		
		assertEquals(Food.FOOD_OBJECTS.length, list2.getSize());
		assertEquals("[ Bacon, Cereal, Coffee, Croissant, Donut, Egg, "
					 + "Hashbrowns, Melon, Milk, Orange Juice, "
					 + "Pancakes, Pie, Toast, Waffle ]",
				list2.toString());
		
		boolean available = list.checkAvailability(list2);

		assertTrue(available == true);	
		
		//second case
		SortedListOfImmutables list3 = new SortedListOfImmutables();
		SortedListOfImmutables list4 = new SortedListOfImmutables();
		for (int i = Food.FOOD_OBJECTS.length - 1; i >= 0; i--) {
			list3.add(Food.FOOD_OBJECTS[i]);
		}
		
		list4.add(BACON);
		list4.add(BACON);
		list4.add(BACON);
		list4.add(WAFFLE);
		list4.add(OJ);
		list4.add(WAFFLE);
		
		assertEquals(Food.FOOD_OBJECTS.length, list3.getSize());
		assertEquals("[ Bacon, Cereal, Coffee, Croissant, Donut, Egg, "
					 + "Hashbrowns, Melon, Milk, Orange Juice, "
					 + "Pancakes, Pie, Toast, Waffle ]",
					 list3.toString());
		
		boolean available2 = list3.checkAvailability(list4);

		assertTrue(available2 == false);	
		
		//third case
		SortedListOfImmutables list5 = new SortedListOfImmutables();
		SortedListOfImmutables list6 = new SortedListOfImmutables();
		for (int i = Food.FOOD_OBJECTS.length - 1; i >= 0; i--) {
			list5.add(Food.FOOD_OBJECTS[i]);
		}
				
		list5.add(BACON);
		list5.add(BACON);
		list5.add(BACON);
		list5.add(BACON);
		list5.add(BACON);
		list5.add(WAFFLE);
		list5.add(OJ);
				
		list6.add(BACON);
		list6.add(BACON);
		list6.add(BACON);
		list6.add(WAFFLE);
		list6.add(OJ);
		list6.add(WAFFLE);
				
		assertEquals(Food.FOOD_OBJECTS.length + 7, list5.getSize());
		assertEquals("[ Bacon, Bacon, Bacon, Bacon, Bacon, "
					 + "Bacon, Cereal, "
					 + "Coffee, Croissant, Donut, Egg, "
					 + "Hashbrowns, Melon, Milk, Orange Juice, "
					 + "Orange Juice, "
					 + "Pancakes, Pie, Toast, Waffle, Waffle ]",
					 list5.toString());
				
		boolean available3 = list5.checkAvailability(list6);

		assertTrue(available3 == true);
	}	
	
	@Test
	public void testRestaurantAddShipment() {
		Restaurant r = new Restaurant("RESTAURANT", 100000000);
		
		SortedListOfImmutables list  = new SortedListOfImmutables();
		list.add(BACON);
		list.add(BACON);
		list.add(BACON);
		list.add(WAFFLE);
		list.add(OJ);
		
		r.addShipmentToInventory(list);
		
		//second case
		Restaurant r2 = new Restaurant("RESTAURANT", 1);
		
		SortedListOfImmutables list2  = new SortedListOfImmutables();
		list.add(BACON);
		list.add(BACON);
		list.add(BACON);
		list.add(WAFFLE);
		list.add(OJ);
		
		r2.addShipmentToInventory(list2);
	}
	
	@Test
	public void testRestaurantGetters() {
		SortedListOfImmutables list  = new SortedListOfImmutables();
		list.add(BACON);
		list.add(BACON);
		list.add(BACON);
		list.add(WAFFLE);
		list.add(OJ);
		
		Restaurant r = new Restaurant("restaurant", 100000);
		String name = r.getName();
		int c = r.getCash();
		assertTrue(name.equals("restaurant"));
		assertTrue(c == 100000);
	}
	
	@Test
	public void testRestaurantPlaceOrder() {
		SortedListOfImmutables inventory = new SortedListOfImmutables();
		inventory.add(BACON);
		inventory.add(BACON);
		inventory.add(BACON);
		inventory.add(BACON);
		inventory.add(BACON);
		inventory.add(BACON);
		inventory.add(WAFFLE);
		inventory.add(OJ);
		
		SortedListOfImmutables meal = new SortedListOfImmutables();
		meal.add(BACON);
		meal.add(BACON);
		
		Entree e = new Entree("breakfast", meal);
		Restaurant r = new Restaurant("restaurant", 1000000);
		r.addEntree(e);
		r.addShipmentToInventory(inventory);
		boolean placed;
		placed = r.placeOrder(e);
		
		assertTrue(placed == true);
	}
}