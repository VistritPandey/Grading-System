import java.util.*;

public class Menu2 extends AbstractMenu {
    private Boolean bool;
    Menu2(Boolean bool){
        this.bool = bool;
        this.menu = new ArrayList<>();
        this.menu.add("1) Add a new T/F question");
        this.menu.add("2) Add a new multiple-choice question");
        this.menu.add("3) Add a new short answer question");
        this.menu.add("4) Add a new Essay question");
        this.menu.add("5) Add a new Valid Date question");
        this.menu.add("6) Add a new matching question");
        this.menu.add("7) back");
    }

    public Survey loadMenuItems(Survey survey)  {
        while(true){
            this.displayMenu();
            Scanner sc = new Scanner(System.in);
            String currentMenu = sc.nextLine();
            if(currentMenu.equals("1")) {
                this.createTrueFalse(survey);
            }
            else if(currentMenu.equals("2")) {
                this.createMultipleChoice(survey);
            }
            else if(currentMenu.equals("3")) {
                this.createShortAnswer(survey);
            }
            else if(currentMenu.equals("4")) {
                this.createEssay(survey);
            }
            else if(currentMenu.equals("5")) {
                this.createValidDate(survey);
            }
            else if(currentMenu.equals("6")) {
                this.createMatching(survey);
            }
            else if(currentMenu.equals("7")) {
                return survey;
            }
            else{
                this.out.display("invalid input");
                continue;
            }
        }
    }

    /*
    purpose: add question
    params: survey, question, prompt
    return: none
    */

    public void addQuestion(Survey survey, Question question, String prompt){
        Question questions = question;
        questions.loadQuestion();
        if(this.bool){
            questions.loadCorrectAnswer(prompt);
        }
        survey.addQuestion(questions);
        this.out.display("question added successfully");
    }

    /*
    purpose: create MCQ
    params: survey
    return: none
    */

    public void createMultipleChoice(Survey survey){
        this.addQuestion(survey, new MultipleChoice(),"Enter the correct answer/answers");
    }

    /*
    purpose: create matching
    params: survey
    return: none
    */

    public void createMatching(Survey survey){
        this.addQuestion(survey, new Matching(), "");
    }

    /*
    purpose: create True/False
    params: survey
    return: none
    */

    public void createTrueFalse(Survey survey){
        this.addQuestion(survey,new TrueFalse(),"Enter the correct answer T/F");
    }

    /*
    purpose: create Essay
    params: survey
    return: none
    */

    public void createEssay(Survey survey){
        this.addQuestion(survey, new Essay(),"");
    }

    /*
    purpose: get input
    params: none
    return: input
    */

    public void createShortAnswer(Survey survey){
        this.addQuestion(survey, new ShortAnswer(),"Enter the correct answer");
    }

    /*
    purpose: create Valid Date
    params: survey
    return: none
    */

    public void createValidDate(Survey survey){
        this.addQuestion(survey, new ValidDate(), "A date should be entered in the following format: YYYY-MM-DD");
    }

}