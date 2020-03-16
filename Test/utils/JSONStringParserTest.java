package utils;

import domain.PageInfo;
import exceptions.ParameterIsNotJSONStringException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONStringParserTest {

    String JSONString = "{\"continue\":{\"rvcontinue\":\"20200301081856|943333165\",\"continue\":\"||\"},\"query\":{\"normalized\":[{\"from\":\"obama\",\"to\":\"Obama\"}],\"redirects\":[{\"from\":\"Obama\",\"to\":\"Barack Obama\"}],\"pages\":{\"534366\":{\"pageid\":534366,\"ns\":0,\"title\":\"Barack Obama\",\"revisions\":[{\"user\":\"LanHikari64\",\"timestamp\":\"2020-03-02T17:52:22Z\"},{\"user\":\"Thanoscar21\",\"timestamp\":\"2020-03-02T16:42:54Z\"},{\"user\":\"Nolanwebb\",\"timestamp\":\"2020-03-02T16:40:58Z\"},{\"user\":\"Zythe\",\"timestamp\":\"2020-03-02T11:19:19Z\"},{\"user\":\"Zzuuzz\",\"timestamp\":\"2020-03-01T08:36:57Z\"}]}}}}";
    String[] userArray = {"LanHikari64", "Thanoscar21", "Nolanwebb", "Zythe", "Zzuuzz"};
    String[] timestampArray = {"2020-03-02T17:52:22Z", "2020-03-02T16:42:54Z", "2020-03-02T16:40:58Z", "2020-03-02T11:19:19Z", "2020-03-01T08:36:57Z"};
    String[] tftArray = {"Barack Obama", "Obama", "Barack Obama"};
    PageInfo pageInfoExpected =  new PageInfo(userArray, timestampArray, tftArray);


    @Test
    void parsesJSONStringToUserArray() throws ParameterIsNotJSONStringException {
        try{
            PageInfo pageInfo = JSONStringParser.parseJSONString(JSONString);
            assertArrayEquals(pageInfoExpected.getUsers(), pageInfo.getUsers());
        }
        catch (ParameterIsNotJSONStringException e){
            System.out.println("ParameterIsNotJSONStringException");
        }
    }

    @Test
    void parsesJSONStringToTFTArray() throws ParameterIsNotJSONStringException {
        try{
            PageInfo pageInfo = JSONStringParser.parseJSONString(JSONString);
            assertArrayEquals(pageInfoExpected.getToFromTitle(), pageInfo.getToFromTitle());
        }
        catch (ParameterIsNotJSONStringException e){
            System.out.println("ParameterIsNotJSONStringException");
        }
    }

    @Test
    void parsesJSONStringToTimestampsArray() throws ParameterIsNotJSONStringException {
        try{
            PageInfo pageInfo = JSONStringParser.parseJSONString(JSONString);
            assertArrayEquals(pageInfoExpected.getTimestamps(), pageInfo.getTimestamps());
        }
        catch (ParameterIsNotJSONStringException e){
            System.out.println("ParameterIsNotJSONStringException");
        }
    }
}