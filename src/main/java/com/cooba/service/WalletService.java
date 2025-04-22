package com.cooba.service;

import com.cooba.entity.Wallet;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WalletService {
    private Wallet wallet = null;


    // 建立錢包
    public Wallet newWallet(int balance) {
        this.wallet = new Wallet();
        wallet.setBalance(balance);
        wallet.setCreatedTime(LocalDateTime.now());
        wallet.setUpdatedTime(LocalDateTime.now());
        return this.wallet;
    }

    // 取得錢包
    public Wallet getWallet() {
        if (this.wallet == null) {
            return newWallet(1000);
        }
        return this.wallet;
    }

    // 設定金額（完全取代）
    public Wallet updateAmount(int amount) {
        if (this.wallet == null) {
            return null;
        }
        this.wallet.setBalance(amount);
        this.wallet.setUpdatedTime(LocalDateTime.now());
        return this.wallet;
    }

    // 增加金額
    public Wallet add(int amount) {
        if (this.wallet == null) {
            return null;
        }
        this.wallet.setBalance(this.wallet.getBalance() + amount);
        this.wallet.setUpdatedTime(LocalDateTime.now());
        return this.wallet;
    }

    // 扣除金額
    public Wallet subtract(int amount) throws Exception {
        Wallet wallet = getWallet();
        if (wallet.getBalance() < amount) {
            throw new Exception("insufficient balance");
        }
        wallet.setBalance(wallet.getBalance() - amount);
        wallet.setUpdatedTime(LocalDateTime.now());
        return wallet;
    }
}
