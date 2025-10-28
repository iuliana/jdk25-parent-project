/*
Freeware License, some rights reserved

Copyright (c) 2025 Iuliana Cosmina

Permission is hereby granted, free of charge, to anyone obtaining a copy
of this software and associated documentation files (the "Software"),
to work with the Software within the limits of freeware distribution and fair use.
This includes the rights to use, copy, and modify the Software for personal use.
Users are also allowed and encouraged to submit corrections and modifications
to the Software for the benefit of other users.

It is not allowed to reuse,  modify, or redistribute the Software for
commercial use in any way, or for a user's educational materials such as books
or blog articles without prior permission from the copyright holder.

The above copyright notice and this permission notice need to be included
in all copies or substantial portions of the software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS OR APRESS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package org.mytoys.one;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mytoys.one.util.Triple;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author iulianacosmina on 22/10/2025
 */
@Disabled
class MainOneTest {

    @Test
    void testOne(){
        final var one  = new org.mytoys.one.MainOne("twenty five");
        assertEquals("TWENTY FIVE", one.version());
    }

    @Test
    void testJumpOne() throws IOException {
        final var input = getInput("array1d-00");
        final var result = MainOne.jump(input.one(), input.two(), input.three());
        assertTrue(result);
    }

    @Test
    void testJumpTwo() throws IOException {
        final var input = getInput("array1d-01");
        final var result = MainOne.jump(input.one(), input.two(), input.three());
        assertTrue(result);
    }

    @Test
    void testJumpThree() throws IOException {
        final var input = getInput("array1d-02");
        final var result = MainOne.jump(input.one(), input.two(), input.three());
        assertFalse(result);
    }

    @Test
    void testJumpFour() throws IOException {
        final var input = getInput("array1d-03");
        final var result = MainOne.jump(input.one(), input.two(), input.three());
        assertFalse(result);
    }

    private Triple<Integer, Integer, Integer[]> getInput(final String fileName) {
        try {
            final var in = Files.readAllLines(Path.of(new File("resources/" + fileName + ".in").getAbsolutePath()));
            final var queries = Integer.parseInt(in.get(0));
            var leap = Integer.parseInt(in.get(1).split(" ")[1]);

            var game = Arrays.stream(in.get(2).split(" ")).map(Integer::parseInt).toArray(Integer[]::new);
            return new Triple<>(queries, leap,game);
        } catch (IOException _) {}
        return null;
    }

}
