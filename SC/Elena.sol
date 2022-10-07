pragma solidity ^0.8.0;

import "https://github.com/OpenZeppelin/openzeppelin-contracts/blob/master/contracts/token/ERC20/ERC20.sol";

contract Elena is ERC20{
    constructor(string memory name, string memory symbol) ERC20(name,symbol){
        // mint 1000 token
        _mint(msg.sender, 100000000000*10**18);
    }
}
