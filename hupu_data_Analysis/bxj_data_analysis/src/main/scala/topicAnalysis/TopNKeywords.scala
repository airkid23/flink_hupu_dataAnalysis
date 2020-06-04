package topicAnalysis

import CaseClass.KeywordsViewCount
import org.apache.flink.api.common.operators.Order
import org.apache.flink.api.scala._


/**
 * @project: hupu_dataAnalysis
 * @description
 * @package: topicAnalysis
 * @author: wzj
 * @create: 2020-05-23 14:45
 **/
object TopNKeywords {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val dataSet = env.readTextFile("/home/wzj/IdeaProjects/Flink/hupu_dataAnalysis/bxj_DataAnalysis/src/main/resources/TopicAnalyzer.txt")

    val keywordStream = dataSet
      .flatMap(_.split(","))
      .map((_,1))
      .groupBy(0)
      .sum(1)
        .sortPartition(1,Order.DESCENDING)
      .writeAsText("topNKeywordResult.txt")

    env.execute()
  }
}
