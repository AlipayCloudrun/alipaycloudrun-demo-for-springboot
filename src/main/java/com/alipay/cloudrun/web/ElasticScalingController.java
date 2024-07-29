package com.alipay.cloudrun.web;




import com.alipay.cloudrun.dao.ElasticScalingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 弹性伸缩
 */
@Log4j2
@RestController
@RequestMapping("/elastic/scaling")
public class ElasticScalingController {

    @Autowired
    ElasticScalingService elasticScalingService;

    @GetMapping("/cpu/update")
    private void cpuUpdate(@RequestParam String percentage) {
        try {
            int load=Integer.parseInt(percentage);
            if (load < 0 || load > 80) {
                throw new Exception("percentage应该在0-80之间");
            }
            elasticScalingService.cpuClean();
            elasticScalingService.cpuUpdate(load);
        }catch(NumberFormatException e) {
            throw new NumberFormatException("percentage应为整数");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/cpu/clean")
    private void cpuClean() {
        elasticScalingService.cpuClean();
    }

    @GetMapping("/cpu/get")
    public void getCpu() {
        elasticScalingService.getCpu();
    }

}
