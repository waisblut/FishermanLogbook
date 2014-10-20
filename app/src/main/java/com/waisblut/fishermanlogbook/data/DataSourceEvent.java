package com.waisblut.fishermanlogbook.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.waisblut.fishermanlogbook.EnumEventType;
import com.waisblut.fishermanlogbook.EnumSQLiteTypes;
import com.waisblut.fishermanlogbook.Event;
import com.waisblut.fishermanlogbook.Fish;
import com.waisblut.fishermanlogbook.Logger;

import java.util.ArrayList;
import java.util.List;

public class DataSourceEvent
        extends MyDataSource
{
    public static final String TABLE_NAME = "Event";

    static final String EVENT_ID = "_id";
    static final String EVENT_NAME = "EventName";
    static final String EVENT_LOCATIONNAME = "EventLocationName";
    static final String START_DATETIME = "StartDateTime";
    static final String END_DATETIME = "EndDateTime";
    // TODO: LOCATION
    // TODO: PEOPLE WITH
    static final String EVENT_TYPE = "EventType";
    // TODO: DAY CONDITION
    // String moon = null;
    // String sunRiseTime = null;
    // String sunSetTime = null;
    static final String DAY_TEMPERATURE = "DayTemp";
    static final String AIR_PRESSURE = "AirPressure";
    static final String WIND_DIRECTION = "WindDirection";
    static final String ISFAVORITE = "IsFavorite";
    static final String RATING = "Rating";

    // static final String WIND_SPEED = "WindSpeed";

    public DataSourceEvent()
    {
        super();

        setIni();
    }

    public DataSourceEvent(Context context)
    {
        super(context);

        setIni();

        super.fisherDbHelper = new FishermanOpenHelper(context);
    }

    private void setIni()
    {
        super.tableName = TABLE_NAME;

        setColumn(EVENT_ID, EnumSQLiteTypes.INTEGER, "PRIMARY KEY AUTOINCREMENT");
        setColumn(EVENT_NAME, EnumSQLiteTypes.TEXT, "UNIQUE");
        setColumn(EVENT_LOCATIONNAME, EnumSQLiteTypes.TEXT, "");
        setColumn(START_DATETIME, EnumSQLiteTypes.REAL, "");
        setColumn(END_DATETIME, EnumSQLiteTypes.REAL, "");
        setColumn(EVENT_TYPE, EnumSQLiteTypes.TEXT, "");
        setColumn(WIND_DIRECTION, EnumSQLiteTypes.INTEGER, "");
        setColumn(ISFAVORITE, EnumSQLiteTypes.INTEGER, "");
        setColumn(RATING, EnumSQLiteTypes.INTEGER, "");
    }

    public Event create(Event e)
    {
        open();

        long insertId;
        ContentValues values = new ContentValues();

        values.put(EVENT_NAME, e.getName());
        values.put(EVENT_LOCATIONNAME, e.getLocationName());
        values.put(START_DATETIME, e.getStartDateTime().getTime());
        values.put(END_DATETIME, e.getEndDateTime().getTime());

        values.put(WIND_DIRECTION, e.getWindDirection());
        values.put(ISFAVORITE, (e.IsFavorite()) ? 1 : 0);
        values.put(RATING, e.getRating());

        try
        {
            insertId = database.insertOrThrow(tableName, null, values);
        }
        catch (SQLException ex)
        {
            Logger.log('i', tableName + " ERROR - " + ex.getMessage());
            return e;
        }

        e.setId(insertId);

        Logger.log('i', tableName + " item being created \n with ID=" + insertId);
        // super.fullIdSelectOnTableOnLogCat();

        testDateDiff();

        // Inserindo os Peixes desse EVENTO no Banco
        setFishesOnDB(e);

        return e;
    }

    private void testDateDiff()
    {
        Cursor c = null;
        try
        {
            c = database.rawQuery(
                    "SELECT " + END_DATETIME + "-" + START_DATETIME + " AS DIFF FROM " + TABLE_NAME,
                    null);
        }
        catch (Exception ex)
        {
            Logger.log('i', "TESTING DATES: " + ex.getMessage());
        }

        if (c != null)
        {
            c.moveToFirst();

            while (!c.isAfterLast())
            {
                Logger.log('i', "TESTING DATES: " + c.getLong(0));
                c.moveToNext();
            }
        }
    }

    private void setFishesOnDB(Event e)
    {
        DataSourceDetailEventFish dsEventFish = new DataSourceDetailEventFish(super.context);

        for (Fish f : e.getLstFishes())
        {
            dsEventFish.create(e, f);
        }
    }

    public boolean eventExists(Long eventId)
    {
        return super.idExists(eventId, EVENT_ID);
    }

    public List<Event> getAllEvents()
    {
        open();

        Event e;
        Cursor c = null;
        DataSourceDetailEventFish dsFishEvent = new DataSourceDetailEventFish(super.context);
        List<Event> ret = new ArrayList<Event>();

        try
        {
            c = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            Logger.log('i', "READING ALL FROM " + tableName);
        }
        catch (Exception ex)
        {
            Logger.log('i', "ERROR READING " + tableName + ": " + ex.getMessage());
        }

        if (c != null && c.getCount() > 0)
        {
            c.moveToFirst();

            while (!c.isAfterLast())
            {
                e = new Event();

                e.setId(c.getLong(c.getColumnIndex(EVENT_ID)));
                e.setName(c.getString(c.getColumnIndex(EVENT_NAME)));
                e.setLocationName(c.getString(c.getColumnIndex(EVENT_LOCATIONNAME)));
                e.setStartDateTime(c.getLong(c.getColumnIndex(START_DATETIME)));
                e.setEndDateTime(c.getLong(c.getColumnIndex(END_DATETIME)));
                e.setEventType(EnumEventType.RECREATIONAL);

                e.setWindDirection(c.getInt(c.getColumnIndex(WIND_DIRECTION)));
                e.IsFavorite((c.getInt(c.getColumnIndex(ISFAVORITE))) == 1);

                e.setRating(c.getInt(c.getColumnIndex(RATING)));

                dsFishEvent.setFishesToEventObject(e, 1, true);

                c.moveToNext();

                ret.add(e);
            }

            return ret;
        }
        else
        {
            return null;
        }
    }

    public void updateFavorite(long eventId, boolean isFavorite)
    {
        open();

        ContentValues values = new ContentValues();
        String where = EVENT_ID + "=" + eventId;

        values.put(ISFAVORITE, (isFavorite) ? 1 : 0);

        try
        {
            database.update(tableName, values, where, null);
            Logger.log('i', tableName + "UPDATING FAVORITE TO: " + ((isFavorite) ? 1 : 0));
        }
        catch (SQLException ex)
        {
            Logger.log('i', tableName + " ERROR - UPDATE: " + ex.getMessage());
        }
    }
}
