package com.example.s3rekognition.controller;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.s3rekognition.PPEClassificationResponse;
import com.example.s3rekognition.PPEResponse;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@RestController
public class RekognitionController implements ApplicationListener<ApplicationReadyEvent> {

    private final AmazonS3 s3Client;
    private final AmazonRekognition rekognitionClient;

    private static final Logger logger = Logger.getLogger(RekognitionController.class.getName());

    public RekognitionController() {
        this.s3Client = AmazonS3ClientBuilder.standard().withRegion("eu-west-1").build();
        this.rekognitionClient = AmazonRekognitionClientBuilder.standard().withRegion("eu-west-1").build();
    }

    /**
     * This endpoint takes an S3 bucket name in as an argument, scans all the
     * Files in the bucket for Protective Gear Violations.
     * <p>
     *
     * @param bucketName
     * @return
     */
    @GetMapping(value = "/scan-ppe", consumes = "*/*", produces = "application/json")
    @ResponseBody
    public ResponseEntity<PPEResponse> scanForPPE(@RequestParam String bucketName) {
        // List all objects in the S3 bucket
        ListObjectsV2Result imageList = s3Client.listObjectsV2(bucketName);

        // This will hold all of our classifications
        List<PPEClassificationResponse> classificationResponses = new ArrayList<>();

        // This is all the images in the bucket
        List<S3ObjectSummary> images = imageList.getObjectSummaries();

        // Iterate over each object and scan for PPE
        for (S3ObjectSummary image : images) {
            logger.info("scanning " + image.getKey());

            // This is where the magic happens, use AWS rekognition to detect PPE
            DetectProtectiveEquipmentRequest request = new DetectProtectiveEquipmentRequest()
                    .withImage(new Image()
                            .withS3Object(new S3Object()
                                    .withBucket(bucketName)
                                    .withName(image.getKey())))
                    .withSummarizationAttributes(new ProtectiveEquipmentSummarizationAttributes()
                            .withMinConfidence(80f)
                            .withRequiredEquipmentTypes("FACE_COVER","HAND_COVER","HEAD_COVER"));
            // updated the .withRequireEquipmentTypes to also include gloves and glasses

            DetectProtectiveEquipmentResult result = rekognitionClient.detectProtectiveEquipment(request);

            // Extract the list of persons from the result
            List<ProtectiveEquipmentPerson> persons = result.getPersons();

            // Determine violations for each type
            boolean faceCoverViolation = isFaceCoverViolation(persons);
            boolean handCoverViolation = isHandCoverViolation(persons);
            boolean headCoverViolation = isHeadCoverViolation(persons);

            // If any person on an image lacks PPE on the face, it's a violation of regulations
            //boolean violation = isViolation(result);

            logger.info("Image " + image.getKey() + " scanned. Face Cover Violation: " + faceCoverViolation
                    + ", Hand Cover Violation: " + handCoverViolation
                    + ", Head Cover Violation: " + headCoverViolation);
            // Categorize the current image as a violation or not.
            int personCount = result.getPersons().size();
            PPEClassificationResponse classification = new PPEClassificationResponse(image.getKey(), persons.size(),faceCoverViolation,handCoverViolation,headCoverViolation);
            classificationResponses.add(classification);
        }
        PPEResponse ppeResponse = new PPEResponse(bucketName, classificationResponses);
        return ResponseEntity.ok(ppeResponse);
    }



    private boolean isFaceCoverViolation(List<ProtectiveEquipmentPerson> persons) {
        for (ProtectiveEquipmentPerson person : persons) {
            for (ProtectiveEquipmentBodyPart bodyPart : person.getBodyParts()) {
                if (bodyPart.getName().equals("FACE")) {
                    // Check if the FACE body part has protective equipment detected
                    if (bodyPart.getEquipmentDetections().isEmpty()) {
                        return true; // Violation found
                    }
                }
            }
        }
        return false; // No violation found
    }
    private boolean isHandCoverViolation(List<ProtectiveEquipmentPerson> persons) {
        for (ProtectiveEquipmentPerson person : persons) {
            for (ProtectiveEquipmentBodyPart bodyPart : person.getBodyParts()) {
                if (bodyPart.getName().equals("HAND")) {
                    // Check if the FACE body part has protective equipment detected
                    if (bodyPart.getEquipmentDetections().isEmpty()) {
                        return true; // Violation found
                    }
                }
            }
        }
        return false; // No violation found
    }

    private boolean isHeadCoverViolation(List<ProtectiveEquipmentPerson> persons) {
        for (ProtectiveEquipmentPerson person : persons) {
            for (ProtectiveEquipmentBodyPart bodyPart : person.getBodyParts()) {
                if (bodyPart.getName().equals("HEAD")) {
                    // Check if the FACE body part has protective equipment detected
                    if (bodyPart.getEquipmentDetections().isEmpty()) {
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
