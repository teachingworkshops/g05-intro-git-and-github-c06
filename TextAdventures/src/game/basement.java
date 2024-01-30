package game;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.List;

public class basement {
    public static boolean heirloom = false;
    public static boolean knife = true;
    public static int user_hp = 10;
    public static int rel_hp = 10;
    private static String str = "";
    private static final Object lock = new Object();
    private static boolean end = false;

    public static void basement() {
        displayString("You walk through the basement door and down the stair case to a dark cellar.\nWhile exploring the basement you hear a loud slam behind you. You turn around between you and the stair case is your long lost relative. However they have been taken over by a curse and is blocking your exit.\n");
        while (user_hp > 0 && rel_hp > 0) {
            displayHP(user_hp, rel_hp);
            combat();
        }
        if (user_hp == 0) { 
            displayString("GAME OVER!!!!!\nRestart?\n");
            restart();
        }
        else { 
            ending_scene();
        }
    }

    public static void ending_scene() {
        Scanner input = new Scanner(System.in);
        displayString("Your relative is now laying dead on the floor.\nAmidst your grieving you see a glow coming from a door in the basement.\nUpon investigating this room you find a ritual table in the middle of the room.\nYou activate the ritual and are faced with a choice.\nWill you SAVE your relative of their curse or will you STEAL the power for yourself?\n");
        String choice = input.nextLine();
        if (choice.equalsIgnoreCase("SAVE")) {

        }
        else if (choice.equalsIgnoreCase("STEAL")){

        }
        end = true;
    }
    
    public static boolean isEnd() {
    	return end;
    }

    public static void displayHP(int user_hp, int rel_hp) {
        int user_diff = 10-user_hp;
        int rel_diff = 10-rel_hp;
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
        if (heirloom) {
            basement += "You have the family heirloom, you can SHOOT to obliterate your relative.\n";
        }
        else if (knife) {
            basement += "You have a knife, you can STRIKE to try to kill your relative.\n";
        }
        else {
            basement += "You have nothing to defend yourself with and your relative rushes and brutally kills you.\n";
            displayString(basement);
            restart();
        }
        displayString(basement);
        boolean input_not_valid = true;
        String option = input.nextLine();
        String[] available_inputs = get_available_inputs();

        while (input_not_valid) {
            for (String available_input : available_inputs) {
                if (option.equalsIgnoreCase(available_input)) {
                    input_not_valid = false;
                }
            }
            if(input_not_valid) {
                displayString("Please enter a proper input.\n");
                option = input.nextLine();
            }
        }
        
        if (option.equalsIgnoreCase("STRIKE")) {
            knife_combat();
        }
        else {
            
        }
    }

    public static String[] get_available_inputs() {
        List<String> inputs = new ArrayList<String>();
        if (knife) {
            inputs.add("STRIKE");
        }
        if (heirloom) {
            inputs.add("SHOOT");
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
        }
        else if (strike <= block) {
            block();
        }
        else if (strike <= dodge) {
            displayString("Your attack was dodged by your relative\n");
        }
        else if (strike <= counter){
            // strike is getting countered prompt to counter the counter if missed rerun sequence if countered -dmg rerun sequence
            counter();
        }
    }

    public static void block() {
        Scanner input = new Scanner(System.in);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run()
            {
                if( str.equalsIgnoreCase("") )
                {
                    displayString( "\nYou missed your atttack\n" );
                }
            }    
        };
        timer.schedule(task, 4*1000);
        displayString("Your attack was blocked by your relative.\nQuickly STRIKE to follow up with another attack.\n");
        str = input.nextLine();
        timer.cancel();
        task.cancel();
        if (str.equalsIgnoreCase("STRIKE")) {
            System.out.print("You followed up with another attack that successfully wounds your relative.\n");
            rel_hp -= 2;
        }
    }

    public static void counter() {
        Scanner input = new Scanner(System.in);
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            public void run()
            {
                if( str.equalsIgnoreCase("") )
                {
                    displayString( "\nYou have been hit by your relatives counter\n" );
                    user_hp -= 2;
                }
            }    
        };
        TimerTask task2 = new TimerTask()
        {
            public void run()
            {
                if( str.equalsIgnoreCase("") )
                {
                    displayString( "\nYou missed your atttack\n" );
                }
            }    
        };
        timer.schedule(task, 4*1000);
        displayString("Your relative dodged your attack and countering your attack.\nQuickly DODGE to avoid the attack.\n");
        str = input.nextLine();
        System.out.print("Test");
        // timer.cancel();
        // task.cancel();
        if (str.equalsIgnoreCase("DODGE")) {
            double stumble = Math.random();
            double stumble_percent = 0.75;
            if (stumble < stumble_percent) {
                Timer timer2 = new Timer();
                timer2.schedule(task2, 5*1000);
                displayString("Your relative stumbled after missing.\nQuickly STRIKE to throw a counter attack.\n");
                str = input.nextLine();
                if (str.equalsIgnoreCase("STRIKE")) {
                    rel_hp -= 2;
                }
                // timer2.cancel();
                // task2.cancel();
            }
        }
        else {
            displayString("You were hit by your relatives counter attack\n");
            user_hp -= 2;
        }
    }

    public static void restart() {
        Scanner input = new Scanner(System.in);
        displayString("You are dead. RESTART?");
        String option = input.nextLine();

        if (option.equalsIgnoreCase("RESTART")) {
            // Add starting method
        }
    }

    public static void displayString(String prompt) {
        for (char letter : prompt.toCharArray()) {
            synchronized (lock) {
                try { 
                    lock.wait(25);
                    System.out.print(letter); 
                } 
                catch (InterruptedException e) { 
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        basement();
    }
}
