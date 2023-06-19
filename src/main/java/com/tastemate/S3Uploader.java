package com.tastemate;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class S3Uploader {
  private final AmazonS3Client amazonS3Client;
  @Value("${cloud.aws.s3.bucket}")
  private String bucket;
  @Value("${boardfile.dir}")
  private String localFileDir;
  public String getFullPath(String fileName) {
    return localFileDir + fileName;
  }
  public String saveFile(MultipartFile multipartFile) throws IOException {
    if (multipartFile.isEmpty()) {
      return "";
    }

    // 유저가 업로드한 파일 명
    String originalFileName = multipartFile.getOriginalFilename();

    String storeFileName = createStoreProfileName(originalFileName);

    // 로컬저장소에 먼저 저장
    multipartFile.transferTo(new File(getFullPath(storeFileName)));

    // 아마존에 저장
     putS3(new File(getFullPath(storeFileName)), storeFileName);

    return storeFileName;
  }

  private String putS3(File uploadFile, String storeFileName) {
    amazonS3Client.putObject(new PutObjectRequest(bucket,storeFileName, uploadFile));
    return amazonS3Client.getUrl(bucket,storeFileName).toString();
  }

  private String createStoreProfileName(String originalFileName) {
    // 확장자 명 추출
    String ext = extracted(originalFileName);
    String uuid = UUID.randomUUID().toString();
    //DB에 저장할 파일 명
    return uuid + "." + ext;
  }

  private String extracted(String originalFileName) {
    int pos = originalFileName.lastIndexOf(".");
    // 확장자 명
    return originalFileName.substring(pos+1);
  }

  public ResponseEntity<byte[]> downloadFile(String storeFileName, String oriName) throws IOException {
    S3Object s3Object = amazonS3Client.getObject(new GetObjectRequest(bucket, storeFileName));
    S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
    byte[] bytes = IOUtils.toByteArray(objectInputStream);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(contentType(storeFileName));
    headers.setContentLength(bytes.length);
    String[] arr = storeFileName.split("/");
    String type = arr[arr.length-1];
    String fileName = URLEncoder.encode(type, "UTF-8").replaceAll("\\+", "%20");
    headers.setContentDispositionFormData("attachment", oriName);
    return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
  }

  private MediaType contentType(String storeFileName) {
    String[] arr = storeFileName.split("\\.");
    String type = arr[arr.length-1];
    switch (type) {
      case "txt":
        return MediaType.TEXT_PLAIN;
      case "jpg":
        return MediaType.IMAGE_JPEG;
      case "png":
        return MediaType.IMAGE_PNG;
      default:
        return MediaType.APPLICATION_OCTET_STREAM;
    }
  }

  private HttpHeaders buildHeaders(String resourcePath, byte[] data) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentLength(data.length);
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.setContentDisposition(
        ContentDisposition.builder("attachment")
            .filename(URLEncoder.encode(resourcePath))
            .build());
    return headers;
  }
}
