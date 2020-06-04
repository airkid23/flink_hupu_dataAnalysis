package cn.yourdad.data_analysis_ui.controller;

import cn.yourdad.data_analysis_ui.pojo.EchartsBean;
import com.alibaba.fastjson.JSON;
import org.apache.flink.api.scala.AggregateDataSet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import userInfoAnalysis.sexAnalysis;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @project: hupu_data_Analysis
 * @description
 * @package: cn.yourdad.data_analysis_ui.controller
 * @author: wzj
 * @create: 2020-05-27 16:14
 **/
@RestController
public class EchartsAsynController {

    @RequestMapping("/getDatas")
    public EchartsBean getDatas(){
        EchartsBean echartsBean =new EchartsBean();
        List<String> xAxisCategory =Arrays.asList("高新区","蜀山区","瑶海区","包河区",
                "经开区","政务区","滨湖新区","新站区");
        List<Integer> datas = Arrays.asList(300,600,230,239,100,800,300,500);
        echartsBean.setDatas(datas);
        echartsBean.setxAxisCategory(xAxisCategory);
        return echartsBean;
    }



    @RequestMapping("/getAddressData")
    public EchartsBean addressData() throws IOException {
        EchartsBean echartsBean =new EchartsBean();
        String filePath = "/home/wzj/IdeaProjects/Flink/hupu_data_Analysis/data_analysis_ui/src/main/resources/static/data/addressAnalysis.txt";
        FileInputStream fin = new FileInputStream(filePath);
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader buffReader = new BufferedReader(reader);
        String strTmp = "";
        ArrayList<String> xAxisCategory = new ArrayList<>();
        List<Integer> datas = new ArrayList<>();
        int count = 0;

        while((strTmp = buffReader.readLine())!=null){
            count += 1 ;
            if (count >= 10 ) break;
            String[] line = strTmp.split(",");
            xAxisCategory.add(line[0].substring(1,3));
            datas.add(Integer.valueOf(line[1].substring(0,line[1].length()-1)));
        }
        buffReader.close();
        echartsBean.setxAxisCategory(xAxisCategory);
        echartsBean.setDatas(datas);
        return echartsBean;
    }


    @RequestMapping("/getSexData")
    public JSON getSexData() throws IOException {
        String filePath = "/home/wzj/IdeaProjects/Flink/hupu_data_Analysis/data_analysis_ui/src/main/resources/static/data/sexAnalysis.txt";
        FileInputStream fin = new FileInputStream(filePath);
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader buffReader = new BufferedReader(reader);
        String strTmp = "";
        List<Map> list = new ArrayList<>();
        while((strTmp = buffReader.readLine())!=null){
            Map<String, Object> map1 = new HashMap<>();
            String[] line = strTmp.split(",");
            map1.put("value",Integer.valueOf(line[1].substring(0,line[1].length()-1)));
            map1.put("name",line[0].substring(1));
            list.add(map1);
        }
        buffReader.close();
        return (JSON) JSON.toJSON(list);
    }

    @RequestMapping("/getFavoriteTeamData")
    public JSON getFavoriteTeamData() throws IOException {
        String filePath = "/home/wzj/IdeaProjects/Flink/hupu_data_Analysis/data_analysis_ui/src/main/resources/static/data/favoriteTeam.txt";
        FileInputStream fin = new FileInputStream(filePath);
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader buffReader = new BufferedReader(reader);
        String strTmp = "";
        int count = 0;
        List<Map> list = new ArrayList<>();
        while((strTmp = buffReader.readLine())!=null){
            count ++;
            if (count == 10) break;
            Map<String, Object> map1 = new HashMap<>();
            String[] line = strTmp.split(",");
            map1.put("value",Integer.valueOf(line[1].substring(0,line[1].length()-1)));
            map1.put("name",line[0].substring(1));
            list.add(map1);
        }
        buffReader.close();
        return (JSON) JSON.toJSON(list);
    }

    @RequestMapping("/getFavoriteLeagueData")
    public EchartsBean getFavoriteLeague() throws IOException {
        EchartsBean echartsBean =new EchartsBean();
        String filePath = "/home/wzj/IdeaProjects/Flink/hupu_data_Analysis/data_analysis_ui/src/main/resources/static/data/favoriteLeague.txt";
        FileInputStream fin = new FileInputStream(filePath);
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader buffReader = new BufferedReader(reader);
        String strTmp = "";
        ArrayList<String> xAxisCategory = new ArrayList<>();
        List<Integer> datas = new ArrayList<>();
        int count = 0;

        while((strTmp = buffReader.readLine())!=null){
            count += 1 ;
            if (count >= 10 ) break;
            String[] line = strTmp.split(",");
            xAxisCategory.add(line[0].substring(1));
            datas.add(Integer.valueOf(line[1].substring(0,line[1].length()-1)));
        }
        buffReader.close();
        echartsBean.setxAxisCategory(xAxisCategory);
        echartsBean.setDatas(datas);
        return echartsBean;
    }

    @RequestMapping("/getTopicData")
    public EchartsBean getTopicData() throws IOException {
        EchartsBean echartsBean =new EchartsBean();
        String filePath = "/home/wzj/IdeaProjects/Flink/hupu_data_Analysis/data_analysis_ui/src/main/resources/static/data/TopicCountByDay.txt";
        FileInputStream fin = new FileInputStream(filePath);
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader buffReader = new BufferedReader(reader);
        String strTmp = "";
        ArrayList<String> xAxisCategory = new ArrayList<>();
        List<Integer> datas = new ArrayList<>();
        while((strTmp = buffReader.readLine())!=null){
            String[] line = strTmp.split("\t");
            if(Integer.valueOf(line[1]) > 1000){
            xAxisCategory.add(line[0].substring(5));
            datas.add(Integer.valueOf(line[1]));}
        }
        buffReader.close();
        echartsBean.setxAxisCategory(xAxisCategory);
        echartsBean.setDatas(datas);
        return echartsBean;
    }


    @RequestMapping("/getFavoriteSportData")
    public EchartsBean getFavoriteSportData() throws IOException {
        EchartsBean echartsBean =new EchartsBean();
        String filePath = "/home/wzj/IdeaProjects/Flink/hupu_data_Analysis/data_analysis_ui/src/main/resources/static/data/favoriteSport.txt";
        FileInputStream fin = new FileInputStream(filePath);
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader buffReader = new BufferedReader(reader);
        String strTmp = "";
        ArrayList<String> xAxisCategory = new ArrayList<>();
        List<Integer> datas = new ArrayList<>();
        int count = 0;
        while((strTmp = buffReader.readLine())!=null){
            count ++;
            if (count == 7) break;
            String[] line = strTmp.split(",");
            xAxisCategory.add(line[0].substring(1));
            datas.add(Integer.valueOf(line[1].substring(0,line[1].length()-1)));
        }
        buffReader.close();
        echartsBean.setxAxisCategory(xAxisCategory);
        echartsBean.setDatas(datas);
        return echartsBean;
    }


}
