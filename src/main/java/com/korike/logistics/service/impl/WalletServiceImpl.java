package com.korike.logistics.service.impl;

import java.sql.Timestamp;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.korike.logistics.entity.Consumer;
import com.korike.logistics.entity.User;
import com.korike.logistics.entity.Wallet;
import com.korike.logistics.exception.ApiErrorCode;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.model.wallet.WalletAddDto;
import com.korike.logistics.repository.WalletRepository;
import com.korike.logistics.service.WalletService;
import com.korike.logistics.util.CommonUtils;

@Service
public class WalletServiceImpl implements WalletService{

	private static Logger logger = Logger.getLogger(WalletServiceImpl.class);
	
	@Autowired
	WalletRepository walletRepo;
	
	@Override
	public void createUserWallet(WalletAddDto walletDto, User selUser) {
		
		Wallet newWallet = new Wallet();
		newWallet.setWalletId(UUID.randomUUID().toString());
		newWallet.setWalletBalance(walletDto.getWalletBalance());
		//newWallet.setWalletCurrencyBalance(walletDto.getWalletCurrencyBalance());
		newWallet.setUser(selUser);
		newWallet.setWalletStatus("NEW"); // need clarification
		newWallet.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		newWallet.setCreatedBy(selUser.getUserId());
		
		try {
			walletRepo.save(newWallet);
		}catch(Exception exc) {
			
			logger.error("Exception occured in createUserWallet() method." + CommonUtils.printException(exc));
			throw new KorikeException("Exception occured in createUserWallet() method while creating "+ApiErrorCode.INTERNAL_SERVER_ERROR);
		}
		
	}

}
