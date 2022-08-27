import java.util.*;

public class ShortAnswer extends Essay {
    private int limit;
    private ArrayList<String> correctChoices;
    private HashMap<String,Integer> tabList;

    public ShortAnswer(){
        this.correctChoices = new ArrayList<>();
        this.answer = new ArrayList<>();
        this.tabList = new HashMap<>();
    }

    /*
    purpose: set a limit for the short answer so that when taken, user is bound to answer in a limited number
    params: none
    return: none
    */

    public void setLimit() {
        while (true) {
            this.limit = this.in.getIntegerInput("enter a limit of characters for the short answer");
            if (this.limit < 1) {
                this.out.display("limit must be at least 1");
            } else if (this.limit != 0) {
                return;
            }
        }
    }

    /*
    purpose: load a prompt for the short answer
    params: none
    return: none
    */

    public void loadQuestion(){
        this.loadPrompt("Enter a prompt for the short answer");
        this.setLimit();
    }

    @Override
    public void displayTabulate() {
        this.out.display("Tabulation:");
        this.tabList.forEach((key,val) -> {
            this.out.display(key+ ": " +val);
        });
    }

    public void displayCorrectAnswer(){
        this.out.display("limit:" + this.limit);
        for (int i = 0; i < correctChoices.size(); i++) {
            this.out.displaySameLine( (i + 1 + ")" + correctChoices.get(i) + " " + "\n"));
        }

    }

    @Override
    public Integer getCorrectPoint() {
        return 10;
    }

    @Override
    public Integer promptAnswer(Boolean isTest) {
        this.out.display("the limit of this prompt is:" + this.limit + " words");
        String choice = "";
        if(isTest){
            choice = this.promptResult();
            if(this.correctChoices.contains(choice)){
                return 10;
            }else{
                return 0;
            }
        }else{
            this.promptResult();
            return 0;
        }
    }

    public String promptResult(){
        while(true){
            String in = this.in.promptAndGet("Please enter the short answer text below");
            if (in.split(" ").length > this.limit){
                this.out.display("Maximum amount of total words exceeded! ");
                continue;
            }
            this.answer.add(in);
            if(this.tabList.get(in) != null){
                this.tabList.replace(in,this.tabList.get(in) + 1);
            }else{
                this.tabList.put(in,1);
            }
            return in;
        }
    }


}