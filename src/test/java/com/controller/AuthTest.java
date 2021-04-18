package com.controller;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

import com.model.User;

import java.util.ArrayList;
import java.util.UUID;

public class AuthTest {

    private User user;

    @RepeatedTest(10)
    public void testUuidLength() {
        user = new User("", "", "", UUID.randomUUID().toString(),new ArrayList<>(), new ArrayList<>());
        Assert.assertEquals(36,user.getUuid().length());
    }

    @Test
    public void testValidEmail() {
        user = new User("abcd@gmail.com",
                "123456",
                "KV",
                UUID.randomUUID().toString(),
                new ArrayList<>(),
                new ArrayList<>());
        Assert.assertTrue(User.isValidEmailAddress(user.getEmail()));
    }

    @Test
    public void testInvalidEmail() {
        user = new User("abcd.com",
                "123456",
                "KV",
                UUID.randomUUID().toString(),
                new ArrayList<>(),
                new ArrayList<>());
        Assert.assertFalse(User.isValidEmailAddress(user.getEmail()));
    }

    @Test
    public void testEmptyEmail() {
        user = new User("",
                "123456",
                "KV",
                UUID.randomUUID().toString(),
                new ArrayList<>(),
                new ArrayList<>());
        Assert.assertFalse(User.isValidEmailAddress(user.getEmail()));
    }

    @Test
    public void testValidPassword() {
        user = new User("123@gmail.com",
                "12345678Aa",
                "KV",
                UUID.randomUUID().toString(),
                new ArrayList<>(),
                new ArrayList<>());
        Assert.assertTrue(User.isValidEmailAddress(user.getEmail()));
    }

    @Test
    public void testInvalidPassword() {
        user = new User("123@gmail.com",
                "abcd",
                "KV",
                UUID.randomUUID().toString(),
                new ArrayList<>(),
                new ArrayList<>());
        Assert.assertFalse(User.isValidPassword(user.getPassword()));
    }

    @Test
    public void testEmptyPassword() {
        user = new User("123@gmail.com",
                "",
                "KV",
                UUID.randomUUID().toString(),
                new ArrayList<>(),
                new ArrayList<>());
        Assert.assertFalse(User.isValidPassword(user.getPassword()));
    }
}

