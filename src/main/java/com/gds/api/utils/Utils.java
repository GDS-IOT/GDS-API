package com.gds.api.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Sujith Ramanathan
 */
@Component
public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    public JSONObject getJson(String payload) {
        JSONObject json = null;
        try {
            json = (JSONObject) new JSONParser().parse(payload);
        } catch (ParseException e) {
            LOGGER.error("Error occurred while parsing string to json", e);
        } catch (Exception e) {
            LOGGER.error("Error occurred while parsing json", e);
        }
        return json;
    }
}
