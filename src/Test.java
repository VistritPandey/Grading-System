import java.util.*;

public class Test extends Survey {
    private ArrayList<Question> correctAnswers;

    public void addAnswer(Question q){
        this.correctAnswers.add(q);
    }
}