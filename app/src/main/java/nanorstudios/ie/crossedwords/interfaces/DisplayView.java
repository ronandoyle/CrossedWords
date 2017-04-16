package nanorstudios.ie.crossedwords.interfaces;

import java.util.List;

/**
 * Interface for View.
 */

public interface DisplayView {
    void updateSynonymList(List<String> synonyms, String wordToSearchFor, int wordSize);
    void displayErrorMessage();
    void unableToFindSynonyms();
    void clearList();
    void showBackground();
    void hideBackground();
}
