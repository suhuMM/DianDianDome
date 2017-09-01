package annotation.com.suhu.tongzhi;

import android.app.Application;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suhu on 2017/8/31.
 */

public class MyApp extends Application{
    private List<Event> eventsList;
    private static MyApp myApp;

    @Override
    public void onCreate() {
        super.onCreate();
        eventsList = new ArrayList<>();
        myApp = this;
        Intent intent = new Intent(this,NotificationServices.class);
        startService(intent);
    }

    public static MyApp getInstance(){
        return myApp;
    }

    public List<Event> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<Event> list) {
        this.eventsList.addAll(list);
    }

    public void setEventsToList(Event event) {
        this.eventsList.add(event);
    }
}
