package com.waisblut.fishermanlogbook;

public enum EnumSQLiteTypes
{
    TEXT("TEXT"),
    NUMERIC("NUMERIC"),
    INTEGER("INTEGER"),
    REAL("REAL"),
    BLOB("BLOB");

    protected String code;

    public String getCode()
    {
        return this.code;
    }

    EnumSQLiteTypes(String s)
    {
        this.code = s;
    }
}
