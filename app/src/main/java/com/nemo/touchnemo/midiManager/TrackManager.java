package com.nemo.touchnemo.midiManager;

public class TrackManager
{
    static final byte[] IDENTIFIER = { 'M', 'T', 'r', 'k' };

    private static TrackManager myInstance;

    public static TrackManager getInstance()
    {
        if(myInstance == null)
        {
            myInstance = new TrackManager();
        }

        return myInstance;
    }

    private TrackManager()
    {
        //empty
    }
}