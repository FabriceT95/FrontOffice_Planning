package com.agendo.frontoffice_planning.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Address")
public class Address {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_address")
    private Long idAddress;
    @Basic
    @Column(name = "city")
    private String city;

    @Basic
    @Column(name = "postal_code")
    private String postalCode;

    public Address(String city, String postalCode) {
        this.city = city;
        this.postalCode = postalCode;
    }

    public Address() {

    }

    public Address(Long idAddress, String city, String postalCode) {
        this.idAddress = idAddress;
        this.city = city;
        this.postalCode = postalCode;
    }

    public Long getIdAddress() {
        return idAddress;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!Objects.equals(idAddress, address.idAddress)) return false;
        if (!Objects.equals(city, address.city)) return false;
        if (!Objects.equals(postalCode, address.postalCode)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAddress.intValue();
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "idAddress=" + idAddress +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
