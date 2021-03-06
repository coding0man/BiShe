import Beans.T_UserInfo;
import Utils.Constants;
import Utils.JsonHelper;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by fandexian on 16/4/11.
 */
@WebServlet(name = "getUserInfo")
public class getUserInfo extends HttpServlet {

    private String userPhone;
    private PrintWriter printWriter;
    private String sql;
    private JSONObject jsonObject;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userPhone = request.getParameter("userPhone");
        sql = "SELECT * from UserInfo where userPhone="+userPhone;
        //sql = "SELECT * from UserInfo where userPhone='18013116680'";
        response.setContentType("text/html;charset=utf-8");
        printWriter =response.getWriter();
        jsonObject = new JSONObject();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(Constants.URL,Constants.USER,Constants.PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                T_UserInfo userInfo = new T_UserInfo();

                userInfo.setUserId(resultSet.getInt("userId"));
                userInfo.setUserPhone(resultSet.getString("userPhone"));
                userInfo.setNickName(resultSet.getString("nickName"));
                userInfo.setUserSex(resultSet.getString("userSex"));
                userInfo.setUserHead(resultSet.getString("userHead"));
                userInfo.setUserQq(resultSet.getString("userQq"));
                userInfo.setUserWechat(resultSet.getString("userWechat"));
                userInfo.setIsCertified(resultSet.getInt("isCertified"));
                userInfo.setUserDepartment(resultSet.getString("userDepartment"));
                userInfo.setStuNumber(resultSet.getString("stuNumber"));

                JSONObject userInfos = JsonHelper.toJSON(userInfo);

                jsonObject.put("data",userInfos);

                jsonObject.put("message","获取信息成功!");
                jsonObject.put("status","1");

            }else {

                jsonObject.put("message","获取信息失败!");
                jsonObject.put("status","0");
            }
            printWriter.print(jsonObject.toString());

            //关闭资源
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
