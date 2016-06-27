package android.altair.testapp.Comment;

import android.altair.testapp.Data.Raw;
import android.altair.testapp.News.Var;
import android.altair.testapp.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AkshayV on 24-Jun-16.
 */
public class CommentListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Integer> movieList;
    private String[] bgColors;
    Raw rw = new Raw();
    Context con;
    LinearLayout ll;
    LinkedHashMap<Integer, JSONObject> CommentData = new LinkedHashMap<>();
    LinkedHashMap<Integer, JSONObject> ReplyData = new LinkedHashMap<>();
    TextView tv_comment;
    TextView tv_user, tv_reply, tv_ago;

    // Button btn_reply;
    static String title, user, reply;
    static long time;
    static int score;

    static String weburl;
    static JSONObject obj;


    public CommentListAdapter(Activity activity, List<Integer> movieLista) {
        this.activity = activity;
        con = activity;
        CommentData = rw.getCommentData();
        ReplyData = rw.getReplydata();
        this.movieList = movieLista;
        bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.movie_serial_bg);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

       // if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // if (convertView == null)
            convertView = inflater.inflate(R.layout.comment_listview_element, null);

        tv_user = (TextView) convertView.findViewById(R.id.tv_user);
        tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
        tv_reply = (TextView) convertView.findViewById(R.id.tv_reply);
        tv_ago = (TextView) convertView.findViewById(R.id.tv_ago);
        // btn_reply = (Button) convertView.findViewById(R.id.btn_reply);
        ll = (LinearLayout) convertView.findViewById(R.id.ll_hider);


        int id = movieList.get(position);

        obj = CommentData.get(id);
        //Log.e("Map: ", obj.toString());

        int repId = rw.getFirstReply(obj);
        JSONObject repObj = ReplyData.get(repId);

        try {

            user = obj.getString("by");
            title = obj.getString("text");
            time = obj.getInt("time");
            if (repObj != null) {

                reply = repObj.getString("text");
                Log.e("REPLIES:",reply);
                tv_reply.setText(Html.fromHtml(Html.fromHtml(reply).toString()));

            }else{
                tv_reply.setText("No Reply");

            }
            /*author = obj.getString("by");
            time = new Date((long) obj.getInt("time") * 1000);
            weburl = obj.getString("url");
            score = obj.getInt("score");*/

        } catch (JSONException e) {
            e.printStackTrace();
        }


        tv_user.setText(user);
        tv_ago.setText(Var.getTimeAgo(time));
        tv_comment.setText(Html.fromHtml(Html.fromHtml(title).toString()));




       /* btn_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }


        });
*/

        return convertView;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int location) {
        return movieList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
