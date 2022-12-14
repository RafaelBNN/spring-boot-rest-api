package com.in28minutes.springboot.firstrestapi.survey;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
        Question question = surveyService.retrieveQuestionById(surveyId, questionId);
        if(question==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return question;
    }

    @PostMapping("surveys/{surveyId}/questions")
    public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question){
        String questionId = surveyService.addNewSurveyQuestion(surveyId, question);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{questionId}").buildAndExpand(questionId).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("surveys/{surveyId}/questions/{questionId}")
    public ResponseEntity<Object> deleteQuestionById(@PathVariable String surveyId, @PathVariable String questionId){
        surveyService.deleteQuestionById(surveyId, questionId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("surveys/{surveyId}/questions/{questionId}")
    public ResponseEntity<Object> updateQuestionById(@PathVariable String surveyId, @PathVariable String questionId, @RequestBody Question question){
        surveyService.updateQuestionById(surveyId, questionId, question);
        return ResponseEntity.noContent().build();
    }
}
