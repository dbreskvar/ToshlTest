package dbug.toshltest.helpers;

import android.app.Activity;

import dbug.toshltest.common.Logger;
import dbug.toshltest.models.Entry;
import dbug.toshltest.network.APIs;
import dbug.toshltest.network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntryHelper {


    public static void createEntry(final Activity activity, final Entry entry) {
        APIs restClient = RestClient.setupRestClient(APIs.class, null);
        Call<Object> create = restClient.createEntry(entry);
        create.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Logger.e(response.message() + " code " + response.code());
                if (response.isSuccessful()) {
                    activity.finish();
                } else {
                    // Bad Request vrze vn za categoryId in samo unsorted deluje...
                    entry.setCategory("unsorted");
                    createEntry(activity, entry);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Logger.e("create entry failure");
            }
        });
    }


    public static void updateEntry(final Activity activity, Entry entry) {
        entry.setModified(entry.getModified());

        RestClient.setupRestClient(APIs.class, null)
                .updateEntry(entry.getId(), entry)
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Logger.e("Update: " + response.message() + " code: " + response.code());
                        if (response.isSuccessful()) {
                            activity.finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Logger.e("Update failed: " + t.getLocalizedMessage());
                    }
                });
    }
}
