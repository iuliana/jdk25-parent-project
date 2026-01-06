/*
Freeware License, some rights reserved

Copyright (c) 2026 Iuliana Cosmina

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
package org.mytoys.three.threads;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

///
/// @author iulianacosmina on 05/01/2026
/// Do not try to compile the result of this though
///
public class CodeGenerator {
    static void main() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("StackOverflow.java"));
        var str = """
                var threadXXX = Thread.ofVirtual().name("Gigi_" + i).unstarted(() -> {
                                                IntStream.range(0, 10).forEach(j -> {
                                                    try {
                                                        Thread.sleep(Duration.ofMinutes(1));
                                                        IO.println(threads.size());
                                                    } catch (InterruptedException _) {
                                                    }
                                                });
                                            });
                threadXXX.start();
                """;

        writer.write("""
                package org.mytoys.three.threads;
                
                import java.time.Duration;
                import java.util.ArrayList;
                
                import java.util.stream.IntStream;
                
                ///
                /// @author iulianacosmina on 05/01/2026
                ///
                /// Run with -XX:+UseCompressedOops
                ///
                public class StackOverflow {
                
                    static void main() throws InterruptedException {
                """);
        for (int i = 0; i < 524288; i++) {
            writer.write(str.replace("XXX" , i +""));
        }
        writer.write("""
                Thread.sleep(600_000);
                    }
           }
        """);
        writer.close();
    }
}
