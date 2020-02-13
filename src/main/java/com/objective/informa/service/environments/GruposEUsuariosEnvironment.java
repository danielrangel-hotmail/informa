package com.objective.informa.service.environments;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.objective.informa.domain.Grupo;
import com.objective.informa.domain.User;
import com.objective.informa.service.GrupoService;
import com.objective.informa.service.UserService;
import com.objective.informa.service.dto.GrupoDTO;
import com.objective.informa.service.dto.SimpleUserDTO;
import com.objective.informa.service.dto.UserDTO;
import com.objective.informa.service.mapper.SimpleUserMapper;
import com.objective.insistence.layer.environment.InsistenceEnvironment;

@Service
@Transactional
public class GruposEUsuariosEnvironment  implements InsistenceEnvironment {

	public static final String GRUPOS_E_USUARIOS = "Grupos e Usuarios";
	private final UserService userService;
	private final GrupoService grupoService;
	private final SimpleUserMapper simpleUserMapper;
	private GrupoDTO grupoObjective;
	private GrupoDTO grupoPapoLivre;
	private User userNormal;
	private User moderador;
	
	
	public GruposEUsuariosEnvironment(UserService userService, GrupoService grupoService,
			SimpleUserMapper simpleUserMapper) {
		super();
		this.userService = userService;
		this.grupoService = grupoService;
		this.simpleUserMapper = simpleUserMapper;
	}


	@Override
	public void execute() {
		grupoObjective = criaGrupo("objective", true, false);
		this.criaUser("diretor", "Diretor", "Ocupado", "ROLE_USER");
		User moderador = this.criaUser("moderador", "Moderador", "Serio", "ROLE_USER");
		userNormal = this.criaUser("normal", "Normal", "Engajado", "ROLE_USER");
		grupoPapoLivre = criaGrupo("papo-livre", false, true, moderador);
	}

	
	private User criaUser(String login, String firstName, String lastName, String ... authorities) {
		UserDTO userDTO = new UserDTO();
		userDTO.setLogin(login);
		userDTO.setEmail(login + "@objective.com.br");
		userDTO.setFirstName(firstName);
		userDTO.setLastName(lastName);
		userDTO.setAuthorities(new HashSet<String>(Arrays.asList(authorities)));
		return this.userService.createUser(userDTO, login);
	}
	
	private GrupoDTO criaGrupo(String nome, Boolean formal, Boolean opcional, User...moderadores) {
		GrupoDTO grupoDTO = new GrupoDTO();
		grupoDTO.setNome(nome);
		grupoDTO.setFormal(formal);
		grupoDTO.setOpcional(opcional);
		if (moderadores != null) {
			grupoDTO.setModeradores(new HashSet<SimpleUserDTO>(this.simpleUserMapper.toDto(Arrays.asList(moderadores))));			
		}
		return this.grupoService.create(grupoDTO);
	}
	
	
	@Override
	public String getName() {
		return GRUPOS_E_USUARIOS;
	}


	public GrupoDTO getGrupoObjective() {
		return grupoObjective;
	}


	public GrupoDTO getGrupoPapoLivre() {
		return grupoPapoLivre;
	}


	public User getUserNormal() {
		return userNormal;
	}


	public User getModerador() {
		return moderador;
	}

}


//Admin
//User
//Diretor
//ModeradorNormal
//Normal
//
//Grupo: objective obrigatório de trabalho sem moderadores
//Grupo: papo livre opcional informal com um moderador