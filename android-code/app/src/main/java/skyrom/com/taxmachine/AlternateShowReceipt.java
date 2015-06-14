package skyrom.com.taxmachine;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import skyrom.com.taxmachine.utils.Utils;

public class AlternateShowReceipt extends ActionBarActivity {

    TextView afm;
    TextView total;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.right_activity, R.anim.left_activity);
        setContentView(R.layout.alternate_show_receipt);
        Receipt receipt = (Receipt) getIntent().getSerializableExtra(Utils.RECEIPT);

        afm = (TextView) findViewById(R.id.afm);
        total = (TextView) findViewById(R.id.total);
        date = (TextView) findViewById(R.id.date);
        afm.setText(receipt.getAfm());
        total.setText(receipt.getPrice());
        date.setText(receipt.getDate());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_activity_back, R.anim.right_activity_back);
    }
}
