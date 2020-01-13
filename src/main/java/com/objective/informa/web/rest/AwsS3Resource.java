package com.objective.informa.web.rest;

import com.objective.informa.service.dto.ArquivoPreSignDTO;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;


@RestController
@RequestMapping("/api")
public class AwsS3Resource {

    private final Logger log = LoggerFactory.getLogger(AwsS3Resource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    /**
     * {@code POST  /s3-presign} : presign a post on AWS S3.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of posts in body.
     */
    @PostMapping("/s3-presign")
    public ResponseEntity<Map> postPreSign(@RequestBody ArquivoPreSignDTO arquivoPreSignDTO) {
        S3Presigner presigner = S3Presigner.create();
        PresignedPutObjectRequest presignedPutObjectRequest = presigner.presignPutObject(z ->
            z.signatureDuration(Duration.ofMinutes(10))
            .putObjectRequest(por -> por
                .bucket(bucketName)
                .key(arquivoPreSignDTO.getFilename())
                .contentType(arquivoPreSignDTO.getContentType())));
        URL url = presignedPutObjectRequest.url();
        HashMap ret = new HashMap();
//        ret.put("method", "PUT");
//        ret.put("url", url.toString().split("\\?")[0]);
//        ret.put("fields", splitQuery(url));
//        ret.put("headers","[]");
        ret.put("method", "PUT");
        ret.put("url", url.toString());
        ret.put("fields", new HashMap<>());
        final HashMap headers = new HashMap();
        headers.put("Content-Type", arquivoPreSignDTO.getContentType());
        ret.put("headers",(headers));

        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    public static Map<String, String> splitQuery(URL url)  {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(pair.substring(0, idx), pair.substring(idx + 1));
        }
        return query_pairs;
    }
}
