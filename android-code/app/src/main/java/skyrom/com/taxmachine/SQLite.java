package skyrom.com.taxmachine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import skyrom.com.taxmachine.utils.Utils;

public class SQLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "com.skyrom.db";
    private static SQLite sqLite;

    final static String IMAGE_TABLE = "images";
    final static String IMAGE_FIELD = "image";
    final static String THUMBNAIL_FIELD = "thumbnail";
    final static String TIME_FIELD = "time";
    final static String ID_FIELD = "id";
    final static String UPLOADED_FIELD = "uploaded";
    static ThreadPoolExecutor executor;

    private SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLite getInstance(Context context) {
        if (sqLite == null) {
            sqLite = new SQLite(context);
        }
        return sqLite;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE images(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "image BLOB," +
                "thumbnail BLOB," +
                "uploaded TINYINT(1) DEFAULT 0," +
                "time INT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    public long saveOrUpdateReceipt(Bitmap bitmap, String time) {
        SQLiteDatabase db = sqLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
        values.put(IMAGE_FIELD, outputStream.toByteArray());
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        values.put(TIME_FIELD, time);
        outputStream = new ByteArrayOutputStream();
        bitmap = Utils.getResizedBitmap(bitmap, 400);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        values.put(THUMBNAIL_FIELD, outputStream.toByteArray());
        long id = db.insertWithOnConflict(IMAGE_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        db.close();
        return id;
    }

    public void updateReceiptUploadStatus(Receipt receipt) {
        SQLiteDatabase db = sqLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UPLOADED_FIELD, receipt.isUploaded());
        db.update(IMAGE_TABLE, values, ID_FIELD + "=?",
                new String[]{String.valueOf(receipt.getId())});
        db.close();
    }

    public Receipt getReceipt(long id) {
        Receipt receipt = null;
        SQLiteDatabase db = sqLite.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from images WHERE id=" + id, null);
        if (cursor.moveToFirst()) {
            receipt = new Receipt();
            receipt.setId(id);
            receipt.setUploaded("1".equals(cursor.getInt(cursor.getColumnIndex(UPLOADED_FIELD))));
            byte[] imgByte = cursor.getBlob(cursor.getColumnIndex(IMAGE_FIELD));
            receipt.setBitmap(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
        }
        db.close();
        return receipt;
    }

    public List<Receipt> getReceipts() {
        List<Receipt> receipts = new ArrayList<>();
        SQLiteDatabase db = sqLite.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id,thumbnail,time,uploaded from images", null);
        if (cursor.moveToFirst()) {
            do {
                Receipt receipt = new Receipt();
                byte[] imgByte = cursor.getBlob(cursor.getColumnIndex(THUMBNAIL_FIELD));
                if (imgByte != null) {
                    receipt.setThumbnail(BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length));
                } else {
                    receipt.setThumbnail(null);
                }
                receipt.setBitmap(null);
                receipt.setId(cursor.getLong(cursor.getColumnIndex(ID_FIELD)));
                receipt.setTime(cursor.getInt(cursor.getColumnIndex(TIME_FIELD)));
                receipt.setUploaded(
                        "1".equals(cursor.getInt(cursor.getColumnIndex(UPLOADED_FIELD))));
                receipts.add(receipt);
            } while (cursor.moveToNext());
        }
        db.close();
        return receipts;
    }
}