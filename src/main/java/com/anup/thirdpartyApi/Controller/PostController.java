package com.anup.thirdpartyApi.Controller;

import com.anup.thirdpartyApi.postservice.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
        System.out.println("PostService injected: " + (postService != null));
    }

    @GetMapping("/getPosts")
    public List<Map<String, Object>> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/getPostsById/{id}")
    Map<String, Object>getPosts(@PathVariable int id) {
        return postService.getPostById(id);
    }

    @PostMapping ("/insertPosts")
    Map<String, Object>insertPosts(@RequestBody Map<String,Object>payload) {
        return postService.insertPosts(payload);
    }

    @PutMapping("/updatePosts/{id}")
    Map<String, Object>updatePosts(@RequestBody Map<String,Object>payload,@PathVariable  int id) {
        return postService.updatePosts(payload,id);
    }

    @DeleteMapping("/deletePosts/{id}")
    Map<String, Object>deletePosts(@PathVariable  int id) {
        return postService.deletePosts(id);
    }


}
