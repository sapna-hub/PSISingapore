package com.capgemini.psisingapore.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.capgemini.psisingapore.R;
import java.util.List;
import java.util.Map;

/**
 * Recycler view to show the list of PSI values.
 */
class PSIViewAdapter extends RecyclerView.Adapter<PSIViewAdapter.PSIViewHolder> {
    private List<Map<String, Integer>> psiList;
    private LayoutInflater mLayoutInflater;

    public PSIViewAdapter(LayoutInflater layoutInflater, List<Map<String, Integer>> psiList) {
        mLayoutInflater = layoutInflater;
        this.psiList = psiList;
    }

    @Override
    public PSIViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.dialog_item, parent, false);
        return new PSIViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PSIViewHolder holder, int position) {
        Map<String, Integer> psi = psiList.get(position);
        holder.psiText.setText(psi.toString().replace("{", "").replace("}", "")
                .replace("_", " "));
    }

    @Override
    public int getItemCount() {
        return psiList.size();
    }

    public static class PSIViewHolder extends RecyclerView.ViewHolder {
        TextView psiText;

        public PSIViewHolder(View itemView) {
            super(itemView);
            this.psiText = itemView.findViewById(R.id.psiText);
        }
    }
}
