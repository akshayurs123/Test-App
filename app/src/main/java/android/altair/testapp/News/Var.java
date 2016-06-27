package android.altair.testapp.News;

import android.content.Context;

import org.json.JSONException;

/**
 * Created by AkshayV on 24-Jun-16.
 */
public class Var {

    public static int NEWS_COUNT = 0;
    public static int COMMENT_COUNT = 0;
    public static int REPLY_COUNT = 0;

    public static int NUMBER_OF_NEWS_LOADS = 100;

    public interface VolleyJsonInterface {
        void processJsonFinish(String output) throws JSONException;
    }

    public interface VolleyInterface {

        void processFinish(String output) throws JSONException;
    }



    public static String getTimeAgo(long time ) {


        final int SECOND_MILLIS = 1000;
        final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        final int DAY_MILLIS = 24 * HOUR_MILLIS;
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now =System.currentTimeMillis();// getCurrentTime(ctx);
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

}
