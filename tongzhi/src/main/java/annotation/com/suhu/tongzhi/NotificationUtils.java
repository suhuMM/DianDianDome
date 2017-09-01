package annotation.com.suhu.tongzhi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * @author suhu
 * @data 2017/8/31
 * @description 这是一个通知工具类
 */
public class NotificationUtils {
    public static final String DELAY = "action.TIME_DELAY";
    public static final String CANCEL = "action.TIME_CANCEL";
    public static final String OK = "action.TIME_OK";

    private static NotificationManager notificationManager;
    private static Notification.Builder builder;


    public static void showNotification(Context context, Event event, int id) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            builder = new Notification.Builder(context);
        }
        event.setNotificationID(id);

        RemoteViews contentViews = new RemoteViews(context.getPackageName(), R.layout.notifi_layout);
        contentViews.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        contentViews.setTextViewText(R.id.message, "会议时间：" + event.getMeetingTime());


        //Intent intent = new Intent(context, cls);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //contentViews.setOnClickPendingIntent(R.id.ok, pendingIntent);
        //延迟
        Intent receiver = new Intent();
        receiver.setAction(DELAY);
        Bundle bundle = new Bundle();
        bundle.putSerializable("event",event);
        receiver.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, receiver, PendingIntent.FLAG_UPDATE_CURRENT);
        contentViews.setOnClickPendingIntent(R.id.cancel, pendingIntent);

        builder.setContent(contentViews)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(event.getContent())
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_VIBRATE);

        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_ALL;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(id, notification);
    }


}
