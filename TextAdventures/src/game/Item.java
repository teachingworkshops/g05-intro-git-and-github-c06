package game;

public class Item {
	
	private String name;
	private String description;
	private String interaction;
	private boolean holdable;
	
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
		System.out.println(description);
	}
	
	public void printInteraction() {
		System.out.println(interaction);
	}
	
	public boolean isHoldable() {
		return holdable;
	}

}
