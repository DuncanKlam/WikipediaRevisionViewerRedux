package utils;

import domain.TimeStamp;
import domain.PageInfo;
import domain.Webpage;

import java.util.*;

public class WebpageBuilder {

    public static Webpage buildAWebpage(PageInfo pageInfo){
        TimeStamp[] timeStampObjectArray = makeTimeStampObjectArray(pageInfo.getTimestamps());
        Map<TimeStamp, String>  sortedByTimeStamp = combineIntoHashmap(pageInfo.getUsers(), timeStampObjectArray);
        Map<String, Integer> sortedByQuantityOfEdits = sortByQuantity(pageInfo.getUsers());

        return new Webpage(sortedByTimeStamp, sortedByQuantityOfEdits, pageInfo.getToFromTitle()[0], pageInfo.getToFromTitle()[1], pageInfo.getToFromTitle()[2]);
    }

    public static TimeStamp[] makeTimeStampObjectArray(String[] timestamps) {
        TimeStamp[] timeStampObjectArray = new TimeStamp[timestamps.length];
        int i = 0;
        for (String timeStampString : timestamps){
            TimeStamp newTimeStamp = new TimeStamp(Integer.parseInt(timeStampString.substring(0,4)), //year
                                                    Integer.parseInt(timeStampString.substring(5,7)), //month
                                                    Integer.parseInt(timeStampString.substring(8,10)), //day
                                                    Integer.parseInt(timeStampString.substring(11,13)), //hour
                                                    Integer.parseInt(timeStampString.substring(14,16)), //minute
                                                    Integer.parseInt(timeStampString.substring(17,19))); //second
            timeStampObjectArray[i] = newTimeStamp;
            i++;
        }
        return timeStampObjectArray;
    }

    public static Map<TimeStamp, String> combineIntoHashmap(String[] users, TimeStamp[] timeStampObjectArray) {

        //Combine two arrays into hashmap
        Map<TimeStamp, String> sortedByTimeStamp = new HashMap<>();
        for (int i=0; i<timeStampObjectArray.length; i++){
            sortedByTimeStamp.put(timeStampObjectArray[i], users[i]);
        }

        return sortedByTimeStamp;
    }

    public static Map<String, Integer> sortByQuantity(String[] users) {

        //Create array containing user edit quantities
        HashMap<String, Integer> sortedByQuantity = new HashMap<>();
        Set<String> userSet = new HashSet<>(Arrays.asList(users));
        int[] editQuantities = new int[userSet.size()];
        int index = 0;
        for(String user : userSet){
            int editsMade = 0;
            for (String s : users) {
                if (user.contains(s)) {
                    editsMade++;
                }
            }
            editQuantities[index] = editsMade;
            index++;
        }

        //Create String[] array from Object[] array
        Object[] uniqueObjectArray = userSet.toArray();
        String[] uniqueUserArray = new String[uniqueObjectArray.length];
        for (int b=0; b<uniqueObjectArray.length;b++){
            uniqueUserArray[b] = (String) uniqueObjectArray[b];
        }

        //Combine arrays into a hashmap
        for (int i=0; i<uniqueUserArray.length; i++){
            sortedByQuantity.put(uniqueUserArray[i],editQuantities[i]);
        }

        //sorts hashmap by value
        sortedByQuantity = sortByValue(sortedByQuantity);

        return sortedByQuantity;
    }

    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        List<Map.Entry<String, Integer> > list = new LinkedList<>(hm.entrySet());

        list.sort(Map.Entry.comparingByValue());

        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


}
