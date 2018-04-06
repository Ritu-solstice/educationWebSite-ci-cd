package com.Ritu.EducationWebsite;

import com.Ritu.EducationWebsite.Exception.ErrorResponse;
import com.Ritu.EducationWebsite.Topic.TopicRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EducationWebsiteApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	public void post_success() {

		TopicRequest topic = new TopicRequest(6L, "bio Course","biology description");
		ResponseEntity<TopicRequest> response=restTemplate.postForEntity("/api/Topic",topic,TopicRequest.class);
		//here we could have macthed response body status codes, had there there was any bos=dy returned.
		// Here only 200 status code is returned
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void post_Unsuccess_InValidName() {

		TopicRequest topic = new TopicRequest(7L, "","biology description");
		ResponseEntity<ErrorResponse> response=restTemplate.postForEntity("/api/Topic",topic,ErrorResponse.class);

		assertThat(response.getBody().getMessage()).isEqualToIgnoringCase("Invalid request Body");
		assertThat(response.getBody().getSuggestedSolution()).isEqualToIgnoringCase("Topic Body is ");
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void post_Unsuccess_NullBody() {

		TopicRequest topic = new TopicRequest();
		//empty contructor is equal to null value
		ResponseEntity<ErrorResponse> response = restTemplate.postForEntity("/api/Topic", topic, ErrorResponse.class);

		assertThat(response.getBody().getMessage()).isEqualToIgnoringCase("Invalid request Body");
		assertThat(response.getBody().getSuggestedSolution()).isEqualToIgnoringCase("Topic Body is ");
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void put_success() {

		TopicRequest topic = new TopicRequest(6L, "bio Course","biology description");
		HttpEntity<TopicRequest> requestEntity = new HttpEntity<>(topic);
		ResponseEntity<TopicRequest> response
				= restTemplate.exchange("/api/Topic/{id}", HttpMethod.PUT, requestEntity, TopicRequest.class,topic.getId());

		assertThat(response.getBody()).isNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void put_Unsuccess_InValidId() {

		TopicRequest topic = new TopicRequest(-1L, "bio Course", "biology description");
		HttpEntity<TopicRequest> requestHttpEntity = new HttpEntity<>(topic);
		ResponseEntity<ErrorResponse> response =
				restTemplate.exchange("/api/Topic/{id}", HttpMethod.PUT, requestHttpEntity, ErrorResponse.class, 2L);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody().getMessage()).isEqualToIgnoringCase("Invalid request Body");
		assertThat(response.getBody().getSuggestedSolution()).isEqualToIgnoringCase("Topic Body is ");
	}

	@Test
	public void put_Unsuccess_NullBody() {

		TopicRequest topic = new TopicRequest();
		//empty contructor is equal to null value
		HttpEntity<TopicRequest> requestHttpEntity = new HttpEntity<>(topic);
		ResponseEntity<ErrorResponse> response = restTemplate.exchange("/api/Topic/{id}",HttpMethod.PUT,requestHttpEntity, ErrorResponse.class,2L);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody().getMessage()).isEqualToIgnoringCase("Invalid request Body");
		assertThat(response.getBody().getSuggestedSolution()).isEqualToIgnoringCase("Topic Body is ");
	}

	@Test
	public void get_success() {

		TopicRequest topic = new TopicRequest(6L, "bio Course","biology description");
		ResponseEntity<TopicRequest> postResponse=restTemplate.postForEntity("/api/Topic",topic,TopicRequest.class);
		ResponseEntity<TopicRequest> getResponse=restTemplate.getForEntity("/api/Topic/{id}",TopicRequest.class,6L);

		assertThat(getResponse.getStatusCode()).isEqualTo(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(getResponse.getBody().getId()).isEqualTo(topic.getId());
		assertThat(getResponse.getBody().getName()).isEqualTo(topic.getName());
		assertThat(getResponse.getBody().getDescription()).isEqualTo(topic.getDescription());
	}

	@Test
	public void get_Unsuccess_NotFound() {
		ResponseEntity<ErrorResponse> response=restTemplate.getForEntity("/api/Topic/{id}",ErrorResponse.class,0L);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody().getMessage()).isEqualToIgnoringCase("TOPIC_NOT_FOUND");
		assertThat(response.getBody().getSuggestedSolution()).isEqualToIgnoringCase("The Topic was not found");
	}


}
