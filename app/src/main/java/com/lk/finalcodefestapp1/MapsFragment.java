package com.lk.finalcodefestapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lk.finalcodefestapp1.Model.Mapdistanceobj;
import com.lk.finalcodefestapp1.Model.Maptimeobj;
import com.lk.finalcodefestapp1.Model.News;
import com.lk.finalcodefestapp1.directionsLib.FetchURL;

public class MapsFragment extends Fragment {


    private static final int LOCATION_PERMISSION = 101;
    FusedLocationProviderClient fusedLocationProviderClient;
    public GoogleMap cgoogleMap;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private LatLng shoplocation;
    private Marker shopmarker;

    private  LatLng customerlocation;
    private Polyline polyline;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {


            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsFragment.super.getContext());
            cgoogleMap = googleMap;


            updateCustomerLocation();
            updateshopLocation();


//            LatLng sydney = new LatLng(-34, 151);
//            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));



        }
    };


    private void updateshopLocation() {


        String newsID = ((Directionpage) getActivity()).getNewsID();

         db.collection("News").document(newsID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                 
                 if(task.isSuccessful()){

                     News news = task.getResult().toObject(News.class);


                     double location_lat = news.getLocation_lat();
                     double location_lon = news.getLocation_lon();


                     shoplocation=new LatLng(location_lat,location_lon);

                     BitmapDescriptor iconcurrent=getBitmapDesc(getActivity(),R.drawable.ic_tracking);

                     MarkerOptions currentposition=new MarkerOptions().draggable(false).position(shoplocation).title("our shop").icon(iconcurrent);
                     // MarkerOptions destinationposition=new MarkerOptions().draggable(true).position(customerlocation).title("I want to go ").icon(icontrack);


                     // cgoogleMap.addMarker(new MarkerOptions().position(customerlocation).title("I am here"));
                     shopmarker = cgoogleMap.addMarker(currentposition);

                     cgoogleMap.moveCamera(CameraUpdateFactory.zoomTo(12));

                   if(customerlocation !=null & shoplocation !=null){

                     //  Toast.makeText(getContext(), " calling", Toast.LENGTH_SHORT).show();

                       new FetchURL(){

                            //Toast.makeText(getContext(), "no calling"+values[0], Toast.LENGTH_SHORT).show();


                           @Override
                           public void onTaskDone(Object... values) {


                               if(polyline !=null){
                                   polyline.remove();


                               }



                               polyline=cgoogleMap.addPolyline((PolylineOptions) values[0]);





                           }

                           @Override
                           public void onTimeTaskDone(Maptimeobj maptimeobj) {


                               Toast.makeText(getContext(), ""+maptimeobj.getTimetext(), Toast.LENGTH_SHORT).show();

                               ((Directionpage)getActivity()).setDuration(maptimeobj.getTimetext());


                           }

                           @Override
                           public void onDistanceTaskDone(Mapdistanceobj mapdistanceobj) {


                               Toast.makeText(getContext(), ""+mapdistanceobj.getDistancetext(), Toast.LENGTH_SHORT).show();
                               ( (Directionpage) getActivity()).setEstimatedvalue(mapdistanceobj.getDistancetext());

                           }


                       }.execute(getUrl(customerlocation,shoplocation,"driving"),"driving");

                   }else {

                       Toast.makeText(getContext(), "no calling", Toast.LENGTH_SHORT).show();

                   }







                 }
                 
                 
             }
         });

    }

    private Marker currentmarker;

    private void updateCustomerLocation() {

        if (ActivityCompat.checkSelfPermission(MapsFragment.super.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsFragment.super.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // write permission request


            requestPermissions( new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION);

            return;
        } else {

            // updateCustomerLocation();


        }


        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {





            @Override
            public void onSuccess(Location location) {



                if(location !=null){

                    Toast.makeText(MapsFragment.super.getContext(),""+location.getAltitude()+""+location.getLatitude(),Toast.LENGTH_LONG).show();





                    customerlocation=new LatLng(location.getLatitude(),location.getLongitude());



                    BitmapDescriptor iconcurrent=getBitmapDesc(getActivity(),R.drawable.ic_walkto);




                    MarkerOptions currentposition=new MarkerOptions().draggable(false).position(customerlocation).title("I am Here").icon(iconcurrent);
                   // MarkerOptions destinationposition=new MarkerOptions().draggable(true).position(customerlocation).title("I want to go ").icon(icontrack);


                    // cgoogleMap.addMarker(new MarkerOptions().position(customerlocation).title("I am here"));
                    currentmarker = cgoogleMap.addMarker(currentposition);
                 //   destnationmarker= cgoogleMap.addMarker(destinationposition);

                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_user))

                    cgoogleMap.moveCamera(CameraUpdateFactory.newLatLng(customerlocation));
                    cgoogleMap.moveCamera(CameraUpdateFactory.zoomTo(12));









                }else{


                    Toast.makeText(MapsFragment.super.getContext(),"Null object",Toast.LENGTH_LONG).show();



                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MapsFragment.super.getContext(),"Error"+e.toString(),Toast.LENGTH_LONG).show();

            }
        });











    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {


        //Toast.makeText(getContext(), "url calling"+origin+"//"+dest, Toast.LENGTH_SHORT).show();
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" +"AIzaSyCSiedqkluAK04m-vTFkMxO1-W0BEZaxFs";
        Log.d("url","URL:"+url);
        return url;
    }







    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


    private BitmapDescriptor getBitmapDesc(FragmentActivity activity, int ic_tracking) {
        Drawable LAYER_1 = ContextCompat.getDrawable(activity,ic_tracking);
        LAYER_1.setBounds(0, 0, LAYER_1.getIntrinsicWidth(), LAYER_1.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(LAYER_1.getIntrinsicWidth(), LAYER_1.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        LAYER_1.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    
    
}