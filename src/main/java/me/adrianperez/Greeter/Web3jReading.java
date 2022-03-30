package me.adrianperez.Greeter;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

public class Web3jReading {

    public static void main(String[] args) throws IOException {

        var web3 = Web3j.build(new HttpService("http://localhost:7545"));

        var version = web3.web3ClientVersion().send();
        System.out.println(version.getWeb3ClientVersion());
    }
}
