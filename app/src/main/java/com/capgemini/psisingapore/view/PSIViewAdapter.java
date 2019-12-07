package com.capgemini.psisingapore.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.capgemini.psisingapore.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Recycler view to show the list of PSI values.
 */
class PSIViewAdapter extends RecyclerView.Adapter<PSIViewAdapter.ViewHolder> {
    private ArrayList<HashMap<String, Integer>> arrPsiList;
    private  LayoutInflater mLayoutInflater;

    public PSIViewAdapter(LayoutInflater layoutInflater, ArrayList<HashMap<String, Integer>> arrPsiList) {
        mLayoutInflater = layoutInflater;
        this.arrPsiList = arrPsiList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.dialog_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, Integer> psi = arrPsiList.get(position);
        holder.psiText.setText(psi.toString().replace("{", "").replace("}", "")
                .replace("_", " "));
    }

    @Override
    public int getItemCount() {
        return arrPsiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView psiText;

        public ViewHolder(View itemView) {
            super(itemView);
            this.psiText = (TextView) itemView.findViewById(R.id.psiText);
        }
    }
}
