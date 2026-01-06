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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author iulianacosmina on 22/12/2025
 */
public class ThreadLocalDemo {

    void main() {
        Thread.ofPlatform().name("T-" + 1).start(new Task());
        Thread.ofPlatform().name("T-" + 2).start(new Task());
    }


    /*
     * Thread safe format method because every thread will use its own DateFormat
     */
    public static String threadSafeFormat(Date date){
        DateFormat formatter = PerThreadFormatter.getDateFormatter();
        return formatter.format(date);
    }
}

/*
 * Thread Safe implementation of SimpleDateFormat
 * Each Thread will get its own instance of SimpleDateFormat which will not be shared between other threads. *
 */
class PerThreadFormatter {

    /*
     * initialValue() is called
     */
    private static final ThreadLocal<SimpleDateFormat> dateFormatHolder = ThreadLocal.withInitial(() -> {
        var sdf =  System.currentTimeMillis() %3 == 0 ? new SimpleDateFormat("dd/MM/yyyy"): new SimpleDateFormat("dd-MM-yyyy");
        System.out.println("Creating SimpleDateFormat " + sdf.hashCode() + "  for Thread : " + Thread.currentThread().getName());
        return sdf;
    });

    /*
     * Every time there is a call for DateFormat, ThreadLocal will return calling
     * Thread's copy of SimpleDateFormat
     */
    public static DateFormat getDateFormatter() {
        return dateFormatHolder.get();
    }
}

class Task implements Runnable{

    @Override
    public void run() {
        for(int i=0; i<2; i++){
            System.out.println("Thread: " + Thread.currentThread().getName() + " Formatted Date: " + ThreadLocalDemo.threadSafeFormat(new Date()) );
        }
    }
}
