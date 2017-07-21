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
 * Created by fandexian on 16/4/10.
 */
@WebServlet(name = "isRegistered")
public class isRegistered extends HttpServlet {

    private String userPhone;
    private PrintWriter printWriter;
    private String sql;
    private JSONObject jsonObject;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userPhone = request.getParameter("userPhone");
        sql = "SELECT * from UserInfo where userPhone="+userPhone;
        response.setContentType("text/html;charset=utf-8");
        printWriter =response.getWriter();
        jsonObject = new JSONObject();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(Constants.URL,Constants.USER,Constants.PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){

                jsonObject.put("message","用户已注册!");
                jsonObject.put("userPhone",userPhone);
                jsonObject.put("status","1");

            }else {

                jsonObject.put("message","用户未注册!");
                jsonObject.put("userPhone",userPhone);
                jsonObject.put("status","0");
            }
            printWriter.print(jsonObject);

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
