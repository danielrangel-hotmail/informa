package com.objective.informa.service.post;

import com.objective.informa.service.dto.SimplePostDTO;

public class PostNonAuthorizedException extends PostException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PostNonAuthorizedException(SimplePostDTO postDTO) {
        super(postDTO, "Post de outro autor");
    }
}
