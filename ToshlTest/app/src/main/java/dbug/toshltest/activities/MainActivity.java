package dbug.toshltest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import dbug.toshltest.R;
import dbug.toshltest.adapters.EntriesAdapter;
import dbug.toshltest.common.Logger;
import dbug.toshltest.models.Currency;
import dbug.toshltest.models.Entry;
import dbug.toshltest.network.APIs;
import dbug.toshltest.network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton        addEntryFab;
    RecyclerView                financesList;

    public static ArrayList<Currency> currencies;
    public static ArrayList<Entry> mEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addEntryFab = (FloatingActionButton) findViewById(R.id.add_entry_fab);
        financesList = (RecyclerView) findViewById(R.id.finances_list);

        //retrieveCurrencies();

        addEntryFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addEntry = new Intent(MainActivity.this, AddEntryActivity.class);
                startActivity(addEntry);
            }
        });
    }

    private void retrieveCurrencies() {
        RestClient.setupRestClient(APIs.class, null).getCurrencies().enqueue(new Callback<ArrayList<Currency>>() {
            @Override
            public void onResponse(Call<ArrayList<Currency>> call, Response<ArrayList<Currency>> response) {
                if (response.isSuccessful()) {
                    currencies = response.body();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Currency>> call, Throwable t) {
                Logger.e(MainActivity.class, "currencies error " + t.getLocalizedMessage());
            }
        });
    }

    private void setupList() {
        RestClient.setupRestClient(APIs.class, null)
                .getEntries("2016-01-01", "2016-10-31")
                .enqueue(new Callback<ArrayList<Entry>>() {
            @Override
            public void onResponse(Call<ArrayList<Entry>> call, Response<ArrayList<Entry>> response) {
                if (response.isSuccessful()) {
                    mEntries = response.body();
                    financesList.setHasFixedSize(true);
                    LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                    financesList.setLayoutManager(manager);
                    financesList.setAdapter(new EntriesAdapter(MainActivity.this, mEntries));
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Entry>> call, Throwable t) {
                Logger.e("Error retrieving entries " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupList();
    }
}
