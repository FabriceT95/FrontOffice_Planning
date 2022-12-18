package com.example.frontoffice_planning.service;

import com.example.frontoffice_planning.entity.Planning;
import com.example.frontoffice_planning.entity.Share;
import com.example.frontoffice_planning.entity.Users;
import com.example.frontoffice_planning.repository.ShareRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShareService {
    private final ShareRepository shareRepository;

    public ShareService(ShareRepository shareRepository){
        this.shareRepository = shareRepository;
    }

    public Optional<Share> getShareByPlanningAndUser(Planning p, Users u) {
        return shareRepository.findByPlanningEqualsAndUsersEquals(p,u);
    }
}
