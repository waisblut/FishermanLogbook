package com.waisblut.fishermanlogbook;

import android.util.Log;

public final class Logger
{
    //region Project Constants
    private final static String TAG = "waisblut";
    public static final CharSequence SEP = "#@@@#";
    private final static boolean IS_DEBUG = BuildConfig.DEBUG;
    //endregion

    public static void log(char type, String s)
    {
        if (IS_DEBUG)
        {
            switch (type)
            {
            case 'd':
                Log.d(TAG, s);
                break;

            case 'e':
                Log.e(TAG, s);
                break;

            case 'i':
                Log.i(TAG, s);
                break;

            case 'v':
                Log.v(TAG, s);
                break;

            case 'w':
                Log.w(TAG, s);
                break;

            default:
                break;
            }
        }
    }
}

