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
                        int index = 1;
                        for(Map.Entry<TimeStamp, String> entry : webpage.getSortedByTimeStamp().entrySet()){
                            if(!entry.getValue().contains("printed")) {
                                Map.Entry<TimeStamp, String> maxEntry = webpage.getRecentUnmarkedTimestamp(entry);
                                System.out.printf("%-3s %-25.25s at %s\n", index+".", maxEntry.getValue(), maxEntry.getKey().getFormattedTimeStamp());
                                webpage.getSortedByTimeStamp().replace(maxEntry.getKey(), maxEntry.getValue(), "printed");
                                index++;
                            }
                        }
                    }
                    else if (displayChoice.equals("b")) {
                        int index = 1;
                        for(Map.Entry<String, Integer> entry : webpage.getSortedByQuantity().entrySet()){
                            String pluralizer = "";
                            if (entry.getValue()>1){
                                pluralizer = "s";
                            }
                            System.out.printf("%-3s %-25.25s made %d edit%s\n", index+".",entry.getKey(),entry.getValue(), pluralizer);
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
