package com.example.frontoffice_planning.service;

import com.example.frontoffice_planning.entity.Address;
import com.example.frontoffice_planning.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

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
