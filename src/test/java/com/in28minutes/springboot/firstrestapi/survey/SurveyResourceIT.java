package com.in28minutes.springboot.firstrestapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void testRetrieveQuestionById_BasicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = template.getForEntity("/surveys/Survey2/questions/question4", String.class);
        String expected = """
            {"id":"Question4","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"]}
                """;
        String body = responseEntity.getBody(); //{"id":"Question4","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
        System.out.println(responseEntity.getHeaders()); // [Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Thu, 17 Nov 2022 13:57:11 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]
        // System.out.println("Hello World Test");

        JSONAssert.assertEquals(expected, body, false);
    }
}
