package com.example.android.c196tracker.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.Entities.TermEntity;
import com.example.android.c196tracker.R;
import com.example.android.c196tracker.TermDetails;
import com.example.android.c196tracker.TermsActivity;
import com.example.android.c196tracker.ViewModel.TermViewModel;

import java.text.SimpleDateFormat;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    private final LayoutInflater inflater;
    private final Context context;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-YYYY");
    private TermEntity deletedItem;
    private List<TermEntity> terms;
    private int termId;
    private TermViewModel termViewModel;
    private Activity activity;


    public TermAdapter(Context context, Activity activity) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.activity = activity;
    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TermViewHolder holder, int position) {
        if (terms != null) {
            TermEntity current = terms.get(position);
            holder.termId = current.getTermId();
            holder.termNameTextView.setText(current.getTermName());
            holder.termStartTextView.setText(dateFormatter.format(current.getTermStart()));
            holder.termEndTextView.setText(dateFormatter.format(current.getTermEnd()));
        } else {
            holder.termNameTextView.setText("nothing");
            holder.termStartTextView.setText("nothing");
            holder.termEndTextView.setText("nothing");
        }
        holder.termNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                termId = holder.termId;
                String termName = holder.termNameTextView.getText().toString();
                String termStart = holder.termStartTextView.getText().toString();
                String termEnd = holder.termEndTextView.getText().toString();
                Intent intent = new Intent(context, TermDetails.class);
                intent.putExtra("termId", termId);
                intent.putExtra("termName", termName);
                intent.putExtra("termStart", termStart);
                intent.putExtra("termEnd", termEnd);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (terms != null)
            return terms.size();
        else return 0;
    }

    public void deleteItem(int position) {
        deletedItem = terms.get(position);
        termViewModel = new ViewModelProvider((TermsActivity) activity).get(TermViewModel.class);
        termViewModel.delete(deletedItem);
    }

    public void setTerms(List<TermEntity> terms) {
        this.terms = terms;
        notifyDataSetChanged();
    }

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termNameTextView;
        private final TextView termStartTextView;
        private final TextView termEndTextView;
        private int termId;

        private TermViewHolder(View itemView) {
            super(itemView);

            termNameTextView = itemView.findViewById(R.id.term_name);
            termStartTextView = itemView.findViewById(R.id.term_details_start);
            termEndTextView = itemView.findViewById(R.id.term_details_end);
            termId = -1;
        }
    }
}