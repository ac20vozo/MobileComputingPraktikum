package com.example.geogeo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.mapsforge.map.layer.download.tilesource.TileSource;

import org.osmdroid.api.IMapController;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.views.Projection;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.tileprovider.util.CloudmadeUtil;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.MapTileIndex;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class map extends Activity {

    MapView map = null;

    ImageView image;
    TextView question;
    Button submitb;
    Button closeb;

    IMapController mapController;

    GeoPoint clicked;

    final double minzoom =  3.119;
    final double maxzoom = 10.0;

    Controller con;

    Polyline line;
    Marker result;
    Marker mark;

    double lat;
    double lon;
    boolean isSet;
    int gameId;
    int questionId;
    int isPicQuestion;
    int [] NextQuestionInfo;


    private void pointSelected(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
        this.isSet = true;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);



        setContentView(R.layout.activity_map);

        Intent mIntent = getIntent();
        gameId = mIntent.getIntExtra("gameId", 0);
        questionId = mIntent.getIntExtra("questionId", 0);
        isPicQuestion = mIntent.getIntExtra("isPicQuestion", 0);

        con = new Controller(getApplicationContext());
        map = (MapView) findViewById(R.id.map);
        image = findViewById(R.id.pic);
        submitb = (Button) findViewById(R.id.submit);
        closeb = findViewById(R.id.closebutton);
        question = (TextView) findViewById(R.id.textv);

        mark = new Marker(map);
        result = new Marker(map);

        line = new Polyline();


        OnlineTileSourceBase sta= new XYTileSource("StamenMap", 1, 17, 256, ".jpg",
                new String[]{

                        "http://c.tile.stamen.com/watercolor/"
                },
                "Stamen");
        map.setTileSource(sta);


        map.setScrollableAreaLimitDouble(new BoundingBox(85, 180, -85, -180));
        map.setMaxZoomLevel(maxzoom);
        map.setMinZoomLevel(minzoom);
        map.setMultiTouchControls(true);


        mapController = map.getController();
        mapController.setZoom(4.0);


        GeoPoint startPoint = new GeoPoint(48.8583, 2.2944); //startpoint
        mapController.setCenter(startPoint);

        submitb.setVisibility(View.INVISIBLE);
        if(isPicQuestion == 1 ) {
            con.showPic(image, questionId);
        }
        else {
            con.showText(question,questionId);
        }

        MapEventsReceiver mReceive = new MapEventsReceiver() {


            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                //Toast.makeText(getBaseContext(), p.getLatitude() + " - " + p.getLongitude(), Toast.LENGTH_LONG).show();

                double lat = p.getLatitude();
                double lon = p.getLongitude();
                clicked = p;

                mark.setPosition(clicked);
                mark.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                map.getOverlays().add(mark);
                map.invalidate();
                submitb.setVisibility(View.VISIBLE);

                pointSelected(lat, lon);

                return false;
            }


            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }

        };


        MapEventsOverlay OverlayEvents = new MapEventsOverlay(getBaseContext(), mReceive);
        map.getOverlays().add(OverlayEvents);







    }

    private void startNextActivity(){
        if (con.isGameOver(gameId)){
            Intent intent = new Intent(this, GameStatistics.class);
            intent.putExtra("gameId", gameId);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, map.class);
            intent.putExtra("gameId", gameId);
            intent.putExtra("questionId", NextQuestionInfo[0]);
            intent.putExtra("isPicQuestion", NextQuestionInfo[1]);
            startActivity(intent);
        }
    }

    public void onResume(){
        super.onResume();
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }
    public void onPause(){
        super.onPause();
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }
    public void submit(View view){
        result.setPosition(new GeoPoint(40.730610, -73.935242));
        result.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        List<GeoPoint> geoPoints = new ArrayList<>();
        geoPoints.add(new GeoPoint(40.730610, -73.935242));//New York hardcoded
        geoPoints.add(clicked);

        line.setPoints(geoPoints);
        line.setColor(Color.GREEN);

        map.getOverlays().add(result);
        map.getOverlayManager().add(line);

        map.invalidate();


        mapController.animateTo(new GeoPoint(40.730610, -73.935242),4.0,3200L);//New YOrk hardcoded
        submitb.setVisibility(View.INVISIBLE);
        con.answerToRound(lat, lon, gameId);
        NextQuestionInfo = con.getNextQuestionInfo(gameId);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startNextActivity();
            }
        }, 5000);
        //startNextActivity();


    }
    public void closeimg(View view){
        image.setVisibility(View.INVISIBLE);
        question.setVisibility(View.INVISIBLE);
        closeb.setVisibility(View.INVISIBLE);
    }




}