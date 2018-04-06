package com.Ritu.EducationWebsite.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    String message;
    String suggestedSolution;
}
