package com.objective.informa.service.signers;

import java.net.URL;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.objective.informa.service.dto.ArquivoDTO;

import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

@Service
@Profile("!s3mocked")
public class AWSS3RealSigner implements AWSS3Signer {


    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    private final S3Presigner presigner;

    public AWSS3RealSigner() {

         this.presigner = S3Presigner.create();
    }
    

	public void sign(ArquivoDTO result) {
		PresignedPutObjectRequest presignedPutObjectRequest = presigner.presignPutObject(z ->
		z.signatureDuration(Duration.ofMinutes(10))
		    .putObjectRequest(por -> por
		        .bucket(bucketName)
		        .key(result.getLink())
		        .contentType(result.getTipo())
		    ));
		URL url = presignedPutObjectRequest.url();
		result.setS3PresignedURL(url.toString());
	}


	@Override
	public String getArquivosURL() {
		return "https://objective-informa-arquivos.s3.us-east-1.amazonaws.com/";
	}

}
