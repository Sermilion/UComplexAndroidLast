package org.ucomplex.ucomplex.Modules.Events;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import org.ucomplex.ucomplex.Common.FacadeCommon;
import org.ucomplex.ucomplex.Common.UCDBOpenHelper;
import org.ucomplex.ucomplex.Common.base.UCApplication;
import org.ucomplex.ucomplex.Common.interfaces.mvp.MVPModel;
import org.ucomplex.ucomplex.Modules.Events.model.EventItem;
import org.ucomplex.ucomplex.Modules.Events.model.EventsRaw;
import org.ucomplex.ucomplex.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.DB_CREATE_EVENTS;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.DB_CREATE_EVENT_PARAMS;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENTS_ID;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENTS_PARAMS;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENTS_SEEN;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENTS_TEXT;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENTS_TIME;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENTS_TYPE;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENT_PARAMS_AUTHOR;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENT_PARAMS_CODE;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENT_PARAMS_COURSE_NAME;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENT_PARAMS_DATE;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENT_PARAMS_EVENT_NAME;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENT_PARAMS_GCOURSE;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENT_PARAMS_HOUT_TYPE;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENT_PARAMS_ID;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENT_PARAMS_MESSAGE;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENT_PARAMS_NAME;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENT_PARAMS_PHOTO;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENT_PARAMS_PK_ID;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENT_PARAMS_SEMESTER;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENT_PARAMS_TYPE;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.KEY_EVENT_PARAMS_YEAR;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.TABLE_EVENTS;
import static org.ucomplex.ucomplex.Common.UCDBOpenHelper.TABLE_EVENT_PARAMS;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 24/03/2017.
 * Project: UComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">www.ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class EventsModel implements MVPModel<EventsRaw, List<EventItem>, Integer> {

    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";
    private List<EventItem> mData;
    private EventsService eventsService;

    public EventsModel() {
        UCApplication.getInstance().getAppDiComponent().inject(this);
        mData = new ArrayList<>();
    }

    @Inject
    public void setEventsService(EventsService service) {
        this.eventsService = service;
    }

    @Override
    public Observable<EventsRaw> loadData(Integer start) {
        return eventsService.getEvents(start).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void setData(List<EventItem> data) {
        mData = data;
    }

    @Override
    public void addData(List<EventItem> data) {
        mData.addAll(data);
    }

    @Override
    public void clear() {
        mData.clear();
    }

    @Override
    public List<EventItem> getData() {
        return mData;
    }

    @Override
    public List<EventItem> processData(EventsRaw data) {
        List<EventItem> items = new ArrayList<>();
        for (EventItem item : data.getEvents()) {
            EventItem.EventParams params = FacadeCommon.jsonStringToObject(item.getParams(), EventItem.EventParams.class);
            item.setParamsObj(params);
            String displayEvent = makeEvent(item.getType(), item.getParamsObj(), UCApplication.getInstance());
            item.setEventText(displayEvent);
            items.add(item);
        }
        mData.addAll(items);
        return items;
    }

    private String makeEvent(int type, EventItem.EventParams params, Context context)
            throws NumberFormatException {
        if (params == null) {
            return "";
        }
        String result = "";
        String[] hourTypes = new String[]{context.getString(R.string.event_protocol_zanyatiya),
                context.getString(R.string.event_protocol_robezhnogo_kontrolya), context.getString(R.string.event_ekzamenazionnuyu_vedomost),
                context.getString(R.string.event_individualnoe_zanyatie)};
        String courseName;
        String name;
        switch (type) {
            case 1:
                courseName = params.getCourseName();
                result = context.getString(R.string.event_zagruzhen_material_po_discipline) + courseName + ".";
                break;
            case 2:
                int hourType = params.getHourType();
                courseName = params.getCourseName();
                name = params.getName();
                result = context.getString(R.string.event_propodavatel) + name + context.getString(R.string.event_zapolnil)
                        + hourTypes[hourType] + context.getString(R.string.event_po_discipline) + courseName
                        + ".";
                break;
            case 3:
                String semestr = params.getSemester();
                String year = params.getYear();
                result = context.getString(R.string.event_vy_proizveli_oplatu) + semestr + context.getString(R.string.event_j_semester) + year
                        + context.getString(R.string.event_uchebnogo_goda)+".";
                break;
            case 4:
                String eventName = params.getEventName();
                String date = params.getDate();
                result = context.getString(R.string.event_vy_priglasheny_na_meropriyatie) + eventName
                        + ", " +  context.getString(R.string.event_kotoroe_sostoitsya) + " " + date;
                break;
            case 5:
                name = params.getEventName();
                String author = params.getAuthor();
                result = context.getString(R.string.event_book_added) + " " + name + ", " + author;
                break;
            case 6:
                String message = "";
                if (params.getType() != 0) {
                    int t = params.getType();
                    if (t == 1) {
                        message = context.getString(R.string.event_photo_declined);
                    }
                } else {
                    message = params.getMessage();
                }
                result = context.getString(R.string.event_system_message) + ": \n" + message;
                break;
        }
        return result;
    }

    public void saveEventsToDB(List<EventItem> list, Context context) {
        SQLiteDatabase db = UCDBOpenHelper.getConnection(context);
        try {
            db.beginTransaction();
            clearTables(db);
            for (EventItem item : list) {
                long id = insertEventParam(db, item.getParamsObj());
                item.setParamInsertedId(id);
                insertEvents(db, item);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    private void clearTables(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_EVENTS);
            db.execSQL(DROP_TABLE_IF_EXISTS + TABLE_EVENT_PARAMS);
            db.execSQL(DB_CREATE_EVENT_PARAMS);
            db.execSQL(DB_CREATE_EVENTS);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    private void insertEvents(SQLiteDatabase db, EventItem item) {
        ContentValues values = new ContentValues();
        values.put(KEY_EVENTS_ID, item.getId());
        values.put(KEY_EVENTS_TEXT, item.getEventText());
        values.put(KEY_EVENTS_PARAMS, item.getParamInsertedId());
        values.put(KEY_EVENTS_TIME, item.getTime());
        values.put(KEY_EVENTS_SEEN, item.getSeen());
        values.put(KEY_EVENTS_TYPE, item.getType());
        db.insert(TABLE_EVENTS, null, values);
    }

    private long insertEventParam(SQLiteDatabase db, EventItem.EventParams params) {
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_PARAMS_ID, params.getId());
        values.put(KEY_EVENT_PARAMS_MESSAGE, params.getMessage());
        values.put(KEY_EVENT_PARAMS_NAME, params.getName());
        values.put(KEY_EVENT_PARAMS_PHOTO, params.getPhoto());
        values.put(KEY_EVENT_PARAMS_CODE, params.getCode());
        values.put(KEY_EVENT_PARAMS_GCOURSE, params.getGcourse());
        values.put(KEY_EVENT_PARAMS_COURSE_NAME, params.getCourseName());
        values.put(KEY_EVENT_PARAMS_HOUT_TYPE, params.getHourType());
        values.put(KEY_EVENT_PARAMS_TYPE, params.getType());
        values.put(KEY_EVENT_PARAMS_SEMESTER, params.getSemester());
        values.put(KEY_EVENT_PARAMS_YEAR, params.getYear());
        values.put(KEY_EVENT_PARAMS_EVENT_NAME, params.getEventName());
        values.put(KEY_EVENT_PARAMS_DATE, params.getDate());
        return db.insert(TABLE_EVENT_PARAMS, null, values);
    }

    public List<EventItem> getEventsFromDB(Context context) {
        SQLiteDatabase db = UCDBOpenHelper.getConnection(context);
        List<EventItem> items = new ArrayList<>();
        try {
            final String EVENTS_QUERY = "SELECT * FROM " + TABLE_EVENTS + " e INNER JOIN " + TABLE_EVENT_PARAMS + " p ON e." + KEY_EVENTS_PARAMS + " = p." + KEY_EVENT_PARAMS_PK_ID + "";
            Cursor cursor = db.rawQuery(EVENTS_QUERY, null);

            int KEY_EVENTS_ID_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENTS_ID);
            int KEY_EVENTS_TEXT_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENTS_TEXT);
            int KEY_EVENTS_PARAMS_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENTS_PARAMS);
            int KEY_EVENTS_TYPE_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENTS_TYPE);
            int KEY_EVENTS_TIME_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENTS_TIME);
            int KEY_EVENTS_SEEN_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENTS_SEEN);

            int KEY_EVENT_PARAMS_ID_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENT_PARAMS_ID);
            int KEY_EVENT_PARAMS_MESSAGE_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENT_PARAMS_MESSAGE);
            int KEY_EVENT_PARAMS_NAME_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENT_PARAMS_NAME);
            int KEY_EVENT_PARAMS_PHOTO_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENT_PARAMS_PHOTO);
            int KEY_EVENT_PARAMS_CODE_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENT_PARAMS_CODE);
            int KEY_EVENT_PARAMS_GCOURSE_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENT_PARAMS_GCOURSE);
            int KEY_EVENT_PARAMS_COURSE_NAME_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENT_PARAMS_COURSE_NAME);
            int KEY_EVENT_PARAMS_HOUT_TYPE_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENT_PARAMS_HOUT_TYPE);
            int KEY_EVENT_PARAMS_TYPE_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENT_PARAMS_TYPE);
            int KEY_EVENT_PARAMS_SEMESTER_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENT_PARAMS_SEMESTER);
            int KEY_EVENT_PARAMS_YEAR_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENT_PARAMS_YEAR);
            int KEY_EVENT_PARAMS_EVENT_NAME_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENT_PARAMS_EVENT_NAME);
            int KEY_EVENT_PARAMS_DATE_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENT_PARAMS_DATE);
            int KEY_EVENT_PARAMS_AUTHOR_INDEX = cursor.getColumnIndexOrThrow(KEY_EVENT_PARAMS_AUTHOR);

            while (cursor.moveToNext()) {
                EventItem item = new EventItem();
                item.setId(cursor.getInt(KEY_EVENTS_ID_INDEX));
                item.setEventText(cursor.getString(KEY_EVENTS_TEXT_INDEX));
                item.setSeen(cursor.getInt(KEY_EVENTS_SEEN_INDEX));
                item.setTime(cursor.getString(KEY_EVENTS_TIME_INDEX));
                item.setType(cursor.getInt(KEY_EVENTS_TYPE_INDEX));
                EventItem.EventParams params = item.getParamsObj();
                params.setId(cursor.getInt(KEY_EVENT_PARAMS_ID_INDEX));
                params.setMessage(cursor.getString(KEY_EVENT_PARAMS_MESSAGE_INDEX));
                params.setName(cursor.getString(KEY_EVENT_PARAMS_NAME_INDEX));
                params.setPhoto(cursor.getInt(KEY_EVENT_PARAMS_PHOTO_INDEX));
                params.setCode(cursor.getString(KEY_EVENT_PARAMS_CODE_INDEX));
                params.setGcourse(cursor.getInt(KEY_EVENT_PARAMS_GCOURSE_INDEX));
                params.setCourseName(cursor.getString(KEY_EVENT_PARAMS_COURSE_NAME_INDEX));
                params.setHourType(cursor.getInt(KEY_EVENT_PARAMS_HOUT_TYPE_INDEX));
                params.setType(cursor.getInt(KEY_EVENT_PARAMS_TYPE_INDEX));
                params.setSemester(cursor.getString(KEY_EVENT_PARAMS_SEMESTER_INDEX));
                params.setYear(cursor.getString(KEY_EVENT_PARAMS_YEAR_INDEX));
                params.setEventName(cursor.getString(KEY_EVENT_PARAMS_EVENT_NAME_INDEX));
                params.setDate(cursor.getString(KEY_EVENT_PARAMS_DATE_INDEX));
                params.setAuthor(cursor.getString(KEY_EVENT_PARAMS_AUTHOR_INDEX));
                items.add(item);
            }
            cursor.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mData = items;
        return items;
    }
}
