package helloandroid.example.com.gridviewexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.provider.SyncStateContract;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.support.v4.content.res.TypedArrayUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
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
import android.widget.ViewFlipper;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {




    GridView myGrid;
    GridView gview2;
    TextToSpeech ttsobject;
    int result;
    TextView mytext;
    String text;
    Button nextbutton, prevbutton;
    ViewFlipper flipper;
    VivzAdaper vivzAdaper;
    DrinkAdaper drinkAdaper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // findviewbyId returns view object, i also performed casting to import gridview lib
        myGrid=(GridView) findViewById(R.id.gridView);
        gview2= (GridView) findViewById(R.id.gridView2);
        flipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        nextbutton = (Button) findViewById(R.id.button2);
        prevbutton = (Button) findViewById(R.id.button3);

        mytext = (TextView) findViewById(R.id.textView);
        mytext.setMovementMethod(new ScrollingMovementMethod());


        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myGrid.setVisibility(View.INVISIBLE);
                gview2.setVisibility(View.VISIBLE);

            }
        });

        prevbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myGrid.setVisibility(View.VISIBLE);
                gview2.setVisibility(View.INVISIBLE);

            }
        });


        vivzAdaper = new VivzAdaper(this);
        myGrid.setAdapter(vivzAdaper);
        myGrid.setOnItemClickListener(this);


        drinkAdaper = new DrinkAdaper(this);
        gview2.setAdapter(drinkAdaper);
        gview2.setOnItemClickListener(this);


        gview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent secondint = new Intent();
                if (secondint != null) {

                    DrinksHolder drinksHolder = (DrinksHolder) view.getTag();
                    Drinks temp2 = (Drinks) drinksHolder.drinksHolder.getTag();
                    secondint.putExtra("drinkNames", temp2.drinkNames);
                    String drinksnames = secondint.getStringExtra("drinkNames");
                    drinksnames = mytext.getText() + " " + drinksnames;
                    TextView mytext = (TextView) findViewById(R.id.textView);
                    mytext.setText(drinksnames);
                    ttsobject.speak(temp2.drinkNames, TextToSpeech.QUEUE_FLUSH, null);

                }

            }
        });


        myGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println(position);


                // vivzAdaper.add(new Country(R.drawable.coffee, "New Country"));
                // myGrid.setAdapter(vivzAdaper);


                CharSequence text = vivzAdaper.list.remove(position).countryName + "deleted.";
                myGrid.setAdapter(vivzAdaper);
                vivzAdaper.notifyDataSetChanged();


                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();


                return false;
            }
        });


        //myGrid.setOnItemLongClickListener(this);


//        myGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {








        ttsobject = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {


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

    @Override
    protected void onDestroy() {


        //Close the Text to Speech Library
        if (ttsobject != null) {

            ttsobject.stop();
            ttsobject.shutdown();
            Log.d(text, "TTS Destroyed");
        }
        super.onDestroy();
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

        Intent intent = new Intent();

        if (i == 0) {

            flipper.showNext();
            System.out.println("went to second");

        }

        if (intent != null) {

            ViewHolder holder = (ViewHolder) view.getTag();
            Country temp = (Country) holder.myCountry.getTag();
            intent.putExtra("countryName", temp.countryName);
            String countryname =intent.getStringExtra("countryName");

            countryname = mytext.getText() + " " + countryname;

            TextView mytext = (TextView) findViewById(R.id.textView);
            mytext.setText(countryname);

            ttsobject.speak(temp.countryName, TextToSpeech.QUEUE_FLUSH, null);

            //temp.countryname - to say only the word thats clicked
            //countryname - to say everything because its a string

        }


    }

//    public void removeItem(int pos, String list){
//        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(list));
//        arrayList.remove(pos);
//        list = new String[arrayList.size()];
//        arrayList.toArray(list);
//        this.notifyDataSetChanged();
//    }


    //    ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(mobileValues));
    // this country class will combine both image and its name together




}


