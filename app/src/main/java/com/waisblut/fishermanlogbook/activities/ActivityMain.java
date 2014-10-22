package com.waisblut.fishermanlogbook.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.waisblut.fishermanlogbook.EnumEventType;
import com.waisblut.fishermanlogbook.EnumFonts;
import com.waisblut.fishermanlogbook.Event;
import com.waisblut.fishermanlogbook.EventList;
import com.waisblut.fishermanlogbook.Fish;
import com.waisblut.fishermanlogbook.Logger;
import com.waisblut.fishermanlogbook.PeopleWith;
import com.waisblut.fishermanlogbook.R;
import com.waisblut.fishermanlogbook.data.DataSourceDetailEventFish;
import com.waisblut.fishermanlogbook.data.DataSourceEvent;
import com.waisblut.fishermanlogbook.data.DataSourceFish;
import com.waisblut.fishermanlogbook.data.DataSourcePeople;

import java.text.ParseException;

public class ActivityMain
        extends Activity
        implements OnClickListener
{
    ActivityHelper ah = new ActivityHelper();

    View mainView;

    ImageButton btnAddEvent, btnViewReport;

    TextView txtAddEvent, txtViewReport, txtImportExport, txtNumberFishCount, txtFishCaught;

    TableRow rowAddEvent, rowViewReport;

    DataSourceEvent dsEvent;
    DataSourceDetailEventFish dsEventFish;
    DataSourceFish dsFish;
    DataSourcePeople dsPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dsFish = new DataSourceFish(this);
        dsEvent = new DataSourceEvent(this);
        dsEventFish = new DataSourceDetailEventFish(this);
        dsPeople = new DataSourcePeople(this);

        initializeActivity();

        dsOpen();

        try
        {
            createEventAsTeste();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }


        // dsEvent = new EventDataSource(this);
    }

    private void createEventAsTeste() throws ParseException
    {
        // PEIXES-------------------------------------------
        Fish f1 = new Fish();
        f1.setFishId(1);
        f1.setFishName("Tainha");

        Fish f2 = new Fish();
        f2.setFishId(2);
        f2.setFishName("Robalo");

        Fish f3 = new Fish();
        f3.setFishId(3);
        f3.setFishName("Lambari");

        Fish f4 = new Fish();
        f4.setFishId(4);
        f4.setFishName("Piranha");

        Fish f5 = new Fish();
        f5.setFishId(5);
        f5.setFishName("Tilapia");

        Fish f6 = new Fish();
        f6.setFishId(6);
        f6.setFishName("Tubar�o");

        Fish f7 = new Fish();
        f7.setFishId(7);
        f7.setFishName("Baleia");

        if (dsFish.getRowsCount() <= 0)
        {
            dsFish.create(f1);
            dsFish.create(f2);
            dsFish.create(f3);
            dsFish.create(f4);
            dsFish.create(f5);
            dsFish.create(f6);
            dsFish.create(f7);
        }

        // dsFish.getColumnsCount();
        // dsFish.getRowsCount();
        dsFish.close();

        // PEOPLE------------------------------------------
        PeopleWith p1 = new PeopleWith();
        PeopleWith p2 = new PeopleWith();
        PeopleWith p3 = new PeopleWith();
        PeopleWith p4 = new PeopleWith();
        PeopleWith p5 = new PeopleWith();
        PeopleWith p6 = new PeopleWith();
        PeopleWith p7 = new PeopleWith();
        PeopleWith p8 = new PeopleWith();

        p1.setId(1);
        p2.setId(2);
        p3.setId(3);
        p4.setId(4);
        p5.setId(5);
        p6.setId(6);
        p7.setId(7);
        p8.setId(8);

        p1.setName("Antonio");
        p2.setName("Antônio");
        p3.setName("Barbara");
        p4.setName("Beatriz");
        p5.setName("Josefino");
        p6.setName("Abelardo");
        p7.setName("Anselmo");
        p8.setName("Antonio");

        if (dsPeople.getRowsCount() <= 0)
        {
            dsPeople.create(p1);
            dsPeople.create(p2);
            dsPeople.create(p3);
            dsPeople.create(p4);
            dsPeople.create(p5);
            dsPeople.create(p6);
            dsPeople.create(p7);
            dsPeople.create(p8);
        }

        dsPeople.close();

        // EVENTOS-----------------------------------------
        Event e1 = new Event();
        e1.setName("Evento 1");
        e1.setId(1);
        e1.setFishes(f1, 5, 10f, 13.5f);// "5kg", "C1", "MINHOCA");
        e1.setWindDirection(0);
        // //(NOMEDOPEIXE,
        // QTDE,
        // PESOMEDIO, COMBO1, ISCA)
        e1.setFishes(f1, 3, 30f, 14.2f);// "15kg", "C2", "JIG");
        // //(NOMEDOPEIXE,
        // QTDE,
        // PESOMEDIO, COMBO2, ISCA)
        e1.setFishes(f1, 25, 100f, 22.8f);// "35kg", "C3", "ARTIFICIAL");
        // (NOMEDOPEIXE,
        // QTDE, PESOMEDIO, COMBO3, ISCA)
        e1.setFishes(f1, 6, 180.7f, 14.7f);// "5kg", "C4", "SUPERFICIE");
        // //(NOMEDOPEIXE,
        // QTDE, PESOMEDIO, COMBO4, ISCA)
        e1.setFishes(f2, 3, 240f, 7.3f);
        e1.setFishes(f3, 6, 240f, 7.4f);
        e1.setFishes(f4, 3, 240f, 7.5f);
        e1.setFishes(f5, 45, 1.5f, 8.2f);
        e1.setFishes(f6, 1, 3.1f, 9.3f);
        e1.setFishes(f7, 18, 18.1f, 12.88f);

        e1.setLocationName("Florianópolis");
        e1.setRating(2);
        e1.setStartDateTime("01/08/2013 15:03");
        e1.setEndDateTime("15/11/2014 15:04");
        e1.setEventType(EnumEventType.COMMERCIAL);
        e1.IsFavorite(true);
        e1.getPeopleWith().add(p1);
        e1.getPeopleWith().add(p3);
        e1.getPeopleWith().add(p5);

        Event e2 = new Event();
        e2.setName("Pescaria A");
        e2.setId(2);
        e2.setWindDirection(45);
        e2.setFishes(f1, 15, 15f, 8f);
        e2.setFishes(f3, 5, 35f, 99f);
        e2.setLocationName("Lagoa da conceição");
        e2.setRating(3);
        e2.setStartDateTime("15/08/2012 11:19");
        e2.setEventType(EnumEventType.COMMERCIAL);
        e2.IsFavorite(false);

        Event e3 = new Event();
        e3.setName("Pesca Boa");
        e3.setId(3);
        e3.setWindDirection(77);
        e3.setFishes(f3, 15, 150f, 1.99f);
        e3.setFishes(f3, 5, 200f, 1.45f);
        e3.setLocationName("Rio Tietê");
        e3.setRating(4);
        e3.setStartDateTime("");
        e3.setEventType(EnumEventType.RECREATIONAL);
        e3.IsFavorite(true);

        Event e4 = new Event();
        e4.setName("Com os Amigos");
        e4.setId(4);
        e4.setWindDirection(23);
        e4.setLocationName("Guarujá");
        e4.setRating(1);
        e4.setStartDateTime("01/08/1999 00:00");
        e4.setEventType(EnumEventType.RECREATIONAL);
        e4.IsFavorite(false);

        Event e5 = new Event();
        e5.setName("Evento 5");
        e5.setId(5);
        e5.setWindDirection(22);
        e5.setLocationName("Ubatuba");
        e5.setRating(5);
        e5.setStartDateTime("01/01/1900 00:00");
        e5.setEventType(EnumEventType.TOURNMENT);
        e5.IsFavorite(false);

        Event e6 = new Event();
        e6.setName("Dia de Muito Peixe");
        e6.setId(6);
        e6.setWindDirection(0);
        e6.setLocationName("Rio de Janeiro");
        e6.setRating(4);
        e6.setStartDateTime("21/09/1978 00:00");
        e6.setEventType(EnumEventType.TOURNMENT);
        e6.IsFavorite(false);

        Event e7 = new Event();
        e7.setName("Peguei nada");
        e7.setId(7);
        e7.setWindDirection(180);
        e7.setLocationName("Argentina");
        e7.setRating(0);
        e7.setStartDateTime("04/10/1975 00:00");
        e7.setEventType(EnumEventType.RECREATIONAL);
        e7.IsFavorite(false);

        if (dsEvent.getRowsCount() <= 0)
        {
            dsEvent.create(e1);
            dsEvent.create(e2);
            dsEvent.create(e3);
            dsEvent.create(e4);
        }
        // EventList.addItem(e1);
        // EventList.addItem(e2);
        // EventList.addItem(e3);
        // EventList.addItem(e4);
        // EventList.addItem(e5);
        // EventList.addItem(e6);
        // EventList.addItem(e7);

        EventList.recycle();
        EventList.addItems(dsEvent.getAllEvents());

        dsEvent.selectOnTableOnLogCat();
        dsEventFish.selectOnTableOnLogCat();
        dsPeople.selectOnTableOnLogCat();

        // forTestPurposes(f1, f3, e1, e2);
    }

    private void forTestPurposes(Fish f1, Fish f3, Event e1, Event e2, PeopleWith p1)
    {
        dsFish.selectOnTableOnLogCat();
        dsEvent.selectOnTableOnLogCat();
        dsEventFish.selectOnTableOnLogCat();

        dsEvent.eventExists(e1.getId());
        dsEvent.eventExists(e2.getId());
        dsEvent.eventExists(4L);

        dsFish.fishExists(f1.getFishId());
        dsFish.fishExists(1L);
        dsFish.fishExists(14L);
        dsFish.fishExists(f3.getFishId());

        dsPeople.personExists(p1.getId());
    }

    private void initializeActivity()
    {
        // Setting components on variables
        setComponents();

        // SET CLICK LISTENERS
        setListeners();

        // SET FONTS
        setFonts();

        // SET TITLE CONFIGS
        ah.setTitle(this);

        // SET TOTAL COUNT
        //txtNumberFishCount.setText(String.valueOf(dsEventFish.getTotalSumCaught()));

        // SET BACKGROUND
        // ah.setBackGround(mainView);
    }

    private void setComponents()
    {
        btnAddEvent = (ImageButton) findViewById(R.id.btnAddEvent);
        btnViewReport = (ImageButton) findViewById(R.id.btnViewReport);

        txtAddEvent = (TextView) findViewById(R.id.txtAddEvent);
        txtViewReport = (TextView) findViewById(R.id.txtViewReport);
        txtImportExport = (TextView) findViewById(R.id.txtImportExport);
        txtNumberFishCount = (TextView) findViewById(R.id.txtNumberFishCount);
        txtFishCaught = (TextView) findViewById(R.id.txtFishCaught);

        rowAddEvent = (TableRow) findViewById(R.id.rowAddEvent);
        rowViewReport = (TableRow) findViewById(R.id.rowViewReport);

        mainView = findViewById(R.id.mainMainLayout);
    }

    private void setFonts()
    {
        ah.setFont(this, txtAddEvent, EnumFonts.ABEAKRG.getCode());
        ah.setFont(this, txtViewReport, EnumFonts.ABEAKRG.getCode());
        ah.setFont(this, txtImportExport, EnumFonts.ABEAKRG.getCode());
        ah.setFont(this, txtNumberFishCount, EnumFonts.DIGITAL.getCode());
        ah.setFont(this, txtFishCaught, EnumFonts.DIGITAL.getCode());
    }

    private void setListeners()
    {
        btnAddEvent.setOnClickListener(this);
        btnViewReport.setOnClickListener(this);

        rowAddEvent.setOnClickListener(this);
        rowViewReport.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.action_settings)
        {
            Toast.makeText(this, "SETTINGS", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = null;

        Logger.log('v', "OpeNinG view - " + v.getContentDescription());
        Logger.log('v', "OpeNinG2 rowAddEvent - " + rowAddEvent.getContentDescription());

        try
        {
            if (v.getContentDescription().equals(rowAddEvent.getContentDescription()))
            {
                intent = new Intent(ActivityMain.this, ActivityEventsList.class);
            }
            else if (v.getContentDescription().equals(rowViewReport.getContentDescription()))
            {
                // TODO Create Reports Activity!
                return;
            }
        }
        catch (Exception ex)
        {
            Logger.log('v', "ERRO: QUAL EH? - " + ex.getMessage());
        }

        if (intent != null)
        {
            startActivity(intent);
        }
        else
        {
            Logger.log('v', "Pq intent null? ");
        }
        // overridePendingTransition(0,android.R.animator.fade_out);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // SET TOTAL COUNT
        txtNumberFishCount.setText(String.valueOf(dsEventFish.getTotalSumCaught()));

        String tempo = String.valueOf(getResources().getConfiguration().fontScale);
        Toast.makeText(this, "Font Scale = " + tempo, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        dsClose();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        dsClose();
    }

    private void dsOpen()
    {
        dsFish.open();
        dsEvent.open();
        dsEventFish.open();
        dsPeople.open();
    }

    private void dsClose()
    {
        dsFish.close();
        dsEvent.close();
        dsEventFish.close();
        dsPeople.close();
    }
}
