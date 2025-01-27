package com.lmr.pajareandoapp.models;

import com.google.firebase.database.PropertyName;

public class Bird {
    private String commonName;
    private String scientificName;
    private String description;
    private String urlPhoto;

    public Bird() {}

    public Bird(String commonName, String scientificName, String description, String urlPhoto) {
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.description = description;
        this.urlPhoto = urlPhoto;
    }

    @PropertyName("common_name") // Mapea "common_name" en Firebase a "commonName" en la clase
    public String getCommonName() {
        return commonName;
    }

    @PropertyName("common_name") // Mapea "common_name" en Firebase a "commonName" en la clase
    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    @PropertyName("scientific_name") // Mapea "scientific_name" en Firebase a "scientificName" en la clase
    public String getScientificName() {
        return scientificName;
    }

    @PropertyName("scientific_name") // Mapea "scientific_name" en Firebase a "scientificName" en la clase
    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    @PropertyName("description") // No es necesario si el nombre de la propiedad coincide
    public String getDescription() {
        return description;
    }

    @PropertyName("description") // No es necesario si el nombre de la propiedad coincide
    public void setDescription(String description) {
        this.description = description;
    }

    @PropertyName("url_photo") // Mapea "url_photo" en Firebase a "urlPhoto" en la clase
    public String getUrlPhoto() {
        return urlPhoto;
    }

    @PropertyName("url_photo") // Mapea "url_photo" en Firebase a "urlPhoto" en la clase
    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

}
