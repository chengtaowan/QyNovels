package com.jdhd.qynovels.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.EventBean;
import com.jdhd.qynovels.ui.activity.StartActivity;

import java.util.ArrayList;
import java.util.List;

public class EventDbUtils {
    private Context context;
    private DbUtils dbUtils;
    private SQLiteDatabase database;

    public EventDbUtils(Context context) {
        this.context = context;
        dbUtils=new DbUtils(context);
    }

    public List<EventBean.DataBean> updata(int eventType, int eventStartTime, int eventEndTime, int operationType, int event){
        EventBean eventBean=new EventBean();
        if(dbUtils!=null){
            database=dbUtils.getWritableDatabase();
            database.execSQL(
                    "insert into userevent(eventType,eventStartTime,eventEndTime,operationType,event)" +
                            " values('"+ eventType+"'" +
                            ",'"+ eventStartTime+"'" +
                            ",'"+eventEndTime+"'" +
                            ",'"+operationType+"'" +
                            ",'"+event+"') ");
            Cursor cursor = database.rawQuery("select * from userevent", new String[]{});

            List<EventBean.DataBean> list=new ArrayList<>();
            while(cursor.moveToNext()){
                EventBean.DataBean dataBean=new EventBean.DataBean();
                dataBean.setEventType(cursor.getInt(cursor.getColumnIndex("eventType")));
                dataBean.setEventStartTime(cursor.getInt(cursor.getColumnIndex("eventStartTime")));
                dataBean.setEventEndTime(cursor.getInt(cursor.getColumnIndex("eventEndTime")));
                dataBean.setOperationType(cursor.getInt(cursor.getColumnIndex("operationType")));
                dataBean.setEvent(cursor.getInt(cursor.getColumnIndex("event")));
                list.add(dataBean);
            }
            eventBean.setData(list);
        }

        return eventBean.getData();
    }
}
