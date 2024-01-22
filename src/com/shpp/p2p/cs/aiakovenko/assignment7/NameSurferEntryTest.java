package com.shpp.p2p.cs.aiakovenko.assignment7;

import static org.junit.jupiter.api.Assertions.*;

class NameSurferEntryTest {
    final NameSurferEntry John = new NameSurferEntry("John 10 20 30 40 50 60 70 80 90 100 0 999");
    final NameSurferEntry Alice = new NameSurferEntry("Alice 5 15 25 35 45 55 65 75 85 95 105 965");
    final NameSurferEntry Bob = new NameSurferEntry("Bob 1 2 3 4 5 6 7 8 9 10 11 12");
    final NameSurferEntry Jack = new NameSurferEntry("Jack 0 0 0 0 0 0 0 0 0 0 0 0");

    @org.junit.jupiter.api.Test
    void testGetName() {
        assertEquals("John", John.getName());
        assertEquals("Alice", Alice.getName());
        assertEquals("Bob", Bob.getName());
        assertEquals("Jack", Jack.getName());
    }

    @org.junit.jupiter.api.Test
    void testGetRank() {
        assertEquals(10, John.getRank(0));
        assertEquals(15, Alice.getRank(1));
        assertEquals(12, Bob.getRank(11));
        assertEquals(0, Jack.getRank(5));
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        assertEquals("John [10 20 30 40 50 60 70 80 90 100 0 999]", John.toString());
        assertEquals("Alice [5 15 25 35 45 55 65 75 85 95 105 965]", Alice.toString());
        assertEquals("Bob [1 2 3 4 5 6 7 8 9 10 11 12]", Bob.toString());
        assertEquals("Jack [0 0 0 0 0 0 0 0 0 0 0 0]", Jack.toString());
    }
}