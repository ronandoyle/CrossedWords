package nanorstudios.ie.crossedwords;

import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import nanorstudios.ie.crossedwords.interactors.InteractorImpl;
import nanorstudios.ie.crossedwords.interfaces.Presenter;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Tests for the interactor implementation class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class InteractorImplTest {
    @Mock Presenter presenter;

    private InteractorImpl interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        interactor = new InteractorImpl(presenter);
    }

    @Test
    public void testGetCorrectSizedSynonyms_zeroSize() {
        List<String> synonyms = new ArrayList<>();
        assertEquals("Lists do not match", synonyms, interactor.getCorrectSizedSynonyms(synonyms, 0));
    }

    @Test
    public void testGetCorrectSizedSynonyms() {
        List<String> synonyms = new ArrayList<>();
        synonyms.add("one");
        synonyms.add("two");
        synonyms.add("three");
        synonyms.add("four");

        assertEquals("Size of list is not 2", 2, interactor.getCorrectSizedSynonyms(synonyms, 3).size());
        assertTrue("Word is not in list", interactor.getCorrectSizedSynonyms(synonyms, 3).contains("one"));
        assertTrue("Word is not in list", interactor.getCorrectSizedSynonyms(synonyms, 3).contains("two"));
    }

    @Test
    public void testSearchForSynonyms_blankWord() {
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(anyString())).thenReturn(true);
        interactor.searchForSynonyms("", 0);
        verify(presenter).displayErrorMessage();
    }

    @Test
    public void testSearchForSynonyms() {
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(anyString())).thenReturn(false);
        interactor = createSpyInteractor();
        doNothing().when(interactor).fetchSynonmys("word");
        interactor.searchForSynonyms("word", 0);
        verify(interactor).fetchSynonmys("word");

    }

    @Test
    public void testHandleFetchSynonymsResponse_null() {
        interactor.handleFetchSynonymsResponse(null);
        verify(presenter).unableToFindSynonyms(anyString());
    }

    private InteractorImpl createSpyInteractor() {
        return spy(new InteractorImpl(presenter));
    }
}