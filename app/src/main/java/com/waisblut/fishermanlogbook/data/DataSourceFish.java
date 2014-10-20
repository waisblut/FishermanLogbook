package com.waisblut.fishermanlogbook.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.waisblut.fishermanlogbook.EnumSQLiteTypes;
import com.waisblut.fishermanlogbook.Fish;
import com.waisblut.fishermanlogbook.Logger;

import java.util.ArrayList;
import java.util.List;

public class DataSourceFish
        extends MyDataSource
{
    public static final String TABLE_NAME = "Fish";
    public static final String FISH_ID = "_id";
    public static final String FISHNAME = "FishName";

    public DataSourceFish()
    {
        super();

        setIni();
    }

    public DataSourceFish(Context context)
    {
        super(context);

        setIni();

        super.fisherDbHelper = new FishermanOpenHelper(context);
    }

    private void setIni()
    {
        super.tableName = TABLE_NAME;

        super.setColumn(FISH_ID, EnumSQLiteTypes.INTEGER, "PRIMARY KEY AUTOINCREMENT");
        super.setColumn(FISHNAME, EnumSQLiteTypes.TEXT, "UNIQUE NOT NULL");
    }

    public Fish create(Fish f)
    {
        open();

        long insertId;
        ContentValues values = new ContentValues();

        values.put(columns.get(1), f.getFishName());

        try
        {
            insertId = database.insertOrThrow(tableName, null, values);
        }
        catch (SQLException ex)
        {
            Logger.log('i', tableName + " ERROR - " + ex.getMessage());

            return f;
        }

        f.setFishId(insertId);

        Logger.log('i', tableName + "???" + " being created \n with ID=" + insertId);

        return f;
    }

    public boolean fishExists(Long fishId)
    {
        return super.idExists(fishId, FISH_ID);
    }

    public List<Fish> getAllFishes()
    {
        List<Fish> lstFish = new ArrayList<Fish>();
        Fish f;

        Cursor c = null;
        try
        {
            c = database.query(this.tableName,
                               super.columns.toArray(new String[columns.size()]),
                               null,
                               null,
                               null,
                               null,
                               null);
            Logger.log('i', this.tableName + " Returned " + c.getCount() + " rows");
        }
        catch (Exception ex)
        {
            Logger.log('i', "ERROR READING " + tableName + ": " + ex.getMessage());
        }

        assert c != null;
        if (c.getCount() > 0)
        {
            while (c.moveToNext())
            {
                f = new Fish();

                f.setFishId(c.getLong(c.getColumnIndex(FISH_ID)));
                f.setFishName(c.getString(c.getColumnIndex(FISHNAME)));

                lstFish.add(f);
            }
        }

        return lstFish;
    }

    public Fish getFish(Long id)
    {
        Fish f = new Fish();

        String WHERECLAUSE = FISH_ID + "=" + id;

        Cursor c = null;

        try
        {
            c = database.query(this.tableName,
                               super.columns.toArray(new String[columns.size()]),
                               WHERECLAUSE,
                               null,
                               null,
                               null,
                               null);
        }
        catch (Exception ex)
        {
            Logger.log('i', "ERROR READING " + tableName + ": " + ex.getMessage());
        }

        assert c != null;
        if (c.getCount() > 0)
        {
            while (c.moveToNext())
            {
                f.setFishId(c.getLong(c.getColumnIndex(FISH_ID)));
                f.setFishName(c.getString(c.getColumnIndex(FISHNAME)));
            }
        }

        return f;
    }
}