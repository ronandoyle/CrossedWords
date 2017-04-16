package nanorstudios.ie.crossedwords.interfaces;

import java.util.List;

/**
 * Interface for View.
 */

public interface DisplayView {
    void updateSynonymList(List<String> synonyms, String wordToSearchFor, int wordSize);
    void displayErrorMessage();
    void unableToFindSynonyms(String wordSearched);
    void clearList();
    void showProgressBar();
    void hideProgressBar();
    void showBackground();
    void hideBackground();
}