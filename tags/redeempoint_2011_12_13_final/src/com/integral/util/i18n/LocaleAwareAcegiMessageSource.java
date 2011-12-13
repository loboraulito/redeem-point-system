package com.integral.util.i18n;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class LocaleAwareAcegiMessageSource extends
        ReloadableResourceBundleMessageSource {
    public LocaleAwareAcegiMessageSource() {
        setBasename("org.springframework.security.messages");
    }
    public static MessageSourceAccessor getAccessor() {
        return new MessageSourceAccessor(new LocaleAwareAcegiMessageSource());
    }
}
