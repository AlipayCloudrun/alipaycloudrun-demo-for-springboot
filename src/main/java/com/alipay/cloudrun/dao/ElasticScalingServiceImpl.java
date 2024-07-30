package com.alipay.cloudrun.dao;

import com.sun.management.OperatingSystemMXBean;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class ElasticScalingServiceImpl implements ElasticScalingService{
    private final List<Thread> threads = new ArrayList<>();
    private volatile boolean      running = false;

    @Override
    public void cpuUpdate(int percentage)  {
        running = true;
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            Thread thread = new Thread(() -> {
                // 调整循环次数以微调CPU载荷。可以从1开始逐步增加
                // 繁忙时间，单位纳秒
                long busyTime = 10* 1_000_000;
                // 空闲时间，单位纳秒
                long idleTime = busyTime * (100 - percentage) / percentage;
                // 繁忙等待
                while (running) {
                    long startTime = System.nanoTime();
                    while (System.nanoTime() - startTime < busyTime) {
                        // 繁忙等待，什么也不做
                    }
                    try {
                        // 空闲时间，让线程休眠
                        long sleepTime=Math.floorDiv(idleTime,1_000_000);;
                        int sleepTimeNanos = (int)(idleTime % 1_000_000);
                        Thread.sleep(sleepTime,sleepTimeNanos);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            threads.add(thread);
        }
    }

    @Override
    public void cpuClean() {
        running = false;
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threads.clear();
    }

    @Override
    public void getCpu() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        // CPU load for the JVM
        double processCpuLoad = osBean.getProcessCpuLoad() * 100;

        // CPU load for the whole system
        double systemCpuLoad = osBean.getSystemCpuLoad() * 100;

        System.out.println(String.format("Process CPU Load: %.2f%%", processCpuLoad));
        System.out.println(String.format("System CPU Load: %.2f%%", systemCpuLoad));
    }
}
