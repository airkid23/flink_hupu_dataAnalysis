package cn.yourdad.data_analysis_ui.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @project: hupu_data_Analysis
 * @description
 * @package: cn.yourdad.data_analysis_ui.pojo
 * @author: wzj
 * @create: 2020-05-27 16:15
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EchartsBean {

    //表中的x轴类别
    private List<String> xAxisCategory;
    //表中的数据
    private List<Integer> datas;

    private List<Map<Integer, String>> piedata;


    public EchartsBean(List<String> xAxisCategory,List<Integer> datas){
        this.xAxisCategory=xAxisCategory;
        this.datas=datas;
    }

    public List<String> getxAxisCategory() {
        return xAxisCategory;
    }

    public void setxAxisCategory(List<String> xAxisCategory) {
        this.xAxisCategory = xAxisCategory;
    }

    public List<Integer> getDatas() {
        return datas;
    }

    public void setDatas(List<Integer> datas) {
        this.datas = datas;
    }
}