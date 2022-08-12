package com.management.claim.service;

import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;

import com.management.claim.controller.ClaimManagementController;
import com.management.claim.model.Claim;
import com.management.claim.repository.ClaimManagementRepository;
import org.apache.logging.log4j.*;

import java.util.List;
import java.util.Optional;

@Service
public class ClaimManagementServiceImpl implements ClaimManagementService{
	private final ClaimManagementRepository claimRepository;

	Logger logger = LogManager.getLogger(ClaimManagementController.class.getName());


	public ClaimManagementServiceImpl(ClaimManagementRepository claimRepository) {
		this.claimRepository = claimRepository;
	}

	@Override
	public Claim saveClaim(Claim claim) {
		logger.debug("saveClaim(Service Impl) - Entry");
		claimRepository.save(claim);
		logger.info("Claim Saved.");
		return claim;
	}

	@Override
	public List<Claim> getAllClaims() {
		logger.debug("updateClaim(Service Impl) - Entry");
		return claimRepository.findAll();
	}

	@Override
	public Optional<Claim> getClaim(Long id) {
		logger.debug("getClaim(Service Impl) - Entry");
		return claimRepository.findById(id);
	}
}
