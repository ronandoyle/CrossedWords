package nanorstudios.ie.crossedwords.presenters;

import java.util.List;

import nanorstudios.ie.crossedwords.interfaces.DisplayView;
import nanorstudios.ie.crossedwords.interfaces.Interactor;
import nanorstudios.ie.crossedwords.interactors.InteractorImpl;
import nanorstudios.ie.crossedwords.interfaces.Presenter;

/**
 * Presenter impplementation class.
 */

public class PresenterImpl implements Presenter {

    private DisplayView view;
    private Interactor interactor;


    public PresenterImpl(DisplayView displayView) {
        this.view = displayView;
        interactor = new InteractorImpl(this);
    }

    @Override
    public void searchForSynonyms(String word, final int wordSize) {
        interactor.searchForSynonyms(word, wordSize);
    }

    @Override
    public void displayErrorMessage() {
        view.displayErrorMessage();
    }

    @Override
    public void foundSynonyms(List<String> synonyms, String wordToSearchFor, int wordSize) {
        view.updateSynonymList(synonyms, wordToSearchFor, wordSize);
    }

    @Override
    public void unableToFindSynonyms() {
        view.unableToFindSynonyms();
    }
}
