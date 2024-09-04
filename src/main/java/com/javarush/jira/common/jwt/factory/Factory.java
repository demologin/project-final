package com.javarush.jira.common.jwt.factory;

import java.util.function.Function;

public interface Factory<T, V> extends Function<T, V> {
}
