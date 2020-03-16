package utils;

import domain.TimeStamp;

import java.util.Map;

public class Sorter {

    public String[] sortedFormattedTimeStamps;
    public String[] sortedByTSUsernames;
    public String[] sortedByEUsernames;
    public String[] sortedEValues;

    public Sorter(Map<TimeStamp, String> sortedByTimeStamp, Map<String, Integer> sortedByQuantity) {
        //Create arrays for conversion
        Object[] sbtKeyArray = sortedByTimeStamp.keySet().toArray();
        Object[] sbtValueArray = sortedByTimeStamp.values().toArray();

        Object[] sbqKeyArray = sortedByQuantity.keySet().toArray();
        Object[] sbqValueArray = sortedByQuantity.values().toArray();

        TimeStamp[] tSArray = new TimeStamp[sbtKeyArray.length];
        String[] tSStringArray = new String[sbtKeyArray.length];
        String[] userArray = new String[sbtValueArray.length];

        String[] eUsernamesArray = new String[sbqKeyArray.length];
        Integer[] userEditNumbersArray = new Integer[sbqValueArray.length];

        //convert arrays from Object[] to TimeStamp[] and String[]
        for (int i=0; i<sbtKeyArray.length; i++){
            tSArray[i] = (TimeStamp) sbtKeyArray[i];
            userArray[i] = (String) sbtValueArray[i];
        }

        //Object[] to String[] and Integer[]
        for (int i=0; i<sbqKeyArray.length; i++){
            eUsernamesArray[i] = (String) sbqKeyArray[i];
            userEditNumbersArray[i] = (Integer) sbqValueArray[i];
        }

        //Swap values to order
        TimeStamp intermediateStamp;
        String intermediateString;
        for(int i=0; i<tSArray.length;i++){
            for(int j=0; j<tSArray.length; j++){
                if(!tSArray[i].isYoungerThan(tSArray[j])){
                    intermediateStamp = tSArray[i];
                    tSArray[i] = tSArray[j];
                    tSArray[j] = intermediateStamp;

                    intermediateString = userArray[i];
                    userArray[i] = userArray[j];
                    userArray[j] = intermediateString;
                }
            }
        }

        //Format and convert Timestamps to Strings and adding an index value to usernames
        for (int i = 0; i < tSArray.length; i++){
            userArray[i] = String.format("%-3s %s",i+1+".",userArray[i]);
            tSStringArray[i] = tSArray[i].getFormattedTimeStamp();
        }
        sortedFormattedTimeStamps = tSStringArray;
        sortedByTSUsernames = userArray;

        //Format and reverse sorted-by-quantity arrays
        sortedByEUsernames = new String[eUsernamesArray.length];
        sortedEValues = new String[userEditNumbersArray.length];
        int index = 0;
        for (int i=eUsernamesArray.length-1; i > 0; i--){
            String pluralize = "";
            if (userEditNumbersArray[i]>1){
                pluralize = "s";
            }
            sortedByEUsernames[index] = String.format("%-3s %-25.25s", index+1+".", eUsernamesArray[i]);
            sortedEValues[index] = String.format("%d edit%s", userEditNumbersArray[i], pluralize);
            index++;
        }
    }
}