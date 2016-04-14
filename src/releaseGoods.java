import Beans.T_GoodsDetail;
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
@WebServlet(name = "releaseGoods")
public class releaseGoods extends HttpServlet {
    private T_GoodsDetail releaseGoods;

    private String insertGoods;
    private PrintWriter printWriter;
    private JSONObject jsonObject;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        releaseGoods = new T_GoodsDetail();

        releaseGoods.setUserPhone(request.getParameter("userPhone"));
        releaseGoods.setGoodsCampus(request.getParameter("goodsCampus"));
        releaseGoods.setGoodsName(request.getParameter("goodsName"));
        releaseGoods.setReleaseTime(Timestamp.valueOf(request.getParameter("releaseTime")));
        releaseGoods.setSoldDate(Timestamp.valueOf(request.getParameter("soldDate")));
        releaseGoods.setGoodsPrice(Float.parseFloat(request.getParameter("goodsPrice")));
        releaseGoods.setGoodsDescription(request.getParameter("goodsDescription"));
        releaseGoods.setGoodsStatus(Integer.parseInt(request.getParameter("goodsStatus")));
        releaseGoods.setMinorCategoryId(Integer.parseInt(request.getParameter("minorCategoryId")));
        releaseGoods.setTradeAddress(request.getParameter("tradeAddress"));
        releaseGoods.setContactTel(request.getParameter("contactTel"));
        releaseGoods.setContactQq(request.getParameter("contactQq"));
        releaseGoods.setContactWeChat(request.getParameter("contactWeChat"));
        releaseGoods.setGoodsImg1(request.getParameter("goodsImg1"));
        releaseGoods.setGoodsImg2(request.getParameter("goodsImg2"));
        releaseGoods.setGoodsImg3(request.getParameter("goodsImg3"));
        releaseGoods.setGoodsImg4(request.getParameter("goodsImg4"));

        response.setContentType("text/html;charset=utf-8");
        printWriter = response.getWriter();

        jsonObject = new JSONObject();

        insertGoods = "insert into GoodsInfo(userPhone,goodsCampus,goodsName,releaseTime,soldDate,goodsPrice,goodsDescription,goodsStatus,minorCategoryId,tradeAddress,contactTel,contactQq,contactWeChat,goodsImg1,goodsImg2,goodsImg3,goodsImg4) " +
                "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(Constants.URL,Constants.USER,Constants.PASSWORD);
            PreparedStatement statement = connection.prepareStatement(insertGoods);
            statement.setString(1,releaseGoods.getUserPhone());
            statement.setString(2,releaseGoods.getGoodsCampus());
            statement.setString(3,releaseGoods.getGoodsName());
            statement.setTimestamp(4,releaseGoods.getReleaseTime());
            statement.setTimestamp(5,releaseGoods.getSoldDate());
            statement.setFloat(6,releaseGoods.getGoodsPrice());
            statement.setString(7,releaseGoods.getGoodsDescription());
            statement.setInt(8,releaseGoods.getGoodsStatus());
            statement.setInt(9,releaseGoods.getMinorCategoryId());
            statement.setString(10,releaseGoods.getTradeAddress());
            statement.setString(11,releaseGoods.getContactTel());
            statement.setString(12,releaseGoods.getContactQq());
            statement.setString(13,releaseGoods.getContactWeChat());
            statement.setString(14,releaseGoods.getGoodsImg1());
            statement.setString(15,releaseGoods.getGoodsImg2());
            statement.setString(16,releaseGoods.getGoodsImg3());
            statement.setString(17,releaseGoods.getGoodsImg4());

            int i = statement.executeUpdate();

            if(i>0){
                jsonObject.put("message","发布商品成功!");
                jsonObject.put("status","1");
            }else {
                jsonObject.put("message","发布商品失败!");
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
