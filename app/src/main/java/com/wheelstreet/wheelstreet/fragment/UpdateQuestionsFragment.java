package com.wheelstreet.wheelstreet.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.wheelstreet.wheelstreet.R;
import com.wheelstreet.wheelstreet.adapter.ChatListAdapter;
import com.wheelstreet.wheelstreet.adapter.UpdateChatAbdapter;
import com.wheelstreet.wheelstreet.database.MyDatabase;
import com.wheelstreet.wheelstreet.model.ProfileDatabase;
import com.wheelstreet.wheelstreet.model.QuestionDatabase;
import com.wheelstreet.wheelstreet.model.Questions;
import com.wheelstreet.wheelstreet.model.Response;
import com.wheelstreet.wheelstreet.retrofit.SOSInterface;
import com.wheelstreet.wheelstreet.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class UpdateQuestionsFragment extends Fragment implements FloatingActionButton.OnClickListener {

    private MyDatabase db;
    private List<QuestionDatabase> questionDetails;
    private UpdateChatAbdapter mMessageAdapter;
    private RecyclerView mMessageRecycler;
    private ProfileDatabase profileDetails;
    private TextView userName;
    private TextView emailId;
    private CircleImageView imageView;
    private FloatingActionButton updateFloatingButton;
    private SOSInterface mService;
    private List<QuestionDatabase> questionAndAnswerDetails;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.activity_updatechat, container, false);
        return result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db=new MyDatabase(getActivity());
        mService = Utils.getSOService();

        userName=(TextView)view.findViewById(R.id.userName);
        emailId=(TextView)view.findViewById(R.id.emailId);
        imageView=(CircleImageView)view.findViewById(R.id.imageview_account_profile);
        updateFloatingButton=(FloatingActionButton)view.findViewById(R.id.updateButton);

        mMessageRecycler = (RecyclerView) view.findViewById(R.id.reyclerview_message_list);
        questionDetails = db.getQuestionDetails();
        profileDetails=db.getAllProfileDetails().get(0);
        userName.setText(profileDetails.getProfileName());
        emailId.setText(profileDetails.getProfileEmailId());
        updateFloatingButton.setOnClickListener(this);
        Glide.with(getActivity()).load(profileDetails.getProfilePic()).into(imageView);

        mMessageAdapter = new UpdateChatAbdapter(getActivity(), questionDetails);
        //  mMessageAdapter.addItem( questionDetails,id);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessageRecycler.setAdapter(mMessageAdapter);
    }

    public static Fragment newInstance() {
        UpdateQuestionsFragment fragment = new UpdateQuestionsFragment();
        return fragment;    }

    @Override
    public void onClick(View v) {
        ProfileDatabase profileList = db.getAllProfileDetails().get(0);
        questionAndAnswerDetails = db.getQuestionDetails();
        JSONObject hashMap = new JSONObject();

        try {
            hashMap.put("id", profileList.getFaceBookId());
            hashMap.put("name", profileList.getProfileName());
            hashMap.put("fbUserName", profileList.getProfileName());
            hashMap.put("mobile", profileList.getProfileNumber());
            hashMap.put("gender", profileList.getProfileGender());
            hashMap.put("age", profileList.getProfileAge());
            hashMap.put("email", profileList.getProfileEmailId());

            JSONArray array = new JSONArray();
            for (int i = 0; i < questionAndAnswerDetails.size(); i++) {
                JSONObject jsonValues = new JSONObject();
                jsonValues.put("id", questionAndAnswerDetails.get(i).getQuestionId());
                jsonValues.put("answer", questionAndAnswerDetails.get(i).getAnswer());
                array.put(jsonValues);
            }

            hashMap.put("Questions", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        mService.sendQuestionsAndAnswers(hashMap).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Toast.makeText(getActivity(),"Question and answer successfully updated",Toast.LENGTH_LONG).show();
                updateFloatingButton.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }
}
