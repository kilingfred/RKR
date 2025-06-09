package com.example.rkr.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rkr.R;
import com.example.rkr.models.CompanyModel;
import com.example.rkr.models.Product;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {

    private List<CompanyModel> companies;

    public CompanyAdapter(List<CompanyModel> companies) {
        this.companies = companies;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, parent, false);
        return new CompanyViewHolder(view);
    }

    public interface OnCompanyClickListener {
        void onCompanyClick(CompanyModel companyModel);
    }

    private OnCompanyClickListener listener;

    public void setOnCompanyClickListener(OnCompanyClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        CompanyModel companyModel = companies.get(position);
        holder.nameTextView.setText(companyModel.getName());
        Glide.with(holder.imageView.getContext())
                .load(companyModel.getLogo())
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onCompanyClick(companyModel);
        });
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public void updateCompanies(List<CompanyModel> newCompanies) {
        this.companies = newCompanies;
        notifyDataSetChanged();
    }

    static class CompanyViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;

        CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.companyNameTextView);
            imageView = itemView.findViewById(R.id.companyLogo);
        }
    }
}