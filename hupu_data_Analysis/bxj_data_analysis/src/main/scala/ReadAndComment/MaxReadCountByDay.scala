package ReadAndComment

import java.text.SimpleDateFormat
import java.util.Date

import CaseClass.bxjTopic
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector
import org.apache.flink.streaming.api.scala._
/**
 * @project: hupu_dataAnalysis
 * @description
 * @package:
 * @author: wzj
 * @create: 2020-05-21 11:23
 **/
object MaxReadCountByDay {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    val dataStream = env.readTextFile("/home/wzj/IdeaProjects/Flink/hupu_data_Analysis/bxj_data_analysis/src/main/resources/bxj.csv")
      .filter(data => {
        data.split(";").size ==6
      })
        .map(data => {
          val dataArray = data.split(";")
          val format  = new SimpleDateFormat("yyyy-MM-dd")
          bxjTopic(dataArray(0).trim, dataArray(1).trim, dataArray(2).trim, format.parse(dataArray(3).trim).getTime, dataArray(4).trim.toLong, dataArray(5).trim.toLong)
        })
      .assignAscendingTimestamps(_.writeTime * 1000L)
      .keyBy(_.writeTime)
    .timeWindow(Time.days(1))
        .process( new ProcessWindowFunction[bxjTopic, String, Long, TimeWindow] {
          lazy val maxRead: ValueState[Long] = getRuntimeContext.getState( new ValueStateDescriptor[Long]("maxRead",classOf[Long]))
          lazy val topic:ValueState[String] = getRuntimeContext.getState(new ValueStateDescriptor[String]("maxReadTopic",classOf[String]))
          override def process(key: Long, context: Context, elements: Iterable[bxjTopic], out: Collector[String]): Unit = {
            val it = elements.iterator
            while(it.hasNext){
              val element = it.next()
              val currentReadCount = element.readCount
              val currentTopic = element.topic
              if (maxRead.value() == 0.0 || maxRead.value() < currentReadCount){
                maxRead.update(currentReadCount)
                topic.update(currentTopic)
              }
            }
            out.collect( new SimpleDateFormat("yyyy-MM-dd").format(new Date(key)) + " 最高阅读量 " + maxRead.value().toString + " "+topic.value())

          }
        })

    dataStream.print()
    env.execute("bxj data analysis")


  }

}
