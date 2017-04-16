package nanorstudios.ie.crossedwords.interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Interface for the word service.
 */

public interface WordService {
    @GET("{word}/synonyms")
    Call<SynonymResponse> getSynonyms(@Path("word") String word);

    class SynonymResponse {
        List<String> synonyms;

        public List<String> getSynonyms() {
            return synonyms;
        }
    }
}
