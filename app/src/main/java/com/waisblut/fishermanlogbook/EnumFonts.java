package com.waisblut.fishermanlogbook;

public enum EnumFonts
{
    RN("Remington-Noiseless.ttf"),
    BASIC("BasicLig_ltd.ttf"),
    DIGITAL("lcdphone.ttf"),
    ABEAKRG("abeakrg.ttf");

    protected String code;

    public String getCode()
    {
        return this.code;
    }

    EnumFonts(String s)
    {
        this.code = s;
    }
}