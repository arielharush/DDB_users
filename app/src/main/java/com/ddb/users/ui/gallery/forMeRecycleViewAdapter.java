package com.ddb.users.ui.gallery;


import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddb.users.Entities.Enums.PackStatus;
import com.ddb.users.Entities.Parcel;
import com.ddb.users.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class forMeRecycleViewAdapter extends RecyclerView.Adapter<forMeRecycleViewAdapter.ParcelsTDViewHolder> {


    private Context baseContext;
    List<Parcel> parcels;

    public forMeRecycleViewAdapter(Context baseContext, List<Parcel> parcels) {
        this.parcels = parcels;
        this.baseContext = baseContext;
    }

    @Override
    public ParcelsTDViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(baseContext.getApplicationContext()).inflate(R.layout.parcel_for_me_item,
                parent,
                false);

        return new ParcelsTDViewHolder(v);
    }

    @Override
    public void onViewRecycled(@NonNull ParcelsTDViewHolder holder) {
        super.onViewRecycled(holder);

    }

    @Override
    public void onBindViewHolder(ParcelsTDViewHolder holder, int position) {
        long time = System.currentTimeMillis();
        final Parcel parcel = parcels.get(position);
        //holder.textViewHourReceived.setText(parcel.getKey());
        Calendar calendar = Calendar.getInstance();
        String date = helperDateAndHour(calendar.get(calendar.DAY_OF_MONTH)) + "/" + helperDateAndHour(calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
        String hour = helperDateAndHour(calendar.get(Calendar.HOUR)) + ":" + helperDateAndHour(calendar.get(Calendar.MINUTE));
        holder.textViewDate.setText(date);
        holder.textViewHour.setText(hour);
        holder.textViewParcelType.setText(Parcel.PackTypeTosString(parcel.getPackType()));
        holder.textViewWeight.setText(Parcel.packageWeightTosString(parcel.getPackageWeight()));
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IrevicedParcel(new Parcel(parcel));
            }
        });
    }

    @Override
    public int getItemCount() {
        return parcels.size();
    }

    class ParcelsTDViewHolder extends RecyclerView.ViewHolder {


        //TextView textViewHourReceived;
        TextView textViewParcelType;
        TextView textViewWeight;
        TextView textViewDate;
        TextView textViewHour;
        ImageButton imageButton;

        ParcelsTDViewHolder(final View itemView) {
            super(itemView);
            //textViewHourReceived = itemView.findViewById(R.id.nameddd);
            textViewParcelType = itemView.findViewById(R.id.parceltype);
            textViewWeight = itemView.findViewById(R.id.weight);
            textViewDate = itemView.findViewById(R.id.date);
            textViewHour = itemView.findViewById(R.id.hour);
            imageButton = itemView.findViewById(R.id.buttonImager);
            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

                @Override
                public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.setHeaderTitle("Select Action");

                    MenuItem delete = menu.add(Menu.NONE, 1, 1, "Close");

                    delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            // v.findViewById(R.id.LinnerParcel).setVisibility(View.GONE);

                            return true;
                        }
                    });
                }
            });
        }
    }


    /****************Functions for help******************/

    public String helperDateAndHour(int d) {

        if (d < 10) {
            return "0" + d;
        } else {
            return d + "";
        }
    }


    public void IrevicedParcel(Parcel parcel) {


        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://dblogisticare.firebaseio.com/");
            DatabaseReference ParcelsRef;
            ParcelsRef = database.getReference("parcels");
            ParcelsRef = ParcelsRef.child(parcel.getKey());
            //parcel.setDeliveryman_phone(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
            parcel.setPackStatus(PackStatus.RECEIVED);
            parcel.setLastUpdateTime((new Date()).getTime());
            ParcelsRef.setValue(parcel);

        } catch (Exception e) {

            Toast.makeText(baseContext, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }



}