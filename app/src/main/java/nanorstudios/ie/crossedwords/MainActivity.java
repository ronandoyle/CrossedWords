package nanorstudios.ie.crossedwords;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements DisplayView, WordInputDialogFragment.UserInputInterface {

    private Presenter presenter;
    @BindView(R.id.fabButton) FloatingActionButton fabButton;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.wordsRecyclerView) RecyclerView recyclerView;
    @BindView(R.id.search_terms_text_view) TextView searchTermsTextView;
    private WordsAdapter wordsAdapter;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        presenter = new PresenterImpl(this);

        mUnbinder = ButterKnife.bind(this);

        MobileAds.initialize(getApplicationContext(), getString(R.string.admob_app_id));

        setupToolbar();
        setupFab();
        setupRecyclerView();
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

    private void setupFab() {
        fabButton = (FloatingActionButton) findViewById(R.id.fabButton);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        WordInputDialogFragment wordInputDialog = new WordInputDialogFragment();
        wordInputDialog.show(getSupportFragmentManager(), WordInputDialogFragment.TAG);
    }

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.wordsRecyclerView);
        wordsAdapter = new WordsAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new BottomDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(wordsAdapter);
    }

    @Override
    public void updateSynonymList(List<String> synonyms, String wordToSearchFor, int wordSize, int matchCount) {
        loadAdRequest();
        wordsAdapter.updateWordList(synonyms);
        updateSearchTerms(wordToSearchFor, wordSize, matchCount);
    }

    private void updateSearchTerms(String wordToSearchFor, int wordSize, int matchCount) {
        searchTermsTextView.setText(String.format(getString(R.string.search_terms), matchCount, wordToSearchFor, wordSize));
    }

    @Override
    public void displayErrorMessage(String message) {

    }

    @Override
    public void submit(String word, int wordSize) {
        presenter.searchForSynonyms(word, wordSize);
    }

    private void loadAdRequest() {
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
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });
        adView.loadAd(adRequest);
    }

    public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewHolder> {

        private List<String> wordsList = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (wordsList == null || wordsList.isEmpty()) {
                return;
            }
            holder.wordTextView.setText(wordsList.get(position));
        }

        @Override
        public int getItemCount() {
            return wordsList.size();
        }

        public void updateWordList(List<String> wordsList) {
            this.wordsList = wordsList;
            notifyDataSetChanged();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            TextView wordTextView;

            ViewHolder(View itemView) {
                super(itemView);
                wordTextView = (TextView) itemView.findViewById(R.id.wordTextView);
            }
        }

    }
}
