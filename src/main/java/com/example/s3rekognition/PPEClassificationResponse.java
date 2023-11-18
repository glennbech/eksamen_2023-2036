package com.example.s3rekognition;

import java.io.Serializable;

public class PPEClassificationResponse  implements Serializable  {

    private String fileName;
    private boolean faceCoverViolation;
    private boolean handCoverViolation;
    private boolean headCoverViolation;
    private int personCount;

    public PPEClassificationResponse(String fileName, int personCount, boolean faceCoverViolation, boolean handCoverViolation, boolean headCoverViolation) {
        this.fileName = fileName;
        this.personCount = personCount;
        this.faceCoverViolation = faceCoverViolation;
        this.handCoverViolation = handCoverViolation;
        this.headCoverViolation = headCoverViolation;

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public boolean isFaceCoverViolation() {
        return faceCoverViolation;
    }

    public void setFaceCoverViolation(boolean faceCoverViolation) {
        this.faceCoverViolation = faceCoverViolation;
    }

    public boolean isHandCoverViolation() {
        return handCoverViolation;
    }

    public void setHandCoverViolation(boolean handCoverViolation) {
        this.handCoverViolation = handCoverViolation;
    }

    public boolean isHeadCoverViolation() {
        return headCoverViolation;
    }

    public void setHeadCoverViolation(boolean headCoverViolation) {
        this.headCoverViolation = headCoverViolation;
    }
}
