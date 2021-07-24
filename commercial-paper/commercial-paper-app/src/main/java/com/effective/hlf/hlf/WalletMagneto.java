/*
SPDX-License-Identifier: Apache-2.0
*/

package com.effective.hlf.hlf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.hyperledger.fabric.gateway.GatewayException;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallet.Identity;

public class WalletMagneto {

	public static void main(String[] args) {
		try {
			// A wallet stores a collection of identities
			Path walletPath = Paths.get("identity", "user", "isabella", "wallet");
			Wallet wallet = Wallet.createFileSystemWallet(walletPath);

	        // Location of credentials to be stored in the wallet
			Path credentialPath = Paths.get("dev-network", "crypto-config",
					"peerOrganizations", "org1.example.com", "users", "User1@org1.example.com", "msp");
			Path certificatePem = credentialPath.resolve(Paths.get("signcerts",
					"User1@org1.example.com-cert.pem"));
			Path privateKey = credentialPath.resolve(Paths.get("keystore",
					"de4d2ee4a3b5cf7abe6f1b7aa27d601d7df5329ab187fe6d6234459b36ab0cfe_sk"));

		       // Load credentials into wallet
			String identityLabel = "User1@org1.example.com";
			Identity identity = Identity.createIdentity("Org1MSP", Files.newBufferedReader(certificatePem), Files.newBufferedReader(privateKey));

			wallet.put(identityLabel, identity);

		} catch (IOException e) {
			System.err.println("Error adding to wallet");
			e.printStackTrace();
		}
	}

}
