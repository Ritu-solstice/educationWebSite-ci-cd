package com.Ritu.EducationWebsite.Exception;

import com.Ritu.EducationWebsite.Topic.TopicController;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(assignableTypes = {TopicController.class})
public class TopicControllerAdvice {


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleHttpMessageNotReadableException(final HttpMessageNotReadableException ex){
        return new ErrorResponse("Invalid request Body","Topic Body is ");
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex){
        return new ErrorResponse("Invalid request Body","Topic Body is ");
    }

    @ExceptionHandler(TopicNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleTopicNotFoundException(final TopicNotFoundException ex) {
        return new ErrorResponse("TOPIC_NOT_FOUND", "The Topic was not found");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleInValidIDException(final ConstraintViolationException ex) {
        return new ErrorResponse("ID is inValid", "Id is invalid");
    }

    @ExceptionHandler(InValidInputParametersException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleInValidInputParametersException(final InValidInputParametersException ex) {
        return new ErrorResponse("Input parameter don't meet accepted criteria", "update id in url dont match the body id");
    }

    @ExceptionHandler(IncorrectDataFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleIncorrectDataFormatException(final IncorrectDataFormatException ex){
        return new ErrorResponse("Data format is incorrect", "Please check data format");
    }

}

