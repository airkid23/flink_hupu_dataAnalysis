package test

import org.apache.flink.api.common.functions.{FilterFunction, RichMapFunction}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala._

case class SensorReading( id: String, timestamp: Long, temperature: Double )

object TransfromTest {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
      env.setParallelism(1)

    val streamFromFile = env.readTextFile("/home/wzj/IdeaProjects/Flink/FlinkTutorial/src/main/resources/sensor.txt")


    val datastream :DataStream[SensorReading] = streamFromFile.map(
      data => {
        val dataArray = data.split(",")
        SensorReading(dataArray(0).trim , dataArray(1).trim.toLong ,dataArray(2).trim.toDouble )

      }
    )
    // 对key的聚合，数据类型KeyedStream[T, Tuple
    val aggStream =  datastream.keyBy(0)
//      .sum(2)
    //输出当前传感器最新的温度+10,时间是上一次时间加1
        .reduce((x,y)=>SensorReading(x.id , x.timestamp+ 1 ,y.temperature+10))

    //多流转换算子
  val splitStream = datastream.split(data =>{

  if (data.temperature > 30)  Seq("high") else Seq("low")

  })

    val high = splitStream.select("high")
    val low = splitStream.select("low")
    val all = splitStream.select("high","low")

//    high.print("high")
//    low.print("low")



    //合并两条流
    val warning = high.map( data=>(data.id,data.temperature))

    val connectedStream = warning.connect(low)

    val coMapDataStream = connectedStream.map(
      warningData => (warningData._1,warningData._2,"warning"),
      lowData => (lowData.id,lowData.temperature,"healthy")
    )


//    coMapDataStream.print("coMap")



    //合并多条流,connect 操作两个流，union操作多个流,connect可以数据类型不一样，union必须相同
//    val unionStream = high.union(low)
//
//    unionStream.print("union")

    datastream.filter( new MyFilter())
        .print()

    //    datastream.print()
    env.execute("apitest")
  }
}

class MyFilter() extends FilterFunction[SensorReading]{
  override def filter(t: SensorReading): Boolean = {
    t.id.startsWith("sensor_1")

  }
}

class MyMapper extends RichMapFunction[SensorReading , String]{
  override def map(in: SensorReading): String = {

    return "flink"
  }
}