package com.nemo.touchnemo.midimanager;

public class MidiManager
{
    private static MidiManager myInstance;

    public static MidiManager getInstance()
    {
        if(myInstance == null)
        {
            myInstance = new MidiManager();
        }

        return myInstance;
    }

    private MidiManager()
    {
        //empty
    }
}
