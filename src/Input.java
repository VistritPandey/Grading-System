import java.io.*;
import java.util.*;

public class Input implements Serializable {
    private Output out;
    public Input(){
        this.out = new Output();
    }

    public String promptAndGet(String prompt){
        if(!prompt.isBlank()){
            this.out.display(prompt);
        }
        return this.getInput();
    }

    public ArrayList<String> getMultipleInput(int num, String prompt){
        ArrayList<String> inputs = new ArrayList<>();
        for(int i = 1; i < num + 1; i ++){
            while(true){
                String in = promptAndGet(prompt +" " + i);
                if(!inputs.contains(in)){
                    inputs.add(in);
                    break;
                }else{
                    this.out.display("you have already entered this choice, please enter a different one");
                }
            }
        }
        return inputs;
    }
    public Integer getIntegerInput(String prompt){
        while(true){
            try{
                int in = Integer.parseInt(this.promptAndGet(prompt));
                if(in < 0) {
                    this.out.display("must be greater than 0");
                    return null;
                }
                return in;
            }catch(Exception error){
                this.out.display("must be an integer");
                return null;
            }
        }
    }

    /*
    purpose: deserialize the file
    params: path
    return: survey
    */

    public Survey deserialize(String path){
        try{
            FileInputStream file = new FileInputStream(path);
            ObjectInputStream inp = new ObjectInputStream(file);
            Survey survey = (Survey) inp.readObject();
            file.close();
            inp.close();
            return survey;
        }catch(IOException | ClassNotFoundException error){
            this.out.display(error);
            return null;
        }
    }

    /*
    purpose: get input
    params: none
    return: input
    */

    public String getInput(){
        while(true){
            try{
                Scanner confirmation = new Scanner(System.in);
                String in = confirmation.nextLine().trim();
                if(in.isBlank()){
                    this.out.display("it cannot be blank");
                    continue;
                }
                return in;
            } catch (Exception error){
                this.out.display("incorrect input");
            }
        }
    }

    /*
    purpose: get integer input
    params: none
    return: input number
    */

    public Integer getIntInput(){
        while(true){
            try{
                int i = Integer.parseInt(this.getInput());
                if(i < 0) {
                    this.out.display("must be greater than 0");
                    return null;
                }
                return i;
            }catch(Exception e){
                this.out.display("must be an integer");
                return null;
            }
        }
    }
}