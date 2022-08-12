package com.management.claim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.*;

import com.management.claim.model.Claim;
import com.management.claim.service.ClaimManagementService;

//import org.springframework.web.servlet.ModelAndView;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ClaimManagementController {
    @Autowired
    ClaimManagementService claimManagementService;

//    Logger logger = Logger.getLogger(ClaimManagementController.class.getName());
    Logger logger = LogManager.getLogger(ClaimManagementController.class.getName());

    @GetMapping("/claim")
    public ResponseEntity<List<Claim>> getAll() {
        logger.debug("getAll - Entry");
        try {
            logger.info("Trying to get all the claims...");
            return new ResponseEntity<>(claimManagementService.getAllClaims(), HttpStatus.FOUND);
        } catch (Exception e) {
            logger.error("getAll - could not get all the claims - " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/claim/{id}")
    public ResponseEntity<Claim> getClaim(@PathVariable(value = "id") Long id) {
        logger.debug("getClaim - Entry");
        logger.info("Trying to get the claim...");
        Optional<Claim> claimData = claimManagementService.getClaim(id);
        return claimData.map(claim -> new ResponseEntity<>(claim, HttpStatus.OK)).orElseGet(() -> {
            logger.error("getClaim - error getting claim with specified ID");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @PutMapping("/updateClaim/{id}")
    public ResponseEntity<Claim> updateClaim(@PathVariable("id") long id, @RequestBody Claim claim) {
        logger.debug("updateClaim - Entry");
        logger.warn("The claim will only update if the username is correct...");
        Optional<Claim> claimData = claimManagementService.getClaim(id);
        if (claimData.isPresent()) {
			Claim claim1 = claimData.get();
            if (!claim.getUsername().isEmpty() && claim1.getUsername().equals(claim.getUsername())) {
				claim1.setStatus(claim.getStatus());
				claim1.setAmount(claim.getAmount());
				claim1.setEmail(claim.getEmail());
				claim1.setFirstName(claim.getFirstName());
				claim1.setLastName(claim.getLastName());
				claim1.setPhoneNumber(claim.getPhoneNumber());
				claim1.setPolicyNumber(claim.getPolicyNumber());
				claim1.setUsername(claim.getUsername());
                logger.info("updateClaim - verified username");
                return new ResponseEntity<>(claimManagementService.saveClaim(claim1), HttpStatus.OK);
			}
			else {
                logger.error("updateClaim - Username did not match");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
        } else {
            logger.error("updateClaim - could not find the claimData for the given ID");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/claim", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Claim> createClaim(@RequestBody Claim claim) {
        logger.debug("createClaim - Entry");
        try {
            logger.info("Trying to save the claim...");
            return new ResponseEntity<>(claimManagementService.saveClaim(claim), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("createClaim - error saving claim - " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
