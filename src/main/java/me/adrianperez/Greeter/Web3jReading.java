package me.adrianperez.Greeter;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.IntStream;

public class Web3jReading {
    static final String WEB3_PROVIDER = "http://localhost:7545";

    public static void main(String[] args) throws IOException {

        var web3 = Web3j.build(new HttpService(WEB3_PROVIDER));

        var version = web3.web3ClientVersion().send();
        System.out.println("Blockchain: " + version.getWeb3ClientVersion());

        var blockNumber = web3.ethBlockNumber().send().getBlockNumber();
        System.out.println("Number of blocks: " + (blockNumber.intValue() + 1));

        var blockTransactions = totalOfTransactions(web3, blockNumber);
        System.out.println("Number of transactions: " + blockTransactions);
    }

    private static int totalOfTransactions(Web3j web3, BigInteger blockNumber) {
        return IntStream.range(0, blockNumber.intValue() + 1)
                .mapToObj(i -> {
                    try {
                        var block = web3.ethGetBlockByNumber(new DefaultBlockParameterNumber(i), false)
                                .send();
                        return Optional.of(block);
                    } catch (IOException e) {
                        return Optional.<EthBlock>empty();
                    }
                })
                .flatMap(Optional::stream)
                .mapToInt(block -> block.getBlock().getTransactions().size())
                .sum();
    }
}
