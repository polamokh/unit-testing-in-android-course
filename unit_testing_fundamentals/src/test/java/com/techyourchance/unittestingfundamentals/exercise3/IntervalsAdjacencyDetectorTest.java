package com.techyourchance.unittestingfundamentals.exercise3;

import com.techyourchance.unittestingfundamentals.example3.Interval;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class IntervalsAdjacencyDetectorTest {

    private IntervalsAdjacencyDetector sut;

    @Before
    public void setUp() throws Exception {
        sut = new IntervalsAdjacencyDetector();
    }

    // interval1 adjacency interval2 on start
    @Test
    public void isAdjacent_interval1AdjacencyInterval2OnStart_trueReturned() {
        Interval interval1 = new Interval(0, 3);
        Interval interval2 = new Interval(3, 8);
        boolean result = sut.isAdjacent(interval1, interval2);
        Assert.assertThat(result, is(true));
    }

    // interval1 adjacency interval2 on end
    @Test
    public void isAdjacent_interval1AdjacencyInterval2OnEnd_trueReturned() {
        Interval interval1 = new Interval(8, 10);
        Interval interval2 = new Interval(3, 8);
        boolean result = sut.isAdjacent(interval1, interval2);
        Assert.assertThat(result, is(true));
    }
    // interval1 overlaps interval2
    @Test
    public void isAdjacent_interval1OverlapsInterval2_falseReturned() {
        Interval interval1 = new Interval(0, 5);
        Interval interval2 = new Interval(1, 8);
        boolean result = sut.isAdjacent(interval1, interval2);
        Assert.assertThat(result, is(false));
    }
    // interval1 before interval2
    @Test
    public void isAdjacent_interval1BeforeInterval2_falseReturned() {
        Interval interval1 = new Interval(0, 3);
        Interval interval2 = new Interval(4, 8);
        boolean result = sut.isAdjacent(interval1, interval2);
        Assert.assertThat(result, is(false));
    }
    // interval1 after interval2
    @Test
    public void isAdjacent_interval1AfterInterval2_falseReturned() {
        Interval interval1 = new Interval(10, 15);
        Interval interval2 = new Interval(3, 8);
        boolean result = sut.isAdjacent(interval1, interval2);
        Assert.assertThat(result, is(false));
    }

    // interval1 same as interval2
    @Test
    public void isAdjacent_interval1SameInterval2_falseReturned() {
        Interval interval1 = new Interval(0, 3);
        Interval interval2 = new Interval(0, 3);
        boolean result = sut.isAdjacent(interval1, interval2);
        Assert.assertThat(result, is(false));
    }
}