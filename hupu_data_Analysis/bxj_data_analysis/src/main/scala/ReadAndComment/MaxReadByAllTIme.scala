package ReadAndComment

import java.text.SimpleDateFormat

import CaseClass.bxjTopic
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.{AllWindowFunction, ProcessAllWindowFunction, RichAllWindowFunction}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
 * @project: hupu_dataAnalysis
 * @description
 * @package: ReadAndComment
 * @author: wzj
 * @create: 2020-05-22 14:39
 **/
object MaxReadByAllTIme {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    val dataStream = env.readTextFile("/home/wzj/IdeaProjects/Flink/hupu_data_Analysis/bxj_data_analysis/src/main/resources/bxj.csv")
      .filter(
        _.split(";").size == 6
      )
      .map(data => {
        val dataArray = data.split(";")
        val format  = new SimpleDateFormat("yyyy-MM-dd")
        bxjTopic(dataArray(0).trim, dataArray(1).trim, dataArray(2).trim, format.parse(dataArray(3).trim).getTime, dataArray(4).trim.toLong, dataArray(5).trim.toLong)
      })
        .max(5)
    .print()


  }
}
