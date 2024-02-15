package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.ArrayList;
import java.util.List;

public class Basement {
	public static boolean heirloom = false;
	public static boolean knife = false;
	public static int user_hp = 10;
	public static int rel_hp = 10;
	private static String str = "";
	private static final Object lock = new Object();
	private static boolean end = false;

	public static class ConsoleInputReadTask {
		private final AtomicBoolean stop = new AtomicBoolean();

		public void stop() {
			stop.set(true);
		}

		public String requestInputWithTimeout(String prompt, int timeoutMillis, String timeoutMessage, boolean attack)
				throws IOException {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			displayString(prompt);
			String input = null;
			long startTime = System.currentTimeMillis();

			do {
				try {
					while (!br.ready() && !stop.get()) {
						Thread.sleep(200);

						if (System.currentTimeMillis() - startTime > timeoutMillis) {
							displayString(timeoutMessage);
							if (attack) {
								user_hp -= 2;
							}
							return "no_input";
						}
					}
					if (br.ready()) {
						input = br.readLine();
					}
				} catch (InterruptedException e) {
					System.out.println("ConsoleInputReadTask() cancelled");
					return "no_input";
				}
			} while ("".equals(input));
			return input;
		}
	}

	public static String getUserInputWithTimeout(int timeoutMillis, String prompt, String timeoutMessage,
			boolean attack) throws IOException {
		ConsoleInputReadTask consoleInputReadTask = new ConsoleInputReadTask();
		String userInput;

		try {
			userInput = consoleInputReadTask.requestInputWithTimeout(prompt, timeoutMillis, timeoutMessage, attack);
		} finally {
			consoleInputReadTask.stop();
		}

		return userInput;
	}

	public static String Basement(boolean pHeirloom, boolean pKnife) {
		heirloom = pHeirloom;
		knife = pKnife;
		displayString(
				"You walk through the Basement door and down the staircase to a dark cellar.\nWhile stepping into the Basement you hear a loud slam behind you. You turn to see your Relative standing between you and the staircase back to the KITCHEN.\nYour Relative brandishes a dagger then steps towards you with madness in their eyes and ready to STRIKE.\n");
		while (user_hp > 0 && rel_hp > 0) {
			displayHP(user_hp, rel_hp);
			combat();
		}
		if (user_hp == 0) {
			// displayString("GAME OVER!!!!!\nRestart?\n");
			return ("lose");
		} else {
			// displayString("Your Relative is now laying dead on the floor.\nAmidst your
			// grieving you see a glow coming from a door in the Basement.\nUpon
			// investigating this room you find a ritual table in the middle of the
			// room.\nYou activate the ritual and are faced with a choice.\nWill you SAVE
			// your Relative of their curse or will you STEAL the power for yourself?\n");
			return ("win");
		}
	}

	/*
	 * public static void ending_scene() { Scanner input = new Scanner(System.in);
	 * displayString("Your Relative is now laying dead on the floor.\nAmidst your grieving you see a glow coming from a door in the Basement.\nUpon investigating this room you find a ritual table in the middle of the room.\nYou activate the ritual and are faced with a choice.\nWill you SAVE your Relative of their curse or will you STEAL the power for yourself?\n"
	 * ); String choice = input.nextLine(); if (choice.equalsIgnoreCase("SAVE")) {
	 * 
	 * } else if (choice.equalsIgnoreCase("STEAL")){
	 * 
	 * } end = true; }
	 */

	public static boolean isEnd() {
		return end;
	}

	public static void displayHP(int user_hp, int rel_hp) {
		int user_diff = 10 - user_hp;
		int rel_diff = 10 - rel_hp;
		System.out.print("User HP: [");
		for (int i = 0; i < user_hp; i++) {
			System.out.print("|");
		}
		for (int i = 0; i < user_diff; i++) {
			System.out.print(".");
		}
		System.out.print("]\n");
		System.out.print("Relative HP: [");
		for (int i = 0; i < rel_hp; i++) {
			System.out.print("|");
		}
		for (int i = 0; i < rel_diff; i++) {
			System.out.print(".");
		}
		System.out.print("]\n");
	}

	public static void combat() {
		Scanner input = new Scanner(System.in);
		String basement = "";
		boolean input_not_valid = true;
		String[] available_inputs = null;
		String option = "";

		String equipOption;

		if(knife && heirloom){
			displayString("Do you want to use your KNIFE, your GUN or your FISTS?");
			while(true){
			equipOption = input.nextLine();

			if(equipOption.equalsIgnoreCase("knife")){
				knife = true;
				heirloom = false;
				break;
			}else if(equipOption.equalsIgnoreCase("gun")){
				knife = false;
				heirloom = true;
				break;
			}else if(equipOption.equalsIgnoreCase("fists")){
				knife = false;
				heirloom = false;
				break;
			}else{
				displayString("Must choose a valid option");
			}
		}
			
		}else if(heirloom){
			displayString("Do you want to use your GUN or your FISTS?");
			while(true){
				equipOption = input.nextLine();
	
				if(equipOption.equalsIgnoreCase("gun")){
					knife = false;
					heirloom = true;
					break;
				}else if(equipOption.equalsIgnoreCase("fists")){
					knife = false;
					heirloom = false;
					break;
				}else{
					displayString("Must choose a valid option");
				}
			}

		}else if(knife){
			displayString("Do you want to use your KNIFE or your FISTS?");

			while(true){
				equipOption = input.nextLine();
	
				if(equipOption.equalsIgnoreCase("knife")){
					knife = true;
					heirloom = false;
					break;
				}else if(equipOption.equalsIgnoreCase("fists")){
					knife = false;
					heirloom = false;
					break;
				}else{
					displayString("Must choose a valid option");
				}
			}
		}

		if (heirloom) {
			basement += "You have the family heirloom, you can SHOOT to obliterate your relative.\n";
		} else if (knife) {
			basement += "You have a knife, you can STRIKE to try to kill your relative.\n";
		} else {
			basement += "You raise your fists, you can PUNCH to try and massacre your relative.\n";
		}
		displayString(basement);
		if (input_not_valid) {
			option = input.nextLine();
			available_inputs = get_available_inputs();
		}
		while (input_not_valid) {
			for (String available_input : available_inputs) {
				if (option.equalsIgnoreCase(available_input)) {
					input_not_valid = false;
				}
			}
			if (input_not_valid) {
				displayString("Please enter a proper input.\n");
				option = input.nextLine();
			}
		}

		if (option.equalsIgnoreCase("STRIKE")) {
			knife_combat();
		} else if (option.equalsIgnoreCase("SHOOT")) {
			rel_hp = 0;
		}else if (option.equalsIgnoreCase("PUNCH")){
			fist_combat();
		}
	}

	public static String[] get_available_inputs() {
		List<String> inputs = new ArrayList<String>();
		if (knife) {
			inputs.add("STRIKE");
		}
		if (heirloom) {
			inputs.add("SHOOT");
		}else{
			inputs.add("PUNCH");

		}
		String[] str_inputs = inputs.toArray(new String[0]);
		return str_inputs;
	}

	public static void knife_combat() {
		double strike = Math.random();
		double hit = 0.45;
		double block = 0.60;
		double dodge = 0.70;
		double counter = 1;
		if (strike <= hit) {
			rel_hp -= 2;
			displayString("Your attack landed wounding your relative.\n");
		} else if (strike <= block) {
			block();
		} else if (strike <= dodge) {
			displayString("Your attack was dodged by your relative.\n");
		} else if (strike <= counter) {
			counter();
		}
	}

	public static void fist_combat() {
		double strike = Math.random();
		double hit = 0.45;
		double block = 0.60;
		double dodge = 0.70;
		double counter = 1;
		if (strike <= hit) {
			rel_hp -= 2;
			displayString("Your attack landed wounding your relative.\n");
		} else if (strike <= block) {
			block();
		} else if (strike <= dodge) {
			displayString("Your attack was dodged by your relative.\n");
		} else if (strike <= counter) {
			counter();
		}
	}

	public static void block() {
		int timeout = 4;
		String prompt = "Your attack was blocked by your relative.\nQuickly STRIKE to follow up with another attack.\n";
		String timeoutMessage = "You missed your attack and your relative countered with an attack.\n";
		try {
			str = getUserInputWithTimeout(timeout * 1000, prompt, timeoutMessage, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (str.equalsIgnoreCase("STRIKE")) {
			displayString("You followed up with another attack that successfully wounds your relative.\n");
			rel_hp -= 2;
		} else if (!str.equals("no_input")) {
			displayString("You missed your attack and your relative countered with an attack.\n");
			user_hp -= 2;
		}
	}

	public static void counter() {
		int timeout = 4;
		String prompt = "Your relative dodged your attack and countering your attack.\nQuickly DODGE to avoid the attack.\n";
		String timeoutMessage = "You have been hit by your relative's counter.\n";
		try {
			str = getUserInputWithTimeout(timeout * 1000, prompt, timeoutMessage, true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (str.equalsIgnoreCase("DODGE")) {
			double stumble = Math.random();
			double stumble_percent = 0.75;
			if (stumble < stumble_percent) {
				timeout = 5;
				prompt = "Your relative stumbled after missing.\nQuickly STRIKE to throw a counter attack.\n";
				timeoutMessage = "You missed your attack.\n";
				try {
					str = getUserInputWithTimeout(timeout * 1000, prompt, timeoutMessage, false);
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (str.equalsIgnoreCase("STRIKE")) {
					displayString("You hit your relative with a counter attack.\n");
					rel_hp -= 2;
				} else if (!str.equals("no_input")) {
					displayString("You missed your attack.\n");
				}
			} else {
				displayString("You dodged your relative's attack.\n");
			}
		} else if (!str.equals("no_input")) {
			displayString("You were hit by your relative's counter attack.\n");
			user_hp -= 2;
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
