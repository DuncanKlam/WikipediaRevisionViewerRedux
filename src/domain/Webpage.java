package domain;

import java.util.Map;

public class Webpage {

    private Map<TimeStamp, String> sortedByTimeStamp;
    private Map<String, Integer> sortedByQuantity;
    private String to = " ";
    private String from = " ";
    private String title = " ";


    public Webpage(Map<TimeStamp, String> aSortedByTimeStampMap, Map<String, Integer> aSortedByQuantityMap, String aTo, String aFrom, String aTitle){
        this.sortedByTimeStamp = aSortedByTimeStampMap;
        this.sortedByQuantity = aSortedByQuantityMap;
        this.to = aTo;
        this.from = aFrom;
        this.title = aTitle;
    }

    public String getTitle() {
        return title;
    }

    public Map<TimeStamp, String> getSortedByTimeStamp() {
        return sortedByTimeStamp;
    }

    public Map<String, Integer> getSortedByQuantity() {
        return sortedByQuantity;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }
}
