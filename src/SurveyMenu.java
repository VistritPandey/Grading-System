import java.io.*;
import java.util.*;

public class SurveyMenu extends AbstractMenu implements Serializable {
    private Survey onGoingSurvey;
    private String seperator;
    private boolean unsavedSurvey;
    private String selectedPath;
    private String surveyName;

    SurveyMenu(){
        this.seperator = File.separator;
        this.unsavedSurvey = false;
        this.menu = new ArrayList<>();
        this.menu.add("1) Create a new survey");
        this.menu.add("2) Display a survey");
        this.menu.add("3) Load a survey");
        this.menu.add("4) Save current survey");
        this.menu.add("5) Take the current survey");
        this.menu.add("6) Modify the current survey");
        this.menu.add("7) Tabulate a survey");
        this.menu.add("8) Back");
    }

    /*
    purpose: load menu items
    params: none
    return: none
    */

    public void loadMenuItems(){
        while(true){
            this.displayMenu();
            String userChoice = this.in.promptAndGet("");
            if(userChoice.equals("1")) {
                this.createSurvey();
            }
            else if(userChoice.equals("2")) {
                this.displaySurvey();
            }
            else if(userChoice.equals("3")) {
                this.loadSurvey();
            }
            else if(userChoice.equals("4")) {
                this.saveSurvey(this.onGoingSurvey);
            }
            else if(userChoice.equals("5")) {
                this.takeSurvey();
            }
            else if(userChoice.equals("6")) {
                this.modifySurvey();
            }
            else if(userChoice.equals("7")) {
                this.tabulateSurvey();
            }
            else if(userChoice.equals("8")) {
                if(this.isTest()){
                    this.gradeTest();
                }
                    else{
                    if(this.goBack()){
                        return;
                    }
            }}
            else if(userChoice.equals("9")) {
                if(this.isTest()){
                    this.goBack();
                }
                else{
                    this.out.display("invalid input");
                    continue;
                    }
                }
            else{
                this.out.display("invalid input");
            }
        }
    }

    private void tabulateSurvey() {
        Survey survey = this.displayAllFiles("tabulate");
        for(int i = 0; i <survey.getQuestionSize(); i ++){
            survey.getQuestion(i + 1).displayReplies();
            this.out.display("");
            survey.getQuestion(i + 1).displayTabulate();
            this.out.display("-----------");
        }
    }

    private boolean goBack() {
        if(this.onGoingSurvey != null){
            while(true){
                String confirmation = this.in.promptAndGet("If you have an unsaved test/survey, changes will be unsaved, enter 'Yes' to confirm");
                if(confirmation.equals("Yes")){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    private void gradeTest() {
        Survey survey = this.displayAllFiles("grade");
        survey.displayGrade();
    }

    /*
    purpose: to create the new survey
    params: none
    return: none
    */

    void createSurvey() {
        Menu2 menu2 = new Menu2(this.isTest());
        if (this.unsavedSurvey){
            String in = this.in.promptAndGet("You have an unsaved survey, Press 5 to continue or 1 to start over");
            if(!in.equals("5") && !in.equals("1")){
                return;
            }
            else if (in.equals("5")) {
                this.continueSurvey();
                return;
            }
        }
        this.onGoingSurvey = menu2.loadMenuItems(this.returnNewSurvey());
        this.unsavedSurvey = true;
        this.isSurveyNull();
    }

    /*
    purpose: display the survey
    params: none
    return: none
    */

    public void displaySurvey(){
        Survey sur = this.displayAllFiles("display");
        if(sur == null){
            return;
        }
        int index = 1;
        for(Question q: sur.getQuestions()){
            this.out.displaySameLine(index + ") ");
            q.display(this.isTest());
            this.out.display("");
            index +=1;
        }
    }

    /*
    purpose: load the survey
    params: none
    return: none
    */

    public void loadSurvey(){
        Survey temp = this.displayAllFiles( "load");
        if(temp == null){
            return;
        }
        if(this.onGoingSurvey != null){
            String in = this.in.promptAndGet("you have an active survey, Enter 'Y' to overwrite");
            if(!in.equals("Y")){
                return;
            }
        }
        this.onGoingSurvey = temp;
        this.unsavedSurvey = false;
        this.out.display("survey loaded successfully");
    }

    /*
    purpose: to save the survey
    params: survey, using from Survey
    return: none
    */

    public void saveSurvey(Survey survey)  {
        if(this.onGoingSurvey == null){
            this.out.display("no " + this.getFileType() + " is in use at the moment to save");
            return;
        }
        String in = this.in.promptAndGet("please enter a name for your " + this.getFileType());
        File path = new File("Serializable" + this.seperator + this.getFileType() + "s" + this.seperator + in + ".ser");
        if(path.exists()){
            String confirm = this.in.promptAndGet("a file with the name '"+in+"' already exists, are you sure you want to overwrite it? Enter 'Y' to overwrite, anything else to stop");
            if(!confirm.equals("Y")){
                this.out.display("save is canceled");
                return;
            }
        }
        survey.setSurveyName(in);
        this.out.serialize(this.onGoingSurvey,"Serializable"+ this.seperator + this.getFileType() +"s" + this.seperator + in + ".ser");
        this.out.display( this.getFileType() + " has been saved");
        this.unsavedSurvey = false;
    }

    /*
    purpose: continuing survey
    params: none
    return: none
    */

    public void continueSurvey(){
        if(this.onGoingSurvey == null){
            this.out.display("no current " + this.getFileType() + " is active");
        }else{
            Menu2 menu2 = new Menu2(this.isTest());
            this.onGoingSurvey = menu2.loadMenuItems(this.onGoingSurvey);
            this.isSurveyNull();
        }
    }

    /*
    purpose: display all the saved and current files
    params: none
    return: none
    */

    public Survey displayAllFiles(String instruction) {
        this.selectedPath = "";
        File[] surveyFiles = new File("Serializable" + this.seperator + this.getFileType() + "s").listFiles();
        if (surveyFiles.length < 1 && this.onGoingSurvey == null) {
            this.out.display("No " + this.getFileType() + "s are stored");
            return null;
        }
        this.out.display("Select a file you want to "+instruction);
        for (int i = 1; i < surveyFiles.length + 1; i++) {
            this.out.display((i) + "." + surveyFiles[i - 1].getName());
        }
        int i2 = surveyFiles.length + 1;
        if(instruction.equals( "display")){
            this.out.display(i2 + "." + "current " + this.getFileType());
        }
        while (true) {
            int i = this.in.getIntegerInput("Enter the number for the survey you want to open");
            if(i == 0) continue;
            if(i == i2 && instruction.equals("display")){
                if(this.onGoingSurvey == null){
                    this.out.display("No survey loaded or is currently active");
                    return null;
                }else{
                    return this.onGoingSurvey;
                }
            }
            if (i != 0 && i > 0 && i <= surveyFiles.length) {
                this.selectedPath = surveyFiles[i - 1].getPath();
                this.surveyName = surveyFiles[i - 1].getName();
                return this.in.deserialize(this.selectedPath);
            }
        }
    }

    /*
    purpose: display all the questions in the saved file
    params: survey
    return: none
    */

    public void displayQuestions(Survey survey){
        int i = 1;
        for(Question q: survey.getQuestions()){
            this.out.displaySameLine(i + ") ");
            q.display(this.isTest());
            this.out.display("");
            i +=1;
        }
    }

    /*
    purpose: modify the survey
    params: none
    return: none
    */

    public void modifySurvey(){
        Survey survey = this.displayAllFiles("modify");
        while(true){
            this.displayQuestions(survey);
            this.out.display("Enter the index of question you want to modify, enter 'B' to go back, 'S' to save the changes");
            String in = this.in.getInput();
            if(in.equals("B")){
                return;
            }else if(in.equals("S")){
                this.out.serialize(survey,this.selectedPath);
                this.out.display("Survey saved successfully!!");
                return;
            }
            try{
                Integer in2 = Integer.parseInt(in);
                try{
                    survey.getQuestionNumber(in2).modifyQuestion(this.isTest());
                }catch(IndexOutOfBoundsException e){
                    this.out.display("Enter from the options");
                    continue;
                }
            }catch (NumberFormatException e){
                this.out.display("Enter from the options");
            }

        }
    }

    /*
    purpose: take the survey
    params: none
    return: none
    */

    public void takeSurvey(){
        Survey survey = this.displayAllFiles("take");
        survey.takeSurvey(this.isTest());
        this.out.serialize(survey,"Serializable" + this.seperator + this.getFileType() +"s" + this.seperator + this.surveyName);
        this.out.display(this.getFileType() +" has been taken and saved");
    }

    /*
    purpose: to check if the survey is empty or not
    params: none
    return: none
    */

    public void isSurveyNull(){
        if(this.onGoingSurvey.getQuestionSize() == 0){
            this.onGoingSurvey = null;
            this.unsavedSurvey = false;
        }else{
            this.unsavedSurvey = true;
        }
    }

    /*
    purpose: this is a function for future, when there would be a survey and a test
    params: none
    return: false
    */

    public Boolean isTest(){
        return false;
    }

    /*
    purpose: this is a function for future, when there would be a survey and a test
    params: none
    return: "survey"
    */

    public String getFileType(){
        return "survey";
    }

    /*
    purpose: function to create a new survey
    params: none
    return: send a response to create a new survey
    */

    public Survey returnNewSurvey(){
        return new Survey();
    }

}