package com.controllers;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

import com.models.User;

public class RegistrationTest {

    private User user;

    @RepeatedTest(10)
    public void testUuidLength() {
        user = new User("", "", "");
        Assert.assertEquals(36,user.getUuid().length());
    }

    @Test
    public void testValidEmail() {
        user = new User("abcd@gmail.com", "123456", "KV");
        Assert.assertTrue(user.isValidEmailAddress());
    }

    @Test
    public void testInvalidEmail() {
        user = new User("abcd.com", "123456", "KV");
        Assert.assertFalse(user.isValidEmailAddress());
    }

    @Test
    public void testEmptyEmail() {
        user = new User("", "123456", "KV");
        Assert.assertFalse(user.isValidEmailAddress());
    }

    @Test
    public void testValidPassword() {
        user = new User("123@gmail.com", "12345678Aa", "KV");
        Assert.assertTrue(user.isValidEmailAddress());
    }

    @Test
    public void testInvalidPassword() {
        user = new User("123@gmail.com", "abcd", "KV");
        Assert.assertFalse(user.isValidPassword());
    }

    @Test
    public void testEmptyPassword() {
        user = new User("123@gmail.com", "", "KV");
        Assert.assertFalse(user.isValidPassword());
    }
}

