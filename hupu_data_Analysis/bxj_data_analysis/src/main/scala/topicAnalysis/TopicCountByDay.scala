package topicAnalysis

import java.text.SimpleDateFormat
import java.util.Date

import CaseClass.{UserTopicViewCount, bxjTopic}
import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.{AllWindowFunction, WindowFunction}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
 * @project: hupu_data_Analysis
 * @description 统计每日发帖量，由于数据是从5月中下旬开始爬的，所以前面的数据较少
 * @package: topicAnalysis
 * @author: wzj
 * @create: 2020-05-30 11:30
 **/
object TopicCountByDay {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val dataStream = env.readTextFile("/home/wzj/IdeaProjects/Flink/hupu_data_Analysis/bxj_data_analysis/src/main/resources/bxj.csv")
      .filter(
        _.split(";").size == 6
      )
      .map(data => {
        val dataArray = data.split(";")
        val format  = new SimpleDateFormat("yyyy-MM-dd")
        bxjTopic(dataArray(0).trim, dataArray(1).trim, dataArray(2).trim, format.parse(dataArray(3).trim).getTime, dataArray(4).trim.toLong, dataArray(5).trim.toLong)
      })
      .assignAscendingTimestamps(_.writeTime * 1000L)
      .timeWindowAll(Time.days(1))

      .aggregate( new CountAgg(), new WindowResult())



    dataStream.print()
    env.execute()
  }
}

class CountAgg() extends AggregateFunction[bxjTopic, Long, Long] {
  override def createAccumulator(): Long = 0l

  override def add(value: bxjTopic, accumulator: Long): Long = accumulator + 1

  override def getResult(accumulator: Long): Long = accumulator

  override def merge(a: Long, b: Long): Long = a + b
}
class WindowResult() extends AllWindowFunction[Long, String,TimeWindow] {
  override def apply( window: TimeWindow, input: Iterable[Long], out: Collector[String]): Unit = {

    val format = new SimpleDateFormat("yyyy/MM/dd")
    val date =format.format(new Date(window.getEnd /1000l) )
    out.collect(date+ "\t"+input.iterator.next())

  }
}