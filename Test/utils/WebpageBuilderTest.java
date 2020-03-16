package utils;

import domain.TimeStamp;
import domain.PageInfo;
import domain.Webpage;
import exceptions.ParameterIsNotJSONStringException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WebpageBuilderTest {

    String JSONString5 = "{\"continue\":{\"rvcontinue\":\"20200301081856|943333165\",\"continue\":\"||\"},\"query\":{\"normalized\":[{\"from\":\"obama\",\"to\":\"Obama\"}],\"redirects\":[{\"from\":\"Obama\",\"to\":\"Barack Obama\"}],\"pages\":{\"534366\":{\"pageid\":534366,\"ns\":0,\"title\":\"Barack Obama\",\"revisions\":[{\"user\":\"LanHikari64\",\"timestamp\":\"2020-03-02T17:52:22Z\"},{\"user\":\"Thanoscar21\",\"timestamp\":\"2020-03-02T16:42:54Z\"},{\"user\":\"Nolanwebb\",\"timestamp\":\"2020-03-02T16:40:58Z\"},{\"user\":\"Zythe\",\"timestamp\":\"2020-03-02T11:19:19Z\"},{\"user\":\"Zzuuzz\",\"timestamp\":\"2020-03-01T08:36:57Z\"}]}}}}";
    String JSONString30 = "{\"continue\":{\"rvcontinue\":\"20200218025700|941357473\",\"continue\":\"||\"},\"query\":{\"redirects\":[{\"from\":\"Obama\",\"to\":\"Barack Obama\"}],\"pages\":{\"534366\":{\"pageid\":534366,\"ns\":0,\"title\":\"Barack Obama\",\"revisions\":[{\"user\":\"KidAd\",\"timestamp\":\"2020-03-03T01:13:39Z\"},{\"user\":\"Jutrumpet42\",\"timestamp\":\"2020-03-02T23:10:34Z\"},{\"user\":\"Jutrumpet42\",\"timestamp\":\"2020-03-02T23:05:30Z\"},{\"user\":\"Snooganssnoogans\",\"timestamp\":\"2020-03-02T22:17:30Z\"},{\"user\":\"Thanoscar21\",\"timestamp\":\"2020-03-02T22:11:59Z\"},{\"user\":\"LanHikari64\",\"timestamp\":\"2020-03-02T17:52:22Z\"},{\"user\":\"Thanoscar21\",\"timestamp\":\"2020-03-02T16:42:54Z\"},{\"user\":\"Nolanwebb\",\"timestamp\":\"2020-03-02T16:40:58Z\"},{\"user\":\"Zythe\",\"timestamp\":\"2020-03-02T11:19:19Z\"},{\"user\":\"Zzuuzz\",\"timestamp\":\"2020-03-01T08:36:57Z\"},{\"user\":\"Nathancard\",\"timestamp\":\"2020-03-01T08:18:56Z\"},{\"user\":\"Oglach na hEireann\",\"timestamp\":\"2020-03-01T06:47:59Z\"},{\"user\":\"SUM1\",\"timestamp\":\"2020-02-29T05:50:47Z\"},{\"user\":\"TrailBlzr\",\"timestamp\":\"2020-02-27T23:15:50Z\"},{\"user\":\"TrailBlzr\",\"timestamp\":\"2020-02-27T23:15:28Z\"},{\"user\":\"Smurrayinchester\",\"timestamp\":\"2020-02-26T15:00:49Z\"},{\"user\":\"Kiwi128\",\"timestamp\":\"2020-02-24T09:27:13Z\"},{\"user\":\"Brett12212\",\"timestamp\":\"2020-02-24T09:26:29Z\"},{\"user\":\"SunCrow\",\"timestamp\":\"2020-02-23T23:44:03Z\"},{\"user\":\"SunCrow\",\"timestamp\":\"2020-02-23T23:42:56Z\"},{\"user\":\"SunCrow\",\"timestamp\":\"2020-02-23T23:37:56Z\"},{\"user\":\"SunCrow\",\"timestamp\":\"2020-02-23T23:36:12Z\"},{\"user\":\"SunCrow\",\"timestamp\":\"2020-02-23T22:43:08Z\"},{\"user\":\"SunCrow\",\"timestamp\":\"2020-02-23T22:41:35Z\"},{\"user\":\"Ich\",\"timestamp\":\"2020-02-23T15:20:59Z\"},{\"user\":\"Tymon.r\",\"timestamp\":\"2020-02-21T19:09:29Z\"},{\"user\":\"TheMightyDuckmen\",\"timestamp\":\"2020-02-21T19:07:59Z\"},{\"user\":\"Eyer\",\"timestamp\":\"2020-02-19T17:31:49Z\"},{\"user\":\"Md320\",\"timestamp\":\"2020-02-19T17:21:33Z\"},{\"user\":\"DemonDays64 Bot\",\"timestamp\":\"2020-02-19T05:40:38Z\"}]}}}}";
    @Test
    void buildWebpageToValue() {
        try{
            PageInfo wI1 = JSONStringParser.parseJSONString(JSONString30);
            Webpage wB1 = WebpageBuilder.buildAWebpage(wI1);
            assertEquals("Barack Obama", wB1.getTo());
        } catch (ParameterIsNotJSONStringException e) {
            e.printStackTrace();
            System.out.println("ParameterIsNotJSONStringException");
        }
    }

    @Test
    void buildWebpageFromValue() {
        try{
            PageInfo wI1 = JSONStringParser.parseJSONString(JSONString30);
            Webpage wB1 = WebpageBuilder.buildAWebpage(wI1);
            assertEquals("Obama", wB1.getFrom());

        } catch (ParameterIsNotJSONStringException e) {
            e.printStackTrace();
            System.out.println("ParameterIsNotJSONStringException");
        }
    }

    @Test
    void buildWebpageTitleValue() {
        try{
            PageInfo wI1 = JSONStringParser.parseJSONString(JSONString30);
            Webpage wB1 = WebpageBuilder.buildAWebpage(wI1);
            assertEquals("Barack Obama", wB1.getTitle());

        } catch (ParameterIsNotJSONStringException e) {
            e.printStackTrace();
            System.out.println("ParameterIsNotJSONStringException");
        }
    }

    @Test
    void makeTimeStampObjectArray() {
    }

    @Test
    void sortByTimeStamp() {
        String[] usersList = {"Pam", "Cyril", "Pam", "Cyril", "Archer", "Lana", "Pam", "Cyril", "Carol", "Archer"};
        TimeStamp ts0 = new TimeStamp(2020, 4, 18, 9, 4, 24);
        TimeStamp ts1 = new TimeStamp(2020, 4, 18, 9, 4, 25);
        TimeStamp ts2 = new TimeStamp(2020, 4, 18, 9, 6, 25);
        TimeStamp ts3 = new TimeStamp(2020, 4, 18, 9, 8, 25);
        TimeStamp ts4 = new TimeStamp(2020, 4, 18, 5, 8, 25);
        TimeStamp ts5 = new TimeStamp(2020, 4, 18, 8, 8, 25);
        TimeStamp ts6 = new TimeStamp(2020, 4, 25, 8, 8, 25);
        TimeStamp ts7 = new TimeStamp(2020, 4, 3, 8, 8, 25);
        TimeStamp ts8 = new TimeStamp(2020, 8, 3, 8, 8, 25);
        TimeStamp ts9 = new TimeStamp(2020, 5, 3, 8, 8, 25);

        TimeStamp[] timeStampList = {ts0, ts1, ts2, ts3, ts4, ts5, ts6, ts7, ts8, ts9};

        Map<TimeStamp, String> expectedSortedByTimeStamp = Map.of(timeStampList[8], usersList[8],
                timeStampList[9], usersList[9],
                timeStampList[6], usersList[6],
                timeStampList[3], usersList[3],
                timeStampList[2], usersList[2],
                timeStampList[1], usersList[1],
                timeStampList[0], usersList[0],
                timeStampList[5], usersList[5],
                timeStampList[4], usersList[4],
                timeStampList[7], usersList[7]);

        assertEquals(expectedSortedByTimeStamp,WebpageBuilder.combineIntoHashmap(usersList,timeStampList));
    }

    @Test
    void sortByQuantity() {
        String[] usersList = {"Pam", "Cyril", "Pam", "Cyril", "Archer", "Lana", "Pam", "Cyril", "Carol", "Archer"};
        Map<String, Integer> expectedSoretedByQuantity = Map.of("Pam", 3,
                                                                "Cyril",3,
                                                                "Archer",2,
                                                                "Lana",1,
                                                                "Carol",1);

        assertEquals(expectedSoretedByQuantity,WebpageBuilder.sortByQuantity(usersList));
    }
}