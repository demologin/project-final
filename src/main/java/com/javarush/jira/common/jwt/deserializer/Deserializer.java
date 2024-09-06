package com.javarush.jira.common.jwt.deserializer;

import java.util.function.Function;

public interface Deserializer<T, V> extends Function<T, V> {
}
