import Beans.T_GoodsDetail;
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
 * Created by fandexian on 16/4/13.
 */
@WebServlet(name = "getGoodsDetail")
public class getGoodsDetail extends HttpServlet {
    //parameters
    private int goodsId;

    private JSONObject jsonObject;
    private PrintWriter printWriter;
    private String selectDetail;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        goodsId = Integer.parseInt(request.getParameter("goodsId"));
        jsonObject = new JSONObject();
        response.setContentType("text/html;charset=utf-8");
        printWriter = response.getWriter();

        selectDetail = "SELECT * FROM GoodsInfo WHERE goodsId="+goodsId;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(Constants.URL,Constants.USER,Constants.PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(selectDetail);

            if(set.next()){

                T_GoodsDetail goodsDetail = new T_GoodsDetail();
                goodsDetail.setGoodsId(set.getInt(1));
                goodsDetail.setUserPhone(set.getString(2));
                goodsDetail.setGoodsCampus(set.getString(3));
                goodsDetail.setGoodsName(set.getString(4));
                goodsDetail.setReleaseTime(set.getTimestamp(5));
                goodsDetail.setSoldDate(set.getTimestamp(6));
                goodsDetail.setGoodsPrice(set.getFloat(7));
                goodsDetail.setGoodsDescription(set.getString(8));
                goodsDetail.setGoodsStatus(set.getInt(9));
                goodsDetail.setMinorCategoryId(set.getInt(10));
                goodsDetail.setTradeAddress(set.getString(11));
                goodsDetail.setContactTel(set.getString(12));
                goodsDetail.setContactQq(set.getString(13));
                goodsDetail.setContactWeChat(set.getString(14));
                goodsDetail.setGoodsImg1(set.getString(15));
                goodsDetail.setGoodsImg2(set.getString(16));
                goodsDetail.setGoodsImg3(set.getString(17));
                goodsDetail.setGoodsImg4(set.getString(18));

                jsonObject.put("data", JsonHelper.toJSON(goodsDetail));
                jsonObject.put("message","获取详情成功!");
                jsonObject.put("status",1);
            }else{
                jsonObject.put("message","数据库无此记录!");
                jsonObject.put("status",0);
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
