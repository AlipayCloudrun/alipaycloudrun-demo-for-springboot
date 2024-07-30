package com.alipay.cloudrun.web;




import com.alipay.cloudrun.dao.ElasticScalingService;
import com.alipay.cloudrun.web.response.Result;
import com.alipay.cloudrun.web.response.ResultCodeEnum;
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
    private Result<String> cpuUpdate(@RequestParam String percentage) {
        try {
            int load=Integer.parseInt(percentage);
            if (load < 0 || load > 80) {
                return Result.error(ResultCodeEnum.ELASTIC_SCALE_PARAM_ERROR);
            }
            elasticScalingService.cpuClean();
            elasticScalingService.cpuUpdate(load);
        }catch(NumberFormatException e) {
            return Result.error(ResultCodeEnum.ELASTIC_SCALE_PARAM_ERROR);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.success("success");
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
