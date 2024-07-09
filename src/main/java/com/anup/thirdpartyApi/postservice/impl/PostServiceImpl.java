package com.anup.thirdpartyApi.postservice.impl;

import com.anup.thirdpartyApi.postservice.PostService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService {

    String baseurl = "https://jsonplaceholder.typicode.com/";
    StringBuilder stringBuilder = new StringBuilder(baseurl);
    private final String POST = "/posts";
    private final String POSTBYID="/posts";


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Map<String, Object>> getPosts() {
        HttpEntity<Void> entity = new HttpEntity<>(gethttpHeaders());
        String url = stringBuilder.append(POST).toString();
        val response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
        return response.getBody();
    }

    @Override

    public Map<String, Object> getPostById(int id) {
        HttpEntity<Void> entity = new HttpEntity<>(gethttpHeaders());
        String url = stringBuilder.append(POSTBYID) + "/" + id;
        try {
            val response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            return response.getBody();
        } catch (RestClientResponseException e) {
            // Handle 404 or other client errors here
            if (e.getStatusCode().is4xxClientError()) {
                // Log the error or throw a custom exception
                System.out.println("Error getting post by ID: " + id + ". Status code: " + e.getStatusCode());
            }
            return null; // Or return a default value
        }


    }

    @Override
    public Map<String,Object>insertPosts(Map<String, Object> payload) {
        HttpEntity<Map> entity = new HttpEntity<>(payload,gethttpHeaders());
        String url = stringBuilder.append(POST).toString();
        try {
            val response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
            return response.getBody();
        }catch (RestClientResponseException e){
            if (e.getStatusCode().is4xxClientError()) {
                // Log the error or throw a custom exception
                System.out.println("Error getting post by ID: " + payload + ". Status code: " + e.getStatusCode());
            }
            return null; // Or return a default value

        }
    }

    @Override
    public Map<String, Object> updatePosts(Map<String, Object> payload, int id) {
        HttpEntity<Map> entity = new HttpEntity<>(payload,gethttpHeaders());
        String url = stringBuilder.append(POSTBYID) + "/" + id;

        try {
            val response = restTemplate.exchange(url, HttpMethod.PUT, entity, Map.class);
            return response.getBody();
        }catch (RestClientResponseException e){
            if (e.getStatusCode().is4xxClientError()) {
                // Log the error or throw a custom exception
                System.out.println("Error getting post by ID: " + payload + ". Status code: " + e.getStatusCode());
            }
            return null; // Or return a default value

        }
    }

    @Override
    public Map<String, Object> deletePosts( int id) {
        HttpEntity<Map> entity = new HttpEntity<>(gethttpHeaders());
        String url = stringBuilder.append(POSTBYID) + "/" + id;

        try {
            val response = restTemplate.exchange(url, HttpMethod.DELETE, entity, Map.class);
            return response.getBody();
        }catch (RestClientResponseException e){
            if (e.getStatusCode().is4xxClientError()) {
                // Log the error or throw a custom exception
                System.out.println("Error getting post by ID: " + id + ". Status code: " + e.getStatusCode());
            }
            return null; // Or return a default value

        }
    }

    private HttpHeaders gethttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;

    }
}
