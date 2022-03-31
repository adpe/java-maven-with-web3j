// SPDX-License-Identifier: UNLICENSED

pragma solidity ^0.7.1;

contract Greeter {

    string greeting;

    constructor() {
        greeting = "Hello World";
    }

    function store(string memory s) public {
        greeting = s;
    }

    function greet() public view returns (string memory) {
        return greeting;
    }
}