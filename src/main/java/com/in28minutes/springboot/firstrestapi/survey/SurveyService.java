package com.in28minutes.springboot.firstrestapi.survey;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {
    private static List<Survey> surveys = new ArrayList<>();

    static {
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
    }

    public List<Survey> retrieveAllSurveys(){
        return surveys;
    }

    public Survey retrieveSurveyById(String id){
        Optional<Survey> optionalSurvey = surveys.stream().filter(survey -> survey.getId().equalsIgnoreCase(id)).findFirst();
        if(optionalSurvey.isEmpty()) return null;
        return optionalSurvey.get();
    }

    public List<Question> retrieveQuestionsBySurveyId(String surveyId){
        Optional<Survey> optionalSurvey = surveys.stream().filter(survey -> survey.getId().equalsIgnoreCase(surveyId)).findFirst();
        if(optionalSurvey.isEmpty()) return Collections.emptyList();
        return optionalSurvey.get().getQuestions();
    }

    public Question retrieveQuestionById(String surveyId, String questionId) {
        List<Question> questions = retrieveQuestionsBySurveyId(surveyId);
        Optional<Question> optionalQuestion = questions.stream().filter(question -> question.getId().equalsIgnoreCase(questionId)).findFirst();
        if(optionalQuestion.isEmpty()) return null;
        return optionalQuestion.get();
    }

    public String addNewSurveyQuestion(String surveyId, Question question){
        List<Question> questions = retrieveQuestionsBySurveyId(surveyId);
        question.setId(generateRandomId());
        questions.add(question);
        return question.getId();
    }

    private String generateRandomId() {
        SecureRandom secureRandom = new SecureRandom();
        return new BigInteger(32, secureRandom).toString();
    }

    public String deleteQuestionById(String surveyId, String questionId) {
        List<Question> surveyQuestions = retrieveQuestionsBySurveyId(surveyId);
        if(surveyQuestions==null) return null;

        boolean removed = surveyQuestions.removeIf(question -> question.getId().equalsIgnoreCase(questionId));
        if(!removed) return null;
        return questionId;
    }

    public void updateQuestionById(String surveyId, String questionId, Question question) {
        List<Question> questions = retrieveQuestionsBySurveyId(surveyId);
        questions.removeIf(q -> q.getId().equalsIgnoreCase(questionId));
        questions.add(question);
    }
}
