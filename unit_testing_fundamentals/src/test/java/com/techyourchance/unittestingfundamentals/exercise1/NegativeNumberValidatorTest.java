package com.techyourchance.unittestingfundamentals.exercise1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class NegativeNumberValidatorTest {

    private NegativeNumberValidator nnv;

    @Before
    public void setNnv() {
        nnv = new NegativeNumberValidator();
    }

    @Test
    public void test1() {
        boolean result = nnv.isNegative(1);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void test2() {
        boolean result = nnv.isNegative(0);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void test3() {
        boolean result = nnv.isNegative(-1);
        Assert.assertThat(result, is(true));
    }
}