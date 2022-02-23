package com.example.fragmenttest2.title;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fragmenttest2.R;
import com.example.fragmenttest2.SetImage;
import com.example.fragmenttest2.asynchronous.AsyncRunnable;

import java.util.Objects;

public class TitleFragment extends Fragment {

    public String[] URLs = new String[] {
            "https://zipcloud.ibsnet.co.jp/api/search?zipcode=0791143",
            "https://zipcloud.ibsnet.co.jp/api/search?zipcode=1001701",
            "https://zipcloud.ibsnet.co.jp/api/search?zipcode=9041103"
    };
    public String[] str = new String[URLs.length];
    public ImageButton imageButton;
    public AssetManager assetManager;
    public SetImage setImage;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_title, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assetManager = Objects.requireNonNull(getActivity()).getAssets();
        setImage = new SetImage(assetManager);

        ImageView imageView = view.findViewById(R.id.image_view1);
        ImageButton userSelectionButton = view.findViewById(R.id.userSelection_Button);
        imageButton = view.findViewById(R.id.Start_Button);

        setImage.setImageViewBitmapFromAsset(imageView, "title/title.png");
        setImage.setImageButtonBitmapFromAsset(userSelectionButton, "title/ic_user.png");
        setImage.setImageButtonBitmapFromAsset(imageButton, "title/non_start.png");

        userSelectionButton.setOnClickListener(new onClickListener());

        Connection();
    }

    private void Connection() {
        new AsyncRunnable(
                URLs[0],
                b->Success(new String(b), 0),
                e->Failure(0)
        ).execute();

        new AsyncRunnable(
                URLs[1],
                b->Success(new String(b), 1),
                e->Failure(1)
        ).execute();

        new AsyncRunnable(
                URLs[2],
                b->Success(new String(b), 2),
                e->Failure(2)
        ).execute();
    }

    private void Success(String b, int i) {
        str[i] = b;
        setImage.setImageButtonBitmapFromAsset(imageButton, "title/start.png");
        imageButton.setOnClickListener(new onClickListener());
    }

    private void Failure(int i) {
        str[i] = "Not found";
        setImage.setImageButtonBitmapFromAsset(imageButton, "title/start.png");
        imageButton.setOnClickListener(new onClickListener());
    }


    public class onClickListener implements ImageButton.OnClickListener {
        @Override
        public void onClick(@NonNull View view) {
            int id = view.getId();
            TitleActivity activity = (TitleActivity) getActivity();

            switch (id){
                case R.id.Start_Button:
                    activity.ChangeActivity(str);
                    break;
                case R.id.userSelection_Button:
                    UserSelectionDialog dialogFragment = new UserSelectionDialog();
                    dialogFragment.show(getFragmentManager(), "userSelection");
                    break;
                default:
                    break;
            }
        }
    }
}