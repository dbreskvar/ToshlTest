package dbug.toshltest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import dbug.toshltest.R;
import dbug.toshltest.models.Entry;

public class EntriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Entry> entries;

    public EntriesAdapter(Context context, ArrayList<Entry> entries) {
        this.context = context;
        this.entries = entries;
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder {

        public EntryViewHolder(View view) {
            super(view);

        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_item, parent, false);
        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return entries.size();
    }
}
