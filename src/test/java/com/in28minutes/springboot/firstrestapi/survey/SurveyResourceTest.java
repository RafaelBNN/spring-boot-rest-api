package com.in28minutes.springboot.firstrestapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = SurveyResource.class)
public class SurveyResourceTest {

    @MockBean
    private SurveyService surveyService;

    @Autowired
    private MockMvc mockMvc;

    private static String SPECIFIC_QUESTION_URL = "http://localhost:8080/surveys/survey1/questions/question1";

    @Test
    void testRetrieveQuestionById_404Scenario() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(404, mvcResult.getResponse().getStatus());
    }
    
    @Test
    void testRetrieveQuestionById_BasicScenario() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);

        Question question = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");

        when(surveyService.retrieveQuestionById("survey1","question1")).thenReturn(question);
        
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = """
            {"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
                """;
                
        assertEquals(200, mvcResult.getResponse().getStatus());
        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }
}
