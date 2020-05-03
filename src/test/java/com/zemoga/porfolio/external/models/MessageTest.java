package com.zemoga.porfolio.external.models;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

public class MessageTest {
    @Test
    public void Testing_Getter_Methods() {
        final Message message = new Message();

        message.getCode();
        message.getDescription();
    }

    @Test
    public void Should_Equal_Itself() {
        final Message message = new Message();
        final boolean result = message.equals(message);

        assertTrue(result);
    }

    @Test
    public void Should_Not_Equal_Null() {
        final Message message = new Message();
        final boolean result = message.equals(null);

        assertFalse(result);
    }


    @Test
    public void Should_Not_Equal_Object_Of_Different_Type() {
        final Message message = new Message("12345","12345");
        final boolean result = message.equals(new String());

        assertFalse(result);
    }

    @Test
    public void Should_Generate_Same_Hash_Code_Every_Time() {
        final Message message = new Message("123213123");

        final int result1 = message.hashCode();
        final int result2 = message.hashCode();

        assertEquals(result1, result2);
    }

    @Test
    public void Should_Generate_Different_Hash_Code_For_Different_Objects() {
        final Message message = new Message();
        message.setCode("123213123");
        final Message message2 = new Message();
        message2.setDescription("adsadsadsadasd");

        final int result1 = message.hashCode();
        final int result2 = message2.hashCode();

        assertNotEquals(result1, result2);
    }
}