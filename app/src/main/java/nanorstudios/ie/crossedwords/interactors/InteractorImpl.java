package nanorstudios.ie.crossedwords.interactors;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import nanorstudios.ie.crossedwords.ApiClient;
import nanorstudios.ie.crossedwords.interfaces.Interactor;
import nanorstudios.ie.crossedwords.interfaces.Presenter;
import nanorstudios.ie.crossedwords.interfaces.WordService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Interactor implementation.
 */

public class InteractorImpl implements Interactor {

    private Presenter presenter;

    private int wordSize;
    private String wordToSearchFor;

    public InteractorImpl(Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void searchForSynonyms(String word, int wordSize) {
        if (TextUtils.isEmpty(word)) {
            presenter.displayErrorMessage();
            return;
        }

        wordToSearchFor = word;
        this.wordSize = wordSize;
        fetchSynonmys(word);
    }

    public void fetchSynonmys(String word) {
        Call<WordService.SynonymResponse> synonymService =
                ApiClient.getWordService().getSynonyms(word);
        synonymService.enqueue(new Callback<WordService.SynonymResponse>() {
            @Override
            public void onResponse(Call<WordService.SynonymResponse> call, Response<WordService.SynonymResponse> response) {
                handleFetchSynonymsResponse(response);
            }

            @Override
            public void onFailure(Call<WordService.SynonymResponse> call, Throwable t) {

            }
        });
    }

    public void handleFetchSynonymsResponse(Response<WordService.SynonymResponse> response) {
        if (response != null && response.body() != null) {
            presenter.foundSynonyms(getCorrectSizedSynonyms(response.body().getSynonyms(), wordSize), wordToSearchFor, wordSize);
        } else {
            presenter.unableToFindSynonyms(wordToSearchFor);
        }
    }


    public List<String> getCorrectSizedSynonyms(List<String> synonyms, int correctSize) {
        if (correctSize == 0) {
            return synonyms;
        }
        List<String> correctlySizedSynonyms = new ArrayList<>();
        for (String word : synonyms) {
            if (word.length() == correctSize) {
                correctlySizedSynonyms.add(word);
            }
        }
        return correctlySizedSynonyms;
    }
}
