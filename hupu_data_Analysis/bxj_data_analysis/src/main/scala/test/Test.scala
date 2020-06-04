package test

import utils.AnsjAnalyzer
/**
 * @project: hupu_dataAnalysis
 * @description
 * @package: test
 * @author: wzj
 * @create: 2020-05-22 23:18
 **/
object Test {
  def main(args: Array[String]): Unit = {
    val str = "宁可真行啊哈哈哈"
    val ansj = new utils.AnsjAnalyzer
    println(ansj.analyzer(str))
  }
}
