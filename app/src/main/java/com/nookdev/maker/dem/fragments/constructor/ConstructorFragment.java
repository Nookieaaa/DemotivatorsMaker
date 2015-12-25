package com.nookdev.maker.dem.fragments.constructor;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nookdev.maker.dem.helpers.FileManager;
import com.nookdev.maker.dem.activity.MainActivity;
import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.interfaces.ImagePicker;
import com.nookdev.maker.dem.interfaces.ImageSetter;
import com.nookdev.maker.dem.interfaces.SaveCallback;
import com.nookdev.maker.dem.models.Demotivator;


public class ConstructorFragment extends Fragment implements ImagePicker, ImageSetter, MainActivity.DemotivatorSaver {

    public static final String TAG_NAME = ConstructorFragment.class.getSimpleName();

    private ImagePicker mActivityImagePicker;
    private ImageSetter mViewImageSetter;
    private DemotivatorInfo mDemotivatorInfo;
    private SaveCallback mSaveCallback;

    private static final String LAST_DEM_BITMAP = "LAST_DEM_BITMAP";
    private Bitmap savedPic;


    public ConstructorFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity) {
            mActivityImagePicker = (ImagePicker) context;
            mSaveCallback = (SaveCallback) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (savedPic!=null){
            outState.putParcelable(LAST_DEM_BITMAP, savedPic);
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ConstructorView v = (ConstructorView)view.findViewById(R.id.constructor_view);
        mViewImageSetter = (ImageSetter)v;
        mDemotivatorInfo = (DemotivatorInfo)v;

        if(savedInstanceState!=null&&savedInstanceState.containsKey(LAST_DEM_BITMAP)){
            savedPic =(savedInstanceState.getParcelable(LAST_DEM_BITMAP));
            setImage(savedPic);
        }
    }

    @Override
    public void pickImage(int sourceMode) {
        mActivityImagePicker.pickImage(sourceMode);
    }

    @Override
    public void setImage(Bitmap image) {
        savedPic = image;
        mViewImageSetter.setImage(image);
    }

    @Override
    public void save() throws FileManager.ExternalStorageNotReadyException, FileManager.DirectoryCreationFailed {
        String caption = mDemotivatorInfo.getCaption();
        String text = mDemotivatorInfo.getText();
        Bitmap image = mDemotivatorInfo.getImage();

        Demotivator demotivator = new Demotivator(image,caption,text);

        SaveFileTask sft = new SaveFileTask();
        sft.execute(demotivator);


    }

    public interface DemotivatorInfo{
        public String getText();
        public String getCaption();
        public Bitmap getImage();
    }

    public class SaveFileTask extends AsyncTask<Demotivator,Void,Uri>{

        @Override
        protected Uri doInBackground(Demotivator... params) {
            Uri result = null;
            if (params.length==0)
                return result;
            FileManager fileManager = FileManager.getInstance();
            try {
                result = fileManager.saveDem(params[0]);
            } catch (FileManager.ExternalStorageNotReadyException | FileManager.DirectoryCreationFailed e) {
                e.printStackTrace();
            }
            return result;

        }

        @Override
        protected void onPostExecute(Uri uri) {
            mSaveCallback.deliverSaveResult(uri,(uri!=null));
        }
    }

}
