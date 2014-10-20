package com.waisblut.fishermanlogbook.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.waisblut.fishermanlogbook.EnumSQLiteTypes;
import com.waisblut.fishermanlogbook.Logger;
import com.waisblut.fishermanlogbook.PeopleWith;

import java.util.ArrayList;
import java.util.List;

public class DataSourcePeople
        extends MyDataSource
{
    public static final String TABLE_NAME = "People";
    public static final String PERSON_ID = "_id";
    public static final String PERSON_NAME = "PersonName";

    public DataSourcePeople()
    {
        super();

        setIni();
    }

    public DataSourcePeople(Context context)
    {
        super(context);

        setIni();

        super.fisherDbHelper = new FishermanOpenHelper(context);
    }

    private void setIni()
    {
        super.tableName = TABLE_NAME;

        super.setColumn(PERSON_ID, EnumSQLiteTypes.INTEGER, "PRIMARY KEY AUTOINCREMENT");
        super.setColumn(PERSON_NAME, EnumSQLiteTypes.TEXT, "UNIQUE NOT NULL");
    }

    public PeopleWith create(PeopleWith p)
    {
        open();

        long insertId;
        ContentValues values = new ContentValues();

        values.put(columns.get(1), p.getName());

        try
        {
            insertId = database.insertOrThrow(tableName, null, values);
        }
        catch (SQLException ex)
        {
            Logger.log('i', tableName + " ERROR - " + ex.getMessage());
            return p;
        }

        Logger.log('i', tableName + "???" + " being created \n with ID=" + insertId);

        p.setId(insertId);

        return p;
    }

    public boolean personExists(Long personId)
    {
        return super.idExists(personId, PERSON_ID);
    }

    public List<PeopleWith> getAllPeople()
    {
        List<PeopleWith> lstPeople = new ArrayList<PeopleWith>();
        PeopleWith p;

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
                p = new PeopleWith();

                p.setId(c.getLong(c.getColumnIndex(PERSON_ID)));
                p.setName(c.getString(c.getColumnIndex(PERSON_NAME)));

                lstPeople.add(p);
            }
        }

        return lstPeople;
    }

    public PeopleWith getPerson(Long id)
    {
        PeopleWith p = new PeopleWith();

        String WHERECLAUSE = PERSON_ID + "=" + id;

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
                p.setId(c.getLong(c.getColumnIndex(PERSON_ID)));
                p.setName(c.getString(c.getColumnIndex(PERSON_NAME)));
            }
        }

        return p;
    }
}