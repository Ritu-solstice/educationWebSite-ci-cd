package com.Ritu.EducationWebsite.Topic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data

class TopicEntity{
    @Id
    @Column(updatable = false)
    @NonNull
    Long id;
    @NotBlank
    @NonNull
    String name;
    String description;
}