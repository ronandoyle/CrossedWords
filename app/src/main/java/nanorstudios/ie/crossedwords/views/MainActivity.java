package nanorstudios.ie.crossedwords.views;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nanorstudios.ie.crossedwords.BottomDecoration;
import nanorstudios.ie.crossedwords.interfaces.DisplayView;
import nanorstudios.ie.crossedwords.interfaces.Presenter;
import nanorstudios.ie.crossedwords.presenters.PresenterImpl;
import nanorstudios.ie.crossedwords.R;
import nanorstudios.ie.crossedwords.adapters.WordsAdapter;

public class MainActivity extends AppCompatActivity implements DisplayView, WordInputDialogFragment.UserInputInterface {

    private Presenter presenter;
    @BindView(R.id.fabButton) FloatingActionButton fabButton;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.wordsRecyclerView) RecyclerView recyclerView;
    @BindView(R.id.search_terms_text_view) TextView searchTermsTextView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    private WordsAdapter wordsAdapter;
    private Unbinder mUnbinder;
    private WordInputDialogFragment wordInputDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        presenter = new PresenterImpl(this);

        mUnbinder = ButterKnife.bind(this);
        MobileAds.initialize(getApplicationContext(), getString(R.string.admob_app_id));

        setupToolbar();
        setupRecyclerView();
        loadAd();
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.fabButton)
    protected void openDialog() {
        if (wordInputDialog == null) {
            wordInputDialog = new WordInputDialogFragment();
        }
        if (!wordInputDialog.isAdded()) {
            wordInputDialog.show(getSupportFragmentManager(), WordInputDialogFragment.TAG);
        }
    }

    private void setupRecyclerView() {
        wordsAdapter = new WordsAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new BottomDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(wordsAdapter);
    }

    @Override
    public void updateSynonymList(List<String> synonyms, String wordToSearchFor, int wordSize) {
        hideProgressBar();
        wordsAdapter.updateWordList(synonyms);
        if (synonyms != null) {
            updateSearchTerms(wordToSearchFor, wordSize, synonyms.size());
        } else {
            updateSearchTerms(wordToSearchFor, wordSize, 0);
        }
    }

    private void updateSearchTerms(String wordToSearchFor, int wordSize, int matchCount) {
        if (wordSize != 0) {
            searchTermsTextView.setText(String.format(getString(R.string.search_terms), matchCount, wordToSearchFor, wordSize));
        } else {
            searchTermsTextView.setText(String.format(getString(R.string.search_terms_no_size_specified), matchCount, wordToSearchFor));
        }
    }

    @Override
    public void displayErrorMessage() {
        hideProgressBar();
        Toast.makeText(getApplicationContext(), getString(R.string.no_blank), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void unableToFindSynonyms() {
        hideProgressBar();
        Toast.makeText(getApplicationContext(), getString(R.string.unable_to_find_synonyms), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void submit(String word, int wordSize) {
        showProgressBar();
        presenter.searchForSynonyms(word, wordSize);
    }

    private void loadAd() {
        final AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                adView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                adView.setVisibility(View.GONE);
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdLeftApplication() {
            }

            @Override
            public void onAdClosed() {
            }
        });
        adView.loadAd(adRequest);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
