package com.google.zxing.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.dommy.qrcode.MainActivity;
import com.dommy.qrcode.R;
import com.dommy.qrcode.util.Constant;
import com.dommy.qrcode.util.LocationinformationModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.camera.CameraManager;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.decoding.CaptureActivityHandler;
import com.google.zxing.decoding.InactivityTimer;
import com.google.zxing.decoding.RGBLuminanceSource;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.view.ViewfinderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Logger;

import static android.content.ContentValues.TAG;


/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
public class CaptureActivity extends AppCompatActivity implements Callback {

    private static final int REQUEST_CODE_SCAN_GALLERY = 100;

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private ImageButton back;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private ProgressDialog mProgress;
    private String photo_path;
    private Bitmap scanBitmap;
    private final Uri CONTENT_URI = Uri.parse("content://com.dommy.qrcode/aibabel_information");
    //	private Button cancelScanButton;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置去除ActionBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_scanner);
        //ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_content);
        back = (ImageButton) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//		cancelScanButton = (Button) this.findViewById(R.id.btn_cancel_scan);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

        //添加toolbar
//        addToolbar();
    }

    private void addToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        ImageView more = (ImageView) findViewById(R.id.scanner_toolbar_more);
//        assert more != null;
//        more.setOnClickListener(new View.OnClickListener() {
//            @Overrid
//            public void onClick(View v) {
//
//            }
//        });
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.scanner_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.scan_local:
//                //打开手机中的相册
//                Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); //"android.intent.action.GET_CONTENT"
//                innerIntent.setType("image/*");
//                Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");
//                this.startActivityForResult(wrapperIntent, REQUEST_CODE_SCAN_GALLERY);
//                return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (requestCode==RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN_GALLERY:
                    //获取选中图片的路径
                    Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
                    if (cursor.moveToFirst()) {
                        photo_path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    }
                    cursor.close();

                    mProgress = new ProgressDialog(CaptureActivity.this);
                    mProgress.setMessage("正在扫描...");
                    mProgress.setCancelable(false);
                    mProgress.show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Result result = scanningImage(photo_path);
                            if (result != null) {
//                                Message m = handler.obtainMessage();
//                                m.what = R.id.decode_succeeded;
//                                m.obj = result.getText();
//                                handler.sendMessage(m);
                                Intent resultIntent = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putString(Constant.INTENT_EXTRA_KEY_QR_SCAN ,result.getText());
//                                Logger.d("saomiao",result.getText());
//                                bundle.putParcelable("bitmap",result.get);
                                resultIntent.putExtras(bundle);
                                CaptureActivity.this.setResult(RESULT_OK, resultIntent);

                            } else {
                                Message m = handler.obtainMessage();
                                m.what = R.id.decode_failed;
                                m.obj = "Scan failed!";
                                handler.sendMessage(m);
                            }
                        }
                    }).start();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 扫描二维码图片的方法
     * @param path
     * @return
     */
    public Result scanningImage(String path) {
        if(TextUtils.isEmpty(path)){
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); //设置二维码内容的编码

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.scanner_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

        //quit the scan view
//		cancelScanButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				CaptureActivity.this.finish();
//			}
//		});
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        String uid=null;
        String uname="准儿翻译机";
        String d=null;
        String oid=null;
        String sn=null;
        String f=null;
        String t=null;
        String nf=null;
        String nt=null;
        String key=null;
        String ddt=null;
        String fft=null;
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        //FIXME
        if (TextUtils.isEmpty(resultString)) {
            Toast.makeText(CaptureActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
        } else {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.INTENT_EXTRA_KEY_QR_SCAN, resultString);
            System.out.println("sssssssssssssssss scan 0 = " + resultString);
            // 不能使用Intent传递大于40kb的bitmap，可以使用一个单例对象存储这个bitmap
            //bundle.putParcelable("bitmap", barcode);
            //Logger.d("saomiao",resultString);

            try
            {
                JSONObject mJsonObject = new JSONObject(resultString);
                //Log.d(TAG, "JsonString:" + string);

                //formats中只有一个对象，直接从其中获取一些参数
                oid = mJsonObject.getString("oid");
                sn = mJsonObject.getString("sn");
                fft = mJsonObject.getString("ff");
                f = mJsonObject.getString("f");
                t = mJsonObject.getString("t");
                nf = mJsonObject.getString("nf");
                nt = mJsonObject.getString("nt");
                uid = mJsonObject.getString("uid");
                //uname = mJsonObject.getString("uname");
                d = mJsonObject.getString("d");
                ddt = mJsonObject.getString("time");
                bundle.putCharSequence("data",t);
                bundle.putCharSequence("data1",f);
                bundle.putCharSequence("dd_time",ddt);
                bundle.putCharSequence("fft",fft);
                bundle.putCharSequence("sn",sn);
                resultIntent.putExtras(bundle);

//                Intent intent = new Intent(this,MainActivity.class);
//                intent.putExtra("data",t);
//                startActivity(intent);

                Log.d("ddd","ddt="+ddt);
                //Log.d("uuu","uid="+uid);
                //Log.d("nnn","uname="+uname);
                //Log.d("d","d="+d);
                //Log.d("oid","oid="+oid);
                //Log.d("sn","sn="+sn);
                //Log.d("f","f="+f);
                //Log.d("t","t="+t);
                //Log.d("nf","fn="+nf);
                //Log.d("nt","nt="+nt);
                //Toast.makeText(CaptureActivity.this, "f＝"+f, Toast.LENGTH_LONG).show();
                //Toast.makeText(CaptureActivity.this, "t＝"+t, Toast.LENGTH_LONG).show();

                contentProvide(d,sn,oid,f,t,ddt,fft);

            } catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Intent intent = new Intent();
            intent.setAction("com.example.leidong.action.MyReceiver");
            intent.putExtra("uid", sn);
            intent.putExtra("uname", uname);

            //发送广播
            sendBroadcast(intent);


            Log.d("t","t="+t);
            this.setResult(1000, resultIntent);

        }

        CaptureActivity.this.finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };
    private void contentProvide(String Country,String City,String Province,String start_time,String end_time,String ddt,String fft) {

        //String Country="美国";
        //String Province="Y-01";
        //String City="纽约";
        ContentValues contentValues = new ContentValues();
        contentValues.put(LocationinformationModel.LocationEntry._ID, 1 + "");
        contentValues.put(LocationinformationModel.LocationEntry.DEST_COUNTRY, Country);
        contentValues.put(LocationinformationModel.LocationEntry.DEST_CITY, City);
        contentValues.put(LocationinformationModel.LocationEntry.DEST_PROVINCE, Province);
        contentValues.put(LocationinformationModel.LocationEntry.START_TIME, start_time);
        contentValues.put(LocationinformationModel.LocationEntry.END_TIME, end_time);
        contentValues.put(LocationinformationModel.LocationEntry.DD_TIME, ddt);
        contentValues.put(LocationinformationModel.LocationEntry.JH_TIME, fft);

        Log.d(TAG,LocationinformationModel.LocationEntry.CONTENT_URI.toString());
        getContentResolver().insert(LocationinformationModel.LocationEntry.CONTENT_URI, contentValues);

        Cursor cursor = getContentResolver().query(LocationinformationModel.LocationEntry.CONTENT_URI, null, null, null, null);
        Log.d("wzf", "LocationinformationModel.LocationEntry.CONTENT_URI="+LocationinformationModel.LocationEntry.CONTENT_URI);
        //Toast.makeText(this, "LocationinformationModel.LocationEntry.CONTENT_URI="+LocationinformationModel.LocationEntry.CONTENT_URI, Toast.LENGTH_SHORT).show();
        try {
            if (cursor != null && cursor.getCount() > 0) {
                //Toast.makeText(this, "数据总数："+cursor.getCount(), Toast.LENGTH_SHORT).show();
                cursor.moveToFirst();
                Log.d("TAG", TAG+"数据》》》》》》》》》》》："+cursor.getString(0));
                Log.d("TAG", TAG+"数据》》》》》》》》》》》："+cursor.getString(1));
                Log.d("TAG", TAG+"数据》》》》》》》》》》》："+cursor.getString(2));
                Log.d("TAG", TAG+"数据》》》》》》》》》》》："+cursor.getString(3));
                Log.d("TAG", TAG+"数据》》》》》》》》》》》："+cursor.getString(4));
                Log.d("TAG", TAG+"数据》》》》》》》》》》》："+cursor.getString(5));
                Log.d("TAG", TAG+"数据》》》》》》》》》》》："+cursor.getString(6));
                Log.d("TAG", TAG+"数据》》》》》》》》》》》："+cursor.getString(7));

                cursor.getString(3);
                Log.d("TAG", TAG+"数据："+cursor.getString(2));
                //Toast.makeText(this, "地点："+cursor.getString(1), Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "SN："+cursor.getString(2), Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "Oid："+cursor.getString(3), Toast.LENGTH_SHORT).show();
            } else {
                Log.d("TAG", TAG+"：无数据");
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }finally {
            if(null!=cursor)
                cursor.close();
        }


    }


}