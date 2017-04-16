package nanorstudios.ie.crossedwords.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nanorstudios.ie.crossedwords.R;

/**
 * Recycler adapter to display the results.
 */

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
        return wordsList != null ? wordsList.size() : 0;
    }

    public void updateWordList(List<String> wordsList) {
        this.wordsList = wordsList;
        notifyDataSetChanged();
    }

    public void clearList() {
        wordsList.clear();
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