package com.ssafy.birdmeal.api

import com.ssafy.birdmeal.base.BaseResponse
import com.ssafy.birdmeal.model.dto.ChildPhotoCardDto
import com.ssafy.birdmeal.model.response.NftImgResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface NftApi {

    // [아이들] NFT 이미지 소스 저장
    @POST("nft/image")
    suspend fun insertPhotoCard(@Body childNft: ChildPhotoCardDto): BaseResponse<Any>

    // [아이들] 내가 만든 포토카드 불러오기
    @GET("nft/user/child/{user-seq}")
    suspend fun getMyPhotoCard(@Path("user-seq") userSeq: Int): BaseResponse<List<ChildPhotoCardDto>?>

    // [기부자] 민팅상태 변경
    @PUT("nft/mint-state/{user-seq}")
    suspend fun changeMintState(@Path("user-seq") userSeq: Int): BaseResponse<Any>

    // [기부자] 랜덤 포토카드 뽑기
    @GET("nft/{user-seq}")
    suspend fun getPhotoCardUrl(@Path("user-seq") userSeq: Int): BaseResponse<NftImgResponse>

    // 파일 업로드
    @Multipart
    @POST("file")
    suspend fun saveFile(@Part file: MultipartBody.Part): BaseResponse<String>
}