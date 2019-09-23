package com.piyushmaheswari.blogreading;

import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.InputStream;
import java.lang.annotation.Documented;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {


    private TextView textView;
    private ImageView imageView;

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlaceHolderFragment taskFragment;

        if(savedInstanceState==null)
        {
            taskFragment=new PlaceHolderFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(taskFragment,"MyFragment").commit();
        }
        else
        {
            taskFragment=(PlaceHolderFragment) getSupportFragmentManager()
                                .findFragmentByTag("MyFragment");

        }
        taskFragment.startTask();
    }

    public static class PlaceHolderFragment extends Fragment{

        TechCrunchTask downloadadTask;
        public PlaceHolderFragment()
        {

        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setRetainInstance(true);
        }

        public void startTask()
        {
            if(downloadadTask!=null)
            {
                downloadadTask.cancel(true);
            }
            else {
                downloadadTask=new TechCrunchTask();
                downloadadTask.execute();
            }
        }
    }

    public static class TechCrunchTask extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {

            String downloadUrl="http://feeds.feedburner.com/techcrunch/android?format=xml";
            try {
                URL url=new URL(downloadUrl);
                HttpURLConnection connection=(HttpURLConnection)url
                        .openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream=connection.getInputStream();
                processXML(inputStream);




            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        public void processXML(InputStream inputStream) throws Exception{

            DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
            Document xmlDocument=documentBuilder.parse(inputStream);

            Element rootElement=xmlDocument.getDocumentElement();

        }
    }
}
