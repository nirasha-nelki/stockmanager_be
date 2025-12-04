package com.stockmanager.stockmanager_be.exception;

import com.stockmanager.stockmanager_be.constant.MessageConstant;
import com.stockmanager.stockmanager_be.util.MessageUtil;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Getter
public class CategoryNotFoundException extends RuntimeException {

    private final MessageConstant messageKey;

    private static MessageUtil messageUtil;

    @Component
    public static class MessageUtilInjector implements ApplicationContextAware {

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) {
            messageUtil = applicationContext.getBean(MessageUtil.class);
        }

    }

    public CategoryNotFoundException(MessageConstant messageKey) {
        super(messageUtil.getMessage(messageKey.getMessageKey()));
        this.messageKey = messageKey;
    }

    public CategoryNotFoundException(MessageConstant messageKey, Object[] args) {
        super(messageUtil.getMessage(messageKey.getMessageKey(), args));
        this.messageKey = messageKey;

    }


}
