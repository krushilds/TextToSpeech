package helloandroid.example.com.gridviewexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dialog);
        Intent intent=getIntent();
        if (intent!=null){

           int imageId= intent.getIntExtra("image",R.drawable.me);
            String countryName= intent.getStringExtra("countryName");
            ImageView myImage=(ImageView) findViewById(R.id.imageView2);
             myImage.setImageResource(imageId);
            TextView myText = (TextView) findViewById(R.id.textView2);
            myText.setText("This button is: "+countryName);

        }
    }


    public void closeDialog(View v){
        finish();
    }


}
