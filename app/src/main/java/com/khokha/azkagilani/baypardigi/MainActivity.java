package com.khokha.azkagilani.baypardigi;
        import com.google.android.gms.ads.AdListener;
        import com.google.android.gms.ads.AdRequest;
        import com.google.android.gms.ads.InterstitialAd;
        import com.google.android.gms.ads.AdView;

        import android.app.Activity;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.res.Resources;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.CountDownTimer;
        import android.os.Handler;
        import android.support.v7.app.AlertDialog;
        import android.view.View;
        import android.webkit.WebView;
        import android.widget.EditText;
        import android.widget.Toast;

        import java.io.File;
        import java.util.Timer;
        import java.util.TimerTask;

public class MainActivity extends Activity {
    int currentImage;
    private InterstitialAd interAd;
    EditText Text1;
    String[] images;
    String html;
    int count = 0;

    Timer buttonTimer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);



            interAd = new InterstitialAd(this);

            interAd.setAdUnitId(getString(R.string.interstitial_ad));
            //Google ad unit
            //interAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

            ///Create Ad
            requestad();

            interAd.setAdListener(new AdListener() {
                //public void onAdLoaded() {
                //displayInterstitial();
                //}
                public void onAdClosed() {
                    // When user closes ad end this activity (go back to first activity)
                    requestad();

                }
            });

            Text1 = (EditText) findViewById(R.id.editText1);
            Text1.setText(String.valueOf(currentImage));

            WebView();

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.

        currentImage = savedInstanceState.getInt("message", currentImage);
        interAd = new InterstitialAd(this);

        interAd.setAdUnitId(getString(R.string.interstitial_ad));
        //Google ad unit
        //interAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        ///Create Ad
        requestad();

        interAd.setAdListener(new AdListener() {
            //public void onAdLoaded() {
            //displayInterstitial();
            //}
            public void onAdClosed() {
                // When user closes ad end this activity (go back to first activity)
                requestad();

            }
        });

        Text1 = (EditText) findViewById(R.id.editText1);
        Text1.setText(String.valueOf(currentImage));

        WebView();
        // This bundle has also been passed to onCreate.


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Rate Us:");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("market://details?id=" + getPackageName()));
                startActivity(i);
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        alert.create();
        alert.show();
    }
    public void requestad()
    {

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();

        interAd.loadAd(adRequest);
        mAdView.loadAd(adRequest);

    }

    public void displayInterstitial()
    {
        if(interAd.isLoaded())
        {
            interAd.show();
        }
    }

    //saved instance
    @Override
    protected void onSaveInstanceState (Bundle outState)
    {
        outState.putInt("message", currentImage );

    }

    public void onSearch (View view){

        try{
            count++;
            if (count>1)
            {
                count =0;

                displayInterstitial();
            }

        EditText searchTextString = (EditText) findViewById(R.id.searchText);

        currentImage = Integer.parseInt(searchTextString.getText().toString());
        Text1.setText(String.valueOf(currentImage));
        WebView();
        }
        catch(Exception ex)
        {
            Toast.makeText(MainActivity.this, "Error! Press any button" , Toast.LENGTH_SHORT).show();
            Text1.setText(String.valueOf(currentImage));
        }
    }

    public void onClickplus (View view)
    {

        count++;
        if (count>4)
        {
            count =0;

            displayInterstitial();
        }


        //String textnum = Text1.getText().toString();
        //int textNumInt = Integer.parseInt(textnum);
        //textNumInt = textNumInt + 1;
        //Text1.setText(textNumInt);

        currentImage = currentImage + 1;
        currentImage = (currentImage + images.length) % images.length;
        Text1.setText(String.valueOf(currentImage));
        WebView();
    }

    public void onClickminus (View view)
    {
        count++;
        if (count>4)
        {
            count =0;

            displayInterstitial();
        }

        currentImage = currentImage - 1;
        currentImage = currentImage % images.length;
        if (currentImage < 0)
        {
            currentImage = 0;
            //currentImage = images.length - 1;
        }
        Text1.setText(String.valueOf(currentImage));
        WebView();
    }
    public MainActivity()
    {  try{
        int i;
        currentImage = 0;
//CODE REPLACE
        images = new String[100];
        try

        {
                for (i = 0; i < images.length; i++) {
                    images[i] = "file:///android_asset/" + i + ".jpg";
                }
        }
        catch (Exception ex)
        {Toast.makeText(getApplicationContext(), "Error: ",
                Toast.LENGTH_LONG).show();
            currentImage = 0;
        }


        // {"file:///android_asset/0.jpg", "file:///android_asset/1.jpg", "file:///android_asset/1.jpg"
    }
    catch(Exception ex)
    {
        Toast.makeText(MainActivity.this, "Error "+ ex, Toast.LENGTH_SHORT).show();
    }
    };
    public void WebView()
    {

        WebView webView = null;
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        // webView.loadUrl(images[currentImage]); //new.html is html file name.
        // String summary = "<html><body><div align='center'><img src = "+ "file:///android_asset/0.jpg" +" align='middle' style='width:800px'></div></body></html>";
        // webView.loadData("<img src = 'file:///android_asset/0.jpg'>", "text/html", "UTF-8");

            html = "<html><body><img alt='End of Book. Thanks for Reading' src='"+ images[currentImage] +"' \" width=\"100%\" /></body></html>";

        if (images[currentImage] == null)
        {
            images[currentImage] = images[currentImage] + 1;
            webView.loadDataWithBaseURL(images[currentImage], html, "text/html", "UTF-8", null); // Encoding9
        }

            webView.loadDataWithBaseURL(images[currentImage], html, "text/html", "UTF-8", null); // Encoding9
    }


}
