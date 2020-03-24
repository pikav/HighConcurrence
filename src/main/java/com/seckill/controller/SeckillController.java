package com.seckill.controller;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.dto.SeckillResult;
import com.seckill.entity.Seckill;
import com.seckill.enums.SeckillStatEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.service.SeckillService;
import com.seckill.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/seckill")  // /模块/资源/{params}/细分
public class SeckillController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(name = "/list", method = RequestMethod.GET)
    public String list(Model model){
        // 获取列表页
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") long seckillId, Model model){
        if(ValidateUtil.isNullOrEmpty(seckillId)) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getByID(seckillId);
        if(ValidateUtil.isNullOrEmpty(seckill)) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "detail";
    }

    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charser=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true,exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charser=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone", required = false) Long phone) {
        if(ValidateUtil.isNullOrEmpty(phone)) {
            return new SeckillResult<SeckillExecution>(false, "您尚未注册");
        }
        SeckillResult<SeckillExecution> result;
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId,phone,md5);
            result = new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (RepeatKillException e) {
            result =  new SeckillResult<SeckillExecution>(false, new SeckillExecution(seckillId,SeckillStatEnum.REPEAT_KILL));
        } catch (SeckillCloseException e) {
            result =  new SeckillResult<SeckillExecution>(false, new SeckillExecution(seckillId,SeckillStatEnum.END));
        } catch (SeckillException e) {
            logger.error(e.getMessage(), e);
            result =  new SeckillResult<SeckillExecution>(false, new SeckillExecution(seckillId,SeckillStatEnum.END));
        }
        return result;
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    public SeckillResult<Long> time() {
        return new SeckillResult<Long>(true, new Date().getTime());
    }

}
