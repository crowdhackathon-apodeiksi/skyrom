package skyrom.com.taxmachine;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import skyrom.com.taxmachine.utils.Utils;


public class ReceiptsActivity extends ActionBarActivity {

    GridView gridView;
    ReceiptAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);
        final List<Receipt> receipts = SQLite.getInstance(this).getReceipts();
        adapter = new ReceiptAdapter(receipts, this);
        gridView = (GridView) findViewById(R.id.grid);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ReceiptsActivity.this, ShowReceiptActivity.class);
                intent.putExtra(Utils.IMAGE_ID, receipts.get(i).getId());
                startActivity(intent);
            }
        });
    }
}
