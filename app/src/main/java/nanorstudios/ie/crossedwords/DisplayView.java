package nanorstudios.ie.crossedwords;

import java.util.List;

/**
 * Interface for View.
 */

public interface DisplayView {
    void updateSynonymList(List<String> synonyms, String wordToSearchFor, int wordSize);
    void displayErrorMessage();
    void unableToFindSynonyms();
}
