package com.waisblut.fishermanlogbook;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Event
        implements Serializable
{
    private static final long serialVersionUID = 1L;

    private long eventId = 0;
    private String eventName = null;

    // TIME----------------------------------------
    private Date dtStartDateTime = null;// OK
    private Date dtEndDateTime = null;// OK

    // private int intEventDuration = 0; //***Autom�tico

    // LOCATION------------------------------------
    private String locationName = null;                        // Mapa/gps?

    // PEOPLE WITH---------------------------------
    private List<PeopleWith> peopleWith = new ArrayList<PeopleWith>();
    // Pessoas que pescaram junto - SearchView

    // TIPO DE PESCA (TORNEIO - RECREACAO - COMERCIAL)--------------
    private EnumEventType eventType;

    public EnumEventType getEventType()
    {
        return eventType;
    }

    public void setEventType(EnumEventType eventType)
    {
        this.eventType = eventType;
    }

    // DAYCONDITION--------------------------------
    // String moon = null; //TODO Create Class Moon
    // String sunRiseTime = null;
    // String sunSetTime = null;
    private Float dayTemperature = null; // INSERIR
    // UNIDADE
    // semi-Transparente
    // no
    // controle
    public String dayTemperatureUnit = null;

    // AIR CONDITION-------------------------------
    public Float pressureAtm = null;
    public String pressureAtmUnit = null; // TODO
    // Create
    // Unit
    // Logic
    private int windDirection = 0;    // Degrees
    // public String windSpeed = null;
    // TODO Create Class Wind

    public String precipitation = null; // Aberto/Nublado/Chuva
    // Ocasional

    // WATER CONDITION-----------------------------
    public String water = null; // TODO
    // Create
    // Class
    // Water
    public Float waterTemperature = null;
    public String waterTemperatureUnit = null; // TODO
    // Create
    // Unit
    // Logic
    public Float waterDepth = 0f;    // TODO
    // Create
    // Unit
    // Logic

    // String navigation = null; //????

    // FISHES------------------------------------------------------------
    private LinkedHashMap<Fish, Integer> tmFishQtde = null;
    private LinkedHashMap<Fish, Float> tmFishWeight = null;
    private LinkedHashMap<Fish, Float> tmFishLength = null;

    public String getName()
    {
        return eventName;
    }

    public void setName(String eventName)
    {
        this.eventName = eventName.replace(Logger.SEP, "");
    }

    public List<Fish> getLstFishes()
    {
        List<Fish> ret = new ArrayList<Fish>();

        for (Map.Entry<Fish, Integer> entry : tmFishQtde.entrySet())
        {
            ret.add(((Fish) entry.getKey()));
        }

        return ret;
    }

    public int getQtde(Fish f)
    {
        return tmFishQtde.get(f);
    }

    public Float getWeight(Fish f)
    {
        return tmFishWeight.get(f);
    }

    public Float getLength(Fish f)
    {
        return tmFishLength.get(f);
    }

    public void clearAllFishes()
    {
        tmFishQtde.clear();
        tmFishWeight.clear();
        tmFishLength.clear();
    }

    public void setFishes(Fish fish, int amount, Float totalWeight, Float totalLength)
    {
        /*
         * if (tmFishQtde.containsKey(fish)) { tmFishQtde.remove(fish);
		 * tmFishWeight.remove(fish); tmFishLength.remove(fish); }
		 */
        updateAmount(fish, amount);
        updateWeight(fish, totalWeight);
        updateLength(fish, totalLength);
    }

    public void updateAmount(Fish fish, int amount)
    {
        tmFishQtde.put(fish, amount);
    }

    public void updateWeight(Fish fish, Float weight)
    {
        tmFishWeight.put(fish, weight);
    }

    public void updateLength(Fish fish, Float length)
    {
        tmFishLength.put(fish, length);
    }

    public int getTotalQtdeFishes()
    {
        int ret = 0;

        for (Map.Entry<Fish, Integer> entry : tmFishQtde.entrySet())
        {
            ret += entry.getValue();
        }

        return ret;
    }

    public Float getTotalWeightFishes()
    {
        Float ret = 0f;

        for (Map.Entry<Fish, Float> entry : tmFishWeight.entrySet())
        {
            ret += entry.getValue();
        }

        return ret;
    }

    public Float getTotalLengthFishes()
    {
        Float ret = 0f;

        for (Map.Entry<Fish, Float> entry : tmFishLength.entrySet())
        {
            ret += entry.getValue();
        }

        return ret;
    }

    public Fish trophy = null;        // It's one fish in particular HERDA
    // DE FISH
    // PESO, TAMANHO, BOTTOM DEPTH, STRIKE DEPTH, PHOTO(S), COMBO, ISCA

    // NAVIGATION------------------------------------------------------
    // ****APENAS se o EVENTO foi EMBARCADO
    private boolean hasBoat;
    public Boat boat;
    public Engine engine;
    public String startNavigationTime;
    public String endNavigationTime;

    // COMBO---------------------------------------------------
    // VARA - CARRETILHA/MOLINETE - LINHA
    // public Combo combo;

    // ISCA----------------------------------------------------

    // EVALUATION---------------------------------
    private int rating = 0;

    public int getRating()
    {
        return rating;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    // COMMENT--------------------------------------------
    public String comments;

    // FAVORITE-----------------------------------------
    private Boolean isFavorite = false;

    public Boolean IsFavorite()
    {
        return isFavorite;
    }

    public void IsFavorite(Boolean isFavorite)
    {
        this.isFavorite = isFavorite;
    }

    // ---------------------------------------------------------------------------------------
    public Event()
    {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);

        tmFishQtde = new LinkedHashMap<Fish, Integer>();
        tmFishLength = new LinkedHashMap<Fish, Float>();
        tmFishWeight = new LinkedHashMap<Fish, Float>();

        try
        {
            dtEndDateTime = formatter.parse("01/01/1900 00:00");
            dtStartDateTime = formatter.parse("01/01/1900 00:01");
        }
        catch (ParseException e)
        {
        }
    }

    public Date getStartDateTime()
    {
        return this.dtStartDateTime;
    }

    public Date getEndDateTime()
    {
        return this.dtEndDateTime;
    }

    public String getStartDate()
    {
        return getDateOrTime(true, true);
    }

    public String getEndDate()
    {
        return getDateOrTime(true, false);
    }

    public String getStartTime()
    {
        return getDateOrTime(false, true);
    }

    public String getEndTime()
    {
        return getDateOrTime(false, false);
    }

    private String getDateOrTime(boolean isDate, boolean isStartTime)
    {
        String ret = null;
        Format f = null;

        if (isDate)
        {
            f = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

            if (isStartTime)
            {
                ret = f.format(dtStartDateTime);
            }
            else
            {
                ret = f.format(dtEndDateTime);
            }
        }
        else
        {
            f = new SimpleDateFormat("HH:mm", Locale.US);

            if (isStartTime)
            {
                ret = f.format(dtStartDateTime);
            }
            else
            {
                ret = f.format(dtEndDateTime);
            }
        }

        return ret;
    }

    public void setStartDateTime(String s)
    {
        if (s != null && s != "")
        {
            setStartorEndDateTime(true, s);
        }
    }

    public void setStartDateTime(Long l)
    {
        if (l != null)
        {
            setStartorEndDateTime(true, l);
        }
    }

    /**
     * @param d Must Be "dd/MM/yyyy HH:mm"
     */
    public void setStartDateTime(Date d)
    {
        dtStartDateTime = d;
    }

    /**
     * @param d Must Be "dd/MM/yyyy HH:mm"
     */
    public void setEndDateTime(Date d)
    {
        dtEndDateTime = d;
    }

    public void setEndDateTime(String s)
    {
        if (s != null && s != "")
        {
            setStartorEndDateTime(false, s);
        }
    }

    public void setEndDateTime(Long l)
    {
        if (l != null)
        {
            setStartorEndDateTime(false, l);
        }
    }

    private void setStartorEndDateTime(boolean isStartDateTime, String s)
    {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
        Date d = null;

        try
        {
            d = formatter.parse(s);
        }
        catch (ParseException e)
        {
            try
            {
                d = formatter.parse("01/01/1900 00:00");
            }
            catch (ParseException e1)
            {
            }
        }

        if (isStartDateTime)
        {
            dtStartDateTime = d;
        }
        else
        {
            dtEndDateTime = d;
        }
    }

    private void setStartorEndDateTime(boolean isStartDateTime, Long l)
    {
        Date d = new Date();

        d.setTime(l);

        if (isStartDateTime)
        {
            dtStartDateTime = d;
        }
        else
        {
            dtEndDateTime = d;
        }
    }

    public String getEventDurationHours()
    {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat) nf;
        long tempo = 0;

        df.applyPattern("#.#");

        tempo = (dtEndDateTime.getTime() - dtStartDateTime.getTime()) / 1000 / 60;

        if (tempo == 1)
        {
            return df.format(tempo) + " HOur";
        }
        else
        {
            return df.format(tempo) + " HOurs";
        }
    }

    public boolean hasBoat()
    {
        return hasBoat;
    }

    public void hasBoat(boolean hasBoat)
    {
        this.hasBoat = hasBoat;
    }

    public Float getTemperature()
    {
        return dayTemperature;
    }

    public void setTemperature(Float dayTemperature)
    {
        this.dayTemperature = dayTemperature;
    }

    public long getId()
    {
        return eventId;
    }

    public void setId(long eventId)
    {
        this.eventId = eventId;
    }

    public int getWindDirection()
    {
        return windDirection;
    }

    public String getWindDirectionName()
    {
        float bearing = this.windDirection % 360;

        String ret = null;

        if (bearing > 337.5f || bearing <= 22.5f)
        {
            ret = "N";
        }
        else
        {
            if (bearing > 292.5f)
            {
                ret = "NW";
            }
            else
            {
                if (bearing > 247.5f)
                {
                    ret = "W";
                }
                else
                {
                    if (bearing > 202.5f)
                    {
                        ret = "SW";
                    }
                    else
                    {
                        if (bearing > 157.5f)
                        {
                            ret = "S";
                        }
                        else
                        {
                            if (bearing > 112.5f)
                            {
                                ret = "SE";
                            }
                            else
                            {
                                if (bearing > 67.5f)
                                {
                                    ret = "E";
                                }
                                else
                                {
                                    if (bearing > 22.5f)
                                    {
                                        ret = "NE";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return ret;
    }

    public void setWindDirection(int windDirection)
    {
        this.windDirection = windDirection;
    }

    public String getLocationName()
    {
        return locationName;
    }

    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }

    public List<PeopleWith> getPeopleWith()
    {
        return peopleWith;
    }

    public void setPeopleWith(List<PeopleWith> p)
    {
        this.peopleWith = p;
    }
}
