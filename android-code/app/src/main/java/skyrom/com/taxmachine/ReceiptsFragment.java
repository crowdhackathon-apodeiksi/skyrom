package skyrom.com.taxmachine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import skyrom.com.taxmachine.utils.Utils;

public class ReceiptsFragment extends Fragment {

    GridView gridView;
    ReceiptAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_receipts, container, false);
        final List<Receipt> receipts = SQLite.getInstance(getActivity()).getReceipts();
        adapter = new ReceiptAdapter(receipts, getActivity());
        gridView = (GridView) v.findViewById(R.id.grid);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ShowReceiptActivity.class);
                intent.putExtra(Utils.IMAGE_ID, receipts.get(i).getId());
                startActivity(intent);
            }
        });
        return v;
    }
}