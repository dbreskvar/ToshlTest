package dbug.toshltest.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dbug.toshltest.R;
import dbug.toshltest.models.Currency;
import dbug.toshltest.models.Entry;
import dbug.toshltest.network.APIs;
import dbug.toshltest.network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEntryActivity extends AppCompatActivity {

    @BindView(R.id.entry_description_edit)      EditText        entryDescriptionEdit;
    @BindView(R.id.entry_amount_edit)           EditText        entryAmountEdit;
    @BindView(R.id.entry_date_text)             TextView        entryDateText;
    @BindView(R.id.entry_category_spinner)      Spinner         entryCategorySpinner;
    @BindView(R.id.activity_add_entry)          LinearLayout    activityAddEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        ButterKnife.bind(this);
    }

    private void createEntry() {
        Entry entry = new Entry();
        String description = entryDescriptionEdit.getText().toString();
        if (description.length() > 0) entry.setDesc(description);
        entry.setAmount(Double.parseDouble(entryAmountEdit.getText().toString()));
        entry.setDate(entryDateText.toString());
        entry.setCategory(entryCategorySpinner.getSelectedItem().toString());

        Currency currency = new Currency();
        currency.setCode("EUR");

        entry.setCurrency(currency);

        APIs restClient = RestClient.setupRestClient(APIs.class, null);
        Call<Object> create = restClient.createEntry(entry);
        create.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}
