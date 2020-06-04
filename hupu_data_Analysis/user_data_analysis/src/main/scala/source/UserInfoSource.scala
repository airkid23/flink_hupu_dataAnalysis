package source

import java.sql.Connection
import utils.MysqlConnUtil
import caseclass.UserInfo
import org.apache.flink.streaming.api.functions.source.SourceFunction

/**
 * @project: hupu_data_Analysis
 * @description
 * @package: source
 * @author: wzj
 * @create: 2020-05-26 21:39
 **/
class UserInfoSource extends SourceFunction[UserInfo]{
  var flag = true
  var conn:Connection = new MysqlConnUtil().getConnection
  val statement = conn.createStatement
  val selectQuery = "select * from userinfo ;"
  override def run(ctx: SourceFunction.SourceContext[UserInfo]): Unit = {
    if (flag == true){
    val rs = statement.executeQuery(selectQuery)
    while (rs.next()){
      //case class UserInfo(link:String, name:String, sex:String, address:String, level:Long, club:String, calorie:String, onlinetime:String, registtime:String, lastlogin:String, favoriteSport:String, favoriteLeague:String, favoriteTeam:String)
      ctx.collect(UserInfo(rs.getString(0).toString, rs.getString(1).toString, rs.getString(2).toString, rs.getString(3).toString,
        rs.getLong(4).toString, rs.getString(5).toString, rs.getString(6).toString, rs.getString(7).toString, rs.getString(8).toString,rs.getString(9).toString,
        rs.getString(10).toString, rs.getString(11).toString, rs.getString(12).toString))
   Thread.sleep(500)
    }
    }
  }

  override def cancel(): Unit = {flag = false}
}
