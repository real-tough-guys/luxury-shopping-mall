package com.skhu.luxuryshop.product.exception;

public class ProductFindByIdException extends IllegalArgumentException {
    public ProductFindByIdException(String s) {
        super("상품 ID가 존재하지 않습니다");
    }
}
