import Utils.Constants;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by fandexian on 16/4/9.
 */
@WebServlet(name = "ChangePassword")
public class changePassword extends HttpServlet {
    //有问题

    private String userPhone,newPassword,userPassword;
    private String confirmPsd,updatePsd;
    private PrintWriter printWriter;
    private JSONObject jsonObject;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userPhone = request.getParameter("userPhone");
        userPassword = request.getParameter("userPassword");
        newPassword = request.getParameter("newPassword");
        response.setContentType("text/html;charset=utf-8");
        printWriter = response.getWriter();
        jsonObject = new JSONObject();
        confirmPsd = "SELECT userPassword FROM UserInfo WHERE userPhone="+userPhone;
        updatePsd = "UPDATE UserInfo SET userPassword=? WHERE userPhone=?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(Constants.URL,Constants.USER,Constants.PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(confirmPsd);
            if(resultSet.next()){
                if(resultSet.getString("userPassword").equals(userPassword)){
                    PreparedStatement preparedStatement = connection.prepareStatement(updatePsd);
                    preparedStatement.setString(1,newPassword);
                    preparedStatement.setString(2,userPhone);

                    int i = preparedStatement.executeUpdate();
                    if(i>0){
                        jsonObject.put("message","修改密码成功!");
                        jsonObject.put("status","1");
                    }else{
                        jsonObject.put("message","修改密码失败!");
                        jsonObject.put("status","0");
                    }
                }else {
                    jsonObject.put("message","密码错误!");
                    jsonObject.put("status","0");
                }

            }else {

                jsonObject.put("message","用户未注册!");
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
