package me.adrianperez.Greeter;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Web3jWallets {

    public static void main(String[] args) throws IOException, InvalidAlgorithmParameterException, CipherException, NoSuchAlgorithmException, NoSuchProviderException {

        /* Create new local stored wallet. */
        var password = "secret";
        var fileName = WalletUtils.generateNewWalletFile(password, new File("."));

        var createdCredentials = WalletUtils.loadCredentials(password, new File(fileName));
        printCredentials(createdCredentials, "Created");

        /* It's a local Ganache address, don't be surprised that the private key is exposed. 😉 */
        var privateKey = "81eb749b381a334ddd24f53b38ee85c072ef27718e03aa42366c2c3780e4232b";

        var importedCredentials = Credentials.create(privateKey);
        printCredentials(importedCredentials, "Imported");
    }

    private static void printCredentials(Credentials credentials, String credentialsType) {
        /* Base64 hexadecimal */
        var numeralSystem = 16;
        var keyPair = credentials.getEcKeyPair();

        System.out.println(credentialsType + " address: " + credentials.getAddress());
        System.out.println("- Public key: " + keyPair.getPublicKey().toString(numeralSystem));
        System.out.println("- Private key: " + keyPair.getPrivateKey().toString(numeralSystem));
    }
}
