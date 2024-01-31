package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {
	private static final Object lock = new Object();

	public static void main(String[] args) {

		Room courtyard = new Room("COURTYARD",
				"TO PLAY THIS GAME TRY TYPING THE CAPITALIZED WORDS YOU SEE\n\nAfter recieving a worrisome letter about your Relative, you find yourself standing in the COURTYARD of your Relatives decrepit looking manor.\nYou could ENTER through the front door, or simply LEAVE.",
				true);
		Room enter = new Room("ENTER",
				"You find yourself in the ATRIUM. you notice three paths leading to the KITCHEN, the DINING ROOM, or UPSTAIRS",
				true);
		Room leave = new Room("LEAVE", "", true);

		Room atrium = new Room("ATRIUM",
				"From the ATRIUM, you notice three paths leading to the KITCHEN, the DINING ROOM, or UPSTAIRS", true);
		Room kitchen = new Room("KITCHEN",
				"You enter the KITCHEN and immediately smell rot. You notice a door to the ATRIUM but also another bronze door, seemingly leading to the BASEMENT.",
				true);
		kitchen.setItem(new Item("KNIFE", "You see a KNIFE embedded in what you're fairly sure is a pig carcass.",
				"You pull the KNIFE from the carcass and take it with you. It looks awful and smell worse.", true));
		Room diningRoom = new Room("DINING ROOM",
				"The DINING ROOM is foreboding. There's a dusty MIRROR in the corner. Maybe you could reflect on what led you to be trapped in the Manor of your Relative or head back to the ATRIUM.",
				true);

		Room upstairs = new Room("UPSTAIRS",
				"You enter the UPSTAIRS hallway. You see one entrance to the STUDY and another to the BEDROOM. Looking down the stairs you see the ATRIUM.",
				true);
		Room study = new Room("STUDY",
				"You enter the STUDY. You're not sure which is older, the cobwebs or the books. Behind you is the door to the UPSTAIRS hallway.",
				true);
		study.setItem(new Item("BOOK", "One BOOK however seems to have been used recently.",
				"You read the BOOK. It talks about an additional entrance to the ATTIC from the UPSTAIRS hallway.",
				true));
		Room bedroom = new Room("BEDROOM",
				"You enter the BEDROOM, the bed seems dusty from lack of use. Behind you is a door to the UPSTAIRS hallway.",
				true);
		bedroom.setItem(new Item("KEY", "There seems to be a bronze KEY sitting on the top of the dresser.",
				"You picked up the KEY.", true));
		Room attic = new Room("ATTIC",
				"You are in the ATTIC. You feel oddly at peace while sorrounded by all of the boxes full of things that have been long forgotten.\nThe path behind you leads to the UPSTAIRS hallway.",
				false);
		attic.setItem(
				new Item("HEIRLOOM", "You notice a family HEIRLOOM hung up on the wall, it is the ol' family rifle.",
						"You picked up the HIERLOOM rifle", true));

		Room basement1 = new Room("BASEMENT", "You are in the BASEMENT.", false);
		Room basement2 = new Room("BASEMENT",
				"Your Relative is now laying dead on the floor.\nAmidst your adrenaline and confusion, you notice a glow coming from another ROOM in the Basement.\n",
				true);
		Room room = new Room("ROOM",
				"Upon investigating this room you find a ritual table in the middle of the room, glowing with some sort of unkown energy.\nThe power begins to course through you, tempting your mind with possibilities.\nYou can make a choice, will you SAVE your Relative curing them of their wounds and insanity, will you STEAL the power for yourself, or will you LEAVE it all behind?\n",
				true);

		Room current = courtyard;

		Player character = new Player(current);

		ArrayList<Room> c = new ArrayList<Room>() {
			{
				add(enter);
				add(leave);
			}
		};

		ArrayList<Room> k = new ArrayList<Room>() {
			{
				add(atrium);
			}

		};

		ArrayList<Room> k2 = new ArrayList<Room>() {
			{
				add(atrium);
				add(basement1);
			}

		};

		ArrayList<Room> k3 = new ArrayList<Room>() {
			{
				add(atrium);
				add(basement2);
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
			}

		};

		ArrayList<Room> u2 = new ArrayList<Room>() {
			{
				add(atrium);
				add(study);
				add(bedroom);
				add(attic);
			}

		};

		ArrayList<Room> b = new ArrayList<Room>() {
			{
				add(room);
			}
		};

		ArrayList<Room> s = new ArrayList<Room>() {
			{
				add(upstairs);
			}
		};

		Scanner scan = new Scanner(System.in);

		courtyard.fillList(c);
		enter.fillList(a);

		atrium.fillList(a);
		kitchen.fillList(k);
		diningRoom.fillList(k);

		upstairs.fillList(u);
		study.fillList(s);
		bedroom.fillList(s);
		attic.fillList(s);

		basement2.fillList(b);

		Basement base = new Basement();
		boolean end = false;

		while (!end) {
			character.move();

			if (character.getCurrentRoom().compareTo(leave) == 0) {
				displayString("ENDING 5: You depart back the way you came. Unsatisfied, but alive.\n");
				end = true;
			}

			if (character.getCurrentRoom().compareTo(enter) == 0) {
				displayString("You hear a loud click as the front door closes behind you. Seems like you're locked in here now.\n");
				current = atrium;
			}

			if (character.getCurrentRoom().compareTo(upstairs) == 0
					&& character.getRoomInput().equalsIgnoreCase("Attic") && character.hasItem("Book")) {
				displayString(
						"You find a part of the wall that pushes in. Upon opening it, you find a dark stair case leading to the ATTIC infront of you.\n");
				attic.setUnlocked(true);
				character.currentRoom = attic;
				upstairs.fillList(u2);
			}

			if (character.getCurrentRoom().compareTo(kitchen) == 0) {

				if (character.getRoomInput().equalsIgnoreCase("Basement") && character.hasItem("Key")) {
					displayString("Unlock the BASEMENT door? (YES/NO)\n");
					if (scan.nextLine().equalsIgnoreCase("Yes")) {
						displayString(
								"You unlock the BASEMENT door, what remains behind it is up to you to find out.\n");
						character.useItem("Key");
						character.getCurrentRoom().addRoom(basement1);
						basement1.setUnlocked(true);
					} else if (scan.nextLine().equalsIgnoreCase("No")) {
						displayString("You leave the BASEMENT door locked, maybe for the better...\n");
					}
				} else if (character.getRoomInput().equalsIgnoreCase("Basement")) {
					displayString(
							"The BASEMENT door appears to be locked. There must be a KEY somewhere to unlock it.\n");
				}
			}

			if (character.getCurrentRoom().compareTo(diningRoom) == 0) {
				if (character.getRoomInput().equalsIgnoreCase("Mirror")) {
					displayString(
							"The mirror is incredibly dirty. You try and wipe away the grime, but the mirror doesn't seem to lose any and you remain unable to see yourself.\n");
				}
			}

			if (character.getCurrentRoom().compareTo(basement1) == 0) {
				String result = base.Basement(character.hasItem("Heirloom"), character.hasItem("Knife"));

				if (result.equals("lose")) {
					displayString("ENDING 4: You are dead.\n");
					end = true;
				} else if (result.equals("win")) {
					kitchen.fillList(k3);
					character.currentRoom = basement2;
				}
			}

			if (character.getCurrentRoom().compareTo(room) == 0) {
				if (character.getRoomInput().equalsIgnoreCase("Leave")) {
					displayString(
							"ENDING 1: This is all too much for you.\nWhatever ritual magic this is should not be messed with.\nYou head out the front door, now somehow unlocked, exhausted and ready to leave the Manor behind for good.\n");
					end = true;
				} else if (character.getRoomInput().equalsIgnoreCase("Save")) {
					displayString(
							"ENDING 2: You ignore the temptation of power and channel the energy into your dead Relative.\nYour Relative stirs and stands up, they seem to be as confused as you are and seem to have no memory of setting up or activating the ritual.\nYou and your Relative head out the front door, now somehow unlocked, exhausted and ready to leave the Manor behind for good, though that thought may not be shared by your Relative.\n");
					end = true;
				} else if (character.getRoomInput().equalsIgnoreCase("Steal")) {
					displayString(
							"ENDING 3: Yes, yes, the whispers are correct.\nThis power should not be wasted, especially when it can give you everything you have ever wanted!\nYou head out the front door, now somehow unlocked, ready to claim what is yours and ecstatic to test your new powers on any who get in your way.\n");
					end = true;
				}
			}
		}

	}
	
	public static void displayString(String prompt) {
		for (char letter : prompt.toCharArray()) {
			synchronized (lock) {
				try {
					lock.wait(25);
					System.out.print(letter);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
