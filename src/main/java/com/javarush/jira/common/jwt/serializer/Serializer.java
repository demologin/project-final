package com.javarush.jira.common.jwt.serializer;

import java.util.function.Function;

public interface Serializer<T, V> extends Function<T, V> {
}
