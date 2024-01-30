package game;

import java.util.ArrayList;
import java.util.Scanner;

public class Room implements Comparable<Room>{
	
	private ArrayList<Room> rooms = new ArrayList<Room>();
	private String name;
	private String description;
	private String input;
	private boolean hasItem = false;
	private boolean unlocked = false;
	
	private Player character;
	private Item item;
	
	public Room(String name, String description, boolean unlocked) {
		
		this.name = name;
		this.description = description;
		this.unlocked = unlocked;
		
		
	}
	
	public void fillList(ArrayList<Room> a) {
		this.rooms = a;
	}
	
	public boolean getUnlocked() {
		return unlocked;
	}
	
	public void setUnlocked(boolean unlocked) {
		this.unlocked = unlocked;
	}
	
	public void setItem(Item item) {
		this.item = item;
		hasItem = true;
	}
	
	public void takeItem() {
		item.printInteraction();
		hasItem = false;
	}
	
	public Item getItem() {
		return item;
	}
	
	public String previousRoom(Room p) {
		return p.name;
	}
	
	public ArrayList<Room> getRooms() {
		return rooms;
	}
	
	public String getName() {
		return name;
	}
	
	public void getDescript() {
		Scanner scan = new Scanner(System.in);
		
		System.out.println(description);
		
		if(hasItem) {
			item.printDescript();
		}
		
		this.input = scan.nextLine();
	}
	
	public String getInput() {
		return input;
	}
	
	public void addRoom(Room r) {
		rooms.add(r);
	}
	
	public Room hasRoom() {
		for(int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).getName().equalsIgnoreCase(input)) {
				return rooms.get(i);
			}
		}
		return this;
	}
	
	public boolean getHasItem() {
		return hasItem;
	}

	@Override
	public int compareTo(Room o) {
		if(this.name.compareTo(o.getName()) != 0) {
		return this.name.compareTo(o.getName());
		}
		else {
			return this.name.compareTo(o.getName());
		}
	}

}
