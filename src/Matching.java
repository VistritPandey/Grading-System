import java.util.*;

public class Matching extends Question {
    ArrayList<String> column1;
    ArrayList<String> column2;
    ArrayList<String> usedMatches;
    HashMap<String, String> correctAnswers;
    HashMap<String, String> userAnswers;
    HashMap<HashMap<String,String>,Integer> tabList;
    ArrayList<HashMap<String,String>> userReplies;

    int totalMatching;

    public Matching() {
        this.correctAnswers = new HashMap<>();
        this.userAnswers =new HashMap<>();
        this.tabList = new HashMap<>();
        this.usedMatches = new ArrayList<>();
        this.userReplies = new ArrayList<>();
    }

    /*
    purpose: load the question
    params: none
    return: none
    */

    @Override
    public void loadQuestion() {
        this.loadPrompt("Enter a prompt for your question:");
        while (true) {
            this.totalMatching = this.in.getIntegerInput("enter total no. of matching choice");
            if (this.totalMatching == 0) {
            } else if (this.totalMatching > 1000) {
                this.out.display("Try putting in less than 1000 choices");
            } else if (this.totalMatching < 2) {
                this.out.display("Enter more than 1 choice");
            } else {
                break;
            }
        }
        this.out.display("enter choices for your 1st column");
        this.column1 = this.loadColumn("first");
        this.out.display("enter choices for your 2nd column");
        this.column2 = this.loadColumn("second");
        this.userReplies = new ArrayList<>();
    }

    /*
    purpose: load columns for question
    params: column
    return: multiple inputs
    */

    public ArrayList<String> loadColumn(String column) {
        return this.in.getMultipleInput(this.totalMatching, "match choice no");
    }

    /*
    purpose: load correct answer
    params: none
    return: none
    */

    @Override
    public void loadCorrectAnswer(String content) {
        this.correctAnswers = new HashMap<>();
        this.usedMatches = new ArrayList<>();
        this.out.display("enter the correct answer for each match by typing in the index number");
        ArrayList<String> temp = this.column2;
        for (String s : column1) {
            while (true) {
                for (int j = 1; j < temp.size() + 1; j++) {
                    this.out.displaySameLine(j + ")" + temp.get(j - 1) + " ");
                }
                int index = this.in.getIntegerInput("\n please select the correct answer for " + s);
                if (index == 0 || index > temp.size() || index < 1) {
                    this.out.display("invalid number");
                    continue;
                }
                String match = temp.get(index - 1);
                if (!column2.contains(match) || this.usedMatches.contains(match)) {
                    this.out.display("the choice you entered is not found/already entered");
                } else {
                    temp.remove(match);
                    this.correctAnswers.put(s, match);
                    this.usedMatches.add(match);
                    break;
                }
            }
        }
    }

    /*
    purpose: display
    params: bool
    return: none
    */

    @Override
    public void display(Boolean bool) {
        this.out.display(instruction);
        for (int i = 1; i < this.totalMatching + 1; i++) {
            this.out.display(this.column1.get(i - 1) + "      |      " + this.column2.get(i - 1));
        }
        if (bool) {
            int index = 1;
            this.out.display("correct answers:");
            for (Map.Entry<String, String> entry : correctAnswers.entrySet()) {
                this.out.display(index + ")" + entry.getKey() + " ---> " + entry.getValue());
                index += 1;
            }
        }
    }

    /*
    purpose: modify question
    params: bool
    return: none
    */

    @Override
    public void modifyQuestion(Boolean bool) {
        while (true) {
            if (bool) {
                String in = this.in.promptAndGet("1) Modify prompt 2) Modify left column 3) Modify right column 4) Modify answer 5) back");
                if(in=="1") {
                    this.loadPrompt("Enter a new prompt");
                }
                else if(in=="2") {
                    this.modifyColumn(true, true);
                }
                else if(in=="3") {
                        this.modifyColumn(false,true);
                }
                else if(in=="4") {
                    this.loadCorrectAnswer("");
                    this.out.display("Answer updated");
                }
                else if(in=="5") {
                    return;
                }
                else{
                    this.out.display("invalid choice");;
                }
            }else{
                String i = this.in.promptAndGet("1) Modify prompt 2) Modify left column 3) Modify right column 4) Back");
                if (i=="1") {
                    this.loadPrompt("Enter a new prompt");
                }
                else if (i=="2"){
                    this.modifyColumn(true,false);
                }
                else if (i=="3"){
                    this.modifyColumn(false,false);
                }
                else if (i=="4"){
                    return;
                }
                else{
                        this.out.display("invalid choice");
                }
            }
        }
    }

    /*
    purpose: display left column
    params: none
    return: none
    */

    public void displayLeftColumn() {
        for (int i = 0; i < column1.size(); i++) {
            this.out.displaySameLine((i + 1 + ")" + column1.get(i) + " " + "\n"));
        }
    }

    /*
    purpose: display right column
    params: none
    return: none
    */

    public void displayRightColumn() {
        for (int i = 0; i < column2.size(); i++) {
            this.out.displaySameLine((i + 1 + ")" + column2.get(i) + " " + "\n"));
        }
    }

    /*
    purpose: modify the column
    params: isLeft, bool
    return: none
    */

    public void modifyColumn(Boolean isLeft, Boolean bool){
        ArrayList column;
        if(isLeft)column=this.column1;else column = this.column2;
        while(true){
            if(isLeft) this.displayLeftColumn(); else this.displayRightColumn();
            int in2 = this.in.getIntegerInput("\n" + "item" + " in integer index");
            if (in2 == 0 || in2 < 1 || in2 > column.size()) {
                this.out.display("The index you have entered is not valid ");
                continue;
            }
            String item = (String)column.get(in2 - 1);
            while(true){
                String usrInput4 = this.in.promptAndGet("please enter replacement text for this item");
                if(column.contains(usrInput4)){
                    this.out.display("You have already entered this, please try again.");
                    continue;
                }
                column.set(in2-1,usrInput4);
                if (bool){
                    if(isLeft){
                        String temp = this.correctAnswers.get(item);
                        this.correctAnswers.remove(item);
                        this.correctAnswers.put(usrInput4,temp);
                    }else{
                        this.correctAnswers.forEach((key,val) -> {
                            if(val.equals(item)){
                                this.correctAnswers.replace(key,usrInput4);
                                return;
                            }
                        });
                    }
                }
                this.out.display("Modified successfully!");
                return;
            }
        }
    }

    /*
    purpose: prompt for answers
    params: bool
    return: none
    */

    @Override
    public Integer promptAnswer(Boolean bool) {
        this.userAnswers = new HashMap<>();
        this.usedMatches = new ArrayList<>();
        this.out.display("Enter the answer by typing in the index");
        ArrayList<String> temp = (ArrayList<String>) this.column2.clone();
        for (String s : column1) {
            while (true) {
                for (int j = 1; j < temp.size() + 1; j++) {
                    this.out.displaySameLine(j + ")" + temp.get(j - 1) + " ");
                }
                int index = this.in.getIntegerInput("\n please select an option for " + s);
                if (index == 0 || index > temp.size() || index < 1) {
                    this.out.display("invalid index ");
                    continue;
                }
                String match = temp.get(index - 1);
                if (!column2.contains(match) || this.usedMatches.contains(match)) {
                    this.out.display("the choice entered is not found/already entered");
                } else {
                    temp.remove(match);
                    this.userAnswers.put(s, match);
                    this.usedMatches.add(match);
                    break;
                }
            }
        }
        this.userReplies.add(this.userAnswers);
        if(!this.tabList.containsKey(this.userAnswers)){
            this.tabList.put(this.userAnswers,1);
        }else{
            this.tabList.put(this.userAnswers, this.tabList.get(this.userAnswers) + 1);
        }
        if(bool){
            if(this.userAnswers.equals(this.correctAnswers)){
                return 10;
            }else{
                return 0;
            }
        }
        return 0;
    }

    /*
    purpose: get correct point
    params: none
    return: 10
    */

    @Override
    public Integer getCorrectPoint() {
        return 10;
    }

    /*
    purpose: display replies
    params: none
    return: none
    */

    @Override
    public void displayReplies() {
        this.out.display("Replies:");
        for(int i = 0; i < this.userReplies.size();i++){
            this.userReplies.get(i).forEach((key,val)->{
                this.out.display(key + "--->" + val);
            });
            this.out.display("");
        }
    }

    /*
    purpose: display tabulate
    params: none
    return: none
    */

    @Override
    public void displayTabulate() {
        this.out.display("Tabulate:");
        this.tabList.forEach((key,i)->{
            this.out.display(i);
            key.forEach((key2,x)->{
                this.out.display(key2 + "---->" + x);
            });
            this.out.display("");
        });
    }
}