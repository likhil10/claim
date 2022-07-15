package com.management.claim.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.claim.model.Claim;
import com.management.claim.repository.ClaimManagementRepository;

@Service
public class ClaimManagementServiceImpl implements ClaimManagementService{
	ClaimManagementRepository claimRepository;
	
	@Override
	public Claim saveClaim(Claim claim) {
		return claimRepository.save(claim);
	}
}
