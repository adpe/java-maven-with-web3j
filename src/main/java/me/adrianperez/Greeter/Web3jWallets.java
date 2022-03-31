package me.adrianperez.Greeter;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class Web3jWallets {

    public static void main(String[] args) throws Exception {

        var web3 = Web3j.build(new HttpService("http://localhost:7545"));

        /* Create new local stored wallet. */
        var password = "secret";
        var fileName = WalletUtils.generateNewWalletFile(password, new File("."));

        var createdCredentials = WalletUtils.loadCredentials(password, new File(fileName));
        printCredentials(createdCredentials, "Created");

        /* It's a local Ganache address, don't be surprised that the private key is exposed. 😉 */
        var privateKey1 = "81eb749b381a334ddd24f53b38ee85c072ef27718e03aa42366c2c3780e4232b";

        var importedCredentials1 = Credentials.create(privateKey1);
        printCredentials(importedCredentials1, "Imported");

        /* It's a local Ganache address, don't be surprised that the private key is exposed. 😉 */
        var privateKey2 = "b451897f3a0f55f72634283ae9f6399cda955bdc8a9cb3066fa54662d7ab5d7e";

        var importedCredentials2 = Credentials.create(privateKey2);
        printCredentials(importedCredentials1, "Imported");

        /* Transfer balance from address 1 to address 2. */
        printBalance(web3, importedCredentials1, importedCredentials2);

        var receipt = Transfer.sendFunds(web3, importedCredentials1, importedCredentials2.getAddress(), BigDecimal.TEN, Convert.Unit.ETHER).send();
        System.out.println("Recent transaction number: " + receipt.getBlockNumber());

        printBalance(web3, importedCredentials1, importedCredentials2);
    }

    private static void printBalance(Web3j web3, Credentials credentials1, Credentials credentials2) throws IOException {
        var balance = web3.ethGetBalance(credentials1.getAddress(), DefaultBlockParameterName.LATEST).send();
        System.out.println("The balance of sender wallet is: " + Convert.fromWei(String.valueOf(balance.getBalance()), Convert.Unit.ETHER));

        balance = web3.ethGetBalance(credentials2.getAddress(), DefaultBlockParameterName.LATEST).send();
        System.out.println("The balance of receipt wallet is: " + Convert.fromWei(String.valueOf(balance.getBalance()), Convert.Unit.ETHER));
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
