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
package org.mytoys.three.tlocal;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author iulianacosmina on 22/12/2025
 */
public class PiggyBankDemo {

    void main() throws InterruptedException {
        final var t1 = Thread.ofPlatform().name("Child-01").start(new Child());
        final var t2 = Thread.ofPlatform().name("Child-02").start(new Child());

        t1.join();
        t2.join();
    }

    // code executed by both threads
    static void addMoney(final Integer money){
        var piggy = PerChildPiggy.getPiggy(); // retrieved from ThreadLocal
        piggy.addMoney(money);
    }

}

class Child implements Runnable {

    @Override
    public void run() {
        IntStream.range(0,2).forEach(_ -> {
            var sum = new Random().nextInt(10) +1;
            IO.println(Thread.currentThread().getName() + " adding to piggy: " + sum);
            PiggyBankDemo.addMoney(sum);
        });
        IO.println(Thread.currentThread().getName()+": piggy = "+ PerChildPiggy.destroyPiggy());
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

class PerChildPiggy {

    // after Java 8
    //ThreadLocal class is extend in Java 8 with a new method withInitial() that takes Supplier functional interface as argument
      private static final ThreadLocal<PiggyBank> piggyBankHolder = ThreadLocal.withInitial(() -> {
        var piggy =  new PiggyBank(new AtomicInteger(0));
        IO.println("Creating PiggyBank " + piggy + " for " + Thread.currentThread().getName());
        return piggy;
    });

    // before Java 8
    /*private static final ThreadLocal<PiggyBank> piggyBankHolder = new ThreadLocal<PiggyBank>(){
        @Override
        protected PiggyBank initialValue() {
            var piggy =  new PiggyBank(new AtomicInteger(0));
            IO.println("Creating PiggyBank " + piggy + " for " + Thread.currentThread().getName());
            return piggy;
        }
    };*/

    static PiggyBank getPiggy() {
        return piggyBankHolder.get();
    }

    static Integer destroyPiggy(){
        var sum = piggyBankHolder.get().sum().get();
        piggyBankHolder.remove();
        IO.println("Destroying PiggyBank for " + Thread.currentThread().getName());
        return sum;
    }
}
