package com.waisblut.fishermanlogbook;

import java.util.ArrayList;
import java.util.List;

public final class EventList
{
    //---------------------------------------------------------------------------------------
    private static List<Event> listOfEvents = new ArrayList<Event>();


    //---------------------------------------------------------------------------------------
    public static List<Event> getListOfEvents()
    {
        return listOfEvents;
    }


    //---------------------------------------------------------------------------------------
    public static void addItem(Event e)
    {
        listOfEvents.add(e);
    }

    public static void addItems(List<Event> e)
    {
        listOfEvents.addAll(e);
    }

    public static void removeItem(Event e)
    {
        listOfEvents.remove(e);
    }

    public static int getCount()
    {
        return listOfEvents.size();
    }

    public static Event get(int position)
    {
        return listOfEvents.get(position);
    }

    public static void recycle()
    {
        listOfEvents = new ArrayList<Event>();
    }
}
