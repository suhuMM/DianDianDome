package com.example.mymvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymvp.api.ApiManager;
import com.example.mymvp.api.ApiUrl;
import com.example.mymvp.bean.Tngou;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<String> {
    private TextView textView;

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
