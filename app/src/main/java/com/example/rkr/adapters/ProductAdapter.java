package com.example.rkr.adapters;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rkr.R;
import com.example.rkr.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_FOOTER = 1;
    private static final String TAG = "ProductAdapter";

    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    private OnProductClickListener listener;

    public void setOnProductClickListener(OnProductClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        Log.d(TAG, "Binding: " + product.getName());
        Glide.with(holder.imageView.getContext())
                .load(product.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);
        holder.nameTextView.setText(product.getName());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onProductClick(product);
        });
    }

    public void updateProducts(List<Product> newProducts) {
        this.products = newProducts;
        notifyDataSetChanged();
        Log.d(TAG, "Adapter item count after update: " + getItemCount());
    }

    static class ProductDiffCallback extends DiffUtil.Callback {
        private final List<Product> oldList;
        private final List<Product> newList;

        public ProductDiffCallback(List<Product> oldList, List<Product> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            // Використовуємо ID для порівняння елементів
            return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.productNameTextView);
            imageView = itemView.findViewById(R.id.productImageView);
        }
    }
}