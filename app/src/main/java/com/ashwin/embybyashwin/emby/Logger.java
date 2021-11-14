package com.ashwin.embybyashwin.emby;

import android.util.Log;

import mediabrowser.model.logging.ILogger;

public class Logger implements ILogger {
    String TAG = "Logger";
    @Override
    public void Info(String s, Object... objects) {
        Log.i(TAG, s);
    }

    @Override
    public void Error(String s, Object... objects) {
        Log.e(TAG, s);
    }

    @Override
    public void Warn(String s, Object... objects) {
        Log.e(TAG, s);
    }

    @Override
    public void Debug(String s, Object... objects) {
        Log.d(TAG, s);
    }

    @Override
    public void Fatal(String s, Object... objects) {
        Log.e(TAG, s);
    }

    @Override
    public void FatalException(String s, Exception e, Object... objects) {
        Log.e(TAG, s);
    }

    @Override
    public void ErrorException(String s, Exception e, Object... objects) {
        Log.e(TAG, s);
    }
}
