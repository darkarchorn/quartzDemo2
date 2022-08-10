package com.example.multithreadDem.AppController;

import com.example.multithreadDem.Thread.MyThread;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {
    List<MyThread> list = new ArrayList<>();

    @GetMapping("/main")
    public String main() throws InterruptedException {
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
        for(int i=0; i<10; i++) {
            siz += list.get(i).getNumber().size();
        }
        return (int) siz + "/100000\n" + siz/(100000) +"%";
    }

}
