package com.example.geogeo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;




import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;


public class map extends Activity {

    MapView map = null;
    TextView question;
    Button submit;
    ImageView mark;
    double lat;
    double lon;
    boolean isSet;
    Controller con;
    int gameId;

    //Marker mio = new Marker(map);

    private void pointSelected(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
        this.isSet = true;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_map);
        con = new Controller(ctx);
        Intent mIntent = getIntent();
        gameId = mIntent.getIntExtra("gameId", 0);

        map = (MapView) findViewById(R.id.map);

        question = (TextView) findViewById(R.id.question);
        question.setText("Insert Pic/text");

        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setMultiTouchControls(true); //2 Finger scroll
        IMapController mapController = map.getController();
        mapController.setZoom(2.5);


        GeoPoint startPoint = new GeoPoint(48.8583, 2.2944); //startpoint
        mapController.setCenter(startPoint);

        submit = (Button) findViewById(R.id.submit);

        MapEventsReceiver mReceive = new MapEventsReceiver() {

            Marker mark = new Marker(map);

            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                //Toast.makeText(getBaseContext(), p.getLatitude() + " - " + p.getLongitude(), Toast.LENGTH_LONG).show();

                double lat = p.getLatitude();
                double lon = p.getLongitude();
                GeoPoint clicked = new GeoPoint(lat, lon);

                mark.setPosition(clicked);
                mark.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                map.getOverlays().add(mark);

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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSet){
                    con.answerToRound(lat, lon, gameId);
                    startNextActivity();
                }
            }
        });



    }

    private void startNextActivity(){
        if (con.isGameOver(gameId)){
            Intent intent = new Intent(this, GameStatistics.class);
            intent.putExtra("gameId", gameId);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, map.class);
            intent.putExtra("gameId", gameId);
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



}