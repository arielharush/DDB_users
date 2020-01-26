package com.ddb.users.ui.share;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ddb.users.Entities.User;
import com.ddb.users.UserData;

public class ShareViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<User> mUser;
    public ShareViewModel() {
        mText = new MutableLiveData<>();
        mUser = new MutableLiveData<>();
        mText.setValue("This is share fragment");
        User temp = UserData.getUserData(UserData.contextT);
        if (temp != null) {
            mUser.setValue(temp);
        } else {

        }

    }

    public LiveData<String> getText() {
        return mText;
    }


    public LiveData<User> getUser() {

        return mUser;
    }

}