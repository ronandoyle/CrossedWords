package nanorstudios.ie.crossedwords;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * TODO Update this line
 */

public interface WordService {
    @GET("{word}/synonyms")
    Call<SynonymResponse> getSynonyms(@Path("word") String word);

    class SynonymResponse {
        String word;
        List<String> synonyms;

        public String getWord() {
            return word;
        }

        public List<String> getSynonyms() {
            return synonyms;
        }
    }
}
