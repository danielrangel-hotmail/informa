package com.objective.informa.service.signers;

import com.objective.informa.service.dto.ArquivoDTO;

public interface AWSS3Signer {

	public void sign(ArquivoDTO result);

	public String getArquivosURL();
	
}
