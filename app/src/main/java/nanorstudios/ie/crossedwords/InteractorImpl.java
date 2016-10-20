package nanorstudios.ie.crossedwords;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * TODO Update this line
 */

public class InteractorImpl implements Interactor {

    @Override
    public void fetchSynonmys(String word, final FetchedWordsListener listener) {
        Call<WordService.SynonymResponse> sysonmyService =
                ApiClient.getWordService().getSynonyms(word);
        sysonmyService.enqueue(new Callback<WordService.SynonymResponse>() {
            @Override
            public void onResponse(Call<WordService.SynonymResponse> call, Response<WordService.SynonymResponse> response) {
                listener.foundSynonmys(response.body().getSynonyms());
            }

            @Override
            public void onFailure(Call<WordService.SynonymResponse> call, Throwable t) {

            }
        });
    }
}
