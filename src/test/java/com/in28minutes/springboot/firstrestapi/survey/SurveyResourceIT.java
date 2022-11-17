package com.in28minutes.springboot.firstrestapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

    //http://localhost:8080/surveys/Survey2/questions/question4
    @Autowired
    private TestRestTemplate template;

    @Test
    void testRetrieveAllSurveys_BasicScenario() throws JSONException{
        ResponseEntity<String> responseEntity = template.getForEntity("/surveys", String.class);
        String expected = """
            [
                {
                    "id": "Survey1",
                    "title": "My Favorite Survey",
                    "description": "Description of the Survey"
                },
                {
                    "id": "Survey2",
                    "title": "My Second Favorite Survey",
                    "description": "Description of the Second Survey"
                }
            ]
                """;
        String body = responseEntity.getBody(); 
        
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expected, body, false);
    }

    @Test
    void testRetrieveSurveyById_BasicScenario() throws JSONException{
        ResponseEntity<String> responseEntity = template.getForEntity("/surveys/Survey2", String.class);
        String expected = """
            {"id":"Survey2","title":"My Second Favorite Survey","description":"Description of the Second Survey"}
                """;
        String body = responseEntity.getBody(); 
        
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expected, body, false);
    }

    @Test
    void testRetrieveQuestionsBySurveyId_BasicScenario() throws JSONException{
        ResponseEntity<String> responseEntity = template.getForEntity("/surveys/Survey1/questions", String.class);
        String expected = """
            [{"id":"Question1"},{"id":"Question2"},{"id":"Question3"}]
                """;
        String body = responseEntity.getBody(); 
        
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expected, body, false);
    }

    @Test
    void testRetrieveQuestionById_BasicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = template.getForEntity("/surveys/Survey2/questions/question4", String.class);
        String expected = """
            {"id":"Question4","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"]}
                """;
        String body = responseEntity.getBody(); 
        
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expected, body, false);
    }

    @Test
    void testAddNewSurveyQuestion_BasicScenario(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        String requestBody = """
            {
                "description": "My Favorite Programming Language",
                "options":[
                    "C",
                    "C++",
                    "Java",
                    "Gallina"
                ],
                "correctAnswer": "Gallina"
            }
                """;
        HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, headers);
        ResponseEntity<String> responseEntity = template.exchange("/surveys/Survey1/questions", HttpMethod.POST, httpEntity, String.class);
        
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        String locationHeader = responseEntity.getHeaders().get("Location").get(0);
        assertTrue(locationHeader.contains("/surveys/Survey1/questions/"));

        template.delete(locationHeader);
    }
}
