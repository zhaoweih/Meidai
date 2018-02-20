package me.zhaoweihao.shopping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class TestActivity extends AppCompatActivity {

    public static final String TAG = "TestActivity";
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");


    private Button button;
    private OkHttpClient client;
    private String jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        button = findViewById(R.id.post);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                client = new OkHttpClient();

                Gson gson = new Gson();

                User user = new User();

                user.setName("zhao");
                user.setPassword("123456");
                user.setPhone("18320663333");
                user.setSex(1);

               jsonObject = gson.toJson(user);

                Log.d("TA",jsonObject);

                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            String post=post("http://meidai.maocanhua.cn/register",jsonObject);
                            Log.d("TA",post);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();




            }
        });
    }

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }





}
