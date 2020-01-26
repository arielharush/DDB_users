package com.ddb.users.ui.home;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddb.users.Entities.Enums.PackStatus;
import com.ddb.users.Entities.Parcel;
import com.ddb.users.R;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import static android.view.View.VISIBLE;


public class TakeDeliveryRecycleViewAdapter extends RecyclerView.Adapter<TakeDeliveryRecycleViewAdapter.ParcelsTDViewHolder> {


    private Context baseContext;
    List<Parcel> parcels;

    public TakeDeliveryRecycleViewAdapter(Context baseContext, List<Parcel> parcels) {
        this.parcels = parcels;
        this.baseContext = baseContext;
    }

    @Override
    public ParcelsTDViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(baseContext.getApplicationContext()).inflate(R.layout.take_parcel_item,
                parent,
                false);

        return new ParcelsTDViewHolder(v);
    }

    public void setParcels(List<Parcel> notes) {
        this.parcels = notes;
        notifyDataSetChanged();
    }


    @Override
    public void onViewRecycled(@NonNull ParcelsTDViewHolder holder) {
        super.onViewRecycled(holder);

    }


    @Override
    public void onBindViewHolder(ParcelsTDViewHolder holder, int position) {
        long time = System.currentTimeMillis();
        Parcel parcel = parcels.get(position);
        Calendar calendar = Calendar.getInstance();
        String date = helperDateAndHour(calendar.get(calendar.DAY_OF_MONTH)) + "/" + helperDateAndHour(calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
        String hour = helperDateAndHour(calendar.get(Calendar.HOUR)) + ":" + helperDateAndHour(calendar.get(Calendar.MINUTE));
        holder.textViewDate.setText(date);
        holder.textViewHour.setText(hour);
        holder.textViewParcelType.setText(Parcel.PackTypeTosString(parcel.getPackType()));
        holder.textViewWeight.setText(Parcel.packageWeightTosString(parcel.getPackageWeight()));
        float d = parcel.getDistance() / 1000;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        float twoDigitsF = Float.valueOf(decimalFormat.format(d));
        holder.textViewDisFrom.setText("  " + twoDigitsF + "  Km");


    }

    @Override
    public int getItemCount() {
        return parcels.size();
    }

    class ParcelsTDViewHolder extends RecyclerView.ViewHolder {


        TextView textViewHour;
        TextView textViewDate;
        TextView textViewParcelType;
        TextView textViewWeight;
        TextView textViewDisFrom;

        ParcelsTDViewHolder(final View itemView) {
            super(itemView);
            textViewHour = itemView.findViewById(R.id.hour);
            textViewDate = itemView.findViewById(R.id.date);
            textViewParcelType = itemView.findViewById(R.id.parceltype);
            textViewWeight = itemView.findViewById(R.id.weight);
            textViewDisFrom = itemView.findViewById(R.id.disFrom);

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


}