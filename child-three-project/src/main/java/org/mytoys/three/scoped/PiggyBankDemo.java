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
package org.mytoys.three.scoped;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.mytoys.three.scoped.PiggyBankDemo.PIGGY_BANK;

/**
 * @author iulianacosmina on 22/12/2025
 * Run with -XX:+PrintCommandLineFlags
 */
public class PiggyBankDemo {

    final static ScopedValue<PiggyBank> PIGGY_BANK = ScopedValue.newInstance();

    void main() throws InterruptedException {
        final var t1 = Thread.ofVirtual().name("Child-01").start(new Child());
        final var t2 = Thread.ofVirtual().name("Child-02").start(new Child());

        t1.join();
        t2.join();
    }

}

class Child implements Runnable {

    @Override
    public void run() {
       ScopedValue.where(PIGGY_BANK, new PiggyBank(new AtomicInteger(0))).run( () -> {
            IntStream.range(0,2).forEach(_ -> {
                var sum = new Random().nextInt(10) +1;
                IO.println(Thread.currentThread().getName() + " adding to piggy: " + sum);
                PIGGY_BANK.get().addMoney(sum);
            });
            IO.println(Thread.currentThread().getName()+": piggy = "+ PIGGY_BANK.get().sum());
        });
    }

}

record PiggyBank(AtomicInteger sum, String name){

    PiggyBank(AtomicInteger sum) {
        this(sum, UUID.randomUUID().toString().substring(0, 6));
    }

    void addMoney(Integer money){
        sum.addAndGet(money);
    }

}
