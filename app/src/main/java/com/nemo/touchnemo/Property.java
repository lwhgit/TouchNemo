package com.nemo.touchnemo;

/**
 * Created by W on 2016-12-28.
 */

public class Property {
    public static int currentTheme = Property.THEME_BALCK;

    public static final int THEME_BALCK = 1;
    public static final int THEME_WHITE = 2;

    public static final int RESOURCE_X1_BUTTON = 1;
    public static final int RESOURCE_X2_BUTTON = 2;
    public static final int RESOURCE_X4_BUTTON = 3;
    public static final int RESOURCE_TRACK_ADD_BUTTON = 4;
    public static final int RESOURCE_NEMO_NONE = 5;
    public static final int RESOURCE_NEMO_ENABLE = 6;
    public static final int RESOURCE_INFO_VIEW = 7;
    public static final int RESOURCE_CUSTOM_SPEED_BUTTON = 8;
    public static final int RESOURCE_TRACK_DELETE_BUTTON = 9;
    public static final int RESOURCE_LOAD_DIALOG_FILE = 10;
    public static final int RESOURCE_PLAY_N_PAUSE_BUTTON = 11;
    public static final int RESOURCE_PROJECT_LAYOUT_STROKE = 12;
    public static final int RESOURCE_STOP_BUTTON = 13;
    public static final int RESOURCE_TRACK_NOTE_SEEK_BAR_THUMB = 14;
    public static final int RESOURCE_TRACK_NOTE_SEEK_BAR_PROGRESS_SHAPE = 15;
    public static final int RESOURCE_MAIN_LAYOUT = 16;
    public static final int RESOURCE_PROJECT_NEW = 17;
    public static final int RESOURCE_PROJECT_LOAD = 18;
    public static final int RESOURCE_PROJECT_EXPORT = 19;
    public static final int RESOURCE_PROJECT_SHARE = 20;
    public static final int RESOURCE_TURN_THEME = 21;

    public static int getResource(int res) {
        switch (res) {
            case Property.RESOURCE_X1_BUTTON:
                return Property.currentTheme == Property.THEME_BALCK ? R.drawable._1x_speed_black_btn : R.drawable._1x_speed_white_btn;
            case Property.RESOURCE_X2_BUTTON:
                return Property.currentTheme == Property.THEME_BALCK ? R.drawable._2x_speed_black_btn : R.drawable._2x_speed_white_btn;
            case Property.RESOURCE_X4_BUTTON:
                return Property.currentTheme == Property.THEME_BALCK ? R.drawable._4x_speed_black_btn : R.drawable._4x_speed_white_btn;
            case Property.RESOURCE_TRACK_ADD_BUTTON:
                return Property.currentTheme == Property.THEME_BALCK ? R.drawable.add_black_btn : R.drawable.add_white_btn;
            case Property.RESOURCE_NEMO_NONE:
                return Property.currentTheme == Property.THEME_BALCK ? R.mipmap.black_nemo1 : R.mipmap.white_nemo1;
            case Property.RESOURCE_NEMO_ENABLE:
                return Property.currentTheme == Property.THEME_BALCK ? R.mipmap.black_nemo2 : R.mipmap.white_nemo2;
            case Property.RESOURCE_INFO_VIEW:
                return Property.currentTheme == Property.THEME_BALCK ? R.drawable.black_info_view : R.drawable.white_info_view;
            case Property.RESOURCE_CUSTOM_SPEED_BUTTON:
                return Property.currentTheme == Property.THEME_BALCK ? R.drawable.custom_speed_black_btn : R.drawable.custom_speed_white_btn;
            case Property.RESOURCE_TRACK_DELETE_BUTTON:
                return Property.currentTheme == Property.THEME_BALCK ? R.drawable.del_black_btn : R.drawable.del_white_btn;
            case Property.RESOURCE_LOAD_DIALOG_FILE:
                return Property.currentTheme == Property.THEME_BALCK ? R.drawable.load_dialog_file_black_btn : R.drawable.load_dialog_file_white_btn;
            case Property.RESOURCE_PLAY_N_PAUSE_BUTTON:
                return Property.currentTheme == Property.THEME_BALCK ? R.drawable.play_and_pause_black_btn : R.drawable.play_and_pause_white_btn;
            case Property.RESOURCE_PROJECT_LAYOUT_STROKE:
                return Property.currentTheme == Property.THEME_BALCK ? R.drawable.project_layout_black_stroke : R.drawable.project_layout_white_stroke;
            case Property.RESOURCE_STOP_BUTTON:
                return Property.currentTheme == Property.THEME_BALCK ? R.drawable.stop_black_btn : R.drawable.stop_white_btn;
            case Property.RESOURCE_TRACK_NOTE_SEEK_BAR_THUMB:
                return Property.currentTheme == Property.THEME_BALCK ? R.drawable.track_note_seek_bar_black_thumb : R.drawable.track_note_seek_bar_white_thumb;
            case Property.RESOURCE_TRACK_NOTE_SEEK_BAR_PROGRESS_SHAPE:
                return Property.currentTheme == Property.THEME_BALCK ? R.drawable.track_note_seek_bar_progress_black_shape : R.drawable.track_note_seek_bar_progress_white_shape;
            case Property.RESOURCE_MAIN_LAYOUT:
                return Property.currentTheme == Property.THEME_BALCK ? R.drawable.main_layout_black : R.drawable.main_layout_white;
            case Property.RESOURCE_PROJECT_NEW:
                return Property.currentTheme == Property.THEME_BALCK ? R.mipmap.new_project_btn_black : R.mipmap.new_project_btn_white;
            case Property.RESOURCE_PROJECT_LOAD:
                return Property.currentTheme == Property.THEME_BALCK ? R.mipmap.load_project_btn_black : R.mipmap.load_project_btn_white;
            case Property.RESOURCE_PROJECT_EXPORT:
                return Property.currentTheme == Property.THEME_BALCK ? R.mipmap.export_project_btn_black : R.mipmap.export_project_btn_white;
            case Property.RESOURCE_PROJECT_SHARE:
                return Property.currentTheme == Property.THEME_BALCK ? R.mipmap.share_project_btn_black : R.mipmap.share_project_btn_white;
            case Property.RESOURCE_TURN_THEME:
                return Property.currentTheme == Property.THEME_BALCK ? R.mipmap.turn_theme_btn_black : R.mipmap.turn_theme_btn_white;
        }
        return 0;
    }
}
