package com.example.multithreadDem.AppController;

import com.example.multithreadDem.Thread.MyThread;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {
    List<MyThread> list = new ArrayList<>();
    long start;

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
        if(list.size()>0) return "ANOTHER API IS RUNNING!";
        start = System.currentTimeMillis();
        for(int i=0; i<10; i++) {
            list.add(new MyThread(i+1));
        }
        for (int i=0; i<list.size(); i++) {
            list.get(i).start();
        }
        return "SUCCEED";
    }

    @GetMapping("/per")
    public String per() {
        double siz = 0;
        if(list.isEmpty()) {
            return "No job is being executed!";
        }
        for(int i=0; i<10; i++) {
            siz += list.get(i).getNumber().size();
        }
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        int threadCount = threadGroup.activeCount();
        Thread threadList[] = new Thread[threadCount];
        if(siz == 100000.0) list.clear();
        String s =(int) siz + "/100000\n" + siz/(1000) +"%\nTime elapsed: " + formatTime(System.currentTimeMillis()-start)
                +"\nNumber of thread activating: " + Thread.activeCount() + "\n";
        return s;
    }
}
