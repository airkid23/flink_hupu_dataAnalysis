package UserAnalysis

import java.sql.Timestamp
import java.text.SimpleDateFormat

import CaseClass.{UserTopicViewCount, bxjTopic}
import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.api.common.state.{ListState, ListStateDescriptor, ValueState, ValueStateDescriptor}
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

import scala.collection.mutable.ListBuffer

/**
 * @project: hupu_dataAnalysis
 * @description 统计每天发帖最多的用户
 * @package: UserAnalysis
 * @author: wzj
 * @create: 2020-05-25 19:16
 **/
object UserTopicCount {
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
      .assignAscendingTimestamps(_.writeTime)
      .keyBy(_.author)
      .timeWindow(Time.days(1))
      .aggregate( new TopicCount(), new WindowResult())
      .keyBy(_.windowEnd)
        .process( new TopNUserTopicCount(5))


dataStream.print()
    env.execute()
  }
}
class TopicCount() extends AggregateFunction[bxjTopic, Long, Long] {
  override def createAccumulator(): Long = 0l

  override def add(value: bxjTopic, accumulator: Long): Long = accumulator + 1

  override def getResult(accumulator: Long): Long = accumulator

  override def merge(a: Long, b: Long): Long = a + b
}
class WindowResult() extends WindowFunction[Long, UserTopicViewCount, String, TimeWindow] {
  override def apply(key: String, window: TimeWindow, input: Iterable[Long], out: Collector[UserTopicViewCount]): Unit = {

    out.collect(UserTopicViewCount(key, window.getEnd, input.iterator.next()))

  }
}
class  TopNUserTopicCount(i: Int) extends KeyedProcessFunction[Long, UserTopicViewCount, String] {
  lazy val userTOpicState:ListState[UserTopicViewCount] = getRuntimeContext.getListState( new ListStateDescriptor[UserTopicViewCount]("usertopicviewcount",classOf[UserTopicViewCount]))
  override def processElement(value: UserTopicViewCount, ctx: KeyedProcessFunction[Long, UserTopicViewCount, String]#Context, out: Collector[String]): Unit = {
    userTOpicState.add(value)
    ctx.timerService().registerEventTimeTimer(value.windowEnd + 1)

  }

  override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[Long, UserTopicViewCount, String]#OnTimerContext, out: Collector[String]): Unit = {
    val view:ListBuffer[UserTopicViewCount] = new ListBuffer[UserTopicViewCount]
    val it = userTOpicState.get().iterator()
    while (it.hasNext){
      view += it.next()
    }
    userTOpicState.clear()
    val sortUserTopicCount = view.sortWith(_.count > _.count).take(i)

    val result:StringBuilder = new StringBuilder
    val format = new SimpleDateFormat("yyyy-MM-dd")
    val time = format.parse(new Timestamp( timestamp - 1).toString)
    result.append("时间：").append( time).append("\n")
    for ( i <- sortUserTopicCount.indices ){
      val currentUrlViews = sortUserTopicCount(i)
      result.append("NO").append( i + 1 ).append(":")
        .append(" id=").append(currentUrlViews.key).append("\t")
        .append("发帖数量").append("\t").append(currentUrlViews.count).append("\n")
    }
    result.append("=========================")
    Thread.sleep(1000)
    out.collect(result.toString())
  }
}