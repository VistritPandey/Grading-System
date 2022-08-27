import java.util.*;

public class ValidDate extends Essay {

    public ValidDate(){
        this.answer = new ArrayList<>();
    }

    /*
    purpose: to load the question
    params: none
    return: none
    */

    @Override
    public void loadQuestion() {
        this.loadPrompt("Enter a prompt for your Date");
    }

    /*
    purpose: to display the question
    params: none
    return: none
    */

    @Override
    public void display(Boolean bool) {
        this.out.display("What is today's date: (remember to follow to format or you will lose points)");
    }

    /*
    purpose: to display the instructions after the question is displayed
    params: none
    return: none
    */

    @Override
    public Integer promptAnswer(Boolean bool) {
        String in = this.in.promptAndGet("A date should be entered in the following format: YYYY-MM-DD");
        this.answer.add(in);
        return 0;
    }
}