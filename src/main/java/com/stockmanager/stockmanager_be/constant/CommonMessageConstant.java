package com.stockmanager.stockmanager_be.constant;

import lombok.Getter;

@Getter
public enum CommonMessageConstant implements MessageConstant{

    COMMON_ERROR_CATEGORY_NOT_FOUND("common.error.category.not.found"),
    COMMON_ERROR_CATEGORY_ALREADY_EXISTS("common.error.category.already.exists");



    private final String messageKey;
    CommonMessageConstant(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
