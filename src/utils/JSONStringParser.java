package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.WebInfo;
import exceptions.ParameterIsNotJSONStringException;

import java.util.Map;

public class JSONStringParser {

    public static WebInfo parseJSONString(String JSONString) throws ParameterIsNotJSONStringException {
        if (JSONString.charAt(0) != '{') {
            throw new ParameterIsNotJSONStringException();
        }
        JsonParser jsonParser = new JsonParser();
        JsonElement rootElement = jsonParser.parse(JSONString);
        JsonObject query = rootElement.getAsJsonObject().getAsJsonObject("query");
        JsonObject pages = query.getAsJsonObject("pages");

        //Set To and From redirection values in ToFromTitle array, or make blank if not redirected
        String[] toFromTitleArray = new String[3];
        if (youWereRedirected(query)) {
            toFromTitleArray[0] = query.getAsJsonArray("redirects").get(0).getAsJsonObject().getAsJsonPrimitive("to").getAsString();
            toFromTitleArray[1] = query.getAsJsonArray("redirects").get(0).getAsJsonObject().getAsJsonPrimitive("from").getAsString();
        }
        else{
            toFromTitleArray[0] = "";
            toFromTitleArray[1] = "";
        }

        WebInfo webInfo = null;
        for (Map.Entry<String, JsonElement> entry : pages.entrySet()) {
            JsonObject uniqueNumberValueObject = entry.getValue().getAsJsonObject();
            toFromTitleArray[2] = uniqueNumberValueObject.getAsJsonPrimitive("title").getAsString();
            JsonArray revisionsArray = uniqueNumberValueObject.getAsJsonArray("revisions");
            String[] usersArray = new String[revisionsArray.size()];
            String[] timestampsArray = new String[revisionsArray.size()];

            for (int i = 0; i < revisionsArray.size(); i++) {
                JsonObject individualRevisionObject = revisionsArray.get(i).getAsJsonObject();
                usersArray[i] = individualRevisionObject.getAsJsonPrimitive("user").getAsString();
                timestampsArray[i] = individualRevisionObject.getAsJsonPrimitive("timestamp").getAsString();
            }
            webInfo = new WebInfo(usersArray, timestampsArray, toFromTitleArray);
        }
        return webInfo;
    }

    private static boolean youWereRedirected(JsonObject query) {
        try {
            query.getAsJsonArray("redirects").get(0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
