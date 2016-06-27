package android.altair.testapp.News;

import android.altair.testapp.Comment.CommentPage;
import android.altair.testapp.Data.Raw;
import android.altair.testapp.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AkshayV on 24-Jun-16.
 */
public class SwipeListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Integer> IdList;
    private String[] bgColors;
    Raw rw = new Raw();
    ProgressBar pb;
    Context con;
    RelativeLayout rl;
    LinkedHashMap<Integer, JSONObject> map = new LinkedHashMap<>();
    TextView title_tv, tv_author, tv_score, tv_ago;
    Button btn_article;

    static String title, author, weburl;
    static long time;
    static int score;

    static JSONObject obj;


    public SwipeListAdapter(Activity activity, List<Integer> movieLista) {
        this.activity = activity;
        con = activity;
        map = rw.getObservationData();
        this.IdList = movieLista;
        bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.movie_serial_bg);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.news_listview_element, null);

        InitializeUI(convertView);
        int id = IdList.get(position);
        obj = map.get(id);




        if (obj != null) {

            pb.setVisibility(View.GONE);

            try {

                title = obj.getString("title");
                author = obj.getString("by");
                // time = new java.util.Date((long) obj.getInt("time") * 1000);
                time = obj.getInt("time");
                weburl = obj.getString("url");
                score = obj.getInt("score");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            title_tv.setText(title);
            tv_score.setText(String.valueOf(score));
            tv_ago.setText(Var.getTimeAgo(time));
            tv_author.setText(author);


            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(con, CommentPage.class);
                    i.putExtra("data", map.get(Integer.valueOf(IdList.get(position))).toString());
                    con.startActivity(i);
                }
            });

            btn_article.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        JSONObject obj = map.get(Integer.valueOf(IdList.get(position)));
                        String url = obj.getString("url");
                        i.setData(Uri.parse(url));
                        con.startActivity(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return convertView;
    }

    private void InitializeUI(View convertView) {

        tv_author = (TextView) convertView.findViewById(R.id.tv_author);
        title_tv = (TextView) convertView.findViewById(R.id.title);
        tv_score = (TextView) convertView.findViewById(R.id.tv_score);
        tv_ago = (TextView) convertView.findViewById(R.id.tv_ago);
        btn_article = (Button) convertView.findViewById(R.id.btn_article);
        rl = (RelativeLayout) convertView.findViewById(R.id.item);
        pb = (ProgressBar) convertView.findViewById(R.id.progressBar);


    }

    @Override
    public int getCount() {
        return IdList.size();
    }

    @Override
    public Object getItem(int location) {
        return IdList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
