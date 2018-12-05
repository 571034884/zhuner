package com.dommy.qrcode.util;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class AibabelinformationProvider extends ContentProvider {
private String TAG = AibabelinformationProvider.class.getSimpleName().toString();
    private final static int AIBABEL = 100;

    private AibabelDBinformationHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = new AibabelDBinformationHelper(getContext());
        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        Cursor cursor = null;
        switch (buildUriMatcher().match(uri)) {
            case AIBABEL:
                cursor = db.query(LocationinformationModel.LocationEntry.TABLE_NAME, projection, selection, selectionArgs, sortOrder, null, null);
                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri = null;
        Log.d(TAG,TAG+":插入操作");
//        Cursor cursor = db.query(
//                LocationModel.LocationEntry.TABLE_NAME,
//                new String[]{LocationModel.LocationEntry._ID},
//                LocationModel.LocationEntry._ID + " = ?",
//                new String[]{1 + ""},
//                null,
//                null,
//                null);


        long _id = 0;
        switch (buildUriMatcher().match(uri)) {
            case AIBABEL:
                try {
//                    if (cursor != null && cursor.getCount() > 0) {
//                        //更新操作
//                        _id = (long) db.update(LocationModel.LocationEntry.TABLE_NAME, values, LocationModel.LocationEntry._ID + " = ?", new String[]{1 + ""});
//                    } else {
//                        //插入操作
                        _id = db.replace(LocationinformationModel.LocationEntry.TABLE_NAME, null, values);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//                    cursor.close();
                }

//                _id = db.insert(LocationModel.LocationEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = LocationinformationModel.LocationEntry.buildUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            default:
                throw new android.database.SQLException("Unknown uri: " + uri);
        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {


        return 0;
    }


    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = LocationinformationModel.CONTENT_AUTHORITY;

        matcher.addURI(authority, LocationinformationModel.PATH_AIBABEL, AIBABEL);

        return matcher;
    }


}
