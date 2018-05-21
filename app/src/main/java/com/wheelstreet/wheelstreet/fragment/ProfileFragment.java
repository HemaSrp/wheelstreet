package com.wheelstreet.wheelstreet.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wheelstreet.wheelstreet.R;
import com.wheelstreet.wheelstreet.database.MyDatabase;
import com.wheelstreet.wheelstreet.model.ProfileDatabase;
import com.wheelstreet.wheelstreet.utils.SharedPrefManager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements FloatingActionButton.OnClickListener{

    private EditText userName;
    private EditText email;
    private EditText phoneNumber;
    private EditText age;
    private MyDatabase db;
    private CircleImageView profileImg;
    private FloatingActionButton floatingButton;
    private String strAge;
    private String strPhoneNumber;
    private String strEmail;
    private String strUserName;
    private String drawableImage;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private String strGender;
    private SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.activity_profile, container, false);
        return result;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db=new MyDatabase(getActivity());

        profileImg=(CircleImageView)view.findViewById(R.id.imageview_account_profile);
        userName=(EditText)view.findViewById(R.id.userName);
        email=(EditText)view.findViewById(R.id.email);
        phoneNumber=(EditText)view.findViewById(R.id.phoneNumber);
        radioButtonFemale=(RadioButton)view.findViewById(R.id.radioButtonFemale);
        radioButtonMale=(RadioButton)view.findViewById(R.id.radioButtonMale);
        floatingButton=(FloatingActionButton)view.findViewById(R.id.editButton);
        age=(EditText)view.findViewById(R.id.age);
          db.getAllProfileDetails();
         init();

    }

    private void init() {
        sharedPrefManager = new SharedPrefManager(getActivity());

        drawableImage="edit";
        ProfileDatabase profileDetails = db.getAllProfileDetails().get(0);
        userName.setText(profileDetails.getProfileName());
        email.setText(profileDetails.getProfileEmailId());
        phoneNumber.setText(profileDetails.getProfileNumber());
        age.setText(profileDetails.getProfileAge());
        Glide.with(getActivity()).load(profileDetails.getProfilePic()).into(profileImg);
        floatingButton.setOnClickListener(this);
       if(profileDetails.getProfileGender()!=null && profileDetails.getProfileGender().equalsIgnoreCase("Female")) {
                radioButtonFemale.setChecked(true);
                strGender="Female";
            } else {
                radioButtonMale.setChecked(true);
                strGender="Male";

            }
    }

    /**
     * This method is used to create the instance of the fragment
     *
     * @return  fragment
     */
    public static Fragment newInstance() {
        ProfileFragment fragment=new ProfileFragment();
        return fragment;
    }

    @Override
    public void onClick(View v) {
        if(drawableImage.equals("edit")) {
            userName.setEnabled(true);
            email.setEnabled(true);
            phoneNumber.setEnabled(true);
            age.setEnabled(true);
            radioButtonMale.setClickable(true);
            radioButtonFemale.setClickable(true);
            floatingButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_check_black_24dp));
            drawableImage="tick";
        }else {
            radioButtonMale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    radioButtonMale.setChecked(true);
                    radioButtonMale.setChecked(false);
                    strGender="Male";

                }
            });
            radioButtonFemale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    radioButtonFemale.setChecked(true);
                    radioButtonFemale.setChecked(false);
                    strGender="Female";

                }
            });

            drawableImage = "edit";

            strUserName = userName.getText().toString().trim();
            strEmail = email.getText().toString().trim();
            strPhoneNumber = phoneNumber.getText().toString().trim();
            strAge = age.getText().toString().trim();
            if (strUserName.isEmpty() || strUserName.length() < 2) {
                Toast.makeText(getActivity(), "Please enter the valid username", Toast.LENGTH_SHORT).show();
            } else if (strEmail.isEmpty() || strEmail.length() < 2) {
                Toast.makeText(getActivity(), "Please enter the valid email", Toast.LENGTH_SHORT).show();
            } else if (strPhoneNumber.isEmpty() || strPhoneNumber.length() < 8) {
                Toast.makeText(getActivity(), "Please enter the valid phonenumber", Toast.LENGTH_SHORT).show();
            } else if (strAge.isEmpty() || strAge.length() < 2) {
                Toast.makeText(getActivity(), "Please enter the valid age", Toast.LENGTH_SHORT).show();
            } else if (!radioButtonMale.isChecked()&&!radioButtonFemale.isChecked()){
                Toast.makeText(getActivity(), "Please check the valid gender", Toast.LENGTH_SHORT).show();

            }else{
              db.updateProfileDetails(strUserName,strEmail,strPhoneNumber,strAge,strGender,sharedPrefManager.getUserToken());
              Toast.makeText(getActivity(),"Profile has been successfully updated",Toast.LENGTH_LONG).show();
                userName.setEnabled(false);
                email.setEnabled(false);
                phoneNumber.setEnabled(false);
                age.setEnabled(false);
                radioButtonMale.setClickable(false);
                radioButtonFemale.setClickable(false);
                floatingButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_edit_black_24dp));

            }



        }

    }
}
