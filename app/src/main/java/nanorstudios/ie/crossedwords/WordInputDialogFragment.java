package nanorstudios.ie.crossedwords;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Dialog Fragment for inserting words.
 */

public class WordInputDialogFragment extends DialogFragment {

    public static final String TAG = "WordInputDialogFragment";

    @BindView(R.id.textInputWord) TextInputLayout wordTextInput;
    @BindView(R.id.textInputWordSize) TextInputLayout wordSizeTextInput;
    @BindView(R.id.submit) Button submitBtn;

    private Unbinder mUnbinder;
    private UserInputInterface userInputInterface;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            userInputInterface = (UserInputInterface) context;
        } catch(ClassCastException e) {
            throw new ClassCastException("Must implement interface in MainActivity.");
        }
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment_user_input, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        getDialog().setTitle("Search for a synonym");
        return rootView;
    }

    @OnClick(R.id.submit)
    public void onSubmit() {
        String wordToSearch = wordTextInput.getEditText().getText().toString();
        String wordSizeString = wordSizeTextInput.getEditText().getText().toString();
        if (TextUtils.isEmpty(wordToSearch)) {
            Toast.makeText(getContext(), "Cannot search for blank word.", Toast.LENGTH_SHORT).show();
            return;
        }

        int wordSize = !TextUtils.isEmpty(wordSizeString) ? Integer.valueOf(wordToSearch) : 0;
        userInputInterface.submit(wordToSearch, wordSize);
        dismiss();
        logSearchEvent(wordToSearch, wordSize);
    }

    private void logSearchEvent(String wordToSearch, int wordSize) {
        Answers.getInstance().logCustom(new CustomEvent("Search Made")
        .putCustomAttribute("Word", wordToSearch)
        .putCustomAttribute("Word Size", wordSize));
    }

    public interface UserInputInterface {
        void submit(String word, int wordSize);
    }
}
