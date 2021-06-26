package com.techyourchance.unittestingfundamentals.exercise2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class StringDuplicatorTest {

    private StringDuplicator sut;

    @Before
    public void setUp() throws Exception {
        sut = new StringDuplicator();
    }

    @Test
    public void duplicate_emptyString_emptyStringReturned() {
        String result = sut.duplicate("");
        Assert.assertThat(result, is(""));
    }

    @Test
    public void duplicate_oneCharacter_doubleCharacterReturned() {
        String result = sut.duplicate("a");
        Assert.assertThat(result, is("aa"));
    }

    @Test
    public void duplicate_longString_duplicatedLongString() {
        String result = sut.duplicate("Hello World!");
        Assert.assertThat(result, is("Hello World!Hello World!"));
    }
}