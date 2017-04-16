package nanorstudios.ie.crossedwords.interfaces;

import java.util.List;

/**
 * Interface for the presenter.
 */

public interface Presenter {
    void searchForSynonyms(String word, int wordSize);

    void displayErrorMessage();

    void foundSynonyms(List<String> synonyms, String wordToSearchFor, int wordSize);

    void unableToFindSynonyms();
}