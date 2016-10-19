package dbug.toshltest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import dbug.toshltest.R;
import dbug.toshltest.activities.EditEntryActivity;
import dbug.toshltest.models.Entry;

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.EntryViewHolder> {

    private Context context;
    private ArrayList<Entry> entries;

    public EntriesAdapter(Context context, ArrayList<Entry> entries) {
        this.context = context;
        this.entries = entries;
    }

    class EntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView description, category, amount;

        EntryViewHolder(View view) {
            super(view);
            description = (TextView) view.findViewById(R.id.item_description);
            category = (TextView) view.findViewById(R.id.item_category);
            amount = (TextView) view.findViewById(R.id.item_amount);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Entry entry = entries.get(getAdapterPosition());
            Intent edit = new Intent(context, EditEntryActivity.class);
            edit.putExtra("Entry", entry);
            context.startActivity(edit);
        }
    }


    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_item, parent, false);
        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EntryViewHolder holder, int position) {
        Entry entry = entries.get(position);
        if (entry.getDesc() != null) holder.description.setText(entry.getDesc());
        else holder.description.setVisibility(View.GONE);

        holder.category.setText(entry.getCategory());

        String amountText = String.format(Locale.getDefault(), "%s %s", entry.getAmount(), entry.getCurrency().getCode());
        holder.amount.setText(amountText);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }
}
