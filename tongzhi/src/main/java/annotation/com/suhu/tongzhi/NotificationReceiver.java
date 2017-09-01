package annotation.com.suhu.tongzhi;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by suhu on 2017/8/31.
 */

public class NotificationReceiver extends BroadcastReceiver{

    private  NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Event event =(Event) intent.getExtras().getSerializable("event");
        if (event ==null) return;
        if (notificationManager==null){
            notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        }
        notificationManager.cancel(event.getNotificationID());

        switch (action){
            case NotificationUtils.DELAY:
                break;
            case NotificationUtils.CANCEL:
                break;
            case NotificationUtils.OK:
                break;
        }

    }


}
