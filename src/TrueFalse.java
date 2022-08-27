import java.util.*;

public class TrueFalse extends MultipleChoice{
    public TrueFalse(){
        this.options = new ArrayList<>();
        this.options.add("T");
        this.options.add("F");
        this.tabList = new HashMap<>();
        this.tabList.put("T",0);
        this.tabList.put("F",0);
    }

    /*
    purpose: to load question
    params: none
    return: none
     */

    @Override
    public void loadQuestion() {
        this.loadPrompt("Enter a prompt for your T/F question");
        this.tabList = new HashMap<>();
        this.tabList.put("T",0);
        this.tabList.put("F",0);
    }

    /*
    purpose: to load correct answers
    params: content
    return: none
     */

    @Override
    public void loadCorrectAnswer(String content) {
        while(true){
            String in = this.in.promptAndGet(content);
            if(in != null){
                if(in.equals("T") || in.equals("F")){
                    this.correctChoices.add(in);
                    return;
                }else{
                    this.out.display("must be T or F");
                }
            }
        }
    }

    /*
    purpose: to modify question
    params: Boolean isTest
    return: none
     */

    @Override
    public void modifyQuestion(Boolean isTest) {
        while(true){
            if(isTest){
                String in = this.in.promptAndGet("1) Modify prompt 2) Modify correct answer 3) Back");
                        if(in.equals("1")) {
                            this.loadPrompt("Enter a new prompt");
                        }
                        else if(in.equals("2")) {
                        while(true){
                            String in2 = this.in.promptAndGet("Enter new answer: T/F");
                            if(in2.equals("T") || in2.equals("F")){
                                this.correctChoices.set(0,in2);
                                break;
                            }else{
                                this.out.display("invalid choice");
                                continue;
                            }
                        }
                        }
                        else if(in.equals("3")) {
                        return;
                        }
                        else{
                        this.out.display("invalid choice");
                }
            }else{
                String in = this.in.promptAndGet("1) Modify prompt 2) Back");
                if(in.equals("1")){
                    this.loadPrompt("Enter a new prompt");
                }else if(in.equals("2")) {
                    return;
                }else{
                    this.out.display("invalid choice");
                }
            }
        }
    }

    /*
    purpose: to modify choices
    params: none
    return: none
     */

    @Override
    public void modifyChoicesOnly() {
        super.modifyChoicesOnly();
    }

    /*
    purpose: to modify correct answer
    params: none
    return: none
     */

    @Override
    public void modifyCorrectAnswer() {
    }

    /*
    purpose: to prompt answer
    params: Boolean isTest
    return: none
     */

    @Override
    public Integer promptAnswer(Boolean isTest) {
        this.userChoices = new ArrayList<>();
        while(true){
            String usrInput = this.in.promptAndGet("Please enter T/F");
            if(usrInput.equals("T") || usrInput.equals("F")){
                if(usrInput.equals("T")){
                    this.tabList.replace("T",this.tabList.get("T") + 1);
                }else{
                    this.tabList.replace("F",this.tabList.get("F") + 1);
                }
                this.userChoices.add(usrInput);
                break;
            }else{
                this.out.display("invalid choice. Enter T/F only");
            }
        }
        this.responses.add(this.userChoices);
        if(this.correctChoices.size() == 0){
            return 0;
        }
        if(!this.correctChoices.get(0).equals(this.userChoices.get(0))){
            return 0;
        }
        return 10;
    }
}