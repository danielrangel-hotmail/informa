package com.objective.informa.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * UM DTO simplifcado para {@link com.objective.informa.domain.Post} entity.
 * Ser para os serviços que basicamente precisam de id e versão
 */
public class SimplePostDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
    private Long versao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public SimplePostDTO() {
    	
    }
    
    public SimplePostDTO(Long id, Long versao) {
        this.id = id;
        this.versao = versao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SimplePostDTO postDTO = (SimplePostDTO) o;
        if (postDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), postDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SimplePostDTO{" +
            "id=" + id +
            ", versao=" + versao +
            '}';
    }
   
    
}
