package com.agendo.frontoffice_planning.service;

import com.agendo.frontoffice_planning.entity.Address;
import com.agendo.frontoffice_planning.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public Address createAddress(Address address) {
        System.out.println("Verifying presence of address : " + address.toString());
        Optional<Address> verifyAddress = addressRepository.findByCityEqualsAndPostalCodeEquals(address.getCity(), address.getPostalCode());
        Address tempAddress = null;

        tempAddress = verifyAddress.orElseGet(() -> {
            System.out.println("New address being saved...");
            Address localAddress = addressRepository.save(address);
            System.out.println("New address is saved successfully");
            return localAddress;

        });

        return tempAddress;
    }
}
