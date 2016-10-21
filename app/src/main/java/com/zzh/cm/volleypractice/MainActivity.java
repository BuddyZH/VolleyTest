package com.zzh.cm.volleypractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.zzh.cm.volleypractice.weatherdata.Weather;
import com.zzh.cm.volleypractice.weatherdata.WeatherData;
import com.zzh.cm.volleypractice.weatherdata.WeatherInfo;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView weatherTV;
    private ImageView imageView;
    private Button sendBtn;
    private NetworkImageView networkImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherTV = (TextView) findViewById(R.id.weather);
        imageView = (ImageView) findViewById(R.id.image_iv);
        sendBtn = (Button) findViewById(R.id.button);
        networkImageView = (NetworkImageView) findViewById(R.id.network_image_view);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader imageLoader = new ImageLoader(MyApplication.getHttpQueues(), new BitmapCache());

                /*ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                imageLoader.get("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg", listener);*/

                networkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
                networkImageView.setErrorImageResId(R.mipmap.ic_launcher);
                networkImageView.setImageUrl("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg", imageLoader);
            }
        });
        volleyGet();
    }


    private void volleyGet() {


        StringRequest request = new StringRequest("https://www.baidu.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG_STRING", error.getMessage(), error);
            }
        });

        request.setTag("myGet");
        MyApplication.getHttpQueues().add(request);

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://api.thinkpage.cn/v3/weather/now.json?key=vslt1ly01giiw7ab&location=beijing&language=zh-Hans&unit=c", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG_JSONOBJECT", response.toString());
                WeatherData mWeather = WeatherData.parse(response);
                if (mWeather != null) {
                    weatherTV.setText(mWeather.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG_JSONOBJECT_ERROR", error.getMessage(), error);
            }
        });
        MyApplication.getHttpQueues().add(jsonObjectRequest);

        /*final ImageRequest imageRequest = new ImageRequest(
                "https://developer.android.com/images/home/aw_dac.png",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imageView.setImageResource(R.mipmap.ic_launcher);
            }
        }
        );
        MyApplication.getHttpQueues().add(imageRequest);*/

        XMLRequest xmlRequest = new XMLRequest(
                "http://flash.weather.com.cn/wmaps/xml/china.xml",
                new Response.Listener<XmlPullParser>() {
                    @Override
                    public void onResponse(XmlPullParser response) {
                        try {
                            int eventType = response.getEventType();
                            while (eventType != XmlPullParser.END_DOCUMENT) {
                                switch (eventType) {
                                    case XmlPullParser.START_TAG:
                                        String nodeName = response.getName();
                                        if ("city".equals(nodeName)) {
                                            String pName = response.getAttributeValue(1);
                                            Log.d("TAG_XML", "pName is " + pName);
                                        }
                                        break;
                                }
                                eventType = response.next();
                            }
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG_XML_ERROR", error.getMessage(), error);
            }
        }
        );
        MyApplication.getHttpQueues().add(xmlRequest);

        GsonRequest<Weather> gsonRequest = new GsonRequest<Weather>(
                "https://api.thinkpage.cn/v3/weather/now.json?key=vslt1ly01giiw7ab&location=beijing&language=zh-Hans&unit=c",
                Weather.class,
                new Response.Listener<Weather>() {
                    @Override
                    public void onResponse(Weather response) {
                        ArrayList<WeatherInfo> weatherInfoArrayList = response.getResults();
                        WeatherInfo weatherInfo = weatherInfoArrayList.get(0);
                        weatherTV.setText(weatherInfo.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG_GSON_ERROR", error.getMessage(), error);
            }
        }
        );
        MyApplication.getHttpQueues().add(gsonRequest);


    }


}
