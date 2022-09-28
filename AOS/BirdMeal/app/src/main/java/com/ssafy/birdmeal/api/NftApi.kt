package com.ssafy.birdmeal.api

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.ChildPhotoCardDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NftApi {

    // [아이들] NFT 이미지 소스 저장
    @POST("nft/image")
    suspend fun insertPhotoCard(@Body childNft: ChildPhotoCardDto): BaseResponse<Any>

    // [아이들] 내가 만든 포토카드 불러오기
    @GET("nft/user/child/{user-seq}")
    suspend fun getMyPhotoCard(@Path("user-seq") userSeq: Int): BaseResponse<List<ChildPhotoCardDto>?>

    // [기부자] 포토카드를 Nft로 민팅요청
    @PUT("nft/mint-state/{user-seq}")
    suspend fun createNftFromPhotoCard(@Path("user-seq") userSeq: Int): BaseResponse<Any>

    // [기부자] 랜덤 포토카드 뽑기
    @GET("nft/{user-seq}")
    suspend fun getPhotoCardUrl(@Path("user-seq") userSeq: Int): BaseResponse<Any>
}