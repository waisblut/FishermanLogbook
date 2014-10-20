package com.waisblut.fishermanlogbook.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.waisblut.fishermanlogbook.EnumSQLiteTypes;
import com.waisblut.fishermanlogbook.Logger;

import java.util.ArrayList;
import java.util.List;

public class MyDataSource
{
    FishermanOpenHelper fisherDbHelper;
    SQLiteDatabase database = null;
    Context context = null;

    protected String tableName = "";

    List<String> columns = new ArrayList<String>();
    List<String> columnsType = new ArrayList<String>();
    List<String> columnsExtra = new ArrayList<String>();
    List<String> columnsConst = new ArrayList<String>();

    public MyDataSource()
    {

    }

    public MyDataSource(Context context)
    {
        this.context = context;
    }

    protected void setColumn(String columnName, EnumSQLiteTypes type, String extra)
    {
        this.columns.add(columnName);
        this.getColumnsType().add(type.getCode());
        this.columnsExtra.add(extra);
    }

    protected void setTableConstraint(String constr)
    {
        this.columnsConst.add(constr);
    }

    protected String getForeignKeyString(String fkName, String pkTableName, String pkFieldName)
    {

        return " FOREIGN KEY (" + fkName + ") " + "references " + pkTableName + " (" +
               pkFieldName + ") " + "ON DELETE CASCADE ON UPDATE CASCADE";
    }

    protected String getMultipleUniqueConstraint(List<String> Fields)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(" UNIQUE(");
        for (String s : Fields)
        {
            sb.append(s).append(", ");
        }
        sb.replace(sb.lastIndexOf(","), sb.lastIndexOf(",") + 1, "");

        sb.append(")");

        return sb.toString();
    }

    protected boolean idExists(Long id, String IdColumn)
    {
        {
            open();

            Cursor c = null;

            try
            {
                c = database.rawQuery("SELECT " + IdColumn + " FROM " + tableName, null);
            }
            catch (Exception ex)
            {
                Logger.log('i', "ERROR:" + ex.getMessage());
            }

            if (c != null && c.getCount() > 0)
            {
                c.moveToFirst();

                while (!c.isAfterLast())
                {
                    if (id == c.getLong(0))
                    {
                        Logger.log('i',

                                   "FOUND " + IdColumn + ":" + c.getLong(0));

                        return true;
                    }

                    c.moveToNext();
                }
            }

            Logger.log('i', IdColumn + " " + id + " NOT FOUND!");
            return false;
        }
    }

    public void open()
    {
        // if (database == null)
        // {
        Logger.log('i', "TABLE " + tableName + " called - Database is Opened!");
        database = fisherDbHelper.getWritableDatabase();
    }

    public void close()
    {
        Logger.log('i', "TABLE " + tableName + " - Database Closed!");
        fisherDbHelper.close();
    }

    @SuppressLint("DefaultLocale")
    public void selectOnTableOnLogCat()
    {
        open();

        StringBuilder tag = new StringBuilder();
        Cursor c = null;

        try
        {
            c = database.rawQuery("SELECT * FROM " + tableName + ";", null);
        }
        catch (Exception e)
        {
            Logger.log('i', "ERROR:" + e.getMessage());
        }

        if (c != null && c.getCount() > 0)
        {
            c.moveToFirst();

            tag.append(this.tableName).append(" has:\n");

            for (int i = 0; i < c.getColumnCount(); i++)
            {
                tag.append(c.getColumnName(i).toUpperCase())
                   .append(" - \t")
                   .append(c.getType(i))
                   .append("\n");
            }

            tag.append("-----------------\n");

            while (!c.isAfterLast())
            {
                for (int i = 0; i < c.getColumnCount(); i++)
                {
                    tag.append(c.getString(i)).append(" - \t");
                }

                tag.replace(tag.lastIndexOf("-"), tag.lastIndexOf("-") + 1, "");
                tag.append("\n");

                c.moveToNext();
            }

            tag.append("-----------------\n\n\n");

            Logger.log('i', tag.toString());

            c.close();
        }
    }

    public int getRowsCount()
    {
        Cursor c = database.rawQuery("SELECT 1 FROM " + tableName + ";", null);
        int i = c.getCount();

        Logger.log('i', "Checking " + tableName + " ROWS AMOUNT = " + i);

        return i;
    }

    public int getColumnsCount()
    {
        Cursor c = database.rawQuery("SELECT * FROM " + tableName + ";", null);
        int i = c.getColumnCount();

        Logger.log('i', "Checking " + tableName + " COLUMNS AMOUNT = " + i);

        return i;
    }

    public List<String> getColumnsName()
    {
        return columns;
    }

    public List<String> getColumnsType()
    {
        return columnsType;
    }
}
