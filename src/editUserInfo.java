import Utils.Constants;
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
import java.sql.PreparedStatement;

/**
 * Created by fandexian on 16/4/11.
 */
@WebServlet(name = "EditUserInfo")
public class editUserInfo extends HttpServlet {

    //parameters
    private int userId;
    private String userPhone;
    private String nickName;
    private String userSex;
    private String userHead;
    private String userQq;
    private String userWechat;

    private JSONObject jsonObject;
    private PrintWriter printWriter;
    private String sqlUpdate;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request.setCharacterEncoding("utf-8");
        getParameters(request);
        jsonObject = new JSONObject();
        response.setContentType("text/html;charset=utf-8");
        printWriter = response.getWriter();
        sqlUpdate = "UPDATE UserInfo SET userPhone=?,nickName=?,userSex=?,userHead=?," +
                "userQq=?,userWechat=? WHERE userId=?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(Constants.URL,Constants.USER,Constants.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1,userPhone);
            preparedStatement.setString(2,nickName);
            preparedStatement.setString(3,userSex);
            preparedStatement.setString(4,userHead);
            preparedStatement.setString(5,userQq);
            preparedStatement.setString(6,userWechat);
            preparedStatement.setInt(7,userId);

            int i = preparedStatement.executeUpdate();
            if(i>0){
                jsonObject.put("message","修改信息成功!");
                jsonObject.put("status",1);
            }else{
                jsonObject.put("message","修改信息失败!");
                jsonObject.put("status",0);
            }
            printWriter.print(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getParameters(HttpServletRequest request) {

        userId = Integer.parseInt(request.getParameter("userId"));
        userPhone = request.getParameter("userPhone");
        nickName = request.getParameter("nickName");
        userSex = request.getParameter("userSex");
        userHead = request.getParameter("userHead");
        userQq = request.getParameter("userQq");
        userWechat = request.getParameter("userWechat");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
