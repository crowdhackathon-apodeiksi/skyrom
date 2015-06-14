package skyrom.com.taxmachine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

public class MyDrawsFragment extends Fragment {

    ListView listView;
    DrawAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_draws, container, false);
        final List<Draw> draws = DummyProvider.getMyDraws(getActivity());
        adapter = new DrawAdapter(draws, getActivity());
        listView = (ListView) v.findViewById(R.id.list);
        listView.setAdapter(adapter);
        return v;
    }
}
