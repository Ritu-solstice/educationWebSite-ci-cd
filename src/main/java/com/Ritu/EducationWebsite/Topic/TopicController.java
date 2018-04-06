package com.Ritu.EducationWebsite.Topic;

import com.Ritu.EducationWebsite.Exception.InValidInputParametersException;
import com.Ritu.EducationWebsite.Exception.TopicNotFoundException;
import lombok.NonNull;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;


@RestController
@Validated
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping(value = "/api/Topic")
    public void addTopic(@Valid @RequestBody TopicRequest topic)throws HttpMessageNotReadableException{
         topicService.addTopic(topic);
    }

    @PutMapping(value = "/api/Topic/{id}")
    public void updateTopic(@Valid @RequestBody TopicRequest topic, @PathVariable Long id ) throws InValidInputParametersException{
        //comes wrapped object type
        if(!id.equals(topic.getId()))
            throw  new InValidInputParametersException();
         topicService.updateTopic(topic, id);
       }

    @DeleteMapping(value = "api/Topic/{id}")
    public void DeleteTopic(@Valid @NonNull @Min(value = 1L) @PathVariable Long id)throws ConstraintViolationException {
        topicService.deleteTopic(id);
    }

    @GetMapping(value = "api/Topic/{id}")
    public TopicRequest getTopic(@PathVariable(required = true)  Long id) throws TopicNotFoundException {
        TopicRequest request = topicService.getTopic(id);
        if(request==null)
            throw new TopicNotFoundException();
        return request;
    }
    }
