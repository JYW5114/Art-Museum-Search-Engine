package java.use_case.search;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Search {
    private static final String QUERY_INST = "https://collectionapi.metmuseum.org/public/collection/v1";
    private static final String MESSAGE = "message";

    // change to List<Artwork> soon
    // Frai: This is half finished bro butyou figured out how it works
    // TODO: get the IDs of the java.interface_adapters.search query etc then make an "object" API call to get attributes of the Artwork
    public static String searchArtwork(String query){
        final OkHttpClient client = new OkHttpClient();
        // java.interface_adapters.search?q=%s&username=%s to add more
        final Request req = new Request.Builder().url((String.format("%s/java.interface_adapters.search?q=%s", QUERY_INST, query))).build();
        // ArrayList<JSONObject> qresults = new ArrayList<>();

        try {
            final Response response = client.newCall(req).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            return responseBody.get("total").toString();

        }
        catch (IOException | JSONException event) {
            throw new RuntimeException(event);
        }
    }

}