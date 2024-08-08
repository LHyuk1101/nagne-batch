package org.team.nagnebatch.place.batch.market.processor;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.team.nagnebatch.place.batch.market.domain.CsvData;
import org.team.nagnebatch.place.batch.repository.AreaRepository;
import org.team.nagnebatch.place.batch.repository.PlaceRepository;
import org.team.nagnebatch.place.domain.ApiType;
import org.team.nagnebatch.place.domain.Area;
import org.team.nagnebatch.place.domain.Place;
import org.team.nagnebatch.place.domain.PlaceImg;
import org.team.nagnebatch.place.domain.Store;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.core.sync.RequestBody;

@Component
public class PlaceAndStoreProcessorForRestaurant implements ItemProcessor<CsvData, PlaceAndStore> {

  private static final int CONTENT_TYPE_ID = 82;
  private static final Logger log = LoggerFactory.getLogger(PlaceAndStoreProcessorForRestaurant.class);
  private final AreaRepository areaRepository;
  private final PlaceRepository placeRepository;

  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucketName;

  @Value("${spring.cloud.aws.credentials.access-key}")
  private String accessKey;

  @Value("${spring.cloud.aws.credentials.secret-key}")
  private String secretKey;

  @Autowired
  public PlaceAndStoreProcessorForRestaurant(AreaRepository areaRepository, PlaceRepository placeRepository) {
    this.areaRepository = areaRepository;
    this.placeRepository = placeRepository;
  }

  @Override
  @Transactional
  public PlaceAndStore process(CsvData data) throws Exception {
    if (data.getPhoneNumber() != null && data.getPhoneNumber().contains("Phone")) {
      data.setPhoneNumber(null);
    }

    if (data.getImageUrl() != null && data.getImageUrl().contains("proxy")) {
      return null;
    }

    String businessHours = data.getBusinessHours();
    if (businessHours != null) {
      if (businessHours.contains("\\u202f")) {
        businessHours = businessHours.replace("\\u202f", "");
      }
      if (businessHours.equals("['No Business Hours Available']") || businessHours.equals("checkin_time:,checkout_time:")) {
        businessHours = null;
      }
      data.setBusinessHours(businessHours);
    }

    Area area = areaRepository.findById(Integer.parseInt(data.getAreatype()))
        .orElseThrow(() -> new IllegalArgumentException("AREA 코드 잘못됨 : " + data.getAreatype()));

    ApiType apiType = ApiType.NONE;

    String uniqueId;
    do {
      uniqueId = String.format("%07d", ThreadLocalRandom.current().nextInt(1000000));
    } while (placeRepository.existsByContentId(uniqueId));

    String thumbnailUrl = null;
    try {
      thumbnailUrl = uploadImageToS3(data.getImageUrl());
    } catch (IOException e) {
      log.error("S3에 업로드 실패로 건너뜀: {}", data.getImageUrl(), e);
      return null;
    }

    Place place = new Place(
        data.getAddress(),
        data.getName(),
        uniqueId,
        data.getLatitude(),
        CONTENT_TYPE_ID,
        data.getLongitude(),
        area,
        apiType,
        null,
        thumbnailUrl,
        LocalDateTime.now()
    );

    Store store = new Store(
        null,
        place,
        data.getBusinessHours(),
        data.getPhoneNumber()
    );

    PlaceImg placeImg = new PlaceImg(
        place,
        data.getImageUrl()
    );

    return new PlaceAndStore(place, store, placeImg);
  }

  private String uploadImageToS3(String imageUrl) throws IOException {
    if (accessKey == null || accessKey.isEmpty()) {
      throw new IllegalArgumentException("액세스키 비었거나 null임");
    }
    if (secretKey == null || secretKey.isEmpty()) {
      throw new IllegalArgumentException("시크릿키 비었거나 null임");
    }

    // Download the image
    String fileName = UUID.randomUUID().toString() + "-" + Paths.get(new URL(imageUrl).getPath()).getFileName().toString();
    InputStream inputStream = new URL(imageUrl).openStream();

    // Create a unique temporary file name
    Path tempFile = Files.createTempFile("upload-", UUID.randomUUID().toString() + "-" + fileName);
    Files.copy(inputStream, tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

    // Upload the file to S3
    S3Client s3Client = S3Client.builder()
        .region(Region.AP_NORTHEAST_2)
        .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
        .build();

    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(bucketName)
        .key(fileName)
        .build();

    PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, RequestBody.fromFile(tempFile));

    // Clean up the temporary file
    Files.delete(tempFile);

    return s3Client.utilities().getUrl(b -> b.bucket(bucketName).key(fileName)).toExternalForm();
  }
}
