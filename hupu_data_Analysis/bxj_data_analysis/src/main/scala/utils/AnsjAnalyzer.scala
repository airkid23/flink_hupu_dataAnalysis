package utils

import org.ansj.recognition.impl.StopRecognition
import org.ansj.splitWord.analysis.ToAnalysis

import scala.io.Source

/**
 * @project: hupu_dataAnalysis
 * @description 分词器
 * @package: utils
 * @author: wzj
 * @create: 2020-05-22 23:15
 **/
class AnsjAnalyzer {
  def apply(str: String): AnsjAnalyzer = return  new AnsjAnalyzer

  def analyzer(str:String):String ={
    val file = Source.fromFile("/home/wzj/IdeaProjects/Flink/hupu_data_Analysis/bxj_data_analysis/src/main/resources/stopWord.txt")

    val filter = new StopRecognition()
    filter.insertStopNatures("w") //过滤掉标点
    for (line <- file.getLines()){
      filter.insertStopWords(line.toString.trim)  //添加停用词
    }
    file.close()
    val result = ToAnalysis.parse(str)
      .recognition(filter)// 过滤掉标点
      .toStringWithOutNature(",")// 分词默认会打出词性，此语句用于不打出词性，并且分好的词用“,”隔开
    result
  }
}
