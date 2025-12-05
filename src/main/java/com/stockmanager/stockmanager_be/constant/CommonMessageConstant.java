package com.stockmanager.stockmanager_be.constant;

import lombok.Getter;

@Getter
public enum CommonMessageConstant implements MessageConstant{

    COMMON_ERROR_CATEGORY_NOT_FOUND("common.error.category.not.found"),
    COMMON_ERROR_CATEGORY_ALREADY_EXISTS("common.error.category.already.exists"),
    COMMON_SUCCESS_CATEGORY_CREATED("common.success.category.created"),
    COMMON_SUCCESS_CATEGORY_UPDATED("common.success.category.updated"),
    COMMON_SUCCESS_CATEGORY_DELETED("common.success.category.deleted"),
    COMMON_SUCCESS_DATA_RETRIEVED("common.success.data.retrieved"),
    COMMON_SUCCESS_LIST_RETRIEVED("common.success.list.retrieved"),
    COMMON_SUCCESS_PRODUCT_CREATED("common.success.product.created"),
    COMMON_SUCCESS_PRODUCTS_SAVED("common.success.products.saved"),
    COMMON_SUCCESS_PRODUCT_UPDATED("common.success.product.updated"),
    COMMON_SUCCESS_PRODUCT_DELETED("common.success.product.deleted"),
    COMMON_ERROR_PRODUCT_NOT_FOUND("common.error.product.not.found"),
    COMMON_ERROR_PRODUCT_ALREADY_EXISTS("common.error.product.alreadyExists"),
    COMMON_ERROR_USER_ALREADY_EXISTS("common.error.user.already.exists"),
    COMMON_ERROR_USER_NOT_FOUND("common.error.user.not.found"),
    COMMON_SUCCESS_USER_REGISTERED("common.success.user.created"),
    COMMON_SUCCESS_USER_AUTHENTICATED("common.success.user.authenticated");



    private final String messageKey;
    CommonMessageConstant(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
