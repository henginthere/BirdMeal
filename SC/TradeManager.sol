// SPDX-License-Identifier: GPL-3.0
pragma solidity ^0.8.0;



//본체 : 
import "https://github.com/OpenZeppelin/openzeppelin-contracts/blob/master/contracts/token/ERC20/ERC20.sol";
import "./Trade.sol";

/*
* P2P 거래 정보를 관리하는 Factory Contract
* 
* @author 
* @version 0.1
* @see None
*/

contract TradeManager {
    
    // 배포 된 ERC-20 토큰 계약 주소
    address private _currencyAddress;
    // ERC20 객체화
    IERC20 private _currencyContract;


    // 판매자주소 => 판매물품 이름=> 판매물품 컨트랙트 주소 매핑
    // mapping (address => mapping (string => address)) public product1;


    struct product {
        string productName;
        address tradeAddress;
    }
    mapping (address => product[]) public products;




    constructor(address currencyAddress){
        _currencyAddress = currencyAddress;
        _currencyContract = IERC20(_currencyAddress);
    }
    
    function createTrade(string memory _name ,uint _price) public returns(address)
    {
        //거래 객체 생성
        Trade newTrade = new Trade(_name, _price, _currencyAddress, msg.sender);
        // product[msg.sender][_name] = address(newTrade);
        products[msg.sender].push(product(_name, address(newTrade)));
        return  address(newTrade);
   
    }
}