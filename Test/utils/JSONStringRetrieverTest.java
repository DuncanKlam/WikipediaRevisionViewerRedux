package utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JSONStringRetrieverTest {

    String JSONString = "{\"continue\":{\"rvcontinue\":\"20200301081856|943333165\",\"continue\":\"||\"},\"query\":{\"normalized\":[{\"from\":\"obama\",\"to\":\"Obama\"}],\"redirects\":[{\"from\":\"Obama\",\"to\":\"Barack Obama\"}],\"pages\":{\"534366\":{\"pageid\":534366,\"ns\":0,\"title\":\"Barack Obama\",\"revisions\":[{\"user\":\"LanHikari64\",\"timestamp\":\"2020-03-02T17:52:22Z\"},{\"user\":\"Thanoscar21\",\"timestamp\":\"2020-03-02T16:42:54Z\"},{\"user\":\"Nolanwebb\",\"timestamp\":\"2020-03-02T16:40:58Z\"},{\"user\":\"Zythe\",\"timestamp\":\"2020-03-02T11:19:19Z\"},{\"user\":\"Zzuuzz\",\"timestamp\":\"2020-03-01T08:36:57Z\"}]}}}}";



    @Test
    void getJSONstring() throws IOException {
        try {
            assertEquals(JSONString, JSONStringRetriever.getJSONstring("obama","30"));
        }
        catch (IOException e){
            System.out.println("IOException");
        }
    }

    @Test
    void isConnected() {
        assertEquals(true, JSONStringRetriever.isConnected());
    }
}