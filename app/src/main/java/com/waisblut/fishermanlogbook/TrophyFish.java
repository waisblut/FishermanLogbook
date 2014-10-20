package com.waisblut.fishermanlogbook;

import java.util.Date;

/*
 * It's one fish in particular HERDA DE FISH
 * PESO, TAMANHO, BOTTOM DEPTH, STRIKE DEPTH, PHOTO(S), COMBO, ISCA 
 *
 */

public class TrophyFish
        extends Fish
{
    private static final long serialVersionUID = 1L;

    public int trophyFishId = 0;
    public String trophyFishName = null;
    public Date dateCaught = null;
    public long weight = 0;
    public long size = 0;
    public long bottomDepth = 0;
    public long stikeDepth = 0;
    public String[] photos = null; // CAMINHO DA FOTO?
    public String COMBO = null; // ???????
    public String ISCA = null; // ?????????
}