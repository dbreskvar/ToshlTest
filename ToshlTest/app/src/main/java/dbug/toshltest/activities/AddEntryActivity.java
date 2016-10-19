package dbug.toshltest.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

import dbug.toshltest.R;
import dbug.toshltest.helpers.CategoryHelper;
import dbug.toshltest.helpers.TagHelper;
import dbug.toshltest.models.Currency;
import dbug.toshltest.models.Entry;

public class AddEntryActivity extends AppCompatActivity {

    EditText        entryDescriptionEdit;
    EditText        entryAmountEdit;
    EditText        entryCategoryEdit;
    TextView        entryDateText;
    LinearLayout    activityAddEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        entryAmountEdit = (EditText) findViewById(R.id.entry_amount_edit);
        entryDescriptionEdit = (EditText) findViewById(R.id.entry_description_edit);
        entryCategoryEdit = (EditText) findViewById(R.id.entry_category_edit);
        entryDateText = (TextView) findViewById(R.id.entry_date_text);
        activityAddEntry = (LinearLayout) findViewById(R.id.activity_add_entry);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            View mActionBarView = getLayoutInflater().inflate(R.layout.custom_action_bar, null);
            actionBar.setCustomView(mActionBarView);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }

        ImageButton done = (ImageButton) findViewById(R.id.done_action);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEntry();
            }
        });

        ImageButton clear = (ImageButton) findViewById(R.id.clear_action);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entryAmountEdit.setText("");
                entryDescriptionEdit.setText("");
                finish();
            }
        });

        entryDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEntryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        entryDateText.setText(String.format(Locale.getDefault(), "%d-%d-%d", year, (month + 1), day));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

    }

    private void createEntry() {
        Entry entry = new Entry();
        String description = entryDescriptionEdit.getText().toString();
        if (description.length() > 0) entry.setDesc(description);
        try {
            entry.setAmount(Double.parseDouble(entryAmountEdit.getText().toString()));
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.amount_error), Toast.LENGTH_SHORT).show();
            return;
        }
        entry.setDate(entryDateText.toString());

        Currency currency = new Currency();
        currency.setCode("EUR");

        entry.setCurrency(currency);

        entry.setAccount("2691047");
        if (entryCategoryEdit.getText().toString().length() == 0) {
            Toast.makeText(this, getString(R.string.category_error), Toast.LENGTH_SHORT).show();
            return;
        }
        entry.setCategory(entryCategoryEdit.getText().toString());
        entry.setDate(entryDateText.toString());

        if (false) { //If tags are added -> which is currently not possible
            TagHelper.getTagIds(this, entry, "Create");
        } else {
            CategoryHelper.findCategoryId(this, entry, "Create");
        }
    }
}
