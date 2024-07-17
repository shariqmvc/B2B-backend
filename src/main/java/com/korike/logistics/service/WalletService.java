package com.korike.logistics.service;

import org.springframework.stereotype.Service;

import com.korike.logistics.entity.Consumer;
import com.korike.logistics.entity.User;
import com.korike.logistics.model.wallet.WalletAddDto;

@Service
public interface WalletService {

	public void createUserWallet(WalletAddDto walletDto, User selUser);
}
