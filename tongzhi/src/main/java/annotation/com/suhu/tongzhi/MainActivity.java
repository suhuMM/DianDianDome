package annotation.com.suhu.tongzhi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RemoteViews;

//http://blog.csdn.net/dsc114/article/details/51721472
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //postNotification(this);
        //postCustomNotification(this);

        startService();

    }

    private void startService() {
        MyApp.getInstance().setEventsToList(new Event("明天开会","2017-08-31 19:42:00","2017-09-01 13:56:00",0));
        MyApp.getInstance().setEventsToList(new Event("明天开会","2017-08-31 19:42:00","2017-09-01 13:56:30",0));



    }

    public void postNotification(Context context) {
        NotificationManager notificationManager =  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
//        Intent intent = new Intent(context, MainActivity.class);  //需要跳转指定的页面
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);// 设置图标
        builder.setContentTitle("标题");// 设置通知的标题
        builder.setContentText("内容");// 设置通知的内容
        builder.setWhen(System.currentTimeMillis());// 设置通知来到的时间
        builder.setAutoCancel(true); //自己维护通知的消失
        builder.setTicker("您有一个会议");// 第一次提示消失的时候显示在通知栏上的
        builder.setOngoing(true);
        builder.setNumber(20);

        Notification notification = builder.build();
       // notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;  //只有全部清除时，Notification才会清除

        notification.defaults |= Notification.DEFAULT_VIBRATE;
        long[] vibrate = {0,100,200,300};
        notification.vibrate = vibrate ;

        notificationManager.notify(0,notification);
    }

    public void postCustomNotification(Context context) {
        NotificationManager notificationManager =  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        RemoteViews contentViews = new RemoteViews(context.getPackageName(),
                R.layout.notifi_layout);
        contentViews.setImageViewResource(R.id.image,R.mipmap.ic_launcher);
//        contentViews.setTextViewText(R.id.titleTV,"自定义通知标题");
//        contentViews.setTextViewText(R.id.textTV,"自定义通知内容");

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        contentViews.setOnClickPendingIntent(R.id.cancel, pendingIntent);
        contentViews.setOnClickPendingIntent(R.id.ok, PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT));

        Notification.Builder builder = new Notification.Builder(context);
        builder.setContent(contentViews);
        builder.setSmallIcon(R.mipmap.ic_launcher);

        builder.setContentTitle("custom notification");
        builder.setContentText("custom test");
        builder.setWhen(System.currentTimeMillis());// 设置通知来到的时间
        builder.setTicker("custom ticker");
        builder.setAutoCancel(true);


        Notification notification = builder.build();
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        long[] vibrate = {0,100,200,300};
        notification.vibrate = vibrate ;
        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
        notificationManager.notify(0,notification);
    }

}
