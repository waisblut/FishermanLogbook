package com.waisblut.fishermanlogbook.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.waisblut.fishermanlogbook.Logger;

public class FishermanOpenHelper
        extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "fisherman.db";
    private static final int DATABASE_VERSION = 1;

    public FishermanOpenHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //    public FishermanOpenHelper(Context context,
    //                               String tableName,
    //                               List<String> columns,
    //                               List<String> columnsType,
    //                               List<String> columnsConstraint)
    //    {
    //
    //        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    //
    //        //this.tableName = tableName;
    //        //this.columns = columns;
    //        //this.columnsType = columnsType;
    //        //this.CreateTable = setCreateTable(tableName, columns, columnsType, columnsConstraint);
    //
    //    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        try
        {
            db.execSQL(Table.CreateEventTable());
            Logger.log('i', "Table EVENT has been Created!");
        }
        catch (SQLException e)
        {
            Logger.log('i', "ERROR: Table EVENT - " + e.getMessage());
        }

        try
        {
            db.execSQL(Table.CreateFishTable());
            Logger.log('i', "Table Fish has been Created!");
        }
        catch (SQLException e)
        {
            Logger.log('i', "ERROR: Table FISH - " + e.getMessage());
        }

        try
        {
            db.execSQL(Table.CreateEventFishTable());
            Logger.log('i', "Table EVENTFISHDETAIL has been Created!");
        }
        catch (SQLException e)
        {
            Logger.log('i', "ERROR: Table EVENTFISHDETAIL - " + e.getMessage());
        }

        try
        {
            db.execSQL(Table.CreatePeopleTable());
            Logger.log('i', "Table People has been Created!");
        }
        catch (SQLException e)
        {
            Logger.log('i', "ERROR: Table People - " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + Table.EVENTFISH);
        db.execSQL("DROP TABLE IF EXISTS " + Table.EVENT);
        db.execSQL("DROP TABLE IF EXISTS " + Table.FISH);
        db.execSQL("DROP TABLE IF EXISTS " + Table.PEOPLE);
        onCreate(db);
        Logger.log('i', "Table has been UpGraded!");
    }
}
