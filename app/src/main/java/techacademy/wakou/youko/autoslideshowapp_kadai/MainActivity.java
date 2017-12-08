package techacademy.wakou.youko.autoslideshowapp_kadai;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE= 100;
    private Button start;
    private Button stop;
    private Button next;
    private Button prev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                getContentsInfo();
            }else{
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSIONS_REQUEST_CODE);
            }
        }else{
            getContentsInfo();
        }
    }
//     @Override
//     public void onDestroy(){
//             super.onDestroy();
//            getContentsInfo();
//    }


//    @Override
    public void onRequestPermissionResult(int requestCode,String permissions[],int[] grantResults){
       switch (requestCode){
           case PERMISSIONS_REQUEST_CODE:
               if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                   getContentsInfo();
               }
               break;
           default:
               break;
       }
    }
    private void getContentsInfo(){
        ContentResolver resolver=getContentResolver();
        final Cursor cursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
        );
//        初めの画像はID３７の画像にする
        if(cursor.moveToFirst()) {
            int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
            Long id = cursor.getLong(fieldIndex);
            Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 37);
            Log.d("ANDROID", "URI:" + imageUri.toString());

            ImageView imageVIew = (ImageView) findViewById(R.id.imageView);
            imageVIew.setImageURI(imageUri);

            //            nextボタンを押すと次の画像へ遷移する
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    もしURIのIDが43未満だったらIDが３８以上のIDの画像を表示する
                    int path;
                    path = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                    String pathImg;
                    pathImg = cursor.getString(path);
                    int imgpath;
                    imgpath = Integer.parseInt(pathImg);
                    if(imgpath <=43){
                        for (int i = 38; i <= 43; i++) {
                            int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                            Long id = cursor.getLong(fieldIndex);
                           final Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, i);
                            Log.d("test", "URI:" + imageUri.toString());
                            ImageView imageVIew = (ImageView) findViewById(R.id.imageView);
                            imageVIew.setImageURI(imageUri);

                        }

//                    IDが４３異常だったらIDが３７の画像を表示する
                    }else{
                        int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                        Long id = cursor.getLong(fieldIndex);
                        Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 37);
                        Log.d("test", "URI:" + imageUri.toString());

                        ImageView imageVIew = (ImageView) findViewById(R.id.imageView);
                        imageVIew.setImageURI(imageUri);

                    }
                }
            });
        }
        //    @Override
        //    public void onDestroy(){
        //        super.onDestroy();
//                cursor.close();
        //    }


    }
}
