package com.waisblut.fishermanlogbook.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

import com.waisblut.fishermanlogbook.EnumEventType;
import com.waisblut.fishermanlogbook.EnumFishermanColors;
import com.waisblut.fishermanlogbook.EnumFonts;
import com.waisblut.fishermanlogbook.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ActivityHelper
{
    // Custom Fonts
    // public final String FONT1 = "Remington-Noiseless.ttf";
    // public final String FONT2 = "BasicLig_ltd.ttf";
    // public final String FONT3 = "lcdphone.ttf";
    public final String MYEVENT = "MyEvent";
    public final CharSequence SEP = Logger.SEP;

    /**
     * Sets ActionBar Style
     *
     * @param activity Activity
     */
    @SuppressWarnings("ConstantConditions")
    @SuppressLint("NewApi")
    public void setTitle(Activity activity)
    {
        int actionBarTitleId;
        GradientDrawable gd;
        TextView title;

        // activity.getActionBar().setBackgroundDrawable(new
        // ColorDrawable(Color.parseColor("#333333")));

        gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                                  new int[]{0xff424242,
                                            0xff2a2a2a,
                                            0xff111111,
                                            0xff0a0a0a,
                                            0xff0d0d0d,
                                            0xff2a2a2a,
                                            0xff424242,
                                            0xff2a2a2a,
                                            0xff0d0d0d,
                                            0xff0a0a0a,
                                            0xff111111,
                                            0xff2a2a2a,
                                            0xff424242});
        /*
         * { 0xff000000, 0xff001500, 0xff002500, 0xff003000, 0xff003500,
		 * 0xff003000, 0xff002500, 0xff001500, 0xff000000 });
		 */
        // new int[] {0xFF616261,0xFF131313}

        gd.setCornerRadius(0f);

        if (android.os.Build.VERSION.SDK_INT >= 11)
        {
            activity.getActionBar().setBackgroundDrawable(gd);
        }

        actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");

        if (actionBarTitleId > 0)
        {
            title = (TextView) activity.findViewById(actionBarTitleId);

            if (title != null)
            {
                // setFont(activity, title, font2);
                // title.setTextColor(Color.parseColor("#ff9900"));
                title.setTextColor(EnumFishermanColors.Title_Orange.getCode());
                title.setShadowLayer(3, 3, 3, EnumFishermanColors.Shadow_Black.getCode());
                title.setTextSize(30);
                title.setTypeface(Typeface.MONOSPACE, 1); // SETTING BOLD
                this.setFont(activity, title, EnumFonts.ABEAKRG.getCode());
            }
        }
    }

	/*
     * @SuppressWarnings("deprecation")
	 * 
	 * @SuppressLint("NewApi") public void setBackGround(View v) {
	 * GradientDrawable gd = null; gd = new
	 * GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
	 * 0xff383838, 0xff141414, 0xff111111, 0xff0a0a0a, 0xff0d0d0d, 0xff060606,
	 * 0xff000000, 0xff060606, 0xff0d0d0d, 0xff0a0a0a, 0xff111111, 0xff141414,
	 * 0xff383838 });
	 * 
	 * 
	 * if (android.os.Build.VERSION.SDK_INT >= 16) { v.setBackground(gd); } else
	 * // if(android.os.Build.VERSION.SDK_INT >= 11) {
	 * v.setBackgroundDrawable(gd); } }
	 */

    public void setFont(Activity activity, TextView txtview, String fontName)
    {
        Typeface font = Typeface.createFromAsset(activity.getAssets(), fontName);
        txtview.setTypeface(font);
    }

    public void setFont(Context context, TextView txtview, String fontName)
    {
        Typeface font = Typeface.createFromAsset(context.getAssets(), fontName);
        txtview.setTypeface(font);
    }

    public Calendar convertStringToCalendar(String s)
    {
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
        Calendar calendar = Calendar.getInstance();

        try
        {
            calendar.setTime(sf.parse(s));
        }
        catch (java.text.ParseException e)
        {
            return null;
        }

        return calendar;
    }

    public Calendar convertStringToTime(String s)
    {
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm", Locale.US);
        Calendar calendar = Calendar.getInstance();

        try
        {
            calendar.setTime(sf.parse(s));
        }
        catch (java.text.ParseException e)
        {
            return null;
        }

        return calendar;
    }

    public String addZeroToInt(int i)
    {
        String ret = String.valueOf(i);

        if (ret.length() == 1)
        {
            ret = "0" + ret;
        }

        return ret;
    }

    public int compareDates(Calendar cMin, Calendar cMax)
    {

        return cMax.compareTo(cMin);
    }

    public List<String> getLstStrEventType(View v)
    {
        List<String> ret = new ArrayList<String>();
        for (EnumEventType e : EnumEventType.values())
        {
            ret.add(v.getResources().getString(e.getCode()));
        }

        return ret;
    }

    public float pixelsToSp(Context context, Float px)
    {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }
}