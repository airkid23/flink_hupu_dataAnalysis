package utils

import java.sql.{Connection, DriverManager}

import const.Const


/**
 * @project: hupu_dataAnalysis
 * @description  mysql连接工具类
 * @package: utils
 * @author: wzj
 * @create: 2020-05-22 23:27
 **/
class MysqlConnUtil {

  var conn: Connection = null

  def apply(): MysqlConnUtil = new MysqlConnUtil()

  def getConnection():Connection ={

    Class.forName(Const.DRIVER)
    conn = DriverManager.getConnection(Const.DBURL, Const.USERNAME, Const.PASSWORD)

    conn
  }
}
