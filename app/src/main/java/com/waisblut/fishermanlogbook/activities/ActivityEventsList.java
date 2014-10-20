package com.waisblut.fishermanlogbook.activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.waisblut.fishermanlogbook.EnumFonts;
import com.waisblut.fishermanlogbook.Event;
import com.waisblut.fishermanlogbook.EventList;
import com.waisblut.fishermanlogbook.R;
import com.waisblut.fishermanlogbook.data.DataSourceEvent;

import java.util.List;

public class ActivityEventsList
        extends ListActivity
        implements OnItemClickListener
{
    ActivityHelper ah = new ActivityHelper();
    ListView listview = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.actionmenufisherman, menu);
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventslist);

        refreshEvents();

        MyListAdapter listAdapter = new MyListAdapter(this, 0, EventList.getListOfEvents());

        setListAdapter(listAdapter);

        // SET components on variables
        setComponents();

        // SET CLICK LISTENERS
        setItemListeners();

        // SET FONTS
        // setFonts();

        // SET TITLE CONFIGS
        ah.setTitle(this);

        // LONG CLICK
        setLongClick();
    }

    private void refreshEvents()
    {
        DataSourceEvent dsEvent = new DataSourceEvent(this);

        dsEvent.open();

        EventList.recycle();
        EventList.addItems(dsEvent.getAllEvents());
    }

    private void setLongClick()
    {
        listview.setOnItemLongClickListener(new OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long arg3)
            {
                Event event = (Event) listview.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(),
                               "Long Clicked: " + event.getName(),
                               Toast.LENGTH_LONG).show();

                setFavorite(event);

                return true;
            }
        });
    }

    private void setFavorite(Event e)
    {
        e.IsFavorite(!e.IsFavorite());

        DataSourceEvent dsEvent = new DataSourceEvent(this);

        dsEvent.updateFavorite(e.getId(), e.IsFavorite());
        dsEvent.close();

        listview.invalidateViews();
    }

    private class MyListAdapter
            extends ArrayAdapter<Event>
    {
        Event currentEvent = null;
        // RatingBar rtFavorite = null;
        ImageView imgFavorite = null;

        public MyListAdapter(Context context, int textViewResourceId, List<Event> events)
        {
            super(context, textViewResourceId, events);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View itemView;
            // Event currentEvent = null;
            TextView txtEventName;
            TextView txtEventLocation;
            TextView txtStartDate;
            RatingBar rtRating;
            // RatingBar rtFavorite = null;

            // Making sure we have a view to work with
            itemView = convertView;
            if (itemView == null)
            {
                itemView = getLayoutInflater().inflate(R.layout.item_event, parent, false);
            }

            // Find the Event to work with.
            currentEvent = EventList.get(position);

            // Fill the view
            // ImageView imageView =
            // (ImageView)itemView.findViewById(R.id.itemEvent_imgView);
            // imageView.setImageResource(currentEvent.getIconID());

            // Name:
            txtEventName = (TextView) itemView.findViewById(R.id.itemEvent_txtEvent);
            txtEventName.setText(currentEvent.getName());
            ah.setFont(parent.getContext(), txtEventName, EnumFonts.ABEAKRG.getCode());

            // Location:
            txtEventLocation = (TextView) itemView.findViewById(R.id.itemEvent_txtLocation);
            txtEventLocation.setText(currentEvent.getLocationName());
            ah.setFont(parent.getContext(), txtEventLocation, EnumFonts.ABEAKRG.getCode());

            // StartDate:
            txtStartDate = (TextView) itemView.findViewById(R.id.itemEvent_txtStartDate);
            txtStartDate.setText(currentEvent.getStartDate());
            ah.setFont(parent.getContext(), txtStartDate, EnumFonts.ABEAKRG.getCode());

            // Rating:
            rtRating = (RatingBar) itemView.findViewById(R.id.itemEvent_ratingBar);
            rtRating.setStepSize(0.1f);
            rtRating.setNumStars(5);
            // rating.setMax(5);
            rtRating.setRating(currentEvent.getRating());

            // FAVORITE:
            // rtFavorite = (RatingBar)
            // itemView.findViewById(R.id.itemEvent_Favorite);
            // rtFavorite.setStepSize(1f);
            // rtFavorite.setNumStars(1);
            // rating.setMax(5);
            // rtFavorite.setRating((currentEvent.IsFavorite() ? 1 : 0));

            // FAVORITE
            imgFavorite = (ImageView) itemView.findViewById(R.id.itemEvent_imgIsFavorite);
            if (currentEvent.IsFavorite())
            {
                imgFavorite.setVisibility(View.VISIBLE);
            }
            else
            {
                imgFavorite.setVisibility(View.INVISIBLE);
            }

            return itemView;
        }
    }

    private void setItemListeners()
    {
        listview.setOnItemClickListener(this);
    }

    private void setComponents()
    {
        // btnAddEvent = (ImageButton) findViewById(R.id.btnAddEvent);
        listview = (ListView) findViewById(android.R.id.list);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
    {
        Intent intent;
        Event event = (Event) listview.getItemAtPosition(position);

        Toast.makeText(getApplicationContext(),
                       "OK FUNFOU - Clicou em: " + event.getName(),
                       Toast.LENGTH_LONG).show();

        intent = new Intent(ActivityEventsList.this, ActivityEvent.class);
        intent.putExtra(ah.MYEVENT, event);

        startActivity(intent);
    }
}