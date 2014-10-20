package com.waisblut.fishermanlogbook;

import java.io.Serializable;

public class Fish
        implements Serializable
{
    private static final long serialVersionUID = 1L;

    private long fishId = 0;
    private String fishName;

    public Fish()
    {

    }

    public Fish(long fishId, String fishName)
    {
        this.fishId = fishId;
        this.fishName = fishName;
    }

    public long getFishId()
    {
        return fishId;
    }

    public void setFishId(long fishId)
    {
        this.fishId = fishId;
    }

    public String getFishName()
    {
        return fishName;
    }

    public void setFishName(String fishName)
    {
        this.fishName = fishName;
    }
}