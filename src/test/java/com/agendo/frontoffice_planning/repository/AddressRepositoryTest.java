package com.agendo.frontoffice_planning.repository;

import com.agendo.frontoffice_planning.entity.Address;
import com.agendo.frontoffice_planning.service.File.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest

public class AddressRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressRepository addressRepository;

    @MockBean
    StorageService storageService;

    @Test
    void given_AddressTEST_when_findById__Then_returnAddress(){

        // given
        Address Address = new Address("Test", "00000");
        entityManager.persist(Address);

        // when
        Address result = addressRepository.findById(Address.getIdAddress()).get();

        // then
        assertThat(result).isEqualTo(Address);

    }

    @Test
    void given_AddressTEST_when_delete__Then_findByIdReturnNull(){

        // given
        Address Address = new Address("TEST", "00000");
        entityManager.persist(Address);

        // when
        addressRepository.delete(Address);
        Optional<Address> result = addressRepository.findById(Address.getIdAddress());

        // then
        assertThat(result.isEmpty()).isTrue();

    }

    @Test
    void given_3AddressTEST_when_findAll__then_Size3AndAllAddresss(){

        // given
        Address Address1 = new Address("TEST1", "00001");
        Address Address2 = new Address("TEST2", "00002");
        Address Address3 = new Address("TEST3", "00003");
        entityManager.persist(Address1);
        entityManager.persist(Address2);
        entityManager.persist(Address3);

        // when
        List<Address> result = addressRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result).contains(Address1);
        assertThat(result).contains(Address2);
        assertThat(result).contains(Address3);

    }

    @Test
    void given_3AddressTEST_when_findAllById1And3__then_returnSize2AndAddress1AndAddress3(){

        // given
        Address Address1 = new Address("TEST1", "00001");
        Address Address2 = new Address("TEST2", "00002");
        Address Address3 = new Address("TEST3", "00003");
        entityManager.persist(Address1);
        entityManager.persist(Address2);
        entityManager.persist(Address3);
        List<Long> ids = new ArrayList<>();
        ids.add(Address1.getIdAddress());
        ids.add(Address3.getIdAddress());

        // when
        List<Address> result = addressRepository.findAllById(ids);

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(Address1);
        assertThat(result).doesNotContain(Address2);
        assertThat(result).contains(Address3);

    }

    @Test
    void given_AddressTEST_when_findByCityEqualsAndPostalCodeEquals__then_returnAddress(){
        // given
        Address Address = new Address("Test", "00000");
        entityManager.persist(Address);

        // when
        Address result = addressRepository.findByCityEqualsAndPostalCodeEquals("Test","00000").get();

        // then
        assertThat(result).isEqualTo(Address);
    }

}
