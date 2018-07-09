package com.example.lenovo.weatherreport;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.ProgressDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView tvcity,tvweather,tvtemp;
    private ImageView img1,img2;
    private Spinner spinner;
    private static final String IMGURL = "http://m.weather.com.cn/img/";
    private static final String CITYINFOURL = "http://www.weather.com.cn/data/cityinfo/";
    private String citycode;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        init();
        getJSONByVolley(CITYINFOURL + "101010100" + ".html");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        citycode = "101010100";
                        break;
                    case 1:
                        citycode = "101020100";
                        break;
                    case 2:
                        citycode = "101280101";
                        break;
                    case 3:
                        citycode = "101280601";
                        break;
                    case 4:
                        citycode = "101210101";
                        break;
                    case 5:
                        citycode = "101210401";
                        break;
                    case 6:
                        citycode = "101320101";
                        break;
                    case 7:
                        citycode = "101330101";
                        break;
                    case 8:
                        citycode = "101340101";
                        break;
                }
                getJSONByVolley(CITYINFOURL + citycode + ".html");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void init() {
        tvcity = (TextView)findViewById(R.id.city);
        tvweather = (TextView)findViewById(R.id.weather);
        tvtemp = (TextView)findViewById(R.id.temp);
        img1 = (ImageView)findViewById(R.id.img1);
        img2 = (ImageView)findViewById(R.id.img2);
        spinner = (Spinner)findViewById(R.id.spinner);
    }

    public void getJSONByVolley(String JSONDataurl) {
        final ProgressDialog progressDialog = ProgressDialog.show(this,"耐心等待","...读取天气预报中...");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, JSONDataurl, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject weatherinfo = response.getJSONObject("weatherinfo");
                    Weather weather = convertToBean(weatherinfo);
                    setWeatherData(weather);
                    if (progressDialog.isShowing() && progressDialog != null) {
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    public Weather convertToBean(JSONObject json) {
        Weather w = new Weather();
        try {
            w.setCity(json.getString("city"));
            w.setCityid(json.getString("cityid"));
            w.setImg1(json.getString("img1"));
            w.setImg2(json.getString("img2"));
            w.setTemp1(json.getString("temp1"));
            w.setTemp2(json.getString("temp2"));
            w.setWeather(json.getString("weather"));
            w.setPtime(json.getString("Ptime"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return w;
    }

    private void loadImageByVolley(String img,ImageView imageView) {
        final LruCache<String,Bitmap> lruCache = new LruCache<String, Bitmap>(20);
        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String key) {
                return lruCache.get(key);
            }

            @Override
            public void putBitmap(String key, Bitmap value) {
                lruCache.put(key,value);
            }
        };
        ImageLoader imageLoader = new ImageLoader(requestQueue,imageCache);
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,R.drawable.error,R.drawable.error);
        imageLoader.get(IMGURL+img,listener);
    }

    public void setWeatherData(Weather weather) {
        tvcity.setText(weather.getCity());
        tvweather.setText(weather.getWeather());
        tvtemp.setText(weather.getTemp1()+" - "+weather.getTemp2());
        loadImageByVolley(weather.getImg1(),img1);
        loadImageByVolley(weather.getImg2(),img2);
    }
}
