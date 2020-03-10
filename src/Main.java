import domain.TimeStamp;
import domain.WebInfo;
import domain.Webpage;
import exceptions.ParameterIsNotJSONStringException;
import utils.JSONStringParser;
import utils.JSONStringRetriever;
import utils.WebpageBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("Enter a word (type 'quit' to exit):");
                String input = br.readLine();

                if (input.equals("quit")) {
                    break;}
                if (!JSONStringRetriever.isConnected()) {
                    System.out.println("Internet is not connected.");
                    break;}

                String JSONString = JSONStringRetriever.getJSONstring(input);
                if (JSONString.equals("Error")) {
                    System.out.println("No article exists. Please try again.");
                } else {
                    WebInfo webInfo = JSONStringParser.parseJSONString(JSONString);
                    Webpage webpage = WebpageBuilder.buildAWebpage(webInfo);

                    if(!webpage.getTo().isEmpty()){
                        System.out.printf("Redirected:\n     From: %s\n     To: %s\n\n",webpage.getFrom(),webpage.getTo());
                    }
                    System.out.printf("Page Title: %s\n\n", webpage.getTitle());

                    System.out.println("Type 'a' to see a list of the 30 most recent edits.");
                    System.out.println("Type 'b' to see a list of who has made the most recent edits.");
                    String displayChoice = br.readLine();
                    if (displayChoice.equals("a")) {
                        Object[] keyObjArray = webpage.getSortedByTimeStamp().keySet().toArray();
                        TimeStamp[] keyArray = new TimeStamp[keyObjArray.length];
                        Object[] valueObjArray = webpage.getSortedByTimeStamp().values().toArray();
                        String[] valueArray = new String[valueObjArray.length];
                        for (int k=0; k<keyObjArray.length; k++){
                            keyArray[k] = (TimeStamp) keyObjArray[k];
                            valueArray[k] = (String) valueObjArray[k];
                        }
                        TimeStamp intermediateStamp;
                        String intermediateString;
                        for(int i=0; i<keyArray.length;i++){
                            for(int j=0; j<keyArray.length; j++){
                                if(!keyArray[i].isYoungerThan(keyArray[j])){
                                    intermediateStamp = keyArray[i];
                                    keyArray[i] = keyArray[j];
                                    keyArray[j] = intermediateStamp;

                                    intermediateString = valueArray[i];
                                    valueArray[i] = valueArray[j];
                                    valueArray[j] = intermediateString;
                                }
                            }
                        }
                        for (int index = 0; index < keyArray.length; index++){
                            System.out.printf("%-3s %-25.25s at %s\n", index+1 +".", valueArray[index],keyArray[index].getFormattedTimeStamp());
                        }
                    }
                    else if (displayChoice.equals("b")) {
                        Object[] keyObjArray = webpage.getSortedByQuantity().keySet().toArray();
                        String[] keyArray = new String[keyObjArray.length];
                        Object[] valueObjArray = webpage.getSortedByQuantity().values().toArray();
                        Integer[] valueArray = new Integer[valueObjArray.length];
                        for (int k=0; k<keyObjArray.length; k++){
                            keyArray[k] = (String) keyObjArray[k];
                            valueArray[k] = (Integer) valueObjArray[k];
                        }
                        int index = 1;
                        for (int g=keyArray.length-1; g > 0; g--){
                            String pluralizer = "";
                            if (valueArray[g]>1){
                                pluralizer = "s";
                            }
                            System.out.printf("%-3s %-25.25s made %d edit%s\n", index +".",keyArray[g],valueArray[g], pluralizer);
                            index++;
                        }
                    } else {
                        System.out.println("Incorrect Input. Please Try again.");
                    }
                }
            }
        } catch (ParameterIsNotJSONStringException e) {
            System.out.println("String Retreieved is:\nNOT JSON STRING");
        } catch (NullPointerException e) {
            System.out.println("No Article Exists");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
        }
    }
}
