import java.io.*;

public abstract class Question implements Serializable {
    protected String instruction;
    protected Input in;
    protected Output out;

    public Question(){
        this.in = new Input();
        this.out = new Output();
    }

    public abstract void display(Boolean bool);

    /*
    purpose: to load the prompt
    params: prompt
    return: none
    */

    public void loadPrompt(String prompt){
        this.instruction = this.in.promptAndGet(prompt);
    }

    /*
    purpose: to load the question
    params: none
    return: none
    */

    public abstract void loadQuestion();

    /*
    purpose: to load the question
    params: bool, a boolean
    return: none
    */

    public abstract void modifyQuestion(Boolean bool);

    /*
    purpose: to load the question
    params: bool, a boolean
    return: none
    */

    public abstract Integer promptAnswer(Boolean bool);

    /*
    purpose: to load correct answers in the future (added new, but I was not sure if I needed it)
    params: str, a string
    return: none
    */

    public void loadCorrectAnswer(String str){ }

    public abstract Integer getCorrectPoint();

    public abstract void displayReplies();

    public abstract void displayTabulate();
}