package com.Ritu.EducationWebsite.Topic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TopicServiceTest {

    @MockBean
    TopicRepository mockRepo;

    TopicService topicService;

    @Before
    public void setUp(){
        topicService = new TopicService(mockRepo);
    }
    @Test
    public void addTopicTest(){
        TopicRequest request=new TopicRequest(1223L,"Topic1", "Desc1");
        topicService.addTopic(request);
        TopicEntity entity=new TopicEntity(request.getId(),request.getName(),request.getDescription());
        verify(mockRepo).save(entity);
    }

    @Test
    public void updateTopicTest(){
        TopicRequest request=new TopicRequest(1223L,"Topic1", "Desc1");
        topicService.updateTopic(request,request.getId());
        TopicEntity entity=new TopicEntity(request.getId(),request.getName(),request.getDescription());
        verify(mockRepo).save(entity);
    }

    @Test
    public void deleteTopicTest(){
        Long id = 1223L;
        topicService.deleteTopic(id);
        verify(mockRepo).delete(id);
    }

    @Test
    public void getTopicTest_success()  {
        Long id = 1223L;
        TopicEntity topicEntity=new TopicEntity(1223L,"Topic1", "Desc1");
        when(mockRepo.findOne(id)).thenReturn(topicEntity);
        TopicRequest request = topicService.getTopic(id);
        assertThat(request).isNotNull();
        assertThat(request.getId()).isEqualTo(id);
        assertThat(request.getName()).isEqualTo("Topic1");
        assertThat(request.getDescription()).isEqualTo("Desc1");
    }

    @Test
    public void getTopicTest_Null()  {
        Long id = 1223L;
        when(mockRepo.findOne(id)).thenReturn(null);
        TopicRequest request = topicService.getTopic(id);
        assertThat(request).isNull();
    }
}