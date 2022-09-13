// SPDX-License-Identifier: GPL-3.0
pragma solidity >=0.7.0 <0.9.0;

import "./Elena.sol";

contract Donation{
    // ERC20 토큰의 주소
    address elenaToken;
    // 엘레나 토큰 컨트랙트
    IERC20 private _currencyContract;

    // 코인을 발급한 컨트랙트 주소가 필요하다.
    constructor(address _elenaToken){
        elenaToken = _elenaToken;
        _currencyContract = IERC20(elenaToken);
    }

    // 후원할 코인의 크기를 인자로 받는다
    function funding(uint sendMoney) public payable {
        _currencyContract.transferFrom(msg.sender, address(this), sendMoney*10**18);
    }

    // 현재 총 기부금을 wei단위로 반환
    function currentBalance() public view returns(uint256){
        return _currencyContract.balanceOf(address(this));
    }

    // 아이 계좌의 잔액을 인자로 받는다
    function takeMoney(uint childBalance) public payable  {
        // 10만 - 아이 계좌 잔액 만큼 기부금에서 아이계좌로 보낸다.
        uint validMoney = 100000 - childBalance;
        _currencyContract.transfer(msg.sender,validMoney*10**18);

    }



}