package skyrom.com.taxmachine;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import skyrom.com.taxmachine.requests.UploadFile;
import skyrom.com.taxmachine.utils.Utils;

public class ShowReceiptActivity extends ActionBarActivity implements UploadFile.Callback {

    ImageView imageView;
    SQLite sqLite;
    SessionManager sessionManager;
    Button uploadFileButton;
    CustomProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.right_activity, R.anim.left_activity);
        setContentView(R.layout.activity_show_receipt);
        sqLite = SQLite.getInstance(this);
        sessionManager = SessionManager.getInstance(this);
        imageView = (ImageView) findViewById(R.id.image);
        long id = getIntent().getLongExtra(Utils.IMAGE_ID, 0);
        final Receipt receipt = SQLite.getInstance(this).getReceipt(id);
        final Bitmap bitmap = receipt.getBitmap();
        imageView.setImageBitmap(bitmap);
        uploadFileButton = (Button) findViewById(R.id.upload);
        progressBar = new CustomProgressBar(this);
        uploadFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (receipt.isUploaded()) {
                    Toast.makeText(ShowReceiptActivity.this, R.string.receipt_already_uploaded,
                            Toast.LENGTH_SHORT);
                    return;
                }
                progressBar.show();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
                new UploadFile(ShowReceiptActivity.this,
                        sessionManager.getAuthToken(),
                        outputStream.toByteArray(),
                        receipt,
                        ShowReceiptActivity.this).perform();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_activity_back, R.anim.right_activity_back);
    }

    @Override
    public void fileUploaded(UploadFile request, Receipt receipt) {
        progressBar.cancel();
        if (!request.hasError()) {
            receipt.setUploaded(true);
            sqLite.updateReceiptUploadStatus(receipt);
            Toast.makeText(this, R.string.receipt_uploaded, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.unexpected_error, Toast.LENGTH_SHORT).show();
        }
    }
}
