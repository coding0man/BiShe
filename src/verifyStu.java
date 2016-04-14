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
 * Created by fandexian on 16/4/13.
 */
@WebServlet(name = "varifyStu")
public class verifyStu extends HttpServlet {

    private int userId;
    private String stuNumber,stuName,cardImg;
    private String sqlUpdate;
    private PrintWriter printWriter;
    private JSONObject jsonObject;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        userId = Integer.parseInt(request.getParameter("userId"));
        stuNumber = request.getParameter("stuNumber");
        stuName = request.getParameter("stuName");
        cardImg = request.getParameter("cardImg");

        response.setContentType("text/html;charset=utf-8");
        printWriter = response.getWriter();
        jsonObject = new JSONObject();
        sqlUpdate = "INSERT INTO Salers(userId,stuNumber,stuName,cardImg,hasVerified) VALUES(?,?,?,?,?)";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(Constants.URL, Constants.USER, Constants.PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sqlUpdate);
            statement.setInt(1, userId);
            statement.setString(2, stuNumber);
            statement.setString(3, stuName);
            statement.setString(4, cardImg);
            statement.setInt(5, 0);

            int i = statement.executeUpdate();
            if (i > 0) {
                jsonObject.put("message", "提交信息成功成功!");
                jsonObject.put("status", "1");
            } else {
                jsonObject.put("message", "提交信息失败!");
                jsonObject.put("status", "0");
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
