package nanorstudios.ie.crossedwords;

import java.util.List;

/**
 * TODO Update this line
 */

public interface Presenter {
    void searchForSynonyms(String word, int wordSize);

    void displayErrorMessage();

    void foundSynonyms(List<String> synonyms, String wordToSearchFor, int wordSize);

    void unableToFindSynonyms();
}