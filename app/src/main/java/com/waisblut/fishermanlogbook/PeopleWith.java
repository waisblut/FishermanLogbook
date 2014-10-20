package com.waisblut.fishermanlogbook;

public class PeopleWith
{
    private long personId = 0;
    private String personName = null;

    public PeopleWith()
    {

    }

    public long getId()
    {
        return personId;
    }

    public void setId(long personId)
    {
        this.personId = personId;
    }

    public String getName()
    {
        return personName;
    }

    public void setName(String personName)
    {
        this.personName = personName;
    }
}
