package com.zemoga.porfolio.adapters.exceptions;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

public class RecordNotFoundExceptionTest {
    @Test
    public void Should_Equal_Itself() {
        final RecordNotFoundException recordNotFoundException = new RecordNotFoundException("USER NOT FOUND");
        final boolean result = recordNotFoundException.equals(recordNotFoundException);

        assertTrue(result);
        assertNotNull(recordNotFoundException.getDescription());
    }

    @Test
    public void Should_Not_Equal_Null() {
        final RecordNotFoundException recordNotFoundException = new RecordNotFoundException("USER NOT FOUND");
        final boolean result = recordNotFoundException.equals(null);

        assertFalse(result);
    }


    @Test
    public void Should_Not_Equal_Object_Of_Different_Type() {
        final RecordNotFoundException recordNotFoundException = new RecordNotFoundException("USER NOT FOUND");
        final boolean result = recordNotFoundException.equals(new String());

        assertFalse(result);
    }

    @Test
    public void Should_Generate_Same_Hash_Code_Every_Time() {
        final RecordNotFoundException recordNotFoundException = new RecordNotFoundException("USER NOT FOUND");

        final int result1 = recordNotFoundException.hashCode();
        final int result2 = recordNotFoundException.hashCode();

        assertEquals(result1, result2);
    }

    @Test
    public void Should_Generate_Different_Hash_Code_For_Different_Objects() {
        final RecordNotFoundException recordNotFoundException = new RecordNotFoundException("USER NOT FOUND");
        final RecordNotFoundException recordNotFoundException2 = new RecordNotFoundException("USER NOT FOUND");

        final int result1 = recordNotFoundException.hashCode();
        final int result2 = recordNotFoundException2.hashCode();

        assertNotEquals(result1, result2);
    }
}