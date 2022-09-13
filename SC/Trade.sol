// SPDX-License-Identifier: GPL-3.0
pragma solidity >=0.7.0 <0.9.0;

import "./Elena.sol";

contract Trade {
    address public seller;
    uint public price;
    string public name;
    // ERC20 토큰의 주소
    address elenaToken;
    // 엘레나 토큰 컨트랙트
    IERC20 private _currencyContract;
    // 물품 주문 트랜잭션=> 주문금액 매핑
    mapping(string => uint) public orderSheet;


    // 판매자와 물건 가격 계약생성시 정의
    constructor (string memory _name ,uint _price, address _elenaToken) {
        seller = msg.sender;
        name = _name;
        price = _price;
        elenaToken = _elenaToken;
        _currencyContract = IERC20(elenaToken);
    }

    // 1. 물건 주문시 미리 컨트랙트로 토큰을 보냄
    // 현재 컨트랙트에 토큰이 쌓임
    function buying(uint paymentAmount) public payable {
        _currencyContract.transferFrom(msg.sender, address(this), paymentAmount*10**18);
    }

    // 2. 주문하면서 생긴 트랜잭션과 주문액 매핑하기
    function addOrderSheet(string memory orderTransaction, uint amount ) public {
        orderSheet[orderTransaction] = amount;
    }

    // 3. 상품인수시 orderSheet에서 방금 보낸 트랜잭션으로 매핑된 크기만큼 컨트랙트에서 판매자에게 돈 송금
    function paying(string memory orderTransaction) public payable {

        uint paymentAmount = orderSheet[orderTransaction];

        _currencyContract.transfer(seller,paymentAmount*10**18);
    }




}