package com.alipay.cloudrun.web;




import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;


import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 弹性伸缩
 */
@Log4j2
@RestController
@RequestMapping("/elastic/scaling")
public class ElasticScalingController {

    private volatile boolean running = true;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * 设置cpu使用率
     */
    @GetMapping("/cpu/update")
    public String cpuUp(@RequestParam(value = "percentage", defaultValue = "0") int percentage){
        if (percentage < 0 || percentage > 80) {
            return "Invalid percentage. It should be between 0 and 80.";
        }
        running = false; // Stop any previous CPU manipulation
        scheduler.shutdownNow();
        scheduler = Executors.newScheduledThreadPool(1);
        running = true;

        scheduler.scheduleAtFixedRate(() -> {
            if (!running) {
                return;
            }
            double currentCpuLoad = getProcessCpuLoad();
            if (currentCpuLoad < percentage) {
                increaseCpuLoad(percentage - currentCpuLoad);
            } else if (currentCpuLoad > percentage) {
                decreaseCpuLoad(currentCpuLoad - percentage);
            }
        }, 0, 1, TimeUnit.SECONDS);
        System.out.println("CPU load set to " + percentage + "%");
        return "CPU load set to " + percentage + "%";
    }

    private void increaseCpuLoad(double target) {
        while (getProcessCpuLoad() < target && running) {
            for (int i = 0; i < 1000; i++) {
                // Busy-wait loop to consume CPU
                Math.sin(i);
            }
        }
    }

    private void decreaseCpuLoad(double target) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     *查看CPU使用率
     */
    @GetMapping("/cpu/get")
    private double getProcessCpuLoad() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            double load = ((com.sun.management.OperatingSystemMXBean) osBean).getProcessCpuLoad();
            System.err.println(load);
            return load;
        }
        return 0;
    }
}
