package com.nookdev.maker.dem.fragments.constructor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nookdev.maker.dem.R;


public class ConstructorFragment extends Fragment {
    public static final String TAG_NAME = ConstructorFragment.class.getSimpleName();
    private ConstructorController mConstructorController;



    public ConstructorFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

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

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View v = view.findViewById(R.id.constructor_view);
    }



/*
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
    }*/

}
