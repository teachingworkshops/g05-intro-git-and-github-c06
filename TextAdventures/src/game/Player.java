package game;

import java.util.ArrayList;

public class Player {
	
	ArrayList<Item> inventory = new ArrayList<Item>();
	String input;
	Room currentRoom;
	int health = 100;
	
	public Player(Room current) {
		
		this.currentRoom = current;
		
	}
	
	public void addItem(Item item) {
		inventory.add(item);
	}
	
	public Room getCurrentRoom() {
		
		return currentRoom;
		
	}
	
	public String getRoomInput() {
		return input;
	}
	
	public ArrayList<Item> getInventory(){
		return inventory;
	}
	
	public boolean hasItem(String s) {
		for(int i = 0; i < inventory.size(); i++) {
			if(inventory.get(i).getName().equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}
	
	public void interactItem() {
		if(currentRoom.getItem().isHoldable())
			addItem(currentRoom.getItem());
	}
	
	public void useItem(String s) {
		for(Item i : inventory) {
		if(i.getName().equalsIgnoreCase(s)) {
		inventory.remove(i);
		return;
			}
		}
	}
	
	public void move() {
		if(currentRoom.getUnlocked()) {
		currentRoom.getDescript();
		input = currentRoom.getInput();
		if (currentRoom.getHasItem() && currentRoom.getInput().equalsIgnoreCase(currentRoom.getItem().getName())) {
			
			interactItem();
			currentRoom.takeItem();
			
		}
		if(currentRoom.compareTo(currentRoom.hasRoom()) != 0) {
		System.out.println("You left the " + currentRoom.getName());
		}
		currentRoom = currentRoom.hasRoom();
	}
	}
	
	public void decreaseHealth() {
		health-=10;
	}
	
	public int status() {
		return health;
	}

}
