package com.cooba.example;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface GreetingActivities {
    String composeGreeting(String name);
}
