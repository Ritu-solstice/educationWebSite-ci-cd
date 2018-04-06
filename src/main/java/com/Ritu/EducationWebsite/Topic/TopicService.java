package com.Ritu.EducationWebsite.Topic;

import org.springframework.stereotype.Service;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository){
        this.topicRepository = topicRepository;
    }

    public void addTopic(TopicRequest topic) {
        TopicEntity topicEntity=new TopicEntity(topic.getId(),topic.getName(),topic.getDescription());
        topicRepository.save(topicEntity) ;
    }

    public void updateTopic(TopicRequest topic, Long id) {
        TopicEntity topicEntity=new TopicEntity(topic.getId(),topic.getName(),topic.getDescription());
         topicRepository.save(topicEntity);
    }

    public void deleteTopic(Long id) {
        topicRepository.delete(id);
    }

    public TopicRequest getTopic(Long id)  {
        TopicEntity topicEntity= topicRepository.findOne(id);
        if(topicEntity==null)
            return null;
        return new TopicRequest(topicEntity.getId(),topicEntity.getName(),topicEntity.getDescription());
    }
}
