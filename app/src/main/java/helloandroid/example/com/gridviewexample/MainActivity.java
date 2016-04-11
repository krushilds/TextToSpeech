package helloandroid.example.com.gridviewexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {




    GridView myGrid;
    GridView gview2;
    TextToSpeech ttsobject;
    int result;
    TextView mytext;
    String text;
    Button nextbutton, prevbutton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // findviewbyId returns view object, i also performed casting to import gridview lib
        myGrid=(GridView) findViewById(R.id.gridView);
        gview2= (GridView) findViewById(R.id.gridView2);

        nextbutton = (Button) findViewById(R.id.button2);
        prevbutton = (Button) findViewById(R.id.button3);

        mytext = (TextView) findViewById(R.id.textView);


        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myGrid.setVisibility(View.GONE);
                gview2.setVisibility(View.VISIBLE);

            }
        });

        prevbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myGrid.setVisibility(View.VISIBLE);
                gview2.setVisibility(View.GONE);

            }
        });






        myGrid.setAdapter(new VivzAdaper(this));
        myGrid.setOnItemClickListener(this);



        ttsobject = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.SUCCESS){

                    result = ttsobject.setLanguage(Locale.UK);


                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Language not supported",Toast.LENGTH_SHORT).show();
                }

            }


            });







    }

    public void sayWord(View v){

        if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {

            Toast.makeText(getApplicationContext(),
                    "Feature not supported for this device",
                    Toast.LENGTH_SHORT).show();
        } else {

            text = mytext.getText().toString();



            ttsobject.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }

    }





    public void deleteWord(View view){
        Button delete = (Button) findViewById(R.id.button4);
        String deletetext = mytext.getText().toString();
        if (deletetext.length() > 0){
            mytext.setText(deletetext.substring(0, deletetext.length() - 1));
           // mytext.setSelected(mytext.getText().length());

        }else{

            Toast toast = Toast.makeText(MainActivity.this,"no text",Toast.LENGTH_SHORT);
            toast.show();
        }




    }



//    Button delete = (Button) findViewById(R.id.button4);
//    if(delete != null){
//        delete.setOnClickListener(new onClickListner(){
//            public void onClick(){
//                String deletetext = mytext.getText().toString();
//                if (deletetext.length() > 0 ){
//                mytext.setText(deletetext.substring(0, deletetext.length() -1));
//                    mytext.setSelection(mytext.getText().length());
//
//
//                }
//            }
//        });
//    }
    










    // the first perameter inside onitemclick method 'adapterview' represents the gridview,
    // the second perameter view represents the single item that was clicked inside the gridview,
    // the third is the position where the item was clicked(position of the image inside gridview,
    // fourth is when i will be using sqlite database and content providers etc.

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//        Intent intent=new Intent(this,MyDialog.class);
//        ViewHolder holder= (ViewHolder) view.getTag();
//        Country temp= (Country) holder.myCountry.getTag();
//        intent.putExtra("image",temp.imageId);
//        intent.putExtra("countryName",temp.countryName);
//        startActivity(intent);
//

        Intent intent=new Intent();
        if (intent!=null){

            ViewHolder holder= (ViewHolder) view.getTag();
            Country temp= (Country) holder.myCountry.getTag();
            intent.putExtra("countryName",temp.countryName);
            String countryname =intent.getStringExtra("countryName");

            countryname = mytext.getText()+ " " + countryname ;

            TextView mytext = (TextView) findViewById(R.id.textView);
            mytext.setText(countryname);


        }



    }


    class Country{

        int imageId;
        String countryName;

        //constructor
        Country (int imageId, String countryName){

            this.imageId = imageId;
            this.countryName = countryName;

        }


    }


    class ViewHolder{

        ImageView myCountry;

        ViewHolder(View v){

            myCountry= (ImageView) v.findViewById(R.id.imageView);

        }
    }


    class VivzAdaper extends BaseAdapter{



        ArrayList<Country> list;
        Context context;
        // created object 'context' so that i can get all the list of the names from xml file
        VivzAdaper(Context context){
            this.context=context;
            list=new ArrayList<Country>();
            Resources res = context.getResources();
            String[] tempCountryNames=res.getStringArray(R.array.country_names);
            int[] countryImages = {R.drawable.i,R.drawable.me,R.drawable.you};
            for (int i=0; i<3; i++){

                Country tempCountry = new Country(countryImages[i], tempCountryNames[i]);
                list.add(tempCountry);

            }

        }



        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }



        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ViewHolder holder=null;
            View row=view;
            if (row==null){

                LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // inflate will take the xml file and give me java object
                 row=inflater.inflate(R.layout.single_item,viewGroup,false);
                holder=new ViewHolder(row);
                row.setTag(holder);
            }
            else
            {
                holder= (ViewHolder) row.getTag();
            }
            Country temp=list.get(i);
            holder.myCountry.setImageResource(temp.imageId);
            holder.myCountry.setTag(temp);

            return row;
        }
    }


}


