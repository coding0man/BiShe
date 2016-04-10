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
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by fandexian on 16/4/9.
 */
@WebServlet(name = "Login")
public class Login extends HttpServlet {

    private String userPhone,userPassword,sql;
    private PrintWriter printWriter;
    private JSONObject jsonObject;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userPhone = request.getParameter("userPhone");
        userPassword = request.getParameter("userPassword");
        response.setContentType("text/html;charset=utf-8");
        printWriter = response.getWriter();
        sql = "SELECT userPassword FROM UserInfo WHERE userPhone="+userPhone;
        jsonObject = new JSONObject();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(Constants.URL,Constants.USER,Constants.PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                if(resultSet.getString("userPassword").equals(userPassword)){

                    jsonObject.put("message","登录成功!");
                    jsonObject.put("status","1");
                }else {

                    jsonObject.put("message","密码错误!");
                    jsonObject.put("status","0");
                }

            }else {

                jsonObject.put("message","用户未注册!");
                jsonObject.put("status","0");
            }
            printWriter.print(jsonObject);

            resultSet.close();
            statement.close();
            connection.close();


        } catch (Exception e) {
            //e.printStackTrace();
            printWriter.print("exception!");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
