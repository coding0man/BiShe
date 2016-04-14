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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by fandexian on 16/4/13.
 */
@WebServlet(name = "getMyGoods")
public class getMyGoods extends HttpServlet {

    private String userPhone;
    //=======response
    private JSONObject jsonObject;
    private PrintWriter printWriter;

    //=========sql
    private String sqlQuery;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        userPhone = request.getParameter("userPhone");
        jsonObject = new JSONObject();
        response.setContentType("text/html;charset=utf-8");
        printWriter = response.getWriter();
        sqlQuery = "SELECT goodsId,goodsName,goodsDescription,releaseTime,goodsPrice,goodsImg1 FROM GoodsInfo WHERE userPhone="+userPhone;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(Constants.URL,Constants.USER,Constants.PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sqlQuery);

            set.last();
            int count = set.getRow();
            JSONArray jsonArray = new JSONArray();

            for (int i=count ; i>0 ; i--){
                set.absolute(i);
                T_GoodsBriefMessage briefMessage = new T_GoodsBriefMessage();
                briefMessage.setGoodsId(set.getInt("goodsId"));
                briefMessage.setGoodsName(set.getString("goodsName"));
                briefMessage.setGoodsDescription(set.getString("goodsDescription"));
                briefMessage.setGoodsPrice(set.getFloat("goodsPrice"));
                briefMessage.setReleaseTime(set.getTimestamp("releaseTime"));
                briefMessage.setGoodsImg1(set.getString("goodsImg1"));

                jsonArray.put(JsonHelper.toJSON(briefMessage));
            }
            jsonObject.put("data",jsonArray);
            jsonObject.put("message","获取商品成功");
            jsonObject.put("status","1");
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
