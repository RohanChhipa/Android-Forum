package com.forum.retrofit.ui.picsum;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.forum.retrofit.R;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

public class PicsumFragment extends Fragment {

    private ImageView imageHolder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_picsum, container, false);
        imageHolder = root.findViewById(R.id.image_holder);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl("https://picsum.photos")
                .build();

        PicsumService picsumService = retrofit.create(PicsumService.class);
        picsumService.getImage(576, 1024)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    byte[] image = responseBody.bytes();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                    imageHolder.setImageBitmap(bitmap);
                });
    }
}