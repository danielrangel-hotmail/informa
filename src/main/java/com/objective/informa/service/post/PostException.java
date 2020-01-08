package com.objective.informa.service.post;

import com.objective.informa.service.dto.SimplePostDTO;

public class PostException extends Exception {

  public PostException(SimplePostDTO postDTO, String message) {
    super("message");
    this.postDTO = postDTO;
  }

  public SimplePostDTO getPostDTO() {
      return postDTO;
  }

  protected SimplePostDTO postDTO;
}
