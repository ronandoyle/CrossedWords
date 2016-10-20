package nanorstudios.ie.crossedwords;

import java.util.List;

/**
 * TODO Update this line
 */

public interface DisplayView {
    void updateSynonymList(List<String> synonyms, String wordToSearchFor, int wordSize, int matchCount);
    void displayErrorMessage(String message);
}
