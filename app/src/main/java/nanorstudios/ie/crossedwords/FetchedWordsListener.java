package nanorstudios.ie.crossedwords;

import java.util.List;

/**
 * TODO Update this line
 */

public interface FetchedWordsListener {
    void foundSynonyms(List<String> word);
    void unableToFindSynonyms();
}
