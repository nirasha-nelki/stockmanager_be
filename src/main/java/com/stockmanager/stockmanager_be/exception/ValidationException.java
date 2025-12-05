package com.stockmanager.stockmanager_be.exception;

import com.stockmanager.stockmanager_be.constant.MessageConstant;
import com.stockmanager.stockmanager_be.util.MessageUtil;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
public class ValidationException extends RuntimeException {

    private final MessageConstant messageKey;
    private static MessageUtil messageUtil;

    @Component
    public static class MessageUtilInjector implements org.springframework.context.ApplicationContextAware {
        @Override
        public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) {
            messageUtil = applicationContext.getBean(MessageUtil.class);
        }
    }

    public ValidationException(MessageConstant messageKey) {
        super(messageUtil.getMessage(messageKey.getMessageKey()));
        this.messageKey = messageKey;
    }

    public ValidationException(MessageConstant messageKey, Object[] args) {
        super(messageUtil.getMessage(messageKey.getMessageKey(), args));
        this.messageKey = messageKey;
    }
}
