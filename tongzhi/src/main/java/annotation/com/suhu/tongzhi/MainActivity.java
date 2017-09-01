package annotation.com.suhu.tongzhi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

//http://blog.csdn.net/dsc114/article/details/51721472
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startService();

    }

    private void startService() {
        MyApp.getInstance().setEventsToList(new Event("明天开会","2017-08-31 19:42:00","2017-09-01 17:05:00",1));
        MyApp.getInstance().setEventsToList(new Event("明天开会","2017-08-31 19:42:00","2017-09-01 17:05:30",0));
        MyApp.getInstance().setEventsToList(new Event("明天开会","2017-08-31 19:42:00","2017-09-01 17:06:00",1));
        MyApp.getInstance().setEventsToList(new Event("明天开会","2017-08-31 19:42:00","2017-09-01 17:06:30",0));



    }



}
