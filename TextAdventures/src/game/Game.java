package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {
	
	public static void main(String[] args) {
	
	Room kitchen = new Room("Kitchen","You are in the kitchen",true);
	kitchen.setItem(new Item("Knife", "Shiney knife. Pick it up. Only if you want. Of course. Choice is yours.", "You picked it up.", true));
	Room atrium = new Room("Atrium","You are in the atrium",true);
	Room upstairs = new Room("Upstairs","You are in the UPSTAIRS hallway",true);
	Room basement = new Room("Basement", "You are in the basement.",false);
	Room diningRoom = new Room("Dining Room", "You are in the Dining Room",true);
	Room study = new Room("Study", "You are in the study",true);
	study.setItem(new Item("Book", "Old book.", "You read the book. It talks about an attic.", true));
	Room bedroom = new Room("Bedroom", "You are in the bed room",true);
	bedroom.setItem(new Item("Key", "There seems to be a bronze KEY sitting on the top of the dresser.", "You picked up the key.", true));
	Room attic = new Room("Attic", "You are in the attic", false);
	attic.setItem(new Item("Heirloom", "It is an heirloom","You picked up the hierloom", true));
	
	Room current = atrium;
	
	Player character = new Player(current);
	
	ArrayList<Room> k = new ArrayList<Room>() {
		{
			add(atrium);
		}
		
	};
	
	ArrayList<Room> a = new ArrayList<Room>() {
		{
			add(kitchen);
			add(upstairs);
			add(diningRoom);
		}
		
	};
	
	ArrayList<Room> u = new ArrayList<Room>() {
		{
			add(atrium);
			add(study);
			add(bedroom);
			add(attic);
		}
		
	};
	
	ArrayList<Room> b = new ArrayList<Room>() {
		{
			add(kitchen);
		}
	};
	
	ArrayList<Room> d = new ArrayList<Room>() {
		{
			add(kitchen);
		}
	};
	
	ArrayList<Room> s = new ArrayList<Room>() {
		{
			add(upstairs);
		}
	};
	
	ArrayList<Room> bedroomRooms = new ArrayList<Room>() {
		{
			add(upstairs);
		}
	};
	
	ArrayList<Room> atticRooms = new ArrayList<Room>() {
		{
			add(upstairs);
		}
	};
	
		
		Scanner scan = new Scanner(System.in);
		
		
	
	
	kitchen.fillList(k);
	atrium.fillList(a);
	upstairs.fillList(u);
	basement.fillList(b);
	diningRoom.fillList(d);
	study.fillList(s);
	bedroom.fillList(bedroomRooms);
	
	basement base = new basement();
	
	while(!base.isEnd()) {
		character.move();
		
		if(character.getCurrentRoom().compareTo(kitchen) == 0){

		if(character.getRoomInput().equalsIgnoreCase("Basement") && character.hasItem("Key")) {
			System.out.println("Unlock?");
			if(scan.nextLine().equalsIgnoreCase("Yes")) {
				System.out.println("Basement unlocked");
				character.useItem("Key");
				character.getCurrentRoom().addRoom(basement);
				basement.setUnlocked(true);
			} else if (scan.nextLine().equalsIgnoreCase("No")) {
				System.out.println("The basement is still locked.");
			}
		} else if (character.getRoomInput().equalsIgnoreCase("Basement")){
			System.out.println("Basement door seems to be locked.");
		}
		}
		
		if(character.getCurrentRoom().compareTo(basement) == 0) {
			base.basement();
		}
		
		if (character.getRoomInput().equalsIgnoreCase("Attic") && character.hasItem("Book")) {
			System.out.println("You find a part of the wall that pushes in. Upon opening it, you find a dark stair case infront of you.");
			attic.setUnlocked(true);
		}
	}
	
	}


}
