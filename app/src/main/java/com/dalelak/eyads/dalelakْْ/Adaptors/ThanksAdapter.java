package com.dalelak.eyads.dalelakْْ.Adaptors;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dalelak.eyads.dalelakْْ.DatalistThanks;
import com.dalelak.eyads.dalelakْْ.R;


import java.util.List;

/**
 * Created by eyads on 3/7/2018.
 */

public class ThanksAdapter extends RecyclerView.Adapter<ThanksAdapter.ViewHolder > {
    private Context context;
    private List<DatalistThanks> list;

    public ThanksAdapter(Context context, List<DatalistThanks> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View r=inflater.inflate(R.layout.thanksitem, parent, false);
        ThanksAdapter.ViewHolder viewHolder =new ThanksAdapter.ViewHolder(r);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.link.setText(list.get(position).getLink());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.link.getText().toString().trim()));
                    context.startActivity(browserIntent);
                }catch (ActivityNotFoundException d){
                    return;
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView link;
        private RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name_thanks);
            link=(TextView)itemView.findViewById(R.id.link_thanks);

            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.thankrelative);
        }
    }
}
