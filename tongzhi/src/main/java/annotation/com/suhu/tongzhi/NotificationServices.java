package annotation.com.suhu.tongzhi;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by suhu on 2017/8/31.
 */

public class NotificationServices extends Service {
    /**
     * 时间间隔毫秒数：
     */
    private static final int SPACING_TIME = 5000;

    private List<Event> eventsList;
    private int id = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        sendNotification();

    }

    private void sendNotification() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(SPACING_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    long time = System.currentTimeMillis();
                    eventsList = MyApp.getInstance().getEventsList();
                    for (int i = 0; i < eventsList.size(); i++) {
                        long remindingTime = getTime(eventsList.get(i).getRemindingTime());
                        if (remindingTime < time ) {
                            if (time - remindingTime < SPACING_TIME){
                                NotificationUtils.showNotification(
                                        NotificationServices.this
                                        , eventsList.get(i)
                                        , id);
                                id++;
                            }
                            eventsList.remove(i);
                        }

                    }
                }
            }
        }).start();
    }


    private long getTime(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(data).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String getData(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        return sdf.format(new Date(time));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Intent localIntent = new Intent();
//        localIntent.setClass(this, NotificationReceiver.class);
//        startService(localIntent);
    }
}
