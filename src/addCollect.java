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
import java.sql.Timestamp;

/**
 * Created by fandexian on 16/4/13.
 */
@WebServlet(name = "addCollect")
public class addCollect extends HttpServlet {
    private int userId,goodsId;
    private Timestamp timestamp;

    private String sqlUpdate;

    private PrintWriter printWriter;
    private JSONObject jsonObject;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userId = Integer.parseInt(request.getParameter("userId"));
        goodsId = Integer.parseInt(request.getParameter("goodsId"));
        timestamp = new Timestamp(System.currentTimeMillis());
        sqlUpdate = "insert into Collects values(?,?,?)";

        jsonObject = new JSONObject();
        response.setContentType("text/html;charset=utf-8");
        printWriter = response.getWriter();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(Constants.URL,Constants.USER,Constants.PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sqlUpdate);
            statement.setInt(1,userId);
            statement.setInt(2,goodsId);
            statement.setTimestamp(3,timestamp);
            int i = statement.executeUpdate();
            if(i>0){
                jsonObject.put("message","收藏成功!");
                jsonObject.put("status","1");
            }else {
                jsonObject.put("message","收藏失败!");
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
