package dbug.toshltest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import dbug.toshltest.R;
import dbug.toshltest.common.Logger;
import dbug.toshltest.helpers.CategoryHelper;
import dbug.toshltest.helpers.TagHelper;
import dbug.toshltest.models.Entry;
import dbug.toshltest.network.APIs;
import dbug.toshltest.network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditEntryActivity extends AppCompatActivity {

    EditText changeDesc;
    EditText changeAmount;
    EditText changeCategory;
    EditText changeTags;

    Entry changingEntry;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        changeDesc = (EditText) findViewById(R.id.change_description);
        changeAmount = (EditText) findViewById(R.id.change_amount);
        changeCategory = (EditText) findViewById(R.id.change_category);
        changeTags = (EditText) findViewById(R.id.change_tags);

        final Intent i = getIntent();
        final Entry entry = i.getParcelableExtra("Entry");
        id = entry.getId();

        RestClient.setupRestClient(APIs.class, null).getEntry(entry.getId()).enqueue(new Callback<Entry>() {
            @Override
            public void onResponse(Call<Entry> call, Response<Entry> response) {
                Logger.e("Msg: " + response.message() + " code: " + response.code());
                if (response.isSuccessful()) {
                    changingEntry = response.body();
                    changeDesc.setText(changingEntry.getDesc());
                    changeAmount.setText(String.valueOf(changingEntry.getAmount()));
                    changeCategory.setText(changingEntry.getCategory());
                    try {
                        String tags = "";
                        for (int j = 0; j < changingEntry.getTags().size(); j++) {
                            if (j < changingEntry.getTags().size() - 1)
                                tags += changingEntry.getTags().get(j) + ", ";
                            else tags += changingEntry.getTags().get(j);
                        }
                        changeTags.setText(tags);
                    } catch (NullPointerException e) {
                        Logger.e("Tags are probably empty -> " + e.getLocalizedMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Entry> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.entry_remove_menu:
                removeEntry();
                return true;
            case R.id.entry_done_menu:
                doneEditing();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void removeEntry() {
        RestClient.setupRestClient(APIs.class, null).deleteEntry(id).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                EditEntryActivity.this.finish();
                Toast.makeText(EditEntryActivity.this, getString(R.string.entry_removed), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    private void doneEditing() {
        changingEntry.setDesc(changeDesc.getText().toString());
        try {
            changingEntry.setAmount(Double.parseDouble(changeAmount.getText().toString()));
        } catch (NumberFormatException ignore) {}
        changingEntry.setCategory(changeCategory.getText().toString());
        if (changeTags.getText().toString().length() == 0) {
            CategoryHelper.findCategoryId(this, changingEntry, "Update");
        } else {
            try {
                String[] tags = changeTags.getText().toString().split(",");
                ArrayList<String> tagArray = new ArrayList<>();
                for (int i = 0; i < tags.length; i++) {
                    tagArray.add(tags[i].trim());
                }
                changingEntry.setTags(tagArray);
                TagHelper.getTagIds(this, changingEntry, "Update");
            } catch (NullPointerException e) {
                CategoryHelper.findCategoryId(this, changingEntry, "Update");
            }
        }
    }
}
