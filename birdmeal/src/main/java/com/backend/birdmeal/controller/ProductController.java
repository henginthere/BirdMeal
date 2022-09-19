package com.backend.birdmeal.controller;

import com.backend.birdmeal.dto.CategoryDto;
import com.backend.birdmeal.dto.ProductDto;
import com.backend.birdmeal.service.CategoryService;
import com.backend.birdmeal.util.ResponseFrame;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api("ProductController")
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final CategoryService categoryService;

    /**
     * 카테고리 목록 가져오기
     *
     * @param
     * @return List
     */

    @ApiOperation(value="카테고리 목록 가져오기",response = List.class)
    @GetMapping("/category-list")
    public ResponseEntity<?> getCategoryList(){
        List<CategoryDto> categoryList = categoryService.getCategoryList();

        ResponseFrame<List<CategoryDto>> res = ResponseFrame.of(categoryList,"카테고리 목록 요청을 성공했습니다.");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    /**
     * 카테고리별 상품 목록 보기
     *
     * @param
     * @return List
     */

    @ApiOperation(value="카테고리별 상품 목록 보기",response = List.class)
    @GetMapping("/{category-seq}/list")
    public ResponseEntity<?> getCategoryProducts(@PathVariable("category-seq") Long categorySeq){
        List<ProductDto> productList = categoryService.getProductList(categorySeq);

        ResponseFrame<List<ProductDto>> res = ResponseFrame.of(productList,"카테고리별 상품 목록 요청을 성공했습니다.");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
