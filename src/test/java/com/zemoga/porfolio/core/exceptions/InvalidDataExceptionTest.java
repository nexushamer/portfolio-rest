package com.zemoga.porfolio.core.exceptions;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

public class InvalidDataExceptionTest {
    @Test
    public void Should_Equal_Itself() {
        final InvalidDataException invalidDataException = new InvalidDataException("ERROR_AT_THE_BEGIN");
        final boolean result = invalidDataException.equals(invalidDataException);

        assertTrue(result);
        assertNotNull(invalidDataException.getDescription());
    }

    @Test
    public void Should_Not_Equal_Null() {
        final InvalidDataException invalidDataException = new InvalidDataException("ERROR_AT_THE_BEGIN");
        final boolean result = invalidDataException.equals(null);

        assertFalse(result);
    }


    @Test
    public void Should_Not_Equal_Object_Of_Different_Type() {
        final InvalidDataException invalidDataException = new InvalidDataException("ERROR_AT_THE_BEGIN");
        final boolean result = invalidDataException.equals(new String());

        assertFalse(result);
    }

    @Test
    public void Should_Generate_Same_Hash_Code_Every_Time() {
        final InvalidDataException invalidDataException = new InvalidDataException("ERROR_AT_THE_BEGIN");

        final int result1 = invalidDataException.hashCode();
        final int result2 = invalidDataException.hashCode();

        assertEquals(result1, result2);
    }

    @Test
    public void Should_Generate_Different_Hash_Code_For_Different_Objects() {
        final InvalidDataException invalidDataException = new InvalidDataException("ERROR_AT_THE_BEGIN");
        final InvalidDataException invalidDataException2 = new InvalidDataException("ERROR_AT_THE_BEGIN2");

        final int result1 = invalidDataException.hashCode();
        final int result2 = invalidDataException2.hashCode();

        assertNotEquals(result1, result2);
    }
}