package com.Ritu.EducationWebsite.Topic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public CommandLineRunner populateRepositoryRunner(TopicRepository repo) {
        return (args) -> {
            repo.save(new TopicEntity(100L,"Chemistry1", "Chemistry1 Decription"));
            repo.save(new TopicEntity(222L,"Chemistry2", "Chemistry2 Decription"));
            repo.save(new TopicEntity(333L,"Chemistry3", "Chemistry3 Decription"));
//            repo.delete(111L);

        };
    }

}