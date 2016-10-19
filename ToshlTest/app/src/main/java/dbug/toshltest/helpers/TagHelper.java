package dbug.toshltest.helpers;

import android.app.Activity;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

import dbug.toshltest.common.Logger;
import dbug.toshltest.models.Entry;
import dbug.toshltest.models.Tag;
import dbug.toshltest.network.APIs;
import dbug.toshltest.network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static dbug.toshltest.helpers.EntryHelper.createEntry;
import static dbug.toshltest.helpers.EntryHelper.updateEntry;

public class TagHelper {

    public static void getTagIds(final Activity activity, final Entry entry, final String type) {
        final ArrayList<String> entryTags = entry.getTags();
        RestClient.setupRestClient(APIs.class, null).getTags().enqueue(new Callback<ArrayList<Tag>>() {
            @Override
            public void onResponse(Call<ArrayList<Tag>> call, Response<ArrayList<Tag>> response) {
                Logger.e("Tag IDs " + response.message());
                if (response.isSuccessful()) {
                    ArrayList<String> ids = new ArrayList<>();
                    ArrayList<String> tagsToCreate = new ArrayList<>();
                    ArrayList<Tag> tags = response.body();

                    for (int j = 0; j < entryTags.size(); j++) {
                        boolean hasTag = false;
                        for (int i = 0; i < tags.size(); i++) {
                            if (entryTags.get(j).equals(tags.get(i).getName())) {
                                ids.add(tags.get(i).getId());
                                hasTag = true;
                            }
                        }
                        if (!hasTag) tagsToCreate.add(entryTags.get(j).trim());
                    }

                    if (ids.size() == entryTags.size()) {
                        entry.setTags(ids);
                        CategoryHelper.findCategoryId(activity, entry, type);
                    } else {
                        loopForTags(tagsToCreate, activity, entry, type);
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Tag>> call, Throwable t) {
                Logger.e("Tag ids failure: " + t.getLocalizedMessage());
            }
        });
    }

    public static void loopForTags(ArrayList<String> missingTags, Activity activity, Entry entry, String type) {
        for (int i = 0; i < missingTags.size(); i++) {
            if (i < missingTags.size() - 1) {
                createTag(missingTags.get(i), "expense", false, activity, entry, type);
            } else createTag(missingTags.get(i), "expense", true, activity, entry, type);
        }
    }

    public static void createTag(String name, final String type, final boolean isLast, final Activity activity, final Entry entry, final String type2) {
        Tag tag = new Tag();
        tag.setName(name);
        tag.setType(type);

        RestClient.setupRestClient(APIs.class, null).createTag(tag).enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Logger.e("Create Tag response: " + response.message());
                if (isLast) {
                    getTagIds(activity, entry, type2);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}
