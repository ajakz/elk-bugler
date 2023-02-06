package org.ajakz.elker.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.ajakz.elker.dao.Bugle;
import org.ajakz.elker.dto.BugleData;
import org.ajakz.elker.dto.ElkerBondData;
import org.ajakz.elker.dto.ElkerData;
import org.ajakz.elker.exception.ElkerNotFoundException;
import org.ajakz.elker.service.ElkBuglerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class BugleController {

    ElkBuglerService elkBuglerService;

    @Autowired
    public BugleController(ElkBuglerService elkBuglerService) {
        this.elkBuglerService = elkBuglerService;
    }

    @GetMapping("/timeline")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found one or more bugles on user's timeline."),
            @ApiResponse(responseCode = "204", description = "User has an empty timeline.")})
    public ResponseEntity<List<BugleData>> getUserTimeline(@RequestParam(required = true) UUID userId) {
        List<Bugle> bugles = elkBuglerService.getUserTimeline(userId);

        if(!bugles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(BugleData.fromBugleEntityList(bugles));
        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @GetMapping("/elkers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Elkers found"),
            @ApiResponse(responseCode = "404", description = "UserId does not exist.")})
    public ResponseEntity<List<ElkerData>> getAllElkers(@RequestParam(required = true) UUID userId) {
        List<ElkerData> elkers = elkBuglerService.getElkersRelativeTo(userId);

        if(elkers != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(elkers);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/bugle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bugle successfully published."),
            @ApiResponse(responseCode = "400", description = "User associated with bugle does not exist.")})
    public ResponseEntity<Void> publishBugle(@RequestBody BugleData post) {
        var newBugle = elkBuglerService.publishBugle(post);

        if(newBugle != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/follow")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Elker bond successfully created."),
            @ApiResponse(responseCode = "404", description = "One or both users in bond not found.")})
    public ResponseEntity<Void> followElker(@RequestBody ElkerBondData bond) throws ElkerNotFoundException {
        elkBuglerService.follow(bond);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }


    @PostMapping("/unfollow")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Elker bond successfully deleted."),
            @ApiResponse(responseCode = "404", description = "One or both users in bond not found.")})
    public ResponseEntity<Void> unfollowElker(@RequestBody ElkerBondData bond) throws ElkerNotFoundException {
        elkBuglerService.unfollow(bond);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
