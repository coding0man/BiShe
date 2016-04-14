import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fandexian on 16/4/13.
 */
@WebServlet(name = "tests")
public class tests extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JSONObject o = new JSONObject();

        JSONObject o1= new JSONObject();
        JSONObject o2= new JSONObject();
        JSONObject o3= new JSONObject();

        JSONArray jsonArray = new JSONArray();

        try {
            o1.put("id",1);
            o1.put("name","ha");


            o2.put("id",2);
            o2.put("name","haha");


            o3.put("id",3);
            o3.put("name","hahaha");

            jsonArray.put(o1);
            jsonArray.put(o2);

            o.put("ahahhahah",o3);
            o.put("data",jsonArray);
            o.put("message","ok");
            o.put("status",1);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        response.getWriter().print(o.toString());


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
