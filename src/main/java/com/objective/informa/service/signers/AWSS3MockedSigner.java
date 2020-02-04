package com.objective.informa.service.signers;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.objective.informa.service.dto.ArquivoDTO;

@Service
@Profile("s3mocked")
public class AWSS3MockedSigner implements AWSS3Signer {

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

	@Override
	public void sign(ArquivoDTO result) {
    	try {
			URL url = new URL("http://localhost:4568/"+bucketName+"/"+result.getLink());
			result.setS3PresignedURL(url.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	@Override
	public String getArquivosURL() {
		return "http://localhost:4568/objective-informa-arquivos/";
	}

}
