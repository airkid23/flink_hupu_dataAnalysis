package cn.yourdad.data_analysis_ui;

import cn.yourdad.data_analysis_ui.pojo.EchartsBean;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import userInfoAnalysis.sexAnalysis;

import java.io.*;
import java.util.*;

@SpringBootTest
class DataAnalysisUiApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void getData() throws IOException {
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
    }



    @Test
    void test() throws IOException {
        String filePath = "/home/wzj/IdeaProjects/Flink/hupu_data_Analysis/data_analysis_ui/src/main/resources/static/data/TopicCountByDay.txt";
        FileInputStream fin = new FileInputStream(filePath);
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader buffReader = new BufferedReader(reader);
        String strTmp = "";
        ArrayList<String> xAxisCategory = new ArrayList<>();
        List<Integer> datas = new ArrayList<>();
        int count = 0;

        while((strTmp = buffReader.readLine())!=null){
            String[] line = strTmp.split("\t");
            xAxisCategory.add(line[0].substring(5));
            datas.add(Integer.valueOf(line[1]));
        }
        System.out.println(xAxisCategory);
        System.out.println(datas);
        buffReader.close();
    }

}
