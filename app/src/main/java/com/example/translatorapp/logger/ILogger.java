package com.example.translatorapp.logger;

import android.util.Log;

public interface ILogger {
    boolean VERBOSE_RUNNING = true;
    boolean DEBUG_RUNNING = false;
    boolean INFO_RUNNING = false;

    default void showVerboseLog(String tag, String message) {
        if (VERBOSE_RUNNING) {
            Log.v(tag, message);
        }
    }

    default void showDebugLog(String tag, String message) {
        if (DEBUG_RUNNING) {
            Log.d(tag, message);
        }
    }

    default void showInfoLog(String tag, String message) {
        if (INFO_RUNNING) {
            Log.i(tag, message);
        }
    }
}