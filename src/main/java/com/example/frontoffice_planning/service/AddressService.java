package com.example.frontoffice_planning.service;

import com.example.frontoffice_planning.entity.Address;
import com.example.frontoffice_planning.repository.AddressRepository;
import com.example.frontoffice_planning.repository.PlanningRepository;
import com.example.frontoffice_planning.repository.UserRepository;
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
        Optional<Address> verifyAddress = addressRepository.findByCityEqualsAndPostalCodeEquals(address.getCity(), address.getPostalCode());
        Address tempAddress = null;

        if(!verifyAddress.isPresent()) {
            tempAddress = addressRepository.save(address);
        } else {
            tempAddress = verifyAddress.get();
        }

        return tempAddress;
    }
}
