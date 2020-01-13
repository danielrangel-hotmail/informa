package com.objective.informa.service.dto;

public class ArquivoPreSignDTO {

    private String filename;
    private String contentType;

    public ArquivoPreSignDTO(String filename, String contentType) {
        this.filename = filename;
        this.contentType = contentType;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "ArquivoPreSignDTO{" +
            "filename='" + filename + '\'' +
            ", contentType='" + contentType + '\'' +
            '}';
    }
}
