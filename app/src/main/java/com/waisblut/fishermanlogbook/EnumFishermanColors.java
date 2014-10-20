package com.waisblut.fishermanlogbook;

public enum EnumFishermanColors
{
    Title_Orange(0xffff9900),
    Shadow_Black(0xff000000),
    Number_Blue(0xff525efa);

    protected int code;

    public int getCode()
    {
        return this.code;
    }

    EnumFishermanColors(int i)
    {
        this.code = i;
    }
}
