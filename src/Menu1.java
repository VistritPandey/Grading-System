import java.util.*;

public class Menu1 extends AbstractMenu {
    private SurveyMenu Menu1;
    Menu1(){
        this.menu = new ArrayList<>();
        this.menu.add("1) Survey");
        this.menu.add("2) Test");
        this.menu.add("3) Quit");
        this.loadMenuItems();
    }

    public void loadMenuItems(){
        this.out.display("SE310 Final Project (by Vistrit Pandey)");
        while(true){
            this.displayMenu();
            String userChoice = this.in.promptAndGet("");
                if (userChoice.equals("1")) {
                    Menu1 = new SurveyMenu();
                    Menu1.loadMenuItems();
                }
                else if (userChoice.equals("2")){
                    Menu1 = new TestMenu();
                    Menu1.loadMenuItems();
                }
                else if (userChoice.equals("3")){
                    this.out.display("Bye have a great time");
                    System.exit(0);
                }
                else{
                    this.out.display("invalid input");
                    continue;
            }
        }
    }


}