package org.wallet.commission;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MdrService {

	@Autowired
	MdrRepository mdrRepository;
	
	public Long nbrInscris(BigInteger agentId, String profile){

		return mdrRepository.countProfileByAgentId(agentId, profile);
	}
	
	public Long nbrParrainer(BigInteger parrainId, String profile){

		return mdrRepository.countProfileByParrainId(parrainId, profile);
	}
	
	public Long nbrByAgregateur(BigInteger agregateurId, String profile){

		return mdrRepository.countProfileByParrainId(agregateurId, profile);
	}
}
