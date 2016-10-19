package dbug.toshltest.helpers;

import android.app.Activity;

import java.util.ArrayList;

import dbug.toshltest.common.Logger;
import dbug.toshltest.models.Category;
import dbug.toshltest.models.Entry;
import dbug.toshltest.network.APIs;
import dbug.toshltest.network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static dbug.toshltest.helpers.EntryHelper.createEntry;
import static dbug.toshltest.helpers.EntryHelper.updateEntry;

public class CategoryHelper {

    public static void findCategoryId(final Activity activity, final Entry entry, final String type) {
        RestClient.setupRestClient(APIs.class, null).getCategories().enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Category> categories = response.body();
                    for (int i = 0; i < categories.size(); i++) {
                        Category category = categories.get(i);
                        if (category.getName().equals(entry.getCategory()) || category.getId().equals(entry.getCategory())) {
                            entry.setCategory(category.getId());
                            switch (type) {
                                case "Create":
                                    createEntry(activity, entry);
                                    return;
                                case "Update":
                                    updateEntry(activity, entry);
                                    return;
                            }
                            return;
                        }
                    }
                    createCategory(activity, entry);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {

            }
        });
    }

    public static void createCategory(final Activity activity, final Entry entry) {
        Category category = new Category();
        category.setName(entry.getCategory());
        category.setType("expense");
        RestClient.setupRestClient(APIs.class, null).createCategory(category).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Logger.e("create category response " + response.isSuccessful() + " msg: " + response.message());
                if (response.isSuccessful()) {
                    findCategoryId(activity, entry, "Create");
                } else {
                    Logger.e("Something went wrong -> " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Logger.e("create category failure " + t.getLocalizedMessage());
            }
        });
    }

}
