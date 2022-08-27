import java.util.*;

public abstract class AbstractMenu {
    protected ArrayList<String> menu;
    protected Input in;
    protected Output out;

    AbstractMenu(){
        in = new Input();
        out = new Output();
    }

    /*
    purpose: displaying the menu
    params: none
    return: none
    */

    public void displayMenu(){
        Output output = new Output();
        output.displayStringList(this.menu);
    }

}