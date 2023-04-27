package com.javarush.jira.common;

import com.javarush.jira.bugtracking.to.ObjectType;

public interface Subscribable extends HasId{
    public ObjectType getObjectType();
}
