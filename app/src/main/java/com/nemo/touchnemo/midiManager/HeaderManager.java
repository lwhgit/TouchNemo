package com.nemo.touchnemo.midimanager;

public class HeaderManager
{
    static final byte[] IDENTIFIER = { 'H', 'T', 'h', 'd' };
    static final byte[] HEADER_DATA_LENGTH = {0x00,0x00,0x00,0x06};
    static final byte[] NORMAL_FORMAT = {0x00,0x00};
    static final byte[] NORMAL_TRACK_COUNT = {0x00,0x01};
    static final byte[] UNIT_TIME = {0x01,(byte)0xE0};//1920

    private static HeaderManager myInstance;

    public static HeaderManager getInstance()
    {
        if(myInstance == null)
        {
            myInstance = new HeaderManager();
        }

        return myInstance;
    }

    private HeaderManager()
    {
        //empty
    }

    public byte[] getHeaderBytes()
    {
        int index = 0;
        byte[] headerBytes = new byte[HEADER_DATA_LENGTH[3]];

        for(byte b : IDENTIFIER)
        {
            headerBytes[index] = b;
            index++;
        }

        for (byte b : HEADER_DATA_LENGTH)
        {
            headerBytes[index] = b;
            index++;
        }

        for (byte b : NORMAL_FORMAT)
        {
            headerBytes[index] = b;
            index++;
        }

        for (byte b : NORMAL_TRACK_COUNT)
        {
            headerBytes[index] = b;
            index++;
        }

        for (byte b : UNIT_TIME)
        {
            headerBytes[index] = b;
            index++;
        }

        return headerBytes;
    }
}
