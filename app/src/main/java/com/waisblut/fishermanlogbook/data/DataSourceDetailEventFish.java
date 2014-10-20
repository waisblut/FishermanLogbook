package com.waisblut.fishermanlogbook.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.waisblut.fishermanlogbook.EnumSQLiteTypes;
import com.waisblut.fishermanlogbook.Event;
import com.waisblut.fishermanlogbook.Fish;
import com.waisblut.fishermanlogbook.Logger;

import java.util.Arrays;

public class DataSourceDetailEventFish
        extends MyDataSource
{
    // FishermanOpenHelper fisherDbHelper;
    // SQLiteDatabase database;

    public static final String TABLE_NAME = "EventFishDetail";
    public static final String FISH_ID = "Fish_Id";            // FK
    public static final String EVENT_ID = "Event_Id";            // FK
    public static final String FISH_WEIGHT = "Fish_Weight";        // KG
    public static final String FISH_AMOUNT = "Fish_Amount";        // Qty

    // static final String FK_EVENT_ID = "FKEventId";
    // static final String FK_FISH_ID = "FKFishId";

    public DataSourceDetailEventFish()
    {
        super();

        setIni();
    }

    public DataSourceDetailEventFish(Context context)
    {
        super(context);

        setIni();

        fisherDbHelper = new FishermanOpenHelper(context);
    }

    private void setIni()
    {

        super.tableName = TABLE_NAME;

        super.setColumn(EVENT_ID, EnumSQLiteTypes.INTEGER, "");
        super.setColumn(FISH_ID, EnumSQLiteTypes.INTEGER, "");
        super.setColumn(FISH_AMOUNT, EnumSQLiteTypes.INTEGER, "CHECK (FISH_WEIGHT >0)");
        super.setColumn(FISH_WEIGHT, EnumSQLiteTypes.REAL, "CHECK (FISH_WEIGHT >=0)");

        super.setTableConstraint(super.getForeignKeyString(EVENT_ID,
                                                           DataSourceEvent.TABLE_NAME,
                                                           DataSourceEvent.EVENT_ID));
        super.setTableConstraint(super.getForeignKeyString(FISH_ID,
                                                           DataSourceFish.TABLE_NAME,
                                                           DataSourceFish.FISH_ID));

        super.setTableConstraint(super.getMultipleUniqueConstraint(Arrays.asList(EVENT_ID,
                                                                                 FISH_ID)));
    }

    public void create(Event e, Fish f)
    {
        if (e.getId() > 0 && f.getFishId() > 0)
        {
            open();

            // Event event = (Event) e;
            // Fish fish = (Fish) f;

            ContentValues values = new ContentValues();

            values.put(EVENT_ID, e.getId());
            values.put(FISH_ID, f.getFishId());
            values.put(FISH_AMOUNT, e.getQtde(f));
            values.put(FISH_WEIGHT, e.getWeight(f));

            try
            {
                database.insertOrThrow(tableName, null, values);
            }
            catch (SQLException ex)
            {
                Logger.log('i', tableName + " ERROR - " + ex.getMessage());
                return;
            }

            Logger.log('i', tableName + "???" + " being created \n with IDEVENT=" + e.getId() +
                            " AND IDFISH=" + f.getFishId());

            // super.fullIdSelectOnTableOnLogCat();
        }
    }

    public int getTotalSumCaught()
    {
        open();

        int sum = 0;

        Cursor c = null;

        try
        {
            c = database.rawQuery("SELECT SUM(" + FISH_AMOUNT + ") FROM " + TABLE_NAME, null);
        }
        catch (Exception e)
        {
            Logger.log('i', "ERROR:" + e.getMessage());
        }

        assert c != null;
        if (c.moveToFirst())
        {
            sum = c.getInt(0);
        }

        return sum;
    }

    @SuppressWarnings({"StringBufferReplaceableByString",
                       "StringConcatenationInsideStringBufferAppend"})
    public Cursor getFishesFromEvent(Long eventId, int orderBy, boolean asc)
    {
        open();

        Cursor c;

        //Fish f;
        //String where = EVENT_ID + "=" + eventId;
        String[] order_by = {FISH_AMOUNT, DataSourceFish.FISHNAME, FISH_WEIGHT};

        try
        {
            // c = database.query(TABLE_NAME, strColumns, where, null, null,
            // null, order_by[0]);
            StringBuilder select = new StringBuilder();

            select.append("SELECT EF." + FISH_AMOUNT);
            select.append(", F." + DataSourceFish.FISHNAME);
            select.append(", EF." + FISH_WEIGHT);
            select.append(", F." + DataSourceFish.FISH_ID);
            select.append("\nFROM " + tableName + " EF, " + DataSourceFish.TABLE_NAME + " F ");
            select.append("\nWHERE EF." + FISH_ID + "=F." + DataSourceFish.FISH_ID);
            select.append(" AND EF." + EVENT_ID + "=" + eventId);
            select.append("\nORDER BY " + order_by[orderBy - 1] + (asc ? "" : " DESC"));

            c = database.rawQuery(select.toString(), null);

        }
        catch (Exception ex)
        {
            Logger.log('i', "ERROR:" + ex.getMessage());
            return null;
        }

        return c;
    }

    public Event setFishesToEventObject(Event e, int orderBy, boolean asc)
    {
        Cursor c = getFishesFromEvent(e.getId(), orderBy, asc);

        Fish f;

        e.clearAllFishes();

        if (c != null && c.getCount() > 0)
        {
            c.moveToFirst();

            while (!c.isAfterLast())
            {
                f = new Fish(c.getLong(c.getColumnIndex(DataSourceFish.FISH_ID)),
                             c.getString(c.getColumnIndex(DataSourceFish.FISHNAME)));

                int amount = c.getInt(c.getColumnIndex(FISH_AMOUNT));
                Float weight = c.getFloat(c.getColumnIndex(FISH_WEIGHT));

                e.setFishes(f, amount, weight, (float) 0L);

                c.moveToNext();
            }
        }

        return e;
    }
}
