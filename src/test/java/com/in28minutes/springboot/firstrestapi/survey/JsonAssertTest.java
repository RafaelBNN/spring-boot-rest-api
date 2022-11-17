package com.in28minutes.springboot.firstrestapi.survey;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class JsonAssertTest {
    
    @Test
    void jsonAssert_learningBasics() throws JSONException{
        String expected = """
            {"id": "Question4","description": "Most Popular Cloud Platform Today","options": ["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer": "AWS"}
                """;
        
        String actual = """
            {"id":"Question4","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
                """;

        JSONAssert.assertEquals(expected, actual, true);
    }
}
