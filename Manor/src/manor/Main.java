package manor;

import java.util.Scanner;

public class Main 
{
    public static String kitchenEnter = "You smell rot. You see a KNIFE embedded in what you're fairly sure is a pig carcass. There's also a BASEMENT door, which feels very locked to you.";
    public static Boolean inventory[] = {false, false, false} ;
    // Inventory: Kitchen Knife, blank, blank
    
    public static void main(String[] args)
    {
        
        courtyard(args);
    } 
    
    public static void courtyard (String[] args)
    {
        Scanner myScanner = new Scanner(System.in) ;
        System.out.println("You find yourself in a courtyard before a decrepit looking manor. You could ENTER, or simply LEAVE.") ;
        String userInput = myScanner.nextLine() ;
        
        if(userInput.equals("ENTER"))
        {
            System.out.println("You find yourself in the atrium. You hear a click behind you, and feel a strong locking sensation.") ;
            atrium(args);
        }
        else if(userInput.equals("LEAVE"))
        {
            System.out.println("ENDING 1: You depart back the way you came- unsatisfied, but alive.") ;
        }
        else
        {
            System.out.println("Not a valid choice") ;
            courtyard(args);
        }
    } 
    
    public static void atrium (String[] args)
    {
        Scanner myScanner = new Scanner(System.in) ;
        System.out.println("From the atrium, you could try the KITCHEN, the UPSTAIRS, the DINING ROOM, or GO BACK.") ;
        String userInput = myScanner.nextLine() ;
        
        if(userInput.equals("GO BACK"))
        {
            System.out.println("You push the door. The door pushes back. The two of you call it a tie.") ;
            atrium(args);
        }
        else if(userInput.equals("KITCHEN"))
        {
            System.out.println("You push the door. The door pushes back. The two of you call it a tie.") ;
            kitchen(args);
        }
        else if(userInput.equals("DINING ROOM"))
        {
            System.out.println("You push the door. The door pushes back. The two of you call it a tie.") ;
            diningRoom(args);
        }
        else if(userInput.equals("UPSTAIRS"))
        {
            System.out.println("You push the door. The door pushes back. The two of you call it a tie.") ;
            upstairs(args);
        }
        else
        {
            System.out.println("Not a valid choice") ;
            atrium(args);
        }
    } 
    public static void kitchen (String[] args)
    {
        Scanner myScanner = new Scanner(System.in) ;
        
        System.out.println(kitchenEnter) ;
        String userInput = myScanner.nextLine() ;
        
        if(userInput.equals("GO BACK"))
        {
            System.out.println("You head back to the atrium.") ;
            atrium(args);
        }
        else if(userInput.equals("KNIFE"))
        {
            System.out.println("You pull the knife from the carcass and take it with you. It looks awful. It smell worse.") ;
            kitchenEnter = "You smell rot. There's a BASEMENT door, which feels very locked to you."; // change the text upon entering the kitchen to no longer mentioned the knife
            inventory[0] = true; // adding the knife to the inventory 
            kitchen(args);
        }
        else if(userInput.equals("BASEMENT"))
        {
            if(1 == 0)
            {
                System.out.println("With the lock undone, you descend into the basement.") ;
            }
            else
            {
                System.out.println("Maybe if you stare at this door hard enough, it'll unlock itself.") ;
                kitchen(args);
            }
        }
        else
        {
            System.out.println("Not a valid choice") ;
            atrium(args);
        }
    }
    
    public static void diningRoom (String[] args)
    {
        Scanner myScanner = new Scanner(System.in) ;
        System.out.println("The dining room is foreboding. There's a dusty MIRROR in the corner. Maybe you could reflect on why you didn't leave while you still could.") ;
        String userInput = myScanner.nextLine() ;
        
        if(userInput.equals("GO BACK"))
        {
            System.out.println("You return to the atrium.") ;
            atrium(args);
        }
        else if(userInput.equals("MIRROR"))
        {
            System.out.println("The mirror is incredibly dirty. You try and wipe away the grime, but while your sleeve absorbs gunk, the mirror doesn't seem to lose any. You can't see yourself- is that you're covered in filth, or that you're nothing worth looking at? You leave before you can decide.") ;
            atrium(args);
        }
        else
        {
            System.out.println("Not a valid choice") ;
            diningRoom(args);
        }
    }
    
    public static void upstairs (String[] args)
    {
        Scanner myScanner = new Scanner(System.in) ;
        System.out.println("From the atrium, you could try the KITCHEN, the UPSTAIRS, the DINING ROOM, or GO BACK.") ;
        String userInput = myScanner.nextLine() ;
        
        if(userInput.equals("GO BACK"))
        {
            
        }
        else
        {
            System.out.println("Not a valid choice") ;
            atrium(args);
        }
    }
}
