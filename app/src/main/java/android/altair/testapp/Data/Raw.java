package android.altair.testapp.Data;

import android.altair.testapp.Volley.MyCustomVolley;
import android.altair.testapp.Volley.MyCustomVolley3;
import android.altair.testapp.News.Var;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by AkshayV on 24-Jun-16.
 */
public class Raw {

    //Here create a map to store all data

    private static LinkedHashMap<Integer, String> itemIds = new LinkedHashMap<>();
    private static LinkedHashMap<Integer, JSONObject> ObservationData = new LinkedHashMap<>();
    private static LinkedHashMap<Integer, JSONObject> CommentData = new LinkedHashMap<>();


    private static LinkedHashMap<Integer, JSONObject> ReplyData = new LinkedHashMap<>();


    final Map<String, String> HeadersParams = new HashMap<>();

    // String url = " https://hacker-news.firebaseio.com/v0/item/" + movieList.get(pos) + ".json?print=pretty";


    public void setIds(JSONArray v) {

        for (int i = 0; i <= v.length(); i++) {
            try {

                String id = v.getString(i);
                itemIds.put(i, id);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.e("+++++++CCCC++++", itemIds.toString());
    }


    public ArrayList getTop10Array() {

        ArrayList Alist = new ArrayList();

        for (Integer key : itemIds.keySet()) {
            Alist.add(itemIds.get(key));
        }

        return Alist;

    }


    public void setObservationData(String data) {

        try {
            JSONObject obj = new JSONObject(data);
            int id = obj.getInt("id");
            //String title = obj.getString("title");
            ObservationData.put(id, obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public LinkedHashMap<Integer, JSONObject> getObservationData() {

        return ObservationData;

    }

    public ArrayList<Integer> getObservationDataArray(){

        ArrayList<Integer> Alist = new ArrayList<>();

        for (Integer key : ObservationData.keySet()) {
            Alist.add(key);
        }

        return Alist;

    }


    public void newsShot(Context con) {

        MyCustomVolley volley;
        HeadersParams.put("Content-Type", "application/json");
        ArrayList Al = getTop10Array();
        //for (int i = 0; i < /*Al.size()*/30; i++) {
        for (int i = 0; i < Var.NUMBER_OF_NEWS_LOADS; i++) {

            Log.e("Ethe", "Ulti mariyanu");
            String url = " https://hacker-news.firebaseio.com/v0/item/" + Al.get(i) + ".json?print=pretty";
            volley = new MyCustomVolley(con, url, HeadersParams, null, 0);
            volley.makeVolleyRequests(Var.NEWSITEM_REQUEST);

        }
    }

    public void commentShot(Context con, JSONArray al) {
        try {
            Var.COMMENT_COUNT = 0;
            MyCustomVolley3 volley;
            HeadersParams.put("Content-Type", "application/json");
            //ArrayList Al = getTop10Array();
            for (int i = 0; i < al.length(); i++) {

                Log.e("Kake", "Ethe kids mariyanu");
                if (i >= 10) break;

                String url = " https://hacker-news.firebaseio.com/v0/item/" + al.get(i).toString() + ".json?print=pretty";

                volley = new MyCustomVolley3(con, url, HeadersParams, null, 0);
                volley.makeVolleyRequests(Var.COMMENT_REQUEST,al.length());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void replyShot(Context con, ArrayList al) {

        Var.REPLY_COUNT = 0;
        MyCustomVolley3 volley;
        HeadersParams.put("Content-Type", "application/json");
        //ArrayList Al = getTop10Array();
        Log.e("REPLY", "XXXSHOTSXXXX");
        for (int i = 0; i < al.size(); i++) {


            //if (i >= 10) break;

            String url = " https://hacker-news.firebaseio.com/v0/item/" + al.get(i).toString() + ".json?print=pretty";

            volley = new MyCustomVolley3(con, url, HeadersParams, null, 0);
            volley.makeVolleyRequests(Var.REPLY_REQUEST,al.size());

        }

    }


    public void setCommentData(String data) {


        try {

            JSONObject obj = new JSONObject(data);

            setReply(obj);
            int id = obj.getInt("id");

            CommentData.put(id, obj);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void setReplyData(String data) {

        try {

            JSONObject obj = new JSONObject(data);

            setReply(obj);
            int id = obj.getInt("id");

            ReplyData.put(id, obj);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public LinkedHashMap<Integer, JSONObject> getCommentData() {

        return CommentData;

    }

    public ArrayList getCommentIdArray() {

        ArrayList Alist = new ArrayList();

        for (Integer key : CommentData.keySet()) {
            Alist.add(key);
        }

        return Alist;

    }


    private void setReply(JSONObject obj) {

        int rep = getFirstReply(obj);

        ReplyData.put(rep, null);


    }


    public int getFirstReply(JSONObject obj) {

        try {
            int i;
            JSONArray arr = obj.getJSONArray("kids");
            if (arr.length() != 0) {
                i = arr.getInt(0);
                return i;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
        return 0;

    }


    public LinkedHashMap getReplydata() {

        return ReplyData;
    }


    public ArrayList getReplydataIDs() {

        ArrayList Alist = new ArrayList();

        for (Integer key : ReplyData.keySet()) {
            Alist.add(key);
        }

        return Alist;
    }


    public void clearCommentData() {

        CommentData.clear();

    }

}
