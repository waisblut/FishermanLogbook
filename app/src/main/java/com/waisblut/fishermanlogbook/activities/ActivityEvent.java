package com.waisblut.fishermanlogbook.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.waisblut.fishermanlogbook.EnumDirections;
import com.waisblut.fishermanlogbook.EnumFishermanColors;
import com.waisblut.fishermanlogbook.Event;
import com.waisblut.fishermanlogbook.Fish;
import com.waisblut.fishermanlogbook.R;
import com.waisblut.fishermanlogbook.data.DataSourceDetailEventFish;
import com.waisblut.fishermanlogbook.data.DataSourceFish;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ActivityEvent
        extends FragmentActivity
        implements OnClickListener
{
    ActivityHelper ah = new ActivityHelper();
    boolean mCanEdit = false;

    View mainView = null;

    Event event = null;

    Button btnEventName = null;

    Button btnQtde = null;
    TextView txtFishes = null;

    EditText edtPeopleWith = null;
    Spinner spnPeopleWith = null;

    RatingBar rtFavorite = null;
    RatingBar rtRating = null;

    TextView txtFrom = null;
    TextView txtTo = null;

    Button btnStartDate = null;
    Button btnStartTime = null;

    Button btnEndDate = null;
    Button btnEndTime = null;

    final String strSTARTDATE = "StartDate";
    final String strENDDATE = "EndDate";

    String strSelectedDate = null;
    String strSelectedTime = null;

    DialogFragment dialogDateFragment = null;
    DialogFragment dialogTimeFragment = null;
    int iPickerErrorCount = 0;

    ListView mListView = null;
    Dialog dlgQtdeFishes = null;
    AlertDialog.Builder builder = null;
    List<String> mLstEfs = null;
    private int sortPosition = 1;
    private boolean sortAscending = true;

    Spinner spnEventType = null;

    CheckBox chkHasBoat = null;
    Spinner spnBoat = null;
    Spinner spnEngine = null;

    TableRow tr1 = null;
    TableRow tr2 = null;
    TableRow tr3 = null;
    TableRow tr4 = null;

    SeekBar skTemperature = null;
    SeekBar skPressure = null;
    SeekBar skWaterTemperature = null;

    Dialog dlgEditEventName = null;
    MyFishAdapter adapter = null;
    EditText edtEditEventName = null;
    ImageButton btnOK = null;
    ImageButton btnCancel = null;

    ImageView imgDirection = null;
    TextView txtDirection = null;
    EditText edtDirection = null;
    ArrayList<EnumDirections> lstDirections = null;

    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();

        }

        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (mCanEdit)
        {
            getMenuInflater().inflate(R.menu.actionmenueventsavecancel, menu);
        }
        else
        {
            getMenuInflater().inflate(R.menu.actionmenueventedit, menu);
        }
        ah.setTitle(this);

        return super.onCreateOptionsMenu(menu);
        // return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Toast.makeText(this, item.getTitle() + " Clicked!", Toast.LENGTH_SHORT).show();
        // TODO create a Confirm MENU

        if (item.getItemId() == R.id.actionevent_edit)
        {
            mCanEdit = true;
        }
        else
        {
            mCanEdit = false;
        }

        setClickable(mCanEdit);
        invalidateOptionsMenu();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        this.getWindow()
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        event = (Event) this.getIntent().getSerializableExtra(ah.MYEVENT);

        setControlsPortrait();
        //TESTECHANGEHERE

        // ---------------BACKGROUND---------------------------------------------
        // ah.setBackGround(mainView);

        // ---------------NAME/TITLE-----------------------------------------------
        this.setTitle(event.getName());
        ah.setTitle(this);
        btnEventName.setOnClickListener(this);

        // --------------RATINGS---------------------------------------------------
        setFavorite();
        rtRating.setRating(event.getRating());

        // ----------------FISHES---------------------------------------------------
        setFishes();

        // -------------PEOPLE
        // WITH-----------------------------------------------
        setPeopleWith();

        // ------EVENT Date and
        // Time---------------------------------------------
        setDate();
        setTime();

        // --EVENT
        // Type-----------------------------------------------------------
        setEventType();

        // ---AIR Water
        // Condition-------------------------------------------------
        setAirWater();

        // --BOAT and ENGINE----------------------------------------------------
        setBoat();

        // --WIND DIRECTION----------------------------------------------------
        setWindDirection();

        // -------------------------------------------------------------------------
        setClickable(mCanEdit);

        //---SET TEXTs SIZE
    }

    private void setPeopleWith()
    {
        event.getPeopleWith();
    }

    private void setWindDirection()
    {
        // list of directions
        lstDirections = new ArrayList<EnumDirections>(Arrays.asList(EnumDirections.values()));

        // direction from actual event
        // float windDirection =
        txtDirection.setText(event.getWindDirectionName());
        // edtDirection.setText(String.valueOf(event.getWindDirection()));

        // Rotate BitMap
        rotateBitmap();

    }

    private void rotateBitmap()
    {
        Bitmap myImg = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_fish);

        Matrix matrix = new Matrix();
        matrix.postRotate(event.getWindDirection());

        Bitmap rotated = Bitmap.createBitmap(myImg,
                                             0,
                                             0,
                                             myImg.getWidth(),
                                             myImg.getHeight(),
                                             matrix,
                                             true);

        imgDirection.setImageBitmap(rotated);
    }

    private void setControlsPortrait()
    {
        // ---------------BACKGROUND---------------------------------------------------
        mainView = findViewById(R.id.event_mainLayout);

        // ---------------NAME/TITLE---------------------------------------------
        btnEventName = (Button) findViewById(R.id.event_btnEditEventName);

        // --------------RATINGS-----------------------------------------
        rtFavorite = (RatingBar) findViewById(R.id.event_Favorite);
        rtRating = (RatingBar) findViewById(R.id.event_ratingBar);

        // ----------------FISHES------------------------------------------
        txtFishes = (TextView) findViewById(R.id.event_txtQtde);
        btnQtde = (Button) findViewById(R.id.event_btnQtde);

        // -------PEOPLE WITH------------------------------------------------
        edtPeopleWith = (EditText) findViewById(R.id.event_edtPeopleWith);
        spnPeopleWith = (Spinner) findViewById(R.id.event_spnPeopleWith);

        // ------START DATE-------------END DATE------------------------------
        txtFrom = (TextView) findViewById(R.id.event_txtFrom);
        txtTo = (TextView) findViewById(R.id.event_txtTo);
        btnStartDate = (Button) findViewById(R.id.event_btnStartDate);
        btnEndDate = (Button) findViewById(R.id.event_btnEndDate);

        // ------START TIME-------------END TIME------------------------------
        btnStartTime = (Button) findViewById(R.id.event_btnStartTime);
        btnEndTime = (Button) findViewById(R.id.event_btnEndTime);

        // --EVENT TYPE--------------------------------------------------------
        spnEventType = (Spinner) findViewById(R.id.event_spnEventType);

        // --AIR and WATER Condition--------------------------------------------
        skTemperature = (SeekBar) findViewById(R.id.event_skDayTemp);
        skPressure = (SeekBar) findViewById(R.id.event_skAirPressure);
        skWaterTemperature = (SeekBar) findViewById(R.id.event_skWaterTemp);

        // --BOAT and ENGINE----------------------------------------------------
        chkHasBoat = (CheckBox) findViewById(R.id.event_chkHasBoat);
        spnBoat = (Spinner) findViewById(R.id.event_spnBoat);
        spnEngine = (Spinner) findViewById(R.id.event_spnEngine);

        tr1 = (TableRow) findViewById(R.id.event_trNavigation);
        tr2 = (TableRow) findViewById(R.id.event_trEngine);
        tr3 = (TableRow) findViewById(R.id.event_trNavFrom);
        tr4 = (TableRow) findViewById(R.id.event_trNavTo);

        // --WIND
        // DIRECTION-------------------------------------------------------------
        imgDirection = (ImageView) findViewById(R.id.event_imgDirection);
        txtDirection = (TextView) findViewById(R.id.event_txtDirection);
        edtDirection = (EditText) findViewById(R.id.event_edtDirection);
    }

    private void setClickable(boolean canEdit)
    {
        mainView.setClickable(canEdit);

        btnEventName.setClickable(canEdit);

        btnStartDate.setClickable(canEdit);
        btnStartTime.setClickable(canEdit);

        btnEndDate.setClickable(canEdit);
        btnEndTime.setClickable(canEdit);

        spnPeopleWith.setClickable(canEdit);// TODO Check HERE!
        edtPeopleWith.setClickable(canEdit);
        edtPeopleWith.setEnabled(canEdit);

        spnEventType.setClickable(canEdit);

        rtRating.setIsIndicator(!canEdit);
        rtRating.setClickable(canEdit);

        rtFavorite.setIsIndicator(!canEdit);
        rtFavorite.setClickable(canEdit);

        chkHasBoat.setClickable(canEdit);
        spnBoat.setClickable(canEdit);
        spnEngine.setClickable(canEdit);

        // skTemperature.setClickable(canEdit);
        // skPressure.setClickable(canEdit);
        // skWaterTemperature.setClickable(canEdit);

        skTemperature.setEnabled(canEdit);
        skPressure.setEnabled(canEdit);
        skWaterTemperature.setEnabled(canEdit);

        txtDirection.setClickable(canEdit);
        edtDirection.setClickable(canEdit);
        edtDirection.setEnabled(canEdit);
    }

    private void setAirWater()
    {
        // skTemperature.setMax(MAXTEMP); // Enquanto for Graus Celsius
        // skPressure.setMax(1000);
        // skWaterTemperature.setMax(1000);

        // skTemperature.setProgress(convertEventTemperatureToSeekBar(event.getTemperature()));
        // skPressure.setProgress(event.getPressure());
        // skWaterTemperature.setProgress(event.getWaterTemperature());
    }

    /*
     * private int convertEventTemperatureToSeekBar(float f) { int ret =
     * Math.round(f * 10 + MAXTEMP / 2);
     *
     * return ret; }
     *
     * private float convertSeekBarToEventTemperature(int i) { if (i == 0)
     * return 0f;
     *
     * float ret = (((float) i) / 10) - MAXTEMP / 2;
     *
     * return ret; }
     */
    private void setBoat()
    {
        chkHasBoat.setChecked(event.hasBoat());
        setBoatComponentsVisibility();

        chkHasBoat.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                setBoatComponentsVisibility();
            }
        });
    }

    private void setBoatComponentsVisibility()
    {

        if (chkHasBoat.isChecked())
        {
            tr1.setVisibility(View.VISIBLE);
            tr2.setVisibility(View.VISIBLE);
            tr3.setVisibility(View.VISIBLE);
            tr4.setVisibility(View.VISIBLE);
        }
        else
        {
            tr1.setVisibility(View.GONE);
            tr2.setVisibility(View.GONE);
            tr3.setVisibility(View.GONE);
            tr4.setVisibility(View.GONE);
        }
    }

    private void setEventType()
    {
        ArrayAdapter<String> adpEventType = new ArrayAdapter<String>(this,
                                                                     android.R.layout.simple_spinner_item,
                                                                     ah.getLstStrEventType(
                                                                             spnEventType));

        adpEventType.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);// simple_spinner_dropdown_item);

        spnEventType.setAdapter(adpEventType);
        String strEventType = getResources().getString(event.getEventType().getCode());
        spnEventType.setSelection(adpEventType.getPosition(strEventType));
    }

    private void setTime()
    {
        dialogTimeFragment = new TimePickerFragment();

        btnStartTime.setText(event.getStartTime());
        btnEndTime.setText(event.getEndTime());

        float flt = btnStartTime.getTextSize();
        float scaledDensity = this.getResources().getDisplayMetrics().scaledDensity;
        float flt2 = flt / scaledDensity;

        if (getResources().getConfiguration().fontScale > 1f)
        {
            btnStartTime.setTextSize(flt2 * .8f);
        }
        if (getResources().getConfiguration().fontScale > 1f)
        {
            btnEndTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnEndTime.getTextSize() * .8f);
        }
        btnStartTime.setOnClickListener(this);
        btnEndTime.setOnClickListener(this);
    }


    private void setDate()
    {
        txtFrom.setText(getResources().getString(R.string.From) + ":");
        txtTo.setText(getResources().getString(R.string.To) + ":");

        dialogDateFragment = new DatePickerFragment();

        btnStartDate.setText(event.getStartDate());
        btnEndDate.setText(event.getEndDate());

        btnStartDate.setOnClickListener(this);
        btnEndDate.setOnClickListener(this);
    }

    private void setFishes()
    {
        txtFishes.setText(getResources().getString(R.string.Fishes) + ":");

        prepareFishDialog(this.sortPosition, this.sortAscending);

        btnQtde.setOnClickListener(this);
        btnQtde.setText(
                event.getTotalQtdeFishes() + " " + getResources().getString(R.string.Fishes) +
                "\n(CliCK_mE)");
    }

    private void prepareFishDialog(int sortPosition, boolean sortAscending)
    {
        mLstEfs = new ArrayList<String>();
        mLstEfs = transformLstFishesToDialogFormat(sortPosition, sortAscending);

        adapter = new MyFishAdapter(this, android.R.layout.simple_list_item_1, mLstEfs);

        mListView = new ListView(this);

        mListView.setAdapter(adapter);

        dlgQtdeFishes = new Dialog(this);
        dlgQtdeFishes.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlgQtdeFishes.setContentView(mListView);
    }

    private void updateFishDialog(int sortPosition, boolean sortAscending)
    {
        mLstEfs = new ArrayList<String>();
        mLstEfs = transformLstFishesToDialogFormat(sortPosition, sortAscending);

        adapter.notifyDataSetChanged();
    }

    private void changingSort(int position)
    {
        if (position == sortPosition)
        {
            sortAscending = !sortAscending;
        }
        else
        {
            sortPosition = position;
            sortAscending = true;
        }
    }

    private void setFavorite()
    {
        if (event.IsFavorite())
        {
            rtFavorite.setRating(1);
        }
        else
        {
            rtFavorite.setRating(0);
        }

        rtFavorite.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (mCanEdit)
                {
                    if (event.getAction() == MotionEvent.ACTION_UP)
                    {
                        if (rtFavorite.getRating() == 0)
                        {
                            rtFavorite.setRating(1);
                        }
                        else
                        {
                            rtFavorite.setRating(0);
                        }

                        v.setPressed(false);
                    }

                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        v.setPressed(true);
                    }

                    if (event.getAction() == MotionEvent.ACTION_CANCEL)
                    {
                        v.setPressed(false);
                    }
                }

                return true;
            }
        });
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private List<String> transformCursorToLstString(Event event,
                                                    int sortPosition,
                                                    boolean sortAscending)
    {
        List<String> ret;
        DecimalFormat fd = new DecimalFormat("#.#");
        DataSourceDetailEventFish dsEventFish = new DataSourceDetailEventFish(ActivityEvent.this);

        dsEventFish.setFishesToEventObject(event, sortPosition, sortAscending);
        Cursor c = dsEventFish.getFishesFromEvent(event.getId(), sortPosition, sortAscending);

        ret = new ArrayList<String>();

        ret.add(getResources().getString(R.string.Qtde) + ah.SEP +
                getResources().getString(R.string.Name) + ah.SEP + "kG!" + ah.SEP + "cm!");

        if (c != null && c.getCount() > 0)
        {
            c.moveToFirst();

            while (!c.isAfterLast())
            {
                StringBuilder sb = new StringBuilder();

                sb.append(c.getInt(c.getColumnIndex(DataSourceDetailEventFish.FISH_AMOUNT)));
                sb.append(ah.SEP);
                sb.append(c.getString(c.getColumnIndex(DataSourceFish.FISHNAME)));
                sb.append(ah.SEP);
                sb.append(fd.format(c.getFloat(c.getColumnIndex(DataSourceDetailEventFish.FISH_WEIGHT))));
                sb.append(ah.SEP);
                sb.append(fd.format(0));

                ret.add(sb.toString());

                c.moveToNext();
            }
        }

        ret.add(String.valueOf(event.getTotalQtdeFishes()) + ah.SEP +
                getResources().getString(R.string.Total) + ah.SEP +
                fd.format(event.getTotalWeightFishes()) + ah.SEP +
                fd.format(event.getTotalLengthFishes()));

        return ret;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private List<String> transformLstFishesToDialogFormat(int sortPosition, boolean sortAscending)
    {
        DecimalFormat fd = new DecimalFormat("#.#");
        List<String> ret = new ArrayList<String>();
        DataSourceDetailEventFish dsEventFish = new DataSourceDetailEventFish(ActivityEvent.this);

        this.event = dsEventFish.setFishesToEventObject(event, sortPosition, sortAscending);

        ret.add(getResources().getString(R.string.Qtde) + ah.SEP +
                getResources().getString(R.string.Name) + ah.SEP + "kG!" + ah.SEP + "cm!");

        List<Fish> lstFishes = event.getLstFishes();

        for (Fish f : lstFishes)
        {
            StringBuilder sb = new StringBuilder();

            sb.append(event.getQtde(f));
            sb.append(ah.SEP);
            sb.append(f.getFishName());
            sb.append(ah.SEP);
            sb.append(fd.format(event.getWeight(f)));
            sb.append(ah.SEP);
            sb.append(fd.format(event.getLength(f)));

            ret.add(sb.toString());
        }

        ret.add(String.valueOf(event.getTotalQtdeFishes()) + ah.SEP +
                getResources().getString(R.string.Total) + ah.SEP +
                fd.format(event.getTotalWeightFishes()) + ah.SEP +
                fd.format(event.getTotalLengthFishes()));

        return ret;
    }

    @SuppressLint("NewApi")
    private void instanciateBuilder()
    {
        if (android.os.Build.VERSION.SDK_INT >= 11)
        {
            builder = new AlertDialog.Builder(this, 2);
        }
        else
        {
            builder = new AlertDialog.Builder(this);
        }
    }

    @Override
    public void onClick(View v)
    {
        String strCurrentPicker;

        // ------------EVENT NAME----------------------------------------------
        if (v.getId() == btnEventName.getId())
        {
            setUpEditEventNameDialog();

            dlgEditEventName.show();
        }
        // START DATE or END DATE-------------------------------------------
        else if (v.getId() == btnStartDate.getId() || v.getId() == btnEndDate.getId())
        {
            if (v.getId() == btnStartDate.getId())
            {
                strCurrentPicker = strSTARTDATE;
            }
            else
            {
                strCurrentPicker = strENDDATE;
            }

            dialogDateFragment.show(getSupportFragmentManager(), strCurrentPicker);
        }
        // START TIME or END TIME-------------------------------------------
        else if (v.getId() == btnStartTime.getId() || v.getId() == btnEndTime.getId())
        {
            if (v.getId() == btnStartTime.getId())
            {
                strCurrentPicker = strSTARTDATE;
            }
            else
            {
                strCurrentPicker = strENDDATE;
            }

            dialogTimeFragment.show(getSupportFragmentManager(), strCurrentPicker);
        }
        // Qtde Fishes -------------------------------------------
        else if (v.getId() == btnQtde.getId())
        {
            dlgQtdeFishes.show();
        }
        // FAVORITE--------------------------------------------------
        else if (v.getId() == rtFavorite.getId())
        {
            if (rtFavorite.getRating() == 0f)
            {
                rtFavorite.setRating(1f);
            }
            else
            {
                rtFavorite.setRating(0f);
            }
        }
    }

    private void setUpEditEventNameDialog()
    {
        dlgEditEventName = new Dialog(this);
        dlgEditEventName.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlgEditEventName.setContentView(R.layout.dialog_editeventname);

        dlgEditEventName.getWindow()
                        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        edtEditEventName = (EditText) dlgEditEventName.findViewById(R.id.editEventName_edtName);
        btnOK = (ImageButton) dlgEditEventName.findViewById(R.id.editEventName_imgBtnOK);
        btnCancel = (ImageButton) dlgEditEventName.findViewById(R.id.editEventName_imgBtnCancel);

        OnClickListener MyClick = new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (v.getId() == btnOK.getId())
                {
                    ActivityEvent.this.setTitle(edtEditEventName.getText().toString());
                }
                dlgEditEventName.dismiss();
            }
        };

        btnOK.setOnClickListener(MyClick);
        btnCancel.setOnClickListener(MyClick);
    }

    private boolean validateDateTimeRangePositive(View view, Calendar c1, Calendar c2)
    {
        if (ah.compareDates(c1, c2) < 0)
        {
            tempDu(view);
            return false;
        }
        else
        {
            return true;
        }
    }

    private void tempDu(View view)
    {
        iPickerErrorCount++;

        if (iPickerErrorCount % 2 != 0)
        {
            AlertDialog.Builder a = new AlertDialog.Builder(view.getContext());
            a.setTitle(getResources().getString(R.string.Error) + "!");
            a.setMessage(getResources().getString(R.string.RangeNegative));
            a.setCancelable(true);
            a.setPositiveButton("OK", null);
            a.show();
        }
    }

    @SuppressLint("ValidFragment")
    private class DatePickerFragment
            extends DialogFragment
            implements DatePickerDialog.OnDateSetListener
    {
        final Calendar mCal = Calendar.getInstance();
        int mYear = 0;
        int mMonth = 0;
        int mDay = 0;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            setDefaultDate();

            // Create a new instance of DatePickerDialog

            // setMinMaxToDatePicker(d.getDatePicker());

            return new DatePickerDialog(getActivity(), 2, this, mYear, mMonth, mDay);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day)
        {
            strSelectedDate = (ah.addZeroToInt(day) + "/" + ah.addZeroToInt(month + 1) + "/" +
                               year);

            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();

            if (getCurrentPickerName().equals(strSTARTDATE))
            {
                c1 = ah.convertStringToCalendar(
                        strSelectedDate + " " + btnStartTime.getText().toString());
                c2 = ah.convertStringToCalendar(
                        btnEndDate.getText().toString() + " " + btnEndTime.getText().toString());
            }
            else if (getCurrentPickerName().equals(strENDDATE))
            {
                c1 = ah.convertStringToCalendar(btnStartDate.getText().toString() + " " +
                                                btnStartTime.getText().toString());
                c2 = ah.convertStringToCalendar(
                        strSelectedDate + " " + btnEndTime.getText().toString());
            }

            if (validateDateTimeRangePositive(view, c1, c2))
            {
                if (getCurrentPickerName().equals(strSTARTDATE))
                {
                    btnStartDate.setText(strSelectedDate);
                }
                else if (getCurrentPickerName().equals(strENDDATE))
                {
                    btnEndDate.setText(strSelectedDate);
                }

                event.setStartDateTime(new java.sql.Date(c1.getTimeInMillis()));
                event.setEndDateTime(new java.sql.Date(c2.getTimeInMillis()));
            }/*
             * else { tempDu(view); }
			 */

            // txtName.setText(event.getEventDurationHours());

        }

        private void setCurrentDate()
        {
            mYear = mCal.get(Calendar.YEAR);
            mMonth = mCal.get(Calendar.MONTH);
            mDay = mCal.get(Calendar.DAY_OF_MONTH);
        }

        /**
         * Sets DEFAULT Dates from TextViews...or CurrentDate
         */
        private void setDefaultDate()
        {
            Calendar date = Calendar.getInstance();

            if (getCurrentPickerName().equals(strSTARTDATE))
            {
                strSelectedDate = btnStartDate.getText().toString();
            }
            else if (getCurrentPickerName().equals(strENDDATE))
            {
                strSelectedDate = btnEndDate.getText().toString();
            }

            date = ah.convertStringToCalendar(strSelectedDate);

            if (date == null)
            {
                setCurrentDate();
            }
            else
            {
                mYear = date.get(Calendar.YEAR);
                mMonth = date.get(Calendar.MONTH);
                mDay = date.get(Calendar.DAY_OF_MONTH);
            }
        }

        private String getCurrentPickerName()
        {
            return (this.getTag());
        }

        private boolean isSameDate(Calendar min, Calendar max)
        {
            return min.get(Calendar.DAY_OF_MONTH) == max.get(Calendar.DAY_OF_MONTH) &&
                   min.get(Calendar.MONTH) == max.get(Calendar.MONTH) &&
                   min.get(Calendar.YEAR) == max.get(Calendar.YEAR);
        }
    }

    @SuppressLint("ValidFragment")
    public class TimePickerFragment
            extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener
    {
        Calendar mCal = Calendar.getInstance();

        int mHourOfDay = mCal.get(Calendar.HOUR_OF_DAY);
        int mMinute = mCal.get(Calendar.MINUTE);

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            setDefaultTime();

            // Create a new instance of DatePickerDialog
            TimePickerDialog d = new TimePickerDialog(getActivity(),
                                                      2,
                                                      this,
                                                      mHourOfDay,
                                                      mMinute,
                                                      true);

            return d;
        }

        private void setDefaultTime()
        {
            Calendar date;
            Calendar.getInstance();

            if (getCurrentPickerName().equals(strSTARTDATE))
            {
                strSelectedDate = btnStartTime.getText().toString();
            }
            else if (getCurrentPickerName().equals(strENDDATE))
            {
                strSelectedDate = btnEndTime.getText().toString();
            }

            date = ah.convertStringToTime(strSelectedDate);

            if (date == null)
            {
                setCurrentTime();
            }
            else
            {
                mHourOfDay = date.get(Calendar.HOUR_OF_DAY);
                mMinute = date.get(Calendar.MINUTE);
            }

        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            strSelectedTime = (ah.addZeroToInt(hourOfDay) + ":" +
                               ah.addZeroToInt(minute));

            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();

            if (getCurrentPickerName().equals(strSTARTDATE))
            {
                c1 = ah.convertStringToCalendar(
                        btnStartDate.getText().toString() + " " + strSelectedTime);
                c2 = ah.convertStringToCalendar(
                        btnEndDate.getText().toString() + " " + btnEndTime.getText().toString());
            }
            else if (getCurrentPickerName().equals(strENDDATE))
            {
                c1 = ah.convertStringToCalendar(btnStartDate.getText().toString() + " " +
                                                btnStartTime.getText().toString());
                c2 = ah.convertStringToCalendar(
                        btnEndDate.getText().toString() + " " + strSelectedTime);
            }

            if (validateDateTimeRangePositive(view, c1, c2))
            {
                if (getCurrentPickerName().equals(strSTARTDATE))
                {
                    btnStartTime.setText(strSelectedTime);
                }
                else if (getCurrentPickerName().equals(strENDDATE))
                {
                    btnEndTime.setText(strSelectedTime);
                }

                event.setStartDateTime(new java.sql.Date(c1.getTimeInMillis()));
                event.setEndDateTime(new java.sql.Date(c2.getTimeInMillis()));
            }
            /*
             * else { tempDu(view); }
			 */

            // txtName.setText(event.getEventDurationHours());
        }

        private void setCurrentTime()
        {
            mHourOfDay = mCal.get(Calendar.HOUR_OF_DAY);
            mMinute = mCal.get(Calendar.MINUTE);
        }

        private String getCurrentPickerName()
        {
            return (this.getTag());
        }
    }

    private class MyFishAdapter
            extends ArrayAdapter<String>
    {
        public MyFishAdapter(Context context, int textViewResourceId, List<String> pFishes)
        {
            super(context, textViewResourceId, pFishes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View itemView;

            Button btn1;
            Button btn2;
            Button btn3;
            Button btn4;

            String[] strItm = mLstEfs.get(position).split(ah.SEP.toString());

            // Making sure we have a view to work with
            itemView = convertView;
            if (itemView == null)
            {
                itemView = getLayoutInflater().inflate(R.layout.item_fishtable, parent, false);
            }

            btn1 = (Button) itemView.findViewById(R.id.itemFish_btnAmount);
            btn2 = (Button) itemView.findViewById(R.id.itemFish_btnName);
            btn3 = (Button) itemView.findViewById(R.id.itemFish_btnWeight);
            btn4 = (Button) itemView.findViewById(R.id.itemFish_btnEdit);

            if (position == 0 || position == (mLstEfs.size() - 1))
            {
                setTitleButtons(btn1, "1");
                setTitleButtons(btn2, "2");
                setTitleButtons(btn3, "3");
                setTitleButtons(btn4, "4");
            }
            else
            {
                setNumberButtons(btn1, "1");
                setTextButtons(btn2, "2");
                setNumberButtons(btn3, "3");
                setNumberButtons(btn4, "4");

                // btn2.setTextSize(12);
                // btn2.setContentDescription("2");
            }

            setClick(btn1);
            setClick(btn2);
            setClick(btn3);
            // setClick(btn4);

            btn1.setText(strItm[0]);
            btn2.setText(strItm[1]);
            btn3.setText(strItm[2]);
            btn4.setText(strItm[3]);

            return itemView;
        }

        private void setClick(Button btn)
        {
            btn.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    Toast.makeText(v.getContext(),
                                   "Clickou em " + ((Button) v).getText().toString(),
                                   Toast.LENGTH_SHORT).show();

                    if (!v.getContentDescription().toString().equals(""))
                    {
                        changingSort(Integer.parseInt(v.getContentDescription().toString()));

                        // dlgQtdeFishes.cancel();
                        // prepareFishDialog(sortPosition, sortAscending);
                        // dlgQtdeFishes.show();

                        updateFishDialog(sortPosition, sortAscending);
                    }

                }
            });
        }

        private void setTitleButtons(Button btn, String contentDescription)
        {
            btn.setTextColor(EnumFishermanColors.Title_Orange.getCode());
            btn.setShadowLayer(3, 3, 3, EnumFishermanColors.Shadow_Black.getCode());
            if (Integer.parseInt(contentDescription) == sortPosition)
            {
                btn.setTextSize(25);
            }
            else
            {
                btn.setTextSize(18);
            }

            btn.setTypeface(Typeface.DEFAULT_BOLD, 1);
            btn.setContentDescription(contentDescription);
        }

        private void setNumberButtons(Button btn, String contentDescription)
        {
            btn.setTextColor(EnumFishermanColors.Number_Blue.getCode());
            // btn.setShadowLayer(3, 3, 3,
            // EnumFishermanColors.Shadow_Black.getCode());
            if (Integer.parseInt(contentDescription) == sortPosition)
            {
                btn.setTextSize(30);
                btn.setShadowLayer(3, 3, 3, EnumFishermanColors.Shadow_Black.getCode());
                btn.setTypeface(Typeface.DEFAULT_BOLD, 1);
            }
            else
            {
                btn.setTextSize(22);
                btn.setTypeface(Typeface.DEFAULT, 1);
            }
            btn.setContentDescription(contentDescription);
        }

        private void setTextButtons(Button btn, String contentDescription)
        {
            // btn.setTextColor(EnumFishermanColors.Number_Blue.getCode());
            btn.setShadowLayer(3, 3, 3, EnumFishermanColors.Shadow_Black.getCode());
            if (Integer.parseInt(contentDescription) == sortPosition)
            {
                btn.setTextSize(18);
                btn.setTypeface(Typeface.DEFAULT_BOLD, 1);
            }
            else
            {
                btn.setTextSize(14);
                btn.setTypeface(Typeface.DEFAULT, 1);
            }
            btn.setContentDescription(contentDescription);
        }
    }
}