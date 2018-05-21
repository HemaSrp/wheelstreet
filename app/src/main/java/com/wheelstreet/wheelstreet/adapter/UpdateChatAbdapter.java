package com.wheelstreet.wheelstreet.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wheelstreet.wheelstreet.R;
import com.wheelstreet.wheelstreet.model.QuestionDatabase;

import java.util.List;

public class UpdateChatAbdapter extends RecyclerView.Adapter<UpdateChatAbdapter.MyViewHolder> {


    private final FragmentActivity fragment;
    private final List<QuestionDatabase> questionDetails;

    public UpdateChatAbdapter(FragmentActivity fragment, List<QuestionDatabase> questionDetails) {
    this.fragment=fragment;
    this.questionDetails=questionDetails;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_display_question_answer, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        QuestionDatabase questionAnswerList = questionDetails.get(position);

        holder.question.setText(questionAnswerList.getQuestion());
        holder.answer.setText(questionAnswerList.getAnswer());

    }

    @Override
    public int getItemCount() {
        return questionDetails.size();
    }

    /**
     * This class is the view holder of the current class.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView answer;

        private final TextView question;

        public MyViewHolder(View view) {
            super(view);
            question = (TextView)view.findViewById(R.id.question);
            answer = (TextView)view.findViewById(R.id.answer);



        }
    }
}