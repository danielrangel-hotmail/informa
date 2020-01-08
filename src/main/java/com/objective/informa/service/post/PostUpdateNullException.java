package com.objective.informa.service.post;

import com.objective.informa.service.dto.SimplePostDTO;

public class PostUpdateNullException extends PostException {

    public PostUpdateNullException(SimplePostDTO postDTO) {
        super(postDTO, "Post não encontrado");

    }

}
