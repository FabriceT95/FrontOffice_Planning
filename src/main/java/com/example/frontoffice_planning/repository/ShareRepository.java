package com.example.frontoffice_planning.repository;
import com.example.frontoffice_planning.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {
}
