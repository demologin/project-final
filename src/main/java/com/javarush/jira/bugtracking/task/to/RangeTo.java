package com.javarush.jira.bugtracking.task.to;

import lombok.Value;

import java.time.Duration;
import java.time.Period;

@Value
public class RangeTo {
    Period period;
    Duration duration;
}
