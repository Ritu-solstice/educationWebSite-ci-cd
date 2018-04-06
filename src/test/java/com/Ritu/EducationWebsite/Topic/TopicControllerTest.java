package com.Ritu.EducationWebsite.Topic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TopicController.class)
public class TopicControllerTest {

    @MockBean
    private TopicService topicService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private JacksonTester< TopicRequest > jsonTester;

    @Before
    public void setUpMock()
    {
        JacksonTester.initFields(this, objectMapper);
    }

    //Adding topic Tests
    @Test
    public void addTopicTest_InValid_Null() throws Exception {
       // When no content is passed, it means request body is null
        mvc.perform(post("/api/Topic")).andExpect(status().isBadRequest());
    }

    @Test
    public void addTopicTest_InValid_NullID() throws Exception {
        TopicRequest topic = new TopicRequest( 0L,"abc","biology description");
        String TopicJson = jsonTester.write(topic).getJson();
        TopicJson=TopicJson.replaceAll("0", "null");
        mvc.perform(post("/api/Topic").contentType(MediaType.APPLICATION_JSON).content(TopicJson)).andExpect(status().isBadRequest());
    }

    @Test
    public void addTopicTest_InValid_isIncomplete() throws Exception {
        TopicRequest topic = new TopicRequest(5L, "","biology description");
        String TopicJson = jsonTester.write(topic).getJson();
        mvc.perform(post("/api/Topic").contentType(MediaType.APPLICATION_JSON).content(TopicJson)).andExpect(status().isBadRequest());
    }

    @Test
    public void addTopicTest_Valid_200() throws Exception {
        TopicRequest topic = new TopicRequest(5L, "Biology 2","biology description");
         String TopicJson = jsonTester.write(topic).getJson();
        mvc.perform(post("/api/Topic").contentType(MediaType.APPLICATION_JSON).content(TopicJson)).
                andExpect(status().isOk());
    }

    //Updating topic Tests
    @Test
    public void updateTopicTest_InValid_UpdateId() throws Exception {
        Long id = -1L;
        TopicRequest topic = new TopicRequest(id, "Biology 2", "biology description");
        String topicJson = jsonTester.write(topic).getJson();
        mvc.perform(put("/api/Topic/{id}",100L).contentType(MediaType.APPLICATION_JSON).content(topicJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateTopicTest_InValid_UpdateInvalidname() throws Exception {
        Long id = 222L;
        TopicRequest topic = new TopicRequest(id, "", "biology description");
        String topicJson = jsonTester.write(topic).getJson();
        mvc.perform(put("/api/Topic/{id}",id).contentType(MediaType.APPLICATION_JSON).content(topicJson))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void updateTopicTest_Valid_UpdateNameDesc() throws Exception {
        Long id = 333L;
        TopicRequest topic = new TopicRequest(id, "Biology3", "biology description");
        String topicJson = jsonTester.write(topic).getJson();
        mvc.perform(put("/api/Topic/{id}",id).contentType(MediaType.APPLICATION_JSON).content(topicJson))
                .andExpect(status().isOk());
    }


    //Deleting the Topic
    @Test
    public void deleteTopicTest_Invalid_NullId() throws Exception {
        Long id =null;
        mvc.perform(delete("/api/Topic/{id}",id)).
                andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteTopicTest_Invalid_InValidId() throws Exception {
        Long id = 0L;
        mvc.perform(delete("/api/Topic/{id}",id)).
                andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteTopicTest_Valid_200() throws Exception {
        Long id =555L;
        mvc.perform(delete("/api/Topic/{id}",id)).
                andExpect(status().isOk());
    }

    //Fetching the Topic
    @Test
    public void getTopicTest_Invalid_NullId() throws Exception {
        Long id=null;
        mvc.perform(get("/api/Topic/{id}",id))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getTopicTest_Invalid_InValidId() throws Exception {
        Long id =55555L;
        mvc.perform(get("/api/Topic/{id}",id))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getTopicTest_InValid_NotFound() throws Exception {
        Long id =1000L;
        when(topicService.getTopic(id)).thenReturn(null);
        mvc.perform(get("/api/Topic/{id}",id))
                .andExpect(status().isNotFound() );

    }

    @Test
    public void getTopicTest_Valid_200() throws Exception {
        Long id =111L;
        TopicRequest topic = new TopicRequest(id, "Biology 2", "biology description");
        String topicJson = jsonTester.write(topic).getJson();
        when(topicService.getTopic(id)).thenReturn(topic);
        mvc.perform(get("/api/Topic/{id}",id)).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(111)))
                .andExpect(jsonPath("$.name",Matchers.is("Biology 2")))
                .andExpect(jsonPath("$.description",Matchers.is("biology description")));
    }


}