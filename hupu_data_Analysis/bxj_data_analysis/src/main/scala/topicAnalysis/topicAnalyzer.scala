package topicAnalysis

import java.io._
import java.sql.Connection

import utils.{AnsjAnalyzer, MysqlConnUtil}

/**
 * @project: hupu_dataAnalysis
 * @description
 * @package: topicAnalysis
 * @author: wzj
 * @create: 2020-05-23 11:50
 **/
object topicAnalyzer {
  def main(args: Array[String]): Unit = {
    val ansj  = new utils.AnsjAnalyzer
    val conn:Connection = new  MysqlConnUtil().getConnection()
    val writer = new PrintWriter(new File("TopicAnalyzer.txt" ))

    val statement = conn.createStatement
    //执行查询语句，并返回结果
    val rs = statement.executeQuery("select * from  bxj")

    //打印返回结果
    while (rs.next) {
     val topic = rs.getString(2)
       .filter(data => data.toString.trim != "")
      val result = ansj.analyzer(topic)
      writer.write(result)
    }
    conn.close()

    println("==========================")
    println("查询数据完成！")

  }
}
