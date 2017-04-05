package nanorstudios.ie.crossedwords;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * TODO Update this line
 */

public class PresenterImpl implements Presenter, FetchedWordsListener {

    private DisplayView displayView;
    private Interactor interactor;
    private int wordSize;
    private String wordToSearchFor;

    public PresenterImpl(DisplayView displayView) {
        this.displayView = displayView;
        interactor = new InteractorImpl();
    }

    @Override
    public void searchForSynonyms(String word, final int wordSize) {
        if (TextUtils.isEmpty(word)) {
            displayView.displayErrorMessage("Word cannot be blank.");
            return;
        }

        if (TextUtils.isEmpty(word)) {
            displayView.displayErrorMessage("Word must have a length.");
            return;
        }
        wordToSearchFor = word;
        this.wordSize = wordSize;
        interactor.fetchSynonmys(word, this);
    }

    @Override
    public void foundSynonyms(List<String> synonyms) {
        List<String> matchedSynonyms = getCorrectlySizedWords(synonyms, wordSize);
        displayView.updateSynonymList(matchedSynonyms, wordToSearchFor, wordSize, matchedSynonyms.size());
    }

    @Override
    public void unableToFindSynonyms() {
        displayView.displayErrorMessage("Unable to find anything for this word.");
    }

    private List<String> getCorrectlySizedWords(List<String> words, int correctSize) {
        if (correctSize == 0) {
            return words;
        }
        List<String> correctlySizedWords = new ArrayList<>();
        for (String word : words) {
            if (word.length() == correctSize) {
                correctlySizedWords.add(word);
            }
        }
        return correctlySizedWords;
    }
}
