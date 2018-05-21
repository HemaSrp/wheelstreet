package com.wheelstreet.wheelstreet.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wheelstreet.wheelstreet.R;
import com.wheelstreet.wheelstreet.adapter.ChatListAdapter;
import com.wheelstreet.wheelstreet.database.MyDatabase;
import com.wheelstreet.wheelstreet.model.Data;
import com.wheelstreet.wheelstreet.model.QuestionDatabase;
import com.wheelstreet.wheelstreet.model.Questions;
import com.wheelstreet.wheelstreet.retrofit.SOSInterface;
import com.wheelstreet.wheelstreet.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends Fragment {

    private RecyclerView mMessageRecycler;
    private ChatListAdapter mMessageAdapter;
    private SOSInterface mService;
    private MyDatabase db;
    private ProgressBar mprogressBar;
    private EditText mSendAnswer;
    private Button mSendButton;
    private QuestionDatabase dbModel;
    List<QuestionDatabase> questionDetails;
    private int valueId ;
    private List<QuestionDatabase> answerDetails;
    private List<QuestionDatabase> questionDetailsAnswer;
    private int viewPosition=0;
    private List<String> arrayListQuestionAndAnswer;
    private String questionId;
    private RegenerateArrayButtonClick mCallback;
    private Data connection;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.activity_chat, container, false);
        return result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mService = Utils.getSOService();
        db = new MyDatabase(getActivity());
        arrayListQuestionAndAnswer=new ArrayList<>();
        mMessageRecycler = (RecyclerView) view.findViewById(R.id.reyclerview_message_list);
        mprogressBar = (ProgressBar) view.findViewById(R.id.circular_progress_bar);
        mSendAnswer = (EditText) view.findViewById(R.id.edittext_chatbox);
        mSendButton = (Button) view.findViewById(R.id.button_chatbox_send);

        boolean networkAvailable = Utils.isNetworkAvailable(getActivity());
        questionDetails = db.getQuestionDetails();
          if(questionDetails.size()!=0) {
              mprogressBar.setVisibility(View.GONE);

              bindRecordsToTheRecyclerView();
          }else{
              if (networkAvailable) {
                  loadCollectionApi();
              }
          }

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sendAnswer = mSendAnswer.getText().toString().trim();
                if (!sendAnswer.isEmpty()) {
                    dbModel = new QuestionDatabase();
                    dbModel.setAnswerId(questionId);
                    dbModel.setAnswer(sendAnswer);
                    dbModel.setQuestionId(questionId);
                    dbModel.setAnswerDisplay("yes");
                    db.updateContact(questionId, dbModel);
                    bindRecords();
                    mSendAnswer.setText("");
                } else {
                    Toast.makeText(getActivity(), "Please enter the answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (RegenerateArrayButtonClick) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement RegenerateArrayButtonClick");
        }
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    /**
     * To interact with the activity
     */
    public interface RegenerateArrayButtonClick {
        void onClickButton();
    }
    /**
     * This method is used to create the instance of the fragment
     *
     * @return fragment
     */
    public static Fragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    /**
     * This method  is used to get the request and response from the api and stored in database
     *
     * @param txtFrom
     * @param txtTo
     */
    private void loadCollectionApi() {
        mprogressBar.setMax(20);
        mprogressBar.setVisibility(View.VISIBLE);
        mService.getPhotos().enqueue(new Callback<Questions>() {
            @Override
            public void onResponse(Call<Questions> call, Response<Questions> response) {
                db.deleteRecord();

                List<Data> connections = response.body().getData();
                if (!connections.isEmpty()) {
                    QuestionDatabase dbModel;
                    valueId=1;
                    viewPosition=0;

                    for (int i = 0; i < connections.size(); i++) {
                        dbModel = new QuestionDatabase();
                         connection = connections.get(i);
                        dbModel.setQuestionId(connection.getId());
                        dbModel.setQuestion(connection.getQuestion());
                        dbModel.setDataType(connection.getDataType());
                        if (i == 0) {
                            dbModel.setQuestionDisplay("yes");
                            dbModel.setAnswerDisplay("no");
                            dbModel.setPosition(String.valueOf(connections.size()));
                        } else {
                            dbModel.setQuestionDisplay("no");
                            dbModel.setAnswerDisplay("no");
                        }
                        db.insertQuestions(dbModel);
                    }
                    questionDetails = db.getQuestionDetails();
                    arrayListQuestionAndAnswer.add(questionDetails.get(0).getQuestion());
                    questionId=questionDetails.get(0).getQuestionId();

                    mMessageAdapter = new ChatListAdapter(getActivity(), arrayListQuestionAndAnswer);
                    //  mMessageAdapter.addItem( questionDetails,id);
                    mMessageRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mMessageRecycler.setAdapter(mMessageAdapter);
                    mprogressBar.setMax(100);

                    mprogressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getActivity(), "No route connection available", Toast.LENGTH_LONG).show();
                    mprogressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<Questions> call, Throwable t) {
                Toast.makeText(getActivity(), "Error loading }from API", Toast.LENGTH_LONG).show();
                mprogressBar.setVisibility(View.GONE);

            }
        });
    }

    private void bindRecords() {

        valueId = valueId + 1;
        viewPosition=viewPosition+2;
        dbModel = new QuestionDatabase();
        dbModel.setQuestionDisplay("yes");
        dbModel.setAnswerDisplay("no");
        dbModel.setPosition(String.valueOf(viewPosition));
        db.updateQuestion(String.valueOf(valueId), dbModel);
        bindRecordsToTheRecyclerView();
    }

    private void bindRecordsToTheRecyclerView() {
        questionDetailsAnswer = db.getQuestionDetails();
        arrayListQuestionAndAnswer.clear();
        for(int i=0;i<questionDetailsAnswer.size();i++) {
            arrayListQuestionAndAnswer.add(questionDetailsAnswer.get(i).getQuestion());
            questionId=questionDetailsAnswer.get(i).getQuestionId();
            if (questionDetailsAnswer.get(i).getAnswer()!=null)
                arrayListQuestionAndAnswer.add(questionDetailsAnswer.get(i).getAnswer());
        }
        mMessageAdapter = new ChatListAdapter(getActivity(), arrayListQuestionAndAnswer);
        // mMessageAdapter.addItem( questionDetails,id);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessageRecycler.setAdapter(mMessageAdapter);

        if(db.getAnswerDetails().size()==Integer.valueOf(questionDetailsAnswer.get(0).getPosition())){
            mCallback.onClickButton();
        }




    }



}
