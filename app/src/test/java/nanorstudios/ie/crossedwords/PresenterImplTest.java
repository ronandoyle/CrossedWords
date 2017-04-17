package nanorstudios.ie.crossedwords;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import nanorstudios.ie.crossedwords.interfaces.DisplayView;
import nanorstudios.ie.crossedwords.interfaces.Interactor;
import nanorstudios.ie.crossedwords.presenters.PresenterImpl;

import static org.mockito.Mockito.verify;

/**
 * Tests for the presenter implementation class.
 */
public class PresenterImplTest {

    @Mock DisplayView view;
    @Mock Interactor interactor;

    private PresenterImpl presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new PresenterImpl(view, interactor);
    }

    @Test
    public void testSearchForSynonyms() {
        presenter.searchForSynonyms("word", 0);
        verify(interactor).searchForSynonyms("word", 0);
        verify(view).showProgressBar();
    }

    @Test
    public void testDisplayErrorMessage() {
        presenter.displayErrorMessage();
        verify(view).hideProgressBar();
        verify(view).displayErrorMessage();
        verify(view).showBackground();
    }

    @Test
    public void testFoundSynonyms() {
        List<String> stringsList = new ArrayList<>();
        String word = "word";
        presenter.foundSynonyms(stringsList, word, 0);
        verify(view).hideBackground();
        verify(view).updateSynonymList(stringsList, word, 0);
    }

    @Test
    public void unableToFindSynonyms() {
        presenter.unableToFindSynonyms("word");
        verify(view).hideProgressBar();
        verify(view).unableToFindSynonyms("word");
        verify(view).clearList();
        verify(view).showBackground();
    }
}