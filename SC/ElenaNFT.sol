// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "@openzeppelin/contracts/token/ERC721/extensions/ERC721URIStorage.sol";
import "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "@openzeppelin/contracts/utils/Counters.sol";

contract ElenaNFT is ERC721URIStorage{
    using Counters for Counters.Counter;
    Counters.Counter private _tokenIds;

    mapping (address => uint256[]) public check; 

    constructor() public ERC721("ElenaNFT", "ENT"){}

    // 보낸 이미지 URI를 담아서 NFT민팅
    // 이후 토큰ID를 계정주소와 매핑해둠
    function mintNFT(string memory tokenURI) public{
        _tokenIds.increment();

        uint256 newItemId = _tokenIds.current();
        _mint(msg.sender, newItemId);
        _setTokenURI(newItemId, tokenURI);
        check[msg.sender].push(newItemId);
    }

    // 내가 가진 토큰ID 목록 반환
    function getMyToken() public view returns(uint256[] memory){
        return check[msg.sender];
    }
}
