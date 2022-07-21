package com.management.claim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.*;
import com.management.claim.model.Claim;
import com.management.claim.service.ClaimManagementService;

//import org.springframework.web.servlet.ModelAndView;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ClaimManagementController {
	@Autowired
	ClaimManagementService claimManagementService;

	Logger logger = Logger.getLogger(ClaimManagementController.class.getName());

	@GetMapping("/claim")
	public ResponseEntity<List<Claim>> getAll() {
		try {
			logger.info("Trying to get all the claims...");
			return new ResponseEntity<>(claimManagementService.getAllClaims(), HttpStatus.FOUND);
		} catch (Exception e) {
			logger.info("" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/claim/{id}")
	public ResponseEntity<Claim> getClaim(@PathVariable(value = "id") Long id) {
		logger.info("Trying to get the claim...");
		Optional<Claim> claimData = claimManagementService.getClaim(id);
		if (claimData.isPresent()) {
			return new ResponseEntity<>(claimData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/updateClaim/{id}")
	  public ResponseEntity<Claim> updateClaim(@PathVariable("id") long id, @RequestBody Claim claim) {
	    Optional<Claim> claimData = claimManagementService.getClaim(id);
	    if (claimData.isPresent()) {
	      Claim claim1 = claimData.get();
	      claim1.setStatus(claim.getStatus());
	      claim1.setAmount(claim.getAmount());
	      claim1.setEmail(claim.getEmail());
	      claim1.setFirstName(claim.getFirstName());
	      claim1.setLastName(claim.getLastName());
	      claim1.setPhoneNumber(claim.getPhoneNumber());
	      claim1.setPolicyNumber(claim.getPolicyNumber());
	      return new ResponseEntity<>(claimManagementService.saveClaim(claim1), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }

	@PostMapping(value = "/claim", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Claim> createClaim(@RequestBody Claim claim) {
		try {
			logger.info("Trying to save the claim...");
			return new ResponseEntity<>(claimManagementService.saveClaim(claim), HttpStatus.CREATED);
		} catch (Exception e) {
			logger.info("" + e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
