package com.javarush.jira.profile.internal;

import java.util.Locale;
import java.util.ResourceBundle;

public enum LangResourceManager {
    INSTANCE;

    public static final String BASENAME = "lang";
    private ResourceBundle resourceBundle;

    LangResourceManager() {
        setLocale(Locale.getDefault());
    }

    public void setLocale(Locale locale) {
        this.resourceBundle = ResourceBundle.getBundle(BASENAME, locale);
    }

    public String get(String key) {
        return resourceBundle.getString(key);
    }
}
