// SPDX-License-Identifier: GPL-3.0
pragma solidity >=0.7.0 <0.9.0;

import "./Elena.sol";

contract Exchange{
    // ERC20 토큰의 주소
    address elenaToken;
    // 엘레나 토큰 컨트랙트
    IERC20 private _currencyContract;

    // 코인을 발급한 컨트랙트 주소가 필요하다.
    constructor(address _elenaToken){
        elenaToken = _elenaToken;
        _currencyContract = IERC20(elenaToken);
    }

    // 환전소에 돈 넣기
    function charge(uint chargeMoney) public payable {
        _currencyContract.transferFrom(msg.sender, address(this), chargeMoney);
    }

    // 요청한 만큼 돈 발급
    function changeMoney(uint requestMoney) public payable {
        _currencyContract.transfer(msg.sender,requestMoney*10**18);
    }

}