package org.ajakz.elker.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ajakz.elker.dao.Bugle;
import org.ajakz.elker.dao.BugleRepository;
import org.ajakz.elker.dao.Elker;
import org.ajakz.elker.dao.ElkerRepository;
import org.ajakz.elker.dto.BugleData;
import org.ajakz.elker.dto.ElkerBondData;
import org.ajakz.elker.dto.ElkerData;
import org.ajakz.elker.exception.ElkerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ElkBuglerService {

    private BugleRepository bugleRepository;

    private ElkerRepository elkerRepository;

    @Autowired
    public ElkBuglerService(BugleRepository bugleRepository, ElkerRepository elkerRepository) {
        this.bugleRepository = bugleRepository;
        this.elkerRepository = elkerRepository;
    }

    public List<Bugle> getUserTimeline(UUID userId) {
        List<Bugle> bugles = new ArrayList<>();

        var optionalRequestor = elkerRepository.findById(userId);
        if(optionalRequestor.isPresent()) {
            var requestor = optionalRequestor.get();
            var followingIds = requestor.getFollowingIds();

            Set<UUID> uuidsOnTimeline = new HashSet<>();
            uuidsOnTimeline.add(userId); // User's posts should show up on their own timeline
            uuidsOnTimeline.addAll(followingIds);

            bugles.addAll(bugleRepository.findByUserIdIn(uuidsOnTimeline));
        }
        return bugles;
    }

    public List<ElkerData> getElkersRelativeTo(UUID userId) {
        var optionalRequestor = elkerRepository.findById(userId);
        if(optionalRequestor.isPresent()) {
            var requestor = optionalRequestor.get();
            var allElkers = elkerRepository.findAll();
            var followingIds = requestor.getFollowingIds();
            return allElkers.stream()
                    .map(e -> ElkerData.builder()
                            .elkerId(e.getId())
                            .username(e.getUsername())
                            .following(followingIds.contains(e.getId()))
                            .build())
                    .filter(e -> e.getElkerId() != userId) // Remove self from list
                    .collect(Collectors.toList());
        }
        else {
            return null;
        }
    }

    @Transactional
    public Bugle publishBugle(BugleData post) {
        var poster = elkerRepository.findById(post.getElkerId());
        if(poster.isPresent()) {
            var bugle = Bugle.builder()
                    .bugleId(UUID.randomUUID())
                    .elker(poster.get())
                    .bugle(post.getContent())
                    .build();
            return bugleRepository.save(bugle);
        }
        else {
            return null;
        }
    }

    @Transactional
    public void follow(ElkerBondData bond) throws ElkerNotFoundException {
        var optionalRequestor = elkerRepository.findById(bond.getMyId());
        var optionalToFollow = elkerRepository.findById(bond.getTheirId());

        var requestorExists = optionalRequestor.isPresent();
        var toFollowExists = optionalToFollow.isPresent();

        if(requestorExists && toFollowExists) {
            var requestor = optionalRequestor.get();
            var toFollow = optionalToFollow.get();
            requestor.addFollowing(toFollow);
            elkerRepository.save(requestor);
        }
        else {
            throw new ElkerNotFoundException();
        }
    }

    @Transactional
    public void unfollow(ElkerBondData bond) throws ElkerNotFoundException {
        var optionalRequestor = elkerRepository.findById(bond.getMyId());
        var optionalToUnfollow = elkerRepository.findById(bond.getTheirId());

        var requestorExists = optionalRequestor.isPresent();
        var toUnfollowExists = optionalToUnfollow.isPresent();

        if(requestorExists && toUnfollowExists) {
            var requestor = optionalRequestor.get();
            var toUnfollow = optionalToUnfollow.get();
            requestor.removeFollowing(toUnfollow);
            elkerRepository.save(requestor);
        }
        else {
            throw new ElkerNotFoundException();
        }
    }
}
