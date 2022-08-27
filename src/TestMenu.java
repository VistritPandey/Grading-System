import java.util.*;

public class TestMenu extends SurveyMenu {

    TestMenu(){
        this.menu = new ArrayList<>();
        this.menu.add("1) Create a new test");
        this.menu.add("2) Display a test with correct answers");
        this.menu.add("3) Load a test");
        this.menu.add("4) Save current test");
        this.menu.add("5) Take a test");
        this.menu.add("6) Modify a test");
        this.menu.add("7) Tabulate a test");
        this.menu.add("8) Grade a test");
        this.menu.add("9) Back");
    }



    @Override
    public Boolean isTest() {
        return true;
    }


    @Override
    public Survey returnNewSurvey() {
        return new Test();
    }


    @Override
    public String getFileType() {
        return "test";
    }
}