package com.in28minutes.springboot.firstrestapi.survey;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class SurveyResource {

    private SurveyService surveyService;

    public SurveyResource(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("surveys")
    public List<Survey> retrieveAllSurveys(){
        return surveyService.retrieveAllSurveys();
    }

    @GetMapping("surveys/{id}")
    public Survey retrieveSurveyById(@PathVariable String id){
        Survey survey = surveyService.retrieveSurveyById(id);
        if(survey==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return survey;
    }

    @GetMapping("surveys/{surveyId}/questions")
    public List<Question> retrieveQuestionsBySurveyId(@PathVariable String surveyId){
        return surveyService.retrieveQuestionsBySurveyId(surveyId);
    }

    @GetMapping("surveys/{surveyId}/questions/{questionId}")
    public Question retrieveQuestionById(@PathVariable String surveyId, @PathVariable String questionId){
        return surveyService.retrieveQuestionById(surveyId, questionId);
    }
}
