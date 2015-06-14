package skyrom.com.taxmachine;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import eu.livotov.zxscan.ScannerFragment;
import eu.livotov.zxscan.ScannerView;
import skyrom.com.taxmachine.utils.Utils;


public class QrCodeActivity extends ActionBarActivity implements ScannerView.ScannerViewEventListener {

    private ScannerFragment scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        if (scanner == null)
        {
            scanner = new ScannerFragment();
            scanner.setScannerViewEventListener(this);
        }
        getSupportFragmentManager().beginTransaction().add(R.id.container, scanner).commit();
    }

    @Override
    public void onScannerReady() {
    }

    @Override
    public void onScannerFailure(int cameraError) {
    }

    public boolean onCodeScanned(final String data) {
        Intent res = new Intent();
        res.putExtra(Utils.RESULT_EXTRA_STR, data);
        setResult(RESULT_OK, res);
        finish();
        return true;
    }
}
