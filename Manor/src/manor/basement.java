package game;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.ArrayList;
import java.util.List;

public class basement {
    public static boolean heirloom = false;
    public static boolean knife = true;
    public static int user_hp = 10;
    public static int rel_hp = 10;
    private static String str = "";
    private static final Object lock = new Object();

    public static class ConsoleInputReadTask {
        private final AtomicBoolean stop = new AtomicBoolean();

        public void stop() {
            stop.set(true);
        }

        public String requestInputWithTimeout(String prompt, int timeoutMillis, String timeoutMessage, boolean attack) throws IOException {
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

    public static String getUserInputWithTimeout(int timeoutMillis, String prompt, String timeoutMessage, boolean attack) throws IOException {
        ConsoleInputReadTask consoleInputReadTask = new ConsoleInputReadTask();
        String userInput;

        try {
            userInput = consoleInputReadTask.requestInputWithTimeout(prompt, timeoutMillis, timeoutMessage, attack);
        } finally {
            consoleInputReadTask.stop();
        }

        return userInput;
    }

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
        displayString("Your relative is now laying dead on the floor.\nAmidst you grieving what you have done, you see a glow coming from a door in the basement.\nUpon investigating this room you find a ritual table in the middle of the room.\nYou activate the ritual and are faced with a choice.\nWill you SAVE your relative of their curse or will you STEAL the power for yourself?\n");
        String choice = input.nextLine();
        if (choice.equalsIgnoreCase("SAVE")) {

        }
        else if (choice.equalsIgnoreCase("STEAL")){
            
        }
    }

    public static void displayHP(int user_hp, int rel_hp) {
        int user_diff = 10-user_hp;
        int rel_diff = 10-rel_hp;
        displayString("User HP: [");
        for (int i = 0; i < user_hp; i++) {
            displayString("|");
        }
        for (int i = 0; i < user_diff; i++) {
            displayString(".");
        }
        displayString("]\n");
        displayString("Relative HP: [");
        for (int i = 0; i < rel_hp; i++) {
            displayString("|");
        }
        for (int i = 0; i < rel_diff; i++) {
            displayString(".");
        }
        displayString("]\n");
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
            displayString("Your attack was dodged by your relative.\n");
        }
        else if (strike <= counter){
            counter();
        }
    }

    public static void block() {
        int timeout = 4;
        String prompt = "Your attack was blocked by your relative.\nQuickly STRIKE to follow up with another attack.\n";
        String timeoutMessage = "You missed your attack and your relative countered with an attack.\n";
        try{
            str = getUserInputWithTimeout(timeout*1000, prompt, timeoutMessage, true);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        if (str.equalsIgnoreCase("STRIKE")) {
            displayString("You followed up with another attack that successfully wounds your relative.\n");
            rel_hp -= 2;
        }
        else if (!str.equals("no_input")) {
            displayString("You missed your attack and your relative countered with an attack.\n");
            user_hp -= 2;
        }
    }

    public static void counter() {
        int timeout = 4;
        String prompt = "Your relative dodged your attack and countering your attack.\nQuickly DODGE to avoid the attack.\n";
        String timeoutMessage = "You have been hit by your relative's counter.\n";
        try{
            str = getUserInputWithTimeout(timeout*1000, prompt, timeoutMessage, true);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        if (str.equalsIgnoreCase("DODGE")) {
            double stumble = Math.random();
            double stumble_percent = 0.75;
            if (stumble < stumble_percent) {
                timeout = 5;
                prompt = "Your relative stumbled after missing.\nQuickly STRIKE to throw a counter attack.\n";
                timeoutMessage = "You missed your attack.\n";
                try{
                    str = getUserInputWithTimeout(timeout*1000, prompt, timeoutMessage, false);
                }
                catch (IOException e){
                    e.printStackTrace();
                }

                if (str.equalsIgnoreCase("STRIKE")) {
                    displayString("You hit your relative with a counter attack.\n");
                    rel_hp -= 2;
                }
                else if (!str.equals("no_input")) {
                    displayString("You missed your attack.\n");
                }
            }
            else {
                displayString("You dodged your relative's attack.\n");
            }
        } 
        else if (!str.equals("no_input")) {
            displayString("You were hit by your relative's counter attack.\n");
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
