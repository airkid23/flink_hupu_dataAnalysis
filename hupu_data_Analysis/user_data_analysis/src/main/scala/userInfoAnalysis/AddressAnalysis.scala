package userInfoAnalysis

import UserAnalysis.WindowResult
import caseclass.UserInfo
import org.apache.flink.api.common.operators.Order
import org.apache.flink.api.scala._

/**
 * @project: hupu_data_Analysis
 * @description
 * @package: userInfoAnalysis
 * @author: wzj
 * @create: 2020-05-27 09:25
 **/
object AddressAnalysis {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    val dataSet = env.readTextFile("/home/wzj/IdeaProjects/Flink/hupu_data_Analysis/user_data_analysis/src/main/resources/userinfo.csv")
      .map(
        data =>{
          val dataArray = data.split(";")
          UserInfo(dataArray(0).trim.toString, dataArray(1).trim.toString, dataArray(2).trim.toString, dataArray(3).trim.toString, dataArray(4).trim.toString, dataArray(5).trim.toString, dataArray(6).trim.toString, dataArray(7).trim.toString, dataArray(8).trim.toString, dataArray(9).trim.toString,
           dataArray(10).trim.toString, dataArray(11).trim.toString, dataArray(12).trim.toString)
        }
      )
        .filter(_.address != "null" )
      .filter(_.address.length >=2)
        .map( data => (data.address.replace(" ","").toString.substring(0,2),1))
      .groupBy(0)
        .sum(1)
      .sortPartition(1,Order.DESCENDING)
        .writeAsText("addressAnalysis.txt")

      env.execute()



//    dataSet.print()
  }
}