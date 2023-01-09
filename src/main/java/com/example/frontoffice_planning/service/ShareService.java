package com.example.frontoffice_planning.service;

import com.example.frontoffice_planning.controller.exception.planning.PlanningNotFoundException;
import com.example.frontoffice_planning.controller.exception.share.ShareAlreadyExistsException;
import com.example.frontoffice_planning.controller.exception.share.ShareNotFoundException;
import com.example.frontoffice_planning.controller.exception.share.ShareReadOnlyException;
import com.example.frontoffice_planning.controller.exception.user.UserNotFoundException;
import com.example.frontoffice_planning.controller.exception.user.UserNotOwnerException;
import com.example.frontoffice_planning.controller.models.Share.ShareDTO;
import com.example.frontoffice_planning.controller.models.Share.setNewShareDTO;
import com.example.frontoffice_planning.entity.Planning;
import com.example.frontoffice_planning.entity.Share;
import com.example.frontoffice_planning.entity.ShareId;
import com.example.frontoffice_planning.entity.Users;
import com.example.frontoffice_planning.repository.PlanningRepository;
import com.example.frontoffice_planning.repository.ShareRepository;
import com.example.frontoffice_planning.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ShareService {
    @Autowired
    private ShareRepository shareRepository;

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private UserRepository userRepository;

    public Share getShareByPlanningAndUser(Planning p, Users u) throws ShareNotFoundException {
        Optional<Share> optShare = shareRepository.findByPlanningEqualsAndUsersEquals(p, u);
        if (optShare.isEmpty()) {
            throw new ShareNotFoundException(u.getEmail(), p.getIdPlanning());
        }
        return optShare.get();
    }

    public void isFullAccess(Planning planning, Users users) throws ShareReadOnlyException {
        Optional<Share> optShare = shareRepository.findByPlanningEqualsAndUsersEquals(planning, users);
        if (optShare.isEmpty() || optShare.get().getIsReadOnly()) {
            throw new ShareReadOnlyException(users.getEmail(), planning.getIdPlanning());
        }
    }

    public boolean shareExists(Planning planning, Users users) throws ShareNotFoundException {
        boolean isShared = shareRepository.existsShareByPlanningAndUsers(planning, users);
        if (!isShared) {
            throw new ShareNotFoundException(users.getEmail(), planning.getIdPlanning());
        }
        System.out.println("Planning " + planning.getIdPlanning() + " is shared with User " + users.getIdUser() + " whose email is " + users.getEmail());
        return true;
    }

    public boolean shareNotExist(Planning planning, Users users) throws ShareAlreadyExistsException {
        boolean isShared = shareRepository.existsShareByPlanningAndUsers(planning, users);
        if (isShared) {
            throw new ShareAlreadyExistsException(users.getEmail(), planning.getNamePlanning());
        }
        System.out.println("Planning " + planning.getIdPlanning() + " is NOT shared with User " + users.getIdUser() + " whose email is " + users.getEmail());
        return true;
    }

    @Transactional
    public Share save(Share share) {
        return shareRepository.save(share);
    }

    public void delete(Share share) {
        shareRepository.delete(share);
    }

    public List<Share> findAllByUsers(Users users) {
        return shareRepository.findAllByUsers(users);
    }

    public ShareDTO createShare(setNewShareDTO newShareDTO, Users users) throws PlanningNotFoundException, UserNotOwnerException, UserNotFoundException, ShareAlreadyExistsException {
        System.out.println("Creating a Share for User with email " + newShareDTO.getEmail() + " associated with the Planning " + users.getPlanning().getIdPlanning() + "...");
        Optional<Planning> optPlanning = planningRepository.findById(newShareDTO.getPlanningId());
        if (optPlanning.isEmpty()) {
            throw new PlanningNotFoundException(newShareDTO.getPlanningId());
        }
        Planning planning = optPlanning.get();
        if (!Objects.equals(planning.getUser().getEmail(), users.getEmail())) {
            throw new UserNotOwnerException(users.getUsername(), planning.getNamePlanning());
        }

        Optional<Users> optUsersToShareWith = userRepository.findByEmail(newShareDTO.getEmail());
        if (optUsersToShareWith.isEmpty()) {
            throw new UserNotFoundException(newShareDTO.getEmail());
        }

        Users usersToShareWith = optUsersToShareWith.get();

        shareNotExist(planning, usersToShareWith);

        Share share = new Share(new ShareId(usersToShareWith.getIdUser(), planning.getIdPlanning()), planning, usersToShareWith, true);
        share = save(share);
        System.out.println("Share has been saved");

        usersToShareWith.addShare(share);
        planning.addShare(share);

        userRepository.save(usersToShareWith);
        planningRepository.save(planning);


        ShareDTO shareDTO = new ShareDTO();
        shareDTO.setUserId(share.getUsers().getIdUser());
        shareDTO.setPlanningId(share.getPlanning().getIdPlanning());
        shareDTO.setReadOnly(share.getIsReadOnly());

        return shareDTO;


    }

    public void deleteShare(ShareDTO shareDTO, Users users) throws PlanningNotFoundException, UserNotOwnerException, ShareNotFoundException, UserNotFoundException {
        System.out.println("Delete a Share for User with ID " + shareDTO.getUserId() + " associated with the Planning ID " + users.getPlanning().getIdPlanning() + "...");
        Optional<Planning> optPlanning = planningRepository.findById(shareDTO.getPlanningId());
        if (optPlanning.isEmpty()) {
            throw new PlanningNotFoundException(shareDTO.getPlanningId());
        }
        Planning planning = optPlanning.get();
       /* if (!Objects.equals(planning.getUser().getEmail(), users.getEmail())) {
            throw new UserNotOwnerException(users.getUsername(), planning.getNamePlanning());
        }*/

        Optional<Users> optUsersToShareWith = userRepository.findById(shareDTO.getUserId());
        if (optUsersToShareWith.isEmpty()) {
            throw new UserNotFoundException(shareDTO.getUserId());
        }

        Users usersToShareWith = optUsersToShareWith.get();

        Share share = getShareByPlanningAndUser(planning, usersToShareWith);

        usersToShareWith.removeShare(share);
        planning.removeShare(share);
        delete(share);
        userRepository.save(usersToShareWith);
        planningRepository.save(planning);

        System.out.println("Share has been deleted");

    }

    public ShareDTO updateShare(ShareDTO shareDTO, Users users) throws PlanningNotFoundException, UserNotOwnerException, ShareNotFoundException, UserNotFoundException {
        System.out.println("Updating a Share for User with ID " + shareDTO.getUserId() + " associated with the Planning " + users.getPlanning().getIdPlanning() + "...");
        Optional<Planning> optPlanning = planningRepository.findById(shareDTO.getPlanningId());
        if (optPlanning.isEmpty()) {
            throw new PlanningNotFoundException(shareDTO.getPlanningId());
        }
        Planning planning = optPlanning.get();
        if (!Objects.equals(planning.getUser().getEmail(), users.getEmail())) {
            throw new UserNotOwnerException(users.getUsername(), planning.getNamePlanning());
        }

        Optional<Users> optUsersToShareWith = userRepository.findById(shareDTO.getUserId());
        if (optUsersToShareWith.isEmpty()) {
            throw new UserNotFoundException(shareDTO.getUserId());
        }

        Users usersToShareWith = optUsersToShareWith.get();

        Share share = getShareByPlanningAndUser(planning, usersToShareWith);
        share.setIsReadOnly(!share.getIsReadOnly());
        save(share);
        System.out.println("Share has been updated");

        return shareDTO;


    }


}
