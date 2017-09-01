package annotation.com.suhu.tongzhi;

import java.io.Serializable;

/**
 * Created by suhu on 2017/8/31.
 */

public class Event implements Serializable{


    private String content;

    /**
     * 会议时间
     * 时间格式：yyyy-MM-dd HH:mm:ss
     *
     * */
    private String meetingTime;

    /**
     * 提醒时间
     * 时间格式：yyyy-MM-dd HH:mm:ss
     *
     * */
    private String remindingTime;

    /**
     * 通知类型：
     * 0：直接通知（用户不能操作）
     * 1：选择通知（用户可选择，暂缓、不再通知）
     *
     * */
    private int type;

    private int notificationID;

    public Event() {
    }

    public Event(String content, String meetingTime, String remindingTime ,int type) {
        this.content = content;
        this.meetingTime = meetingTime;
        this.remindingTime = remindingTime;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getRemindingTime() {
        return remindingTime;
    }

    public void setRemindingTime(String remindingTime) {
        this.remindingTime = remindingTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }
}
