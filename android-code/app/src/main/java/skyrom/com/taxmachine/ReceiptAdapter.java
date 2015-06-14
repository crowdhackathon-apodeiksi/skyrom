package skyrom.com.taxmachine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;
import java.util.zip.Inflater;

public class ReceiptAdapter extends BaseAdapter {

    List<Receipt> receipts;
    Context context;
    LayoutInflater inflater;

    public ReceiptAdapter(List<Receipt> receipts, Context context) {
        this.receipts = receipts;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return receipts == null ? 0 : receipts.size();
    }

    @Override
    public Object getItem(int i) {
        return receipts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        View v = convertView;
        final Holder holder;
        if (v == null) {
            holder = new Holder();
            v = inflater.inflate(R.layout.grid_layout, viewGroup, false);
            holder.imageView = (ImageView) v.findViewById(R.id.picture);
            v.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                holder.imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        holder.imageView.setImageBitmap(receipts.get(position).getThumbnail());
                    }
                });
            }
        }).start();

        return v;
    }

    static class Holder {
        ImageView imageView;
    }
}
