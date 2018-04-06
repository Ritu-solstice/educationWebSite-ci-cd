package com.Ritu.EducationWebsite.Topic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TopicRepositoryTest {

    @Autowired
    TopicRepository topicRepository;

    public List<TopicEntity> getMockData(){

        return Arrays.asList(new TopicEntity(1L,"name1","desc1"),
                new TopicEntity(2L,"name2","desc2"),
                new TopicEntity(3L,"name3","desc3"));

    }

    @Test
    public void saveTest(){
        topicRepository.save(getMockData());
        assertThat((topicRepository.findOne(1L).getId()),is(1L));
        assertThat((topicRepository.findOne(2L).getId()),is(2L));
        assertThat((topicRepository.findOne(3L).getId()),is(3L));
    }

    @Test
    public void finOneTest(){
        topicRepository.save(getMockData());
        assertThat((topicRepository.findOne(1L).getId()),is(1L));
        assertThat((topicRepository.findOne(2L).getId()),is(2L));
        assertThat((topicRepository.findOne(3L).getId()),is(3L));
    }

    @Test
    public void findAllTest(){
        topicRepository.save(getMockData());
        topicRepository.delete(1L);
        assertThat((topicRepository.findOne(1L)),equalTo(null));

    }

}