import java.util.*;
import java.io.*;

public class Output implements Serializable {
    public void displayStringList(ArrayList<String> items){
        for(int i = 0; i< items.size(); i++){
            this.display(items.get(i));
        }
    }

    public void display(Object obj){
        System.out.println(obj);
    };

    public void displaySameLine(Object obj){
        System.out.print(obj);
    };

    /*
    purpose: serialization
    params: object, path
    return: none
    */

    public void serialize(Object object, String path){
        try{
            FileOutputStream file = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(object);
            file.close();
            out.close();
        }catch (IOException error){
            this.display(error);
        }
    }
}