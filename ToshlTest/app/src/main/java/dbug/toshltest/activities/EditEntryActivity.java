package dbug.toshltest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import dbug.toshltest.R;

public class EditEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
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

    }

    private void doneEditing() {

    }
}
