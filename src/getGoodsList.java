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
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by fandexian on 16/4/11.
 */
@WebServlet(name = "getGoodsList")
public class getGoodsList extends HttpServlet {

    //========request========-1代表无限制
    private String goodsCampus;
    private int mainCategoryId ,minorCategoryId,pageSize,page;

    //=======response
    private JSONObject jsonObject;
    private PrintWriter printWriter;

    //=========sql
    private String sqlQuery1,sqlQuery2,sqlQuery3,sqlQuery4,sqlQuery5,sqlQuery6;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        initVariable(request,response);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(Constants.URL,Constants.USER,Constants.PASSWORD);
            PreparedStatement preparedStatement = null;
            ResultSet set = null;

            if(!"-1".equals(goodsCampus) && mainCategoryId != -1 && minorCategoryId != -1){
                //对校区有限制,对主分类有限制,对次分类有限制
                preparedStatement = connection.prepareStatement(sqlQuery1);
                preparedStatement.setString(1,goodsCampus);
                preparedStatement.setInt(2,minorCategoryId);
                preparedStatement.setInt(3,(page-1)*pageSize);
                preparedStatement.setInt(4,pageSize);
                set = preparedStatement.executeQuery();
            }else if(!"-1".equals(goodsCampus) && mainCategoryId != -1 && minorCategoryId == -1){
                //对校区有限制,对主分类有限制,对次分类不限制
                preparedStatement = connection.prepareStatement(sqlQuery2);
                preparedStatement.setString(1,goodsCampus);
                preparedStatement.setInt(2,mainCategoryId);
                preparedStatement.setInt(3,(page-1)*pageSize);
                preparedStatement.setInt(4,pageSize);

                set = preparedStatement.executeQuery();

            }else if(!"-1".equals(goodsCampus) && mainCategoryId == -1 && minorCategoryId == -1){
                //只对校区有限制
                preparedStatement = connection.prepareStatement(sqlQuery3);

                preparedStatement.setString(1,goodsCampus);
                preparedStatement.setInt(2,(page-1)*pageSize);
                preparedStatement.setInt(3,pageSize);

                set = preparedStatement.executeQuery();

            }else if("-1".equals(goodsCampus) && mainCategoryId != -1 && minorCategoryId == -1){
                //只对主分类有限制
                preparedStatement = connection.prepareStatement(sqlQuery4);

                preparedStatement.setInt(1,mainCategoryId);
                preparedStatement.setInt(2,(page-1)*pageSize);
                preparedStatement.setInt(3,pageSize);

                set = preparedStatement.executeQuery();
            }else if("-1".equals(goodsCampus) && mainCategoryId == -1 && minorCategoryId != -1){
                //对主,次分类有限制,对校区无限制
                preparedStatement = connection.prepareStatement(sqlQuery5);

                preparedStatement.setInt(1,minorCategoryId);
                preparedStatement.setInt(2,(page-1)*pageSize);
                preparedStatement.setInt(3,pageSize);

                set = preparedStatement.executeQuery();
            }else if("-1".equals(goodsCampus) && mainCategoryId == -1 && minorCategoryId == -1){
                //都无限制
                preparedStatement = connection.prepareStatement(sqlQuery6);
                preparedStatement.setInt(1,(page-1)*pageSize);
                preparedStatement.setInt(2,pageSize);

                set = preparedStatement.executeQuery();
            }
            set.last();
            int count = set.getRow();
            JSONArray jsonArray = new JSONArray();

            for (int i=count ; i>0 ; i--){
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
            jsonObject.put("data",jsonArray);
            jsonObject.put("message","获取商品成功");
            jsonObject.put("status","1");
            printWriter.print(jsonObject);

            set.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initVariable(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //=======parameters
        goodsCampus = request.getParameter("goodsCampus");
        mainCategoryId = Integer.parseInt(request.getParameter("mainCategoryId"));
        minorCategoryId = Integer.parseInt(request.getParameter("minorCategoryId"));
        pageSize = Integer.parseInt(request.getParameter("pageSize"));
        page = Integer.parseInt(request.getParameter("page"));

        //===========var
        jsonObject = new JSONObject();
        response.setContentType("text/html;charset=utf-8");
        printWriter = response.getWriter();
        jsonObject = new JSONObject();

        //对校区有限制,对主分类有限制,对次分类有限制
        sqlQuery1 = "SELECT goodsId,goodsName,goodsDescription,releaseTime,goodsPrice,goodsImg1 FROM GoodsInfo WHERE goodsCampus=? AND minorCategoryId=? LIMIT ?,?";

        //对校区有限制,对主分类有限制,对次分类不限制
        sqlQuery2 = "SELECT goodsId,goodsName,goodsDescription,releaseTime,goodsPrice,goodsImg1 FROM GoodsInfo natural join MinorCategory WHERE goodsCampus=? AND mainCategoryId=? LIMIT ?,?";

        //只对校区有限制
        sqlQuery3 = "SELECT goodsId,goodsName,goodsDescription,releaseTime,goodsPrice,goodsImg1 FROM GoodsInfo WHERE goodsCampus=? LIMIT ?,?";

        //只对主分类有限制
        sqlQuery4 = "SELECT goodsId,goodsName,goodsDescription,releaseTime,goodsPrice,goodsImg1 FROM GoodsInfo natural join MinorCategory WHERE mainCategoryId=? LIMIT ?,?";

        //对次分类有限制,对校区无限制
        sqlQuery5 = "SELECT goodsId,goodsName,goodsDescription,releaseTime,goodsPrice,goodsImg1 FROM GoodsInfo WHERE minorCategoryId=? LIMIT ?,?";

        //无条件
        sqlQuery6 = "SELECT goodsId,goodsName,goodsDescription,releaseTime,goodsPrice,goodsImg1 FROM GoodsInfo LIMIT ?,?";
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
