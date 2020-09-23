package com.zlsoft.core;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.FieldLayout;
import org.openjdk.jol.vm.VM;
import org.openjdk.jol.vm.VirtualMachine;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TranslationClassLayout {
    private ClassLayout classLayout;
    private Object instance;

    public TranslationClassLayout(Object instance) {
        this.classLayout = ClassLayout.parseInstance(instance);
        this.instance = instance;
    }

    public String toMarkWord() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        if (instance != null) {
            VirtualMachine vm = VM.current();
            List list = new ArrayList<>();
            int word = vm.getInt(instance, 0);
            list.add(toBinary((word >> 0)  & 0xFF));
            list.add(toBinary((word >> 8)  & 0xFF));
            list.add(toBinary((word >> 16)  & 0xFF));
            list.add(toBinary((word >> 24)  & 0xFF));
            String firstBit = list.get(0).toString();
            String lock = firstBit.substring(6);
            String biasedLock = firstBit.substring(5);
            String msg = "无锁";
            switch (lock) {
                case "11":
                    msg = "GC标记";
                    break;
                case "10":
                    msg = "重量级锁";
                    break;
                case "00":
                    msg = "轻量级锁";
                    break;
                case "01":
                    msg = biasedLock.equals("001")?"无锁":"偏向锁";
                    break;
            }
            Collections.reverse(list);
            pw.printf("%s %s (%s)",String.join(" ",list),msg,lock.equals("01")? biasedLock:lock);
        } else {
            pw.printf(" %6d %5d %" + 2 + "s %-" + 4 + "s %s%n", 0, classLayout.headerSize(), "", "(object header)", "N/A");
        }
        pw.close();

        return sw.toString();
    }

    public String toPrintable() {
        return this.classLayout.toPrintable();
    }

    private static String toBinary(int x) {
        String s = Integer.toBinaryString(x);
        int deficit = 8 - s.length();
        for (int c = 0; c < deficit; c++) {
            s = "0" + s;
        }
        return s;
    }

    // very ineffective, so what?
    private static String toHex(int x) {
        String s = Integer.toHexString(x);
        int deficit = 2 - s.length();
        for (int c = 0; c < deficit; c++) {
            s = "0" + s;
        }
        return s;
    }
}
