package com.management.claim.comtroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

//	@GetMapping()
//	public ModelAndView getCurrentDateAndTime() {
//		logger.info("inside");
//		ModelAndView mav = new ModelAndView("index");
//		mav.addObject("currentDateAndTime");
//		return mav;
//	}

	@GetMapping("/claim")
	public ResponseEntity<List<Claim>> getAll(){
		try {
			logger.info("Trying to get all the claims...");
			return new ResponseEntity<>(claimManagementService.getAllClaims(), HttpStatus.CREATED);
		} catch (Exception e) {
			logger.info(""+e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping(value = "/claim", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Claim> createClaim(@RequestBody Claim claim) {
		try {
			logger.info("Trying to save the claim...");
			return new ResponseEntity<>(claimManagementService.saveClaim(claim), HttpStatus.CREATED);
		} catch (Exception e) {
			logger.info(""+e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
