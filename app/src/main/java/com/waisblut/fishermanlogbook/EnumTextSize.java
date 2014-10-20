package com.waisblut.fishermanlogbook;

public enum EnumTextSize
{
    Tiny(12f),
    Small(14f),
    Medium(18f),
    Large(24f),
    ExtraLarge(48f);

    protected float code;

    public float getCode()
    {
        return this.code;
    }

    EnumTextSize(float f)
    {
        this.code = f;
    }
}

