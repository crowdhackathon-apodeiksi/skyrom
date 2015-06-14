package skyrom.com.taxmachine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DrawAdapter extends BaseAdapter {

    List<Draw> draws;
    Context context;
    LayoutInflater inflater;

    public DrawAdapter(List<Draw> draws, Context context) {
        this.draws = draws;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return draws == null ? 0 : draws.size();
    }

    @Override
    public Object getItem(int i) {
        return draws.get(i);
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
            v = inflater.inflate(R.layout.draw, viewGroup, false);
            holder.image = (ImageView) v.findViewById(R.id.image);
            holder.name = (TextView) v.findViewById(R.id.name);
            holder.date = (TextView) v.findViewById(R.id.date);
            holder.shopName = (TextView) v.findViewById(R.id.shop_name);
            holder.price = (TextView) v.findViewById(R.id.price);
            v.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Draw draw = draws.get(position);
        holder.image.setImageBitmap(draw.getImage());
        holder.name.setText(draw.getName());
        holder.shopName.setText(draw.getShopName());
        holder.date.setText(draw.getDatetime());
        holder.price.setText(draw.getPrice());
        return v;
    }

    static class Holder {
        TextView date;
        TextView name;
        TextView shopName;
        TextView price;
        ImageView image;
    }
}
