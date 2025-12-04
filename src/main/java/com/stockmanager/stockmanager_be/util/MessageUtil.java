package com.stockmanager.stockmanager_be.util;

import com.stockmanager.stockmanager_be.constant.MessageConstant;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageUtil {

    private final MessageSource messageSource;

    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }

    public String getMessage(MessageConstant messageConstant) {
        return messageSource.getMessage(messageConstant.getMessageKey(), null, Locale.getDefault());
    }

    public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, Locale.getDefault());
    }

    public String getMessage(MessageConstant messageConstant, Object[] args) {
        return messageSource.getMessage(messageConstant.getMessageKey(), args, Locale.getDefault());
    }

    public String getMessage(String code, Object[] args, Locale locale) {
        return messageSource.getMessage(code, args, locale);
    }

    public String getMessage(MessageConstant messageConstant, Object[] args, Locale locale) {
        return messageSource.getMessage(messageConstant.getMessageKey(), args, locale);
    }
}
