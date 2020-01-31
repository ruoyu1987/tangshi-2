package cn.edu.jssvc.tangshi.link;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class HttpRequest {

    public static void post(String link, JSONObject jsonObject, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        String links = "http://" + MyApplication.ipLocation + "/tangshi/" + link;
        JsonArrayRequest request = new JsonArrayRequest(1, links, jsonObject, listener, errorListener);
        MyApplication.queue.add(request);
    }

    public static void post2(String link, JSONObject jsonObject, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        String links = "http://" + MyApplication.ipLocation + "/tangshi/" + link;
        MyJsonRequest request = new MyJsonRequest(1, links, jsonObject, listener, errorListener);
        MyApplication.queue.add(request);
    }

    public static void get(String str, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        MyStringRequest request = new MyStringRequest(str, listener, errorListener);
        MyApplication.queue.add(request);
    }

    public static class MyStringRequest extends StringRequest {

        public MyStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(url, listener, errorListener);
        }
        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            String str = null;
            try {
                str = new String(response.data, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
        }
    }

    public static class MyJsonRequest extends JsonObjectRequest {

        public MyJsonRequest(int i,String links,JSONObject jsonObject, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
            super(1, links, jsonObject, listener, errorListener);
        }
        @Override
        protected Response<JSONObject>  parseNetworkResponse(NetworkResponse response)
        {
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(new String(response.data,"UTF-8"));
                return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return Response.error(new ParseError(e));
            } catch (JSONException e) {
                e.printStackTrace();
                return Response.error(new ParseError(e));
            }
        }
    }
}
