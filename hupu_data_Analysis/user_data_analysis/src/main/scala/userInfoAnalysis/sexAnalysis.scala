package userInfoAnalysis

import caseclass.UserInfo
import org.apache.flink.api.scala._
import java.lang.String
/**
 * @project: hupu_data_Analysis
 * @description
 * @package: userInfoAnalysis
 * @author: wzj
 * @create: 2020-05-28 22:08
 **/
object sexAnalysis {

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
      .map(_.sex)
      //数据集有问题，筛选出正确数据
      .filter( _.nonEmpty  )
      .filter(!_.contains("f"))
      .map((_,1))
      .groupBy(0)
      .sum(1)
    dataSet.writeAsText("sexAnalysis.txt")
    env.execute()
  }


}
