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
        view.showProgressBar();
    }

    @Override
    public void displayErrorMessage() {
        view.hideProgressBar();
        view.displayErrorMessage();
        view.showBackground();
    }

    @Override
    public void foundSynonyms(List<String> synonyms, String wordToSearchFor, int wordSize) {
        view.hideBackground();
        view.updateSynonymList(synonyms, wordToSearchFor, wordSize);
    }

    @Override
    public void unableToFindSynonyms(String wordSearched) {
        view.hideProgressBar();
        view.unableToFindSynonyms(wordSearched);
        view.clearList();
        view.showBackground();
    }
}
