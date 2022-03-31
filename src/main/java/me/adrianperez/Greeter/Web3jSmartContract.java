package me.adrianperez.Greeter;

import org.web3j.crypto.Credentials;
import org.web3j.model.Greeter;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

import static me.adrianperez.Greeter.Web3jReading.WEB3_PROVIDER;
import static me.adrianperez.Greeter.Web3jWallets.printCredentials;

public class Web3jSmartContract {

    public static void main(String[] args) throws Exception {

        var web3 = Web3j.build(new HttpService(WEB3_PROVIDER));

        /* It's a local Ganache address, don't be surprised that the private key is exposed. 😉 */
        var privateKey = "81eb749b381a334ddd24f53b38ee85c072ef27718e03aa42366c2c3780e4232b";

        var importedCredentials = Credentials.create(privateKey);
        printCredentials(importedCredentials, "Imported");

        var gasProvider = new StaticGasProvider(BigInteger.valueOf(1000), BigInteger.valueOf(1000000));

        var importedContractAddress = "0x240333C690Dab452896d68338209d97B5330E50E";
        var contract = Greeter.load(importedContractAddress, web3, importedCredentials, gasProvider);

        if (contract.getContractAddress() != importedContractAddress) {
            contract = Greeter.deploy(web3, importedCredentials, gasProvider).send();
            System.out.println("New smart contract deployed...");
        }

        System.out.println("Smart contract address: " + contract.getContractAddress());
        System.out.println("Greet message: " + contract.greet().send());

        contract.store("Hello Adrian!").send();
        System.out.println("Greet message: " + contract.greet().send());
    }
}