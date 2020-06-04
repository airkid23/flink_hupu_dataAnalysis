package cn.yourdad.data_analysis_ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @project: hupu_data_Analysis
 * @description
 * @package: cn.yourdad.data_analysis_ui.controller
 * @author: wzj
 * @create: 2020-05-30 09:30
 **/
@Controller
public class indexController {
    @RequestMapping("/ajax")
    public String showTable(){
        return "ajaxDemo";
    }


    @RequestMapping("/index")
    public String index(){
        return "index";
    }

}
