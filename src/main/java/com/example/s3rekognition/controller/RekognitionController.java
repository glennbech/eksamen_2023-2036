package com.example.s3rekognition.controller;


import com.example.s3rekognition.PPEClassificationResponse;
import com.example.s3rekognition.PPEResponse;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;


import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@RestController
public class RekognitionController implements ApplicationListener<ApplicationReadyEvent> {

    // Aws S3 client to interact with S3
    private final S3Client s3Client;
    // AWS Rekognition client to interact with Rekognition service
    private final RekognitionClient rekognitionClient;
    //Logger for logging information and errors
    private static final Logger logger = Logger.getLogger(RekognitionController.class.getName());

    // Constructor to initialize S3 and Rekognition clients
    public RekognitionController() {

        this.s3Client = S3Client.builder().region(Region.EU_WEST_1).build();
        this.rekognitionClient = RekognitionClient.builder().region(Region.EU_WEST_1).build();
    }

    // Endpoint to scan personal protective equipment (PPE) in images stored in the S# bucket
    @GetMapping(value = "/scan-ppe", consumes = "*/*", produces = "application/json")
    @ResponseBody
    public ResponseEntity<PPEResponse> scanPPE(@RequestParam String bucketName, @RequestParam List<String> requiredEquipmentTypes) {

        // Retrieve all objects from the specified S3 bucket
        ListObjectsV2Response imageList = s3Client.listObjectsV2(ListObjectsV2Request.builder().bucket(bucketName).build());

        // Store classifications for each image
        List<PPEClassificationResponse> classificationResponses = new ArrayList<>();

        // Get a list of images from the bucket
        List<software.amazon.awssdk.services.s3.model.S3Object> images = imageList.contents();

        // Iterate over each image and analyze for PPE
        for (software.amazon.awssdk.services.s3.model.S3Object image : images) {
            String imageKey = image.key();
            logger.info("scanning " + image.key());
            DetectProtectiveEquipmentRequest request = buildPpeRequest(bucketName, imageKey, requiredEquipmentTypes);
            DetectProtectiveEquipmentResponse response = rekognitionClient.detectProtectiveEquipment(request);

            // Extract information about persons detected in the image
            List<ProtectiveEquipmentPerson> persons = response.persons();
            // Check for violations for each type of PPE
            boolean faceCoverViolation = isViolation(persons, "FACE");
            boolean handCoverViolation = isViolation(persons, "HAND");
            boolean headCoverViolation = isViolation(persons, "HEAD");

            // Log and create response as per the specific requirements
            logger.info("Image " + imageKey + " scanned. Violations - Face: " + faceCoverViolation + ", Hand: " + handCoverViolation + ", Head: " + headCoverViolation);

            // Store the classification results for each image
            PPEClassificationResponse classification = new PPEClassificationResponse(imageKey, persons.size(), faceCoverViolation, handCoverViolation, headCoverViolation);
            classificationResponses.add(classification);

        }
        // Return the result as an HTTP response
        return ResponseEntity.ok(new PPEResponse(bucketName, classificationResponses));
    }

    //Helper method to build a request for detecting PPE in an image
    private DetectProtectiveEquipmentRequest buildPpeRequest(String bucketName, String imageKey, List<String> requiredEquipmentTypes) {
        return DetectProtectiveEquipmentRequest.builder()
                .image(Image.builder()
                        .s3Object(software.amazon.awssdk.services.rekognition.model.S3Object.builder()
                                .bucket(bucketName)
                                .name(imageKey)
                                .build())
                        .build())
                .summarizationAttributes(ProtectiveEquipmentSummarizationAttributes.builder()
                        .minConfidence(80f)
                        .requiredEquipmentTypesWithStrings(requiredEquipmentTypes)
                        .build())
                .build();
    }
    // New endpoint for scanning for face and hand covers in fluid contagion scenarios
    @GetMapping(value = "/scan-ppe/fluid-contagion", consumes = "*/*", produces = "application/json")
    public ResponseEntity<PPEResponse> scanForFluidContagionPPE(@RequestParam String bucketName) {
        return scanPPE(bucketName, List.of("FACE_COVER", "HAND_COVER"));
    }

    // New endpoint for checking hand cover only
    @GetMapping(value = "/scan-ppe/hand-only", consumes = "*/*", produces = "application/json")
    public ResponseEntity<PPEResponse> scanForHandOnlyPPE(@RequestParam String bucketName) {
        return scanPPE(bucketName, List.of("HAND_COVER"));
    }

    // Helper method to determine if there is a PPE violation for a given body part
    private boolean isViolation(List<ProtectiveEquipmentPerson> persons, String ppeType) {
        for (ProtectiveEquipmentPerson person : persons) {
            for (ProtectiveEquipmentBodyPart bodyPart : person.bodyParts()) {
                if (bodyPart.nameAsString().equals(ppeType)) {
                    // Check if the specified body part has protective equipment detected
                    if (bodyPart.equipmentDetections().isEmpty()) {
                        return true; // Violation found
                    }
                }
            }
        }
        return false; // No violation found
    }
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

    }
}
