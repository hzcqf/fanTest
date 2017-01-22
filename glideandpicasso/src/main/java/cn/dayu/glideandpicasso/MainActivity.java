package cn.dayu.glideandpicasso;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private ImageView ivImg1,ivImg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        ivImg1 = (ImageView)findViewById(R.id.iv1);
        ivImg2 = (ImageView)findViewById(R.id.iv2);

        Picasso.with(context)
                .load("http://i.imgur.com/idojSYm.png")
                .into(ivImg1);

        Glide.with(context)
                .load("http://i.imgur.com/idojSYm.png")
                .into(ivImg2);

    }

}
