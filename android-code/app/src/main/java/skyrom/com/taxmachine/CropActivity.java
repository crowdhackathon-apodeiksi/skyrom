package skyrom.com.taxmachine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.edmodo.cropper.CropImageView;

import skyrom.com.taxmachine.utils.Utils;


public class CropActivity extends ActionBarActivity {

    Button ok;
    CropImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        ok = (Button) findViewById(R.id.ok);
        imageView = (CropImageView) findViewById(R.id.CropImageView);
        final Uri imageUri = Uri.parse(getIntent().getStringExtra(Utils.IMAGE_URI));
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                    getContentResolver(), imageUri);
            imageView.setImageBitmap(bitmap);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SQLite sqLite = SQLite.getInstance(CropActivity.this);
                    long id = sqLite.saveOrUpdateReceipt(imageView.getCroppedImage(), "2014");
                    Intent intent = new Intent(CropActivity.this, ShowReceiptActivity.class);
                    intent.putExtra(Utils.IMAGE_ID, id);
                    startActivity(intent);
                    finish();
                }
            });
        } catch (Exception e) {
        }
    }
}
