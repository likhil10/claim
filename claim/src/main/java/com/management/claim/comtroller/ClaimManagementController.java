package com.management.claim.comtroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.claim.model.Claim;
import com.management.claim.service.ClaimManagementService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ClaimManagementController {
	ClaimManagementService costumerService;
	
//	public public ClaimManagementController() {
//		super();
//		
//	}
	
	@PostMapping("/claim")
	public ResponseEntity<Claim> createClaim(@RequestBody Claim claim) {
		try {
//			Claim claim = 
//					claimRepository.save(new Claim
//					(claimModel.getClaimType(), claimModel.getFromDate(), claimModel.getToDate(), 
//							claimModel.getAmount(), claimModel.getComment(), claimModel.getPurpose()));
			return new ResponseEntity<>(costumerService.saveClaim(claim), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
