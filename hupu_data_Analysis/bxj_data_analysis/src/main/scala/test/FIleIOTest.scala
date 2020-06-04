package test

import java.util

import sun.nio.cs.ext.GBK

import scala.io.Source

/**
 * @project: hupu_dataAnalysis
 * @description
 * @package: test
 * @author: wzj
 * @create: 2020-05-23 15:55
 **/
object FIleIOTest {
  def main(args: Array[String]): Unit = {
    val stopWord:List[String] = List()
    val file = Source.fromFile("/home/wzj/IdeaProjects/Flink/hupu_dataAnalysis/bxj_DataAnalysis/src/main/resources/stopWord.txt")
    for (line <- file.getLines()){
      println(line)
    }
    println(stopWord.size)
  }
}
