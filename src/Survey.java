import java.io.*;
import java.util.*;

public class Survey implements Serializable {
    private ArrayList<Question> questions;
    private Output out;
    String surveyName;
    protected HashMap<String,String> surveys;
    protected int totalScore = 0;
    protected int availedScore = 0;
    public Survey(){
        this.questions = new ArrayList<>();
    }

    /*
    purpose: add questions
    params: question, taken from question
    return: none
    */

    public void addQuestion(Question question){
        this.questions.add(question);
    }

    /*
    purpose: get question size
    params: none
    return: none
    */

    public Integer getQuestionSize(){
        return this.questions.size();
    }

    /*
    purpose: get questions as an array
    params: none
    return: none
    */

    public ArrayList<Question> getQuestions(){
        return this.questions;
    }

    /*
    purpose: to take the survey
    params: bool, boolean
    return: none
    */

    public void takeSurvey(Boolean bool){
        this.out = new Output();
        this.totalScore = 0;
        this.availedScore = 0;
        if(bool){
            for(int i =0; i <questions.size(); i++){
                this.questions.get(i).display(false);
                this.totalScore += this.questions.get(i).promptAnswer(true);
                this.availedScore += this.questions.get(i).getCorrectPoint();
            }
            this.out.display("Total score is " + this.totalScore + "/" + this.availedScore);
            this.surveys.put(this.surveyName, this.totalScore + "/" + this.availedScore);
        }else{
            for(int i =0; i <questions.size(); i++){
                this.questions.get(i).display(false);
                this.questions.get(i).promptAnswer(false);
            }
        }
    }

    /*
    purpose: to get the question number
    params: none
    return: the question at that index
    */

    public Question getQuestionNumber(Integer index){
        return this.questions.get(index - 1);
    }

    /*
    purpose: to set the survey name
    params: name, string
    return: none
    */

    public void setSurveyName(String name){
        this.surveyName = name;
    }

    public void displayGrade() {
        this.surveys.forEach((key,val)->{
            this.out.display("id " + key +": "+val);
        });
    }

    public Question getQuestion(Integer index){
        return this.questions.get(index - 1);
    }
}