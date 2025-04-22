package com.cooba.example;

public class GreetingActivitiesImpl implements GreetingActivities {
    @Override
    public String composeGreeting(String name) {
        return "Hi from activity, " + name;
    }
}