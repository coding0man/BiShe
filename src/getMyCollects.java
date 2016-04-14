import Beans.T_GoodsBriefMessage;
import Utils.Constants;
import Utils.JsonHelper;
import org.json.JSONArray;
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
 * Created by fandexian on 16/4/13.
 */
@WebServlet(name = "getMyCollects")
public class getMyCollects extends HttpServlet {

    private int userId;
    //=======response
    private JSONObject jsonObject;
    private PrintWriter printWriter;

    //=========sql
    private String sqlQuery;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        userId = Integer.parseInt(request.getParameter("userId"));
        jsonObject = new JSONObject();
        response.setContentType("text/html;charset=utf-8");
        printWriter = response.getWriter();
        sqlQuery = "SELECT goodsId,goodsName,goodsDescription,releaseTime,goodsPrice,goodsImg1 FROM GoodsInfo WHERE goodsId IN (SELECT goodsId FROM Collects WHERE userId=?)";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(Constants.URL, Constants.USER, Constants.PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1,userId);
            ResultSet set = statement.executeQuery();

            set.last();
            int count = set.getRow();
            JSONArray jsonArray = new JSONArray();

            for (int i = count; i > 0; i--) {
                set.absolute(i);
                T_GoodsBriefMessage TGoodsBriefMessage = new T_GoodsBriefMessage();
                TGoodsBriefMessage.setGoodsId(set.getInt("goodsId"));
                TGoodsBriefMessage.setGoodsName(set.getString("goodsName"));
                TGoodsBriefMessage.setGoodsDescription(set.getString("goodsDescription"));
                TGoodsBriefMessage.setGoodsPrice(set.getFloat("goodsPrice"));
                TGoodsBriefMessage.setReleaseTime(set.getTimestamp("releaseTime"));
                TGoodsBriefMessage.setGoodsImg1(set.getString("goodsImg1"));

                jsonArray.put(JsonHelper.toJSON(TGoodsBriefMessage));
            }
            jsonObject.put("data", jsonArray);
            jsonObject.put("message", "获取收藏商品成功");
            jsonObject.put("status", "1");
            printWriter.print(jsonObject);

            set.close();
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
