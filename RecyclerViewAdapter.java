package com.example.fortouristsbytourists;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * managing the ArrayLists with attraction_names, attraction_locations and images in the RecyclerView widget.
 */
//public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mImageNames = new ArrayList<>(); //Name List Attraction Names - names of the attractions
    private ArrayList<String> mImage = new ArrayList<>(); //Name List Image Names (URL)
    private ArrayList<String> arrKey = new ArrayList<>();   //arrayList with database keys (attractions)
    private ArrayList<String> mLocation = new ArrayList<>();    //arrayList with attraction locations
    private Context mContext;

    Intent toAttraction;
    String uName;
    int category;

    public RecyclerViewAdapter(Context context, ArrayList<String> imageNames, ArrayList<String> image, ArrayList<String> arrKey, ArrayList<String> mLocation,  String uName, int category) {
        this.mImageNames = imageNames;
        this.mImage = image;
        this.mContext = context;
        this.arrKey = arrKey;
        this.uName = uName;
        this.mLocation = mLocation;
        this.category = category;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // describing the connection between current ArrayList element and a line in the RecyclerView/holder
        Log.d(TAG, "onBindViewHolder: called.");

        Picasso.get()   //transferring picture from Firebase Storage in to image holder
                .load(mImage.get(position))
                .into(holder.image);

        holder.imageName.setText(mImageNames.get(position));  //loading attraction name/title

        holder.location.setText(mLocation.get(position));     //loading attraction location

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));  //for testing only

                String url = mImage.get(position);
                String imageName = mImageNames.get(position);   //imageName <-- attraction.title

                toAttraction=new Intent(v.getContext(), Activity_07_Current_Attraction.class);
                toAttraction.putExtra("url",url);              //put the imageUrl in the repository for the next activity
                toAttraction.putExtra("imageName",imageName);  //attraction title
                toAttraction.putExtra("uName",uName);          //put the user name in the repository for the next activity
                toAttraction.putExtra("position",position + 1); //in C++ low level numbers starting from 0
                toAttraction.putExtra("category", category);   //put the category in the repository for the next activity
                v.getContext().startActivity(toAttraction);     // start the activity with that link
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {  //describe one element from the RcyclerReview(review_item.xml)

        ImageView image;
        TextView imageName, location;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) { //one itemView creating
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.attraction_name);
            location = itemView.findViewById(R.id.attraction_location);
        }
    }
}
