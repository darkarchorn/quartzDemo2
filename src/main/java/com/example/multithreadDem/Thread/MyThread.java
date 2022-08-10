package com.example.multithreadDem.Thread;

import java.util.ArrayList;
import java.util.List;

public class MyThread implements Runnable {
    private List<Integer> number;
    private int id;

    public List<Integer> getNumber() {
        return number;
    }

    public void setNumber(List<Integer> number) {
        this.number = number;
    }

    private Thread t;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        this.number = new ArrayList<>();
    }

    public Thread getT() {
        return t;
    }

    public void setT(Thread t) {
        this.t = t;
    }

    public MyThread(int id) {
        this.id = id;
    }


    @Override
    public void run() {
        System.out.println("Running thread " + id);
        number = new ArrayList<>();
        try {
            for(Integer i = (id-1)*10000; i < id*10000; i++) {
                number.add(i);
                Thread.sleep(20);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " +  id + " interrupted.");
        }
    }

    public void start () {
        if (t == null) {
            t = new Thread (this, String.valueOf(id));
            t.start ();
        }
    }
}
