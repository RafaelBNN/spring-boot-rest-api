package com.in28minutes.springboot.firstrestapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Test
    void testRetrieveAllSurveys_BasicScenario() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8080/surveys").accept(MediaType.APPLICATION_JSON);

        List<Survey> surveys = new ArrayList<>();

        Question question1 = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        Question question2 = new Question("Question2",
                "Fastest Growing Cloud Platform", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
        Question question3 = new Question("Question3",
                "Most Popular DevOps Tool", Arrays.asList(
                "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");
        List<Question> questions = new ArrayList<>(Arrays.asList(question1,
                question2, question3));
        Survey survey = new Survey("Survey1", "My Favorite Survey",
                "Description of the Survey", questions);
        surveys.add(survey);

        Question question4 = new Question("Question4",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        Question question5 = new Question("Question5",
                "Fastest Growing Cloud Platform", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
        Question question6 = new Question("Question6",
                "Most Popular DevOps Tool", Arrays.asList(
                "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");
        List<Question> questions2 = new ArrayList<>(Arrays.asList(question4,
                question5, question6));
        Survey survey2 = new Survey("Survey2", "My Second Favorite Survey",
                "Description of the Second Survey", questions2);
        surveys.add(survey2);
                
        when(surveyService.retrieveAllSurveys()).thenReturn(surveys);
        
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = """
            [{"id":"Survey1","title":"My Favorite Survey","description":"Description of the Survey","questions":[{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"},{"id":"Question2","description":"Fastest Growing Cloud Platform","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"Google Cloud"},{"id":"Question3","description":"Most Popular DevOps Tool","options":["Kubernetes","Docker","Terraform","Azure DevOps"],"correctAnswer":"Kubernetes"}]},{"id":"Survey2","title":"My Second Favorite Survey","description":"Description of the Second Survey","questions":[{"id":"Question4","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"},{"id":"Question5","description":"Fastest Growing Cloud Platform","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"Google Cloud"},{"id":"Question6","description":"Most Popular DevOps Tool","options":["Kubernetes","Docker","Terraform","Azure DevOps"],"correctAnswer":"Kubernetes"}]}]
                """;
                
        assertEquals(200, mvcResult.getResponse().getStatus());
        // System.out.println(expected);
        // System.out.println(mvcResult.getResponse().getContentAsString());
        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), true);
    }

    @Test
    void testRetrieveSurveyById() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8080/surveys/survey2").accept(MediaType.APPLICATION_JSON);

        Question question4 = new Question("Question4",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        Question question5 = new Question("Question5",
                "Fastest Growing Cloud Platform", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
        Question question6 = new Question("Question6",
                "Most Popular DevOps Tool", Arrays.asList(
                "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");
        List<Question> questions2 = new ArrayList<>(Arrays.asList(question4,
                question5, question6));
        Survey survey2 = new Survey("Survey2", "My Second Favorite Survey",
                "Description of the Second Survey", questions2);

        when(surveyService.retrieveSurveyById("survey2")).thenReturn(survey2);
        
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = """
            {"id":"Survey2","title":"My Second Favorite Survey","description":"Description of the Second Survey"}
                    """;
                
        assertEquals(200, mvcResult.getResponse().getStatus());
        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    void testRetrieveQuestionsBySurveyId() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8080/surveys/survey2/questions").accept(MediaType.APPLICATION_JSON);

        Question question4 = new Question("Question4",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        Question question5 = new Question("Question5",
                "Fastest Growing Cloud Platform", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
        Question question6 = new Question("Question6",
                "Most Popular DevOps Tool", Arrays.asList(
                "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");
        List<Question> questions2 = new ArrayList<>(Arrays.asList(question4,
                question5, question6));

        when(surveyService.retrieveQuestionsBySurveyId("survey2")).thenReturn(questions2);
        
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = """
            [
                {
                    "id": "Question4",
                    "description": "Most Popular Cloud Platform Today"
                },
                {
                    "id": "Question5",
                    "description": "Fastest Growing Cloud Platform"
                },
                {
                    "id": "Question6",
                    "description": "Most Popular DevOps Tool"
                }
            ]
                """;
                
        assertEquals(200, mvcResult.getResponse().getStatus());
        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    void testRetrieveQuestionById_404Scenario() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8080/surveys/survey1/questions/question1").accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(404, mvcResult.getResponse().getStatus());
    }
    
    @Test
    void testRetrieveQuestionById_BasicScenario() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8080/surveys/survey1/questions/question1").accept(MediaType.APPLICATION_JSON);

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
