package com.example.multithreadDem.AppController;

import com.example.multithreadDem.Thread.MyThread;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {
    List<MyThread> list = new ArrayList<>();
    long start;
    boolean done = false;
    static final int numberOfThread = 10;
    public String formatTime(long time) {
        Duration duration = Duration.ofMillis(time);
        long seconds = duration.getSeconds();
        long HH = seconds / 3600;
        long MM = (seconds % 3600) / 60;
        long SS = seconds % 60;
        return String.format("%02d:%02d:%02d", HH, MM, SS);
    }

    @GetMapping("/main")
    public String main() throws InterruptedException {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        if(threadGroup.activeCount()==28) {
            list.clear();
        }
        if (list.size() > 0) return "ANOTHER API IS RUNNING!";
        if(done) {
            list.clear();
            done = false;
        }
        start = System.currentTimeMillis();
        for (int i = 0; i < numberOfThread; i++) {
            list.add(new MyThread(i + 1));
        }
        for (MyThread thread : list) {
            thread.start();
        }
        return "SUCCEED";
    }

    @GetMapping("/per")
    public String per() {
        if (list.isEmpty()) {
            return "No job is being executed!";
        }
        int siz = 0;
        int workDoneByAThread[] = new int[numberOfThread];
        String progress ="";
        int workPerThread = list.get(0).getWork();
        int totalWork = numberOfThread * workPerThread;
        for (int i = 0; i < numberOfThread; i++) {
            siz += list.get(i).getNumber().size();
            progress += "\nThread " + (i+1) + ": " + list.get(i).getNumber().size() + "/" + list.get(i).getWork();
        }
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        int threadCount = threadGroup.activeCount();
        Thread threadList[] = new Thread[threadCount];
        if (siz == (double) numberOfThread * list.get(0).getWork()) {
            list.clear();
            done = true;
        }
        String s = siz + "/" + totalWork + "\n" + siz / (totalWork / 100) + "%\nTime elapsed: "
                + formatTime(System.currentTimeMillis() - start)
                + "\nNumber of thread activating: " + Thread.activeCount()
                + progress
                + (siz == totalWork ? "\nWork done! Restarting..." : "");
        return s;
    }
}
