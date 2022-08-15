package com.example.multithreadDem.Thread;

import java.util.ArrayList;
import java.util.List;

public class MyThread implements Runnable {
    int work;
    private List<Integer> number;
    private int id;
    public int getWork() {
        return work;
    }
    public void setWork(int work) {
        this.work = work;
    }
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
        this.setWork(10000);
        number = new ArrayList<>();
        try {
            for(Integer i = (id-1)*getWork(); i < id*getWork(); i++) {
                number.add(i);
                Thread.sleep(1);
            }
            System.out.println("Work of thread " + id +" done!");
            Thread.interrupted();
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
