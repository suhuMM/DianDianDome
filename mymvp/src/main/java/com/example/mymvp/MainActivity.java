package com.example.mymvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mymvp.api.ApiManager;
import com.example.mymvp.api.ApiUrl;
import com.example.mymvp.bean.Tngou;
import com.google.gson.Gson;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<String> {
    private TextView textView;
    private ImageView imageView;
    private String url = "http://pic4.nipic.com/20091117/3376018_110331702620_2.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://wwww.baidu.com")
//                .addConverterFactory(new Converter.Factory() {
//                    @Override
//                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
//                        return new Converter<ResponseBody, String>() {
//                            @Override
//                            public String convert(ResponseBody value) throws IOException {
//                                return value.string();
//                            }
//                        };
//                    }
//                }).build();
//        RequestData requstData = retrofit.create(RequestData.class);
//        Call<String> call = requstData.getBaidu();
//        call.enqueue(this);


        //ApiManager.getInstance().getData(this);
        ApiManager.getInstance().tngou(0,1,20,this);
        textView = (TextView) findViewById(R.id.tv);

        imageView = (ImageView) findViewById(R.id.image);
        //Glide.with(this).load(url).thumbnail(0.1f).skipMemoryCache(true).into(imageView);
        Glide
            .with(this)
            .load("http://dev-kupao.mobifox.cn//Beauty/sportsdata/starts/2017-02-27/58b3e94c35cc6.jpg")
            .bitmapTransform(new BlurTransformation(this,25))
            .bitmapTransform(new CropCircleTransformation(this))//圆形
//            .bitmapTransform(new CropSquareTransformation(this))//正方形
            .into(imageView);

    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String str = (call.request().url()+"").trim();
        if (str.contains(ApiUrl.COOK)){
            textView.setText(response.body());
            Gson gson = new Gson();
            Tngou tngou = gson.fromJson(response.body(), Tngou.class);
        }

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        Toast.makeText(this,call.request().url()+"",Toast.LENGTH_LONG).show();
    }
}
