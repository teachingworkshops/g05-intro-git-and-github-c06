package game;

public class Item {
	
	private String name;
	private String description;
	private String interaction;
	private boolean holdable;
	private static final Object lock = new Object();
	
	public Item(String name, String description, String interaction, boolean holdable) {
		
		this.name = name;
		this.description = description;
		this.interaction = interaction;
		this.holdable = holdable;
		
	}
	
	public String getName() {
		return name;
	}
	
	public void printDescript() {
		displayString(description+"\n");
	}
	
	public void printInteraction() {
		displayString(interaction+"\n");
	}
	
	public boolean isHoldable() {
		return holdable;
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
