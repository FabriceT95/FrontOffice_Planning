package com.agendo.frontoffice_planning.repository;

import com.agendo.frontoffice_planning.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByCityEqualsAndPostalCodeEquals(String city, String postalCode);
}
