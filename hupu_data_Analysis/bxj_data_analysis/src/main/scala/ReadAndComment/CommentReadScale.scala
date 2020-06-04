package ReadAndComment

import java.text.SimpleDateFormat
import java.util.Date

import CaseClass.bxjTopic
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
 * @project: hupu_dataAnalysis
 * @description
 * @package: ReadAndComment
 * @author: wzj
 * @create: 2020-05-21 20:39
 **/
object CommentReadScale {
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
      .keyBy(_.writeTime)
      .timeWindow(Time.days(1))
      .process( new ProcessWindowFunction[bxjTopic, String, Long, TimeWindow] {
        lazy val maxScale: ValueState[Double] = getRuntimeContext.getState( new ValueStateDescriptor[Double]("maxScale",classOf[Double]))
        lazy val topic:ValueState[String] = getRuntimeContext.getState(new ValueStateDescriptor[String]("maxReadTopic",classOf[String]))
        override def process(key: Long, context: Context, elements: Iterable[bxjTopic], out: Collector[String]): Unit = {
          val it = elements.iterator
          while(it.hasNext){
            val element = it.next()
            val currentCommentCount = element.commentCOunt.toDouble
            val currentReadCount = element.readCount.toDouble
            val scaleNum = (currentCommentCount./(currentReadCount)).formatted("%.4f").toDouble
            val currentTopic = element.topic
            if (maxScale.value() == 0.0 || maxScale.value() < scaleNum){
              maxScale.update(scaleNum)
              topic.update(currentTopic)
            }
          }
          out.collect( new SimpleDateFormat("yyyy-MM-dd").format(new Date(key)) + " 评论量/阅读量比例最高的帖子 " + maxScale.value().toString + " "+topic.value())

        }
      })

    dataStream.print()
    env.execute("bxj data analysis")


  }
}
