package com.buulke.buulkestore.controller;

import com.buulke.buulkestore.model.Question;
import com.buulke.buulkestore.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping(value = "/new")
    public Question getNewQuestion(){
        return questionService.getQuestionFromQueue();
    }
}
