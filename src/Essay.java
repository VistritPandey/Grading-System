import java.util.*;

public class Essay extends Question {
    ArrayList<String> answer;

    public Essay(){
        this.answer = new ArrayList<>();
    }

    /*
    purpose: load a prompt for essay
    params: none
    return: none
    */

    @Override
    public void loadQuestion() {
        this.loadPrompt("Enter a prompt for your essay");
    }

    /*
    purpose: display the prompt with the added instructions
    params: none
    return: none
    */

    @Override
    public void display(Boolean bool) {
        this.out.display("Essay prompt: " + this.instruction);
    }

    /*
    purpose: modify the question, user will have the option to either modify it or go back
    params: none
    return: none
    */

    @Override
    public void modifyQuestion(Boolean bool) {
        while(true){
            String in = this.in.promptAndGet("1) Modify prompt 2) Go Back");
            if(in.equals("1")){
                this.loadPrompt("Enter a new prompt");
            }else if(in.equals("2")) {
                return;
            }else{
                this.out.display("invalid choice");
            }
        }
    }

    /*
    purpose: load a prompt for the essay
    params: Boolean bool (taken from Question class)
    return: 0, in order to make sure that the compiler understands
    */

    @Override
    public Integer promptAnswer(Boolean bool) {
        String in = this.in.promptAndGet("Enter your essay");
        this.answer.add(in);
        return 0;
    }

    @Override
    public Integer getCorrectPoint() {
        return 0;
    }

    @Override
    public void displayReplies() {
        this.out.display("Replies:");
        for(int i = 0; i <this.answer.size(); i++){
            this.out.display(this.answer.get(i));
        }
    }

    @Override
    public void displayTabulate() {
        this.out.display("Tabulation:");
        for(int i = 0; i <this.answer.size(); i++){
            this.out.display(this.answer.get(i) );
        }
    }
}