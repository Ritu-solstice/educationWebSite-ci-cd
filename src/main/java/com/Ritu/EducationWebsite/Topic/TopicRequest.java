package com.Ritu.EducationWebsite.Topic;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TopicRequest {

    @NonNull
    @Min(1)
    Long id;
    @NonNull
    @Size(min = 1)
    String name;
    String description;
}