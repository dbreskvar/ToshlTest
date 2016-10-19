package dbug.toshltest.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    }

    private void createEntry() {
        Entry entry = new Entry();
        String description = entryDescriptionEdit.getText().toString();
        if (description.length() > 0) entry.setDesc(description);
        entry.setAmount(Double.parseDouble(entryAmountEdit.getText().toString()));
        entry.setDate(entryDateText.toString());

        Currency currency = new Currency();
        currency.setCode("EUR");

        entry.setCurrency(currency);

        entry.setAccount("2691047");
        entry.setCategory(entryCategoryEdit.getText().toString());
        entry.setDate("2016-10-19");

        if (false) { //If tags are added -> which is currentyl not possible
            TagHelper.getTagIds(this, entry, "Create");
        } else {
            CategoryHelper.findCategoryId(this, entry, "Create");
        }
    }
}
