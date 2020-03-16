import domain.PageInfo;
import domain.Webpage;
import exceptions.ParameterIsNotJSONStringException;
import utils.JSONStringParser;
import utils.JSONStringRetriever;
import utils.Sorter;
import utils.WebpageBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("Enter a word (type 'quit' to exit):");
                String input = br.readLine();

                if (input.equals("quit")) {
                    break;
                }
                if (!JSONStringRetriever.isConnected()) {
                    System.out.println("Internet is not connected.");
                    break;
                }

                String JSONString = JSONStringRetriever.getJSONstring(input, "30");
                if (JSONString.equals("Error")) {
                    System.out.println("No article exists. Please try again.");
                } else {
                    PageInfo pageInfo = JSONStringParser.parseJSONString(JSONString);
                    Webpage webpage = WebpageBuilder.buildAWebpage(pageInfo);

                    if (!webpage.getTo().isEmpty()) {
                        System.out.printf("Redirected:\n     From: %s\n     To: %s\n\n", webpage.getFrom(), webpage.getTo());
                    }
                    System.out.printf("Page Title: %s\n\n", webpage.getTitle());

                    System.out.println("Type 'a' to see a list of the 30 most recent edits.");
                    System.out.println("Type 'b' to see a list of who has made the most recent edits.");
                    String displayChoice = br.readLine();
                    Sorter sorter = new Sorter(webpage.getSortedByTimeStamp(),webpage.getSortedByQuantity());
                    if (displayChoice.equals("a")) {
                        String[] timeStamps = sorter.sortedFormattedTimeStamps;
                        String[] tsUsernames = sorter.sortedByTSUsernames;
                        for (int index = 0; index < timeStamps.length; index++) {
                            System.out.printf("%-25.25s at %s\n", tsUsernames[index], timeStamps[index]);
                        }
                    } else if (displayChoice.equals("b")) {
                        String[] eUsernames = sorter.sortedByEUsernames;
                        String[] editValues = sorter.sortedEValues;
                        for (int index = 0; index < eUsernames.length; index++){
                            System.out.printf("%-25.25s made %s\n",eUsernames[index],editValues[index]);
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
