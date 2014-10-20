package com.waisblut.fishermanlogbook;

public enum EnumEventType
{
	TOURNMENT(R.string.Tournment), 
	RECREATIONAL(R.string.Recreational), 
	COMMERCIAL(R.string.Commercial);

	protected int code;

	public int getCode()
	{
		return this.code;
	}
	
	EnumEventType(int i)
	{
		this.code = i;
	}
}
