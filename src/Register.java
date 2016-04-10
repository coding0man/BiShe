import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import Utils.Constants;
import Utils.DbDao;
import org.json.JSONObject;

/**
 * Created by fandexian on 16/4/9.
 */
@WebServlet(name = "Register")
public class Register extends HttpServlet {

    private String userPhone,nickName,userPassword;
    private String sql;
    private PrintWriter printWriter;
    private JSONObject jsonObject;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        userPhone = request.getParameter("userPhone");
        nickName = request.getParameter("nickName");
        userPassword = request.getParameter("userPassword");
        response.setContentType("text/html;charset=utf-8");
        printWriter = response.getWriter();
        jsonObject = new JSONObject();
        sql = "INSERT INTO UserInfo(userPhone,userPassword,nickName) VALUES(?,?,?)";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(Constants.URL,Constants.USER,Constants.PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,userPhone);
            statement.setString(2,userPassword);
            statement.setString(3,nickName);
            int i = statement.executeUpdate();
            if(i>0){
                jsonObject.put("message","注册成功!");
                jsonObject.put("status","1");
            }else {
                jsonObject.put("message","注册失败!");
                jsonObject.put("status","0");
            }
            printWriter.print(jsonObject);

            //关闭资源
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
