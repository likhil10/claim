package com.management.claim.service;

import com.management.claim.comtroller.ClaimManagementController;
import org.springframework.stereotype.Service;

import com.management.claim.model.Claim;
import com.management.claim.repository.ClaimManagementRepository;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ClaimManagementServiceImpl implements ClaimManagementService{
	private ClaimManagementRepository claimRepository;

	Logger logger = Logger.getLogger(ClaimManagementController.class.getName());

	public ClaimManagementServiceImpl(ClaimManagementRepository claimRepository) {
		this.claimRepository = claimRepository;
	}

	@Override
	public Claim saveClaim(Claim claim) {
		claimRepository.save(claim);
		logger.info("Claim Saved.");
		return claim;
	}

	@Override
	public List<Claim> getAllClaims() {
		return claimRepository.findAll();
	}
}
