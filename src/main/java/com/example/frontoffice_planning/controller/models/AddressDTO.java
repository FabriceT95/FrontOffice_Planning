package com.example.frontoffice_planning.controller.models;

public class AddressDTO {
    private Long idAddress;

    private String city;

    private String postalCode;

    public AddressDTO() {
    }

    public AddressDTO(Long idAddress, String city, String postalCode) {
        this.idAddress = idAddress;
        this.city = city;
        this.postalCode = postalCode;
    }

    public AddressDTO(String city, String postalCode) {
        this.city = city;
        this.postalCode = postalCode;
    }

    public Long getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(Long idAddress) {
        this.idAddress = idAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
