package com.waisblut.fishermanlogbook.data;


public final class Table
{
    static String EVENT = DataSourceEvent.TABLE_NAME;
    static String FISH = DataSourceFish.TABLE_NAME;
    static String EVENTFISH = DataSourceDetailEventFish.TABLE_NAME;
    static String PEOPLE = DataSourcePeople.TABLE_NAME;

    public Table()
    {

    }

    private static String CreateTable(MyDataSource ds)
    {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE ");
        sb.append(ds.tableName);
        sb.append(" (\n");

        for (int i = 0; i < ds.columns.size(); i++)
        {
            String constraint = ds.columnsExtra.get(i) == null ? "" : ds.columnsExtra.get(i);
            sb.append(ds.columns.get(i))
              .append(" ")
              .append(ds.columnsType.get(i))
              .append(" ")
              .append(constraint)
              .append(", \n");
        }

        for (int i = 0; i < ds.columnsConst.size(); i++)
        {
            sb.append(ds.columnsConst.get(i)).append(", \n");
        }

        sb.replace(sb.lastIndexOf(","), sb.lastIndexOf(",") + 1, "");

        sb.append(") ");

        return sb.toString();
    }

    public static String CreateEventTable()
    {
        return CreateTable(new DataSourceEvent());
    }

    public static String CreateFishTable()
    {
        return CreateTable(new DataSourceFish());
    }

    public static String CreateEventFishTable()
    {
        return CreateTable(new DataSourceDetailEventFish());
    }

    public static String CreatePeopleTable()
    {
        return CreateTable(new DataSourcePeople());
    }
}
