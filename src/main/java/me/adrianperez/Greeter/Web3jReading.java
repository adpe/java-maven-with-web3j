package me.adrianperez.Greeter;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

public class Web3jReading {

    public static void main(String[] args) throws IOException {

        var web3 = Web3j.build(new HttpService("http://localhost:7545"));

        var version = web3.web3ClientVersion().send();
        System.out.println("Blockchain: " + version.getWeb3ClientVersion());

        var blockNumber = web3.ethBlockNumber().send();
        System.out.println("Number of blocks: " + (blockNumber.getBlockNumber().add(BigInteger.valueOf(1))));
    }
}
