package com.example.rkr.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rkr.R;
import com.example.rkr.models.CompanyModel;

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

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        CompanyModel companyModel = companies.get(position);
        holder.nameTextView.setText(companyModel.getName());
        holder.addressTextView.setText(String.format("Адреса: %s", companyModel.getAddress()));
        holder.emailTextView.setText(String.format("Електронна пошта: %s", companyModel.getEmail()));
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
        TextView addressTextView;
        TextView emailTextView;

        CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.companyNameTextView);
            addressTextView = itemView.findViewById(R.id.companyAddressTextView);
            emailTextView = itemView.findViewById(R.id.companyEmailTextView);
        }
    }
}