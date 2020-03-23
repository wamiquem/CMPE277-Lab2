package com.example.remembermystuff;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.List;

import com.example.remembermystuff.StuffListFragment.OnFragmentInteractionListener;


public class StuffListAdapter extends RecyclerView.Adapter<StuffListAdapter.ViewHolder> {

    private final List<Stuff> mStuffs;
    private final OnFragmentInteractionListener mListener;

    public StuffListAdapter(List<Stuff> stuffs , OnFragmentInteractionListener listener){
        mStuffs = stuffs;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_stuff, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mStuff = mStuffs.get(position);
        holder.mNameView.setText(mStuffs.get(position).getName());
        holder.mLocationView.setText(mStuffs.get(position).getLocation());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onStuffSelected(holder.mStuff);
                }
            }
        });

        holder.mEditButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(holder.mView.getContext(), AddStuff.class);
                intent.putExtra("stuff_id", mStuffs.get(position).getId());
                intent.putExtra("stuff_name", mStuffs.get(position).getName());
                intent.putExtra("stuff_location", mStuffs.get(position).getLocation());
                holder.mView.getContext().startActivity(intent);
            }
        });

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Stuff stuffToDelete = mStuffs.get(position);

                AlertDialog alertDialog = new AlertDialog.Builder(holder.mView.getContext()).create();
                alertDialog.setTitle("Alert Dialog");
                alertDialog.setMessage("Are you sure you want to delete?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDBHelper dbHelper = new SQLiteDBHelper(holder.mView.getContext());
                                dbHelper.deleteOne(stuffToDelete);
                                mStuffs.remove(position);  // remove the item from list
                                notifyItemRemoved(position); // notify the adapter about the removed item
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                // remove your item from data base

            }
        });
    }

    @Override
    public int getItemCount() {
        return mStuffs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mLocationView;
        public final ImageButton mDeleteButton;
        public final ImageButton mEditButton;
        public Stuff mStuff;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.view_stuff_name);
            mLocationView = view.findViewById(R.id.view_stuff_location);
            mDeleteButton = view.findViewById(R.id.delete_stuff_btn);
            mEditButton = view.findViewById(R.id.edit_stuff_btn);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLocationView.getText() + "'";
        }
    }
}
