package dbug.toshltest.network;

import java.util.ArrayList;

import dbug.toshltest.models.Entry;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIs {

    /*
        All queries are optional. If they are not needed, pass the parameter null instead
     */
    @GET("entries")
    Call<ArrayList<Entry>> getEntries(@Query("page") Integer page,
                               @Query("per_page") Integer perPage,
                               @Query("since") String since,
                               @Query("from") String from,
                               @Query("to") String to,
                               @Query("type") String type,
                               @Query("accounts") String accounts,
                               @Query("categories") String categories,
                               @Query("!categories") String noCategories,
                               @Query("tags") String tags,
                               @Query("!tags") String noTags,
                               @Query("locations") String locations,
                               @Query("!locations") String noLocations,
                               @Query("repeat") String repeat,
                               @Query("search") String search,
                               @Query("include_deleted") Boolean includeDeleted,
                               @Query("expand") Boolean expand);

    @GET("entries/{id}/")
    Call<Entry> getEntry(@Path("id") Integer id);

    @POST("/entries")
    Call<?> createEntry(@Body Entry entry);

    @PUT("/entries/{id}/")
    Call<?> updateEntry(@Path("id") Integer id, @Body Entry entry);

    @DELETE("/entries/{id}/")
    Call<?> deleteEntry(@Path("id") Integer id);
}
