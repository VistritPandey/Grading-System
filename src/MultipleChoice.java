import java.util.*;

public class MultipleChoice extends Question {

    int id = 0;
    protected Integer numOfCorrectAns;
    protected Output out;

    protected ArrayList<String> options;
    protected ArrayList<String> correctChoices;
    protected ArrayList<String> userChoices;
    protected  HashMap<String,Integer> tabList;
    protected ArrayList<ArrayList<String>> responses;

    public MultipleChoice(){
        this.out = new Output();
        this.correctChoices = new ArrayList<>();
        this.responses = new ArrayList<>();
        this.tabList = new HashMap<>();

    }

    /*
    purpose: to load question
    params: none
    return: none
    */

    public void loadQuestion(){
        this.loadPrompt("Enter a prompt for your question");
        Integer numberOfChoices;
        while(true){
            numberOfChoices = this.in.getIntegerInput("Enter the number of choices for MCQ");
            if(numberOfChoices == null){
                continue;
            } else if(numberOfChoices > 1000){
                this.out.display("try again with less than 1000 choices");
            }else if (numberOfChoices < 2){
                this.out.display("Add at least 2 choices for MCQ");
            }else{
                break;
            }
        }
        this.options = this.in.getMultipleInput(numberOfChoices,"enter choice");
        this.setUpTab();
    }

    /*
    purpose: to display
    params: bool
    return: none
    */

    @Override
    public void display(Boolean bool) {
        this.out.display(this.instruction);
        this.displayChoices(true,this.options);
        if(bool){
            this.out.display("The correct choice is ");
            this.displayChoices(false,this.correctChoices);
        }
    }

    /*
    purpose: to display choices
    params: isAlpha, choices
    return: none
    */

    public void displayChoices(Boolean isAlpha, ArrayList choices) {
        if (isAlpha) {
            for (int i = 0; i < choices.size(); i++) {
                this.out.displaySameLine((char) (i + 65) + ")" + choices.get(i) + " " + "\n");
            }
        }else{
            for (int i = 0; i < choices.size(); i++) {
                this.out.displaySameLine( (i + 1 + ")" + choices.get(i) + " " + "\n"));
            }
        }
    }

    /*
    purpose: to set up a tab
    params: none
    return: none
    */

    public void setUpTab(){
        for (int i = 0; i < options.size(); i++) {
            String letter = Character.toString((char) (i + 65));
            if(this.tabList.get(letter) == null){
                this.tabList.put(letter,0);
            }
        }
    }

    /*
    purpose: to modify a question
    params: bool
    return: none
    */

    @Override
    public void modifyQuestion(Boolean bool) {
        if(bool){
            while(true){
                String in = this.in.promptAndGet("Type 'A' to modify choices. Type 'B' to modify answers");
                if(in.equals("A")){
                    this.modifyChoicesOnly();
                    return;
                }else if (in.equals("B")){
                    this.modifyCorrectAnswer();
                    return;
                }
                else{
                    this.out.display("invalid choice");
                }
            }
        }else{
            this.modifyChoicesOnly();
        }
    }

    /*
    purpose: to modify correct answers
    params: none
    return: none
    */

    public void modifyCorrectAnswer(){
        while(true){
            this.out.display("which of the following do you want to modify? Select index");
            this.out.display("1) add correct answer  2) delete correct answer  3) back ");
            String inp = this.in.getInput();
            switch(inp){
                case "1":
                    while(true){
                        if(this.correctChoices.size() == this.options.size()){
                            this.out.display("you can't add anymore correct answer");
                            break;
                        }
                        while(this.correctChoices.size() < this.options.size()){
                            this.displayChoices(false,this.options);
                            Integer in = this.in.getIntegerInput("\n" + "answer" + " in integer index");
                            if (in == null || in > this.options.size() || in < 1) {
                                this.out.display("The index you entered is invalid ");
                                continue;
                            }
                            String choice = this.options.get(in - 1);
                            if(this.correctChoices.contains(choice) || !this.options.contains(choice)){
                                this.out.display("the choice you entered is not found/already entered");
                            }else{
                                this.addAnswer(choice);
                                this.out.display("Correct choice added");
                                break;
                            }
                        }
                        break;
                    }
                    break;
                case "2":
                    if(this.correctChoices.size() < 2){
                        this.out.display("you cannot delete more");
                        break;
                    }
                    while(true){
                        this.displayChoices(false,this.correctChoices);
                        Integer in = this.in.getIntegerInput("\n" + "answer to delete"  + " in integer");
                        if (in == null || in > this.correctChoices.size() || in < 1) {
                            this.out.display("Index you entered is invalid ");
                            continue;
                        }
                        this.removeAnswer(in - 1);
                        this.out.display("Answer removed!");
                        break;
                    }
                    break;
                case "3":
                    return;
                default:
                    this.out.display("invalid input");
                    break;
            }
        }
    }

    /*
    purpose: to modify a choice
    params: none
    return: none
    */

    public void modifyChoicesOnly(){
        while(true){
            this.out.display("Select index to modify?");
            this.out.display("1) add choice  2) delete choice  3) modify choice 4) modify prompt 5) back");
            String str = this.in.getInput();
            switch (str){
                case "1":
                    while(true){
                        String usrInput = this.in.promptAndGet("Enter a new choice");
                        if(this.options.contains(usrInput)){
                            this.out.display("It already exists");
                            continue;
                        }
                        this.addChoice(usrInput);
                        this.out.display("Added successfully");
                        break;
                    }
                    break;
                case "2":
                    if(this.options.size() < 3){
                        this.out.display("you cannot delete more choices it choices left is less than 2");
                    }
                    this.displayChoices(false,this.options);
                    Integer usrInput2 = this.in.getIntegerInput("Enter the index for the choice you want to delete");
                    try{
                        this.correctChoices.remove(this.options.get(usrInput2 - 1));
                        this.removeChoice(usrInput2 - 1);
                        this.out.display("Removed successfully");
                    }catch (IndexOutOfBoundsException error){
                        this.out.display("Select from the list");
                    }
                    break;
                case "3":
                    this.displayChoices(false,this.options);
                    Integer usrInput3 = this.in.getIntegerInput("Enter the index of the choice you want to modify") ;
                    try{
                        String c = this.options.get(usrInput3 - 1);
                        String usrInput4 = this.in.promptAndGet("Enter replacement text for it");
                        if(this.options.contains(usrInput4)){
                            this.out.display("It already exists");
                            continue;
                        }
                        this.setChoice(usrInput3 - 1,usrInput4);
                        if(this.correctChoices.contains(c)){
                            this.correctChoices.set(this.correctChoices.indexOf(c),usrInput4);
                        }
                        this.out.display("Replaced successfully");
                    }catch (IndexOutOfBoundsException error){
                        this.out.display("Select from the list");
                    }
                    break;
                case "4":
                    this.loadPrompt("Enter a new prompt");
                    this.out.display("Prompt changed");
                    break;
                case "5":
                    return;
                default:
                    this.out.display("invalid input");
            }
        }
    }

    /*
    purpose: prompt answer
    params: bool
    return: none
    */

    @Override
    public Integer promptAnswer(Boolean bool) {
        this.setUpTab();
        this.userChoices = new ArrayList<>();
        if(bool){
            this.out.display("Enter all of the correct answer (A/B...), there are correct answers followed by 'done'");
        }else{
            this.out.display("Enter all of the correct answer (A/B...), there are correct answers followed by 'done'");
        }
        if(bool){
            while(this.userChoices.size() < this.numOfCorrectAns){
                String usrInput = this.in.getInput();
                int index = (int)usrInput.toCharArray()[0] - 65;
                if(index> this.options.size() - 1 || index < 0 || this.userChoices.contains(usrInput)){
                    this.out.display("The choice you have entered is invalid/already entered");
                }else{
                    this.tabList.replace(usrInput,this.tabList.get(usrInput) + 1);
                    this.userChoices.add(usrInput);
                }
            }
        }else{
            while(this.userChoices.size() < this.options.size()){
                String usrInput = this.in.getInput();
                if(usrInput.equals("done")){
                    if(this.userChoices.size() < 1){
                        this.out.display("Please enter at least 1 answer");
                        continue;
                    }
                    break;
                }
                int index = (int)usrInput.toCharArray()[0] - 65;
                if(index> this.options.size() - 1 || index < 0 || this.userChoices.contains(usrInput)){
                    this.out.display("invalid choice or already entered it");
                }else{
                    this.tabList.replace(usrInput,this.tabList.get(usrInput) + 1);
                    this.userChoices.add(usrInput);
                }
            }
        }

        this.responses.add(this.userChoices);
        if(this.correctChoices.size() == 0){
            return 0;
        }
        for(int i =0; i < this.userChoices.size(); i ++){
            int index = (int)this.userChoices.get(i).toCharArray()[0] - 65;
            if(!this.correctChoices.contains(this.options.get(index))){
                return 0;
            }
        }

        this.id += 1;
        return 10;
    }

    @Override
    public Integer getCorrectPoint(){
        return 10;
    }

    @Override
    public void displayReplies() {
        this.out.display("Replies:");
        for(int i = 0; i <this.responses.size(); i++){
            String temp = "";
            for(int j = 0; j <this.responses.get(i).size(); j ++){
                temp +=  this.responses.get(i).get(j) + ",";
            }
            this.out.display(temp.substring(0,temp.length() -1));
        }
    }

    @Override
    public void displayTabulate() {
        this.out.display("Tabulation:");
        this.tabList.forEach((key,i)->{
            this.out.display(key +": "+i);
        });
    }

    public void setAnswer(Integer index, String context){
        this.correctChoices.set(index,context);
    }

    /*
    purpose: to add a choice
    params: choice
    return: none
    */

    public void addChoice(String choice){
        this.options.add(choice);
    }

    /*
    purpose: to add an answer
    params: choice
    return: none
    */

    public void addAnswer(String choice){
        this.correctChoices.add(choice);
    }

    /*
    purpose: to remove a choice
    params: index
    return: none
    */

    public void removeChoice(Integer index){
        this.options.remove(this.options.get(index));
    }

    /*
    purpose: to remove an answer
    params: index
    return: none
    */

    public void removeAnswer(Integer index){
        this.correctChoices.remove(this.correctChoices.get(index));
    }

    /*
    purpose: to set a choice
    params: index, context
    return: none
    */

    public void setChoice(Integer index, String context){
        this.options.set(index,context);
    }

}