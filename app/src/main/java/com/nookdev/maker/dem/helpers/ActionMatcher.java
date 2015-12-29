package com.nookdev.maker.dem.helpers;

import android.support.annotation.Nullable;

import com.nookdev.maker.dem.activity.MainActivity;
import com.nookdev.maker.dem.fragments.constructor.ConstructorFragment;
import com.nookdev.maker.dem.fragments.preview.PreviewFragment;

import java.util.HashMap;

import static com.nookdev.maker.dem.helpers.Constants.*;


public class ActionMatcher {
    private static HashMap<Integer,String> mMatcher;
    static {
        mMatcher = new HashMap<>();
        mMatcher.put(ACTION_TAKE_PHOTO, MainActivity.TAG_NAME);
        mMatcher.put(ACTION_PICK_IMAGE, MainActivity.TAG_NAME);
        mMatcher.put(ACTION_MAKE_PREVIEW, PreviewFragment.TAG_NAME);
        mMatcher.put(ACTION_SET_IMAGE, ConstructorFragment.TAG_NAME);
        mMatcher.put(ACTION_SAVE, MainActivity.TAG_NAME);
    }

    @Nullable
    public static String getReceiver(int action){
        return mMatcher.get(action);
    }
}
