package com.ddb.users.ui.gallery;


import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddb.users.Entities.Parcel;
import com.ddb.users.R;

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
        View v = LayoutInflater.from(baseContext.getApplicationContext()).inflate(R.layout.parcel_item,
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
        Parcel parcel = parcels.get(position);
        holder.textViewHourReceived.setText(parcel.getKey());
    }

    @Override
    public int getItemCount() {
        return parcels.size();
    }

    class ParcelsTDViewHolder extends RecyclerView.ViewHolder {


        TextView textViewHourReceived;

        ParcelsTDViewHolder(final View itemView) {
            super(itemView);
            textViewHourReceived = itemView.findViewById(R.id.nameddd);

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