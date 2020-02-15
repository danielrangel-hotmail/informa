package com.objective.informa.service.environments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.objective.informa.security.SecurityFacade;
import com.objective.informa.service.dto.PostDTO;
import com.objective.informa.service.dto.SimplePostDTO;
import com.objective.informa.service.post.PostException;
import com.objective.informa.service.post.PostService;
import com.objective.insistence.layer.environment.InsistenceEnvironment;

@Service
@Transactional
public class PostPublicadoEnvironment implements InsistenceEnvironment {

	@Autowired GruposEUsuariosEnvironment gruposEUsuariosEnvironment;
	@Autowired PostService postService;
	@Autowired SecurityFacade securityFacade;
	
	@Override
	public void execute() {
		try {
			securityFacade.user("admin", "ROLE_USER", "ROLE_ADMIN");
			gruposEUsuariosEnvironment.execute();
			PostDTO postDTO = new PostDTO();
			postDTO.setConteudo("qualquer coisa");
			postDTO.setGrupoId(gruposEUsuariosEnvironment.getGrupoObjective().getId());
			postDTO.setOficial(false);
			PostDTO postCriado = postService.create(postDTO);
			postService.publica(new SimplePostDTO(postCriado.getId(), postCriado.getVersao()));
		} catch (PostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			securityFacade.resetUser();
		}
	}

	@Override
	public String getName() {
		return "Post Publicado";
	}

}
