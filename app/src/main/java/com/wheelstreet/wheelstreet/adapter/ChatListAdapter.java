package com.wheelstreet.wheelstreet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wheelstreet.wheelstreet.R;
import com.wheelstreet.wheelstreet.model.QuestionDatabase;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter {

    private List<String> mQuestionList;
    private List<QuestionDatabase> mAnswerDetails;
    private List<QuestionDatabase> mQuestionAndAnswer;


    public ChatListAdapter(Context context, List<String> questionDetails) {
        this.mQuestionList = questionDetails;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        return position % 2 * 2;
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_question, parent, false);
                return new QuestionHolder(view);
            case 2:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_answer, parent, false);
                return new AnswerHolder(view);

        }
        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case 0:
                ((QuestionHolder) holder).bind(mQuestionList.get(position));
           break;
            case 2:
                ((AnswerHolder) holder).bind(mQuestionList.get(position));
           break;

        }

    }

    private class QuestionHolder extends RecyclerView.ViewHolder {
        TextView questionText;

        QuestionHolder(View itemView) {
            super(itemView);

            questionText = (TextView) itemView.findViewById(R.id.text_message_body);
        }

        void bind(String strQuestion) {
            questionText.setText(strQuestion);
        }
    }

    private class AnswerHolder extends RecyclerView.ViewHolder {
        TextView answerText;
        AnswerHolder(View itemView) {
            super(itemView);

            answerText = (TextView) itemView.findViewById(R.id.text_message_body);

        }

       void bind(String answer) {
           answerText.setText(answer);


        }
    }

}
