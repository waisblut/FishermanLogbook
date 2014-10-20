package com.waisblut.fishermanlogbook;

public enum EnumDirections
{
	N(R.string.N, 0), 
	NE(R.string.NE, 45), 
	E(R.string.E, 90), 
	SE(R.string.SE, 135), 
	S(R.string.S, 180), 
	SW(R.string.SW, 225), 
	W(R.string.W, 270), 
	NW(R.string.NW, 315);
	
	protected int code;
	protected int bearing;

	public int getCode()
	{
		return this.code;
	}
	
	public int getBearing()
	{
		return this.bearing;
	}
	
	EnumDirections(int i, int j)
	{
		this.code = i;
		this.bearing = j;
	}
}
