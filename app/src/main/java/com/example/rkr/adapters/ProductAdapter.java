package com.example.rkr.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rkr.R;
import com.example.rkr.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;

    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.nameTextView.setText(product.getName());
        // Залишаємо форматування, оскільки це все ще рядок для виведення
        holder.priceTextView.setText(String.format("Ціна: %s грн", product.getPrice()));
        holder.quantityTextView.setText(String.format("Об’єм: %s", product.getQuantity()));
        holder.barCodeTextView.setText(String.format("Штрих-код: %s", product.getBarCode()));
        holder.manufacturerTextView.setText(String.format("Виробник: %s", product.getManufacturer()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateProducts(List<Product> newProducts) {
        ProductDiffCallback diffCallback = new ProductDiffCallback(this.products, newProducts);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.products = newProducts;
        diffResult.dispatchUpdatesTo(this);
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
        TextView priceTextView;
        TextView quantityTextView;
        TextView barCodeTextView;
        TextView manufacturerTextView;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.productNameTextView);
            priceTextView = itemView.findViewById(R.id.productPriceTextView);
            quantityTextView = itemView.findViewById(R.id.productQuantityTextView);
            barCodeTextView = itemView.findViewById(R.id.productBarCodeTextView);
            manufacturerTextView = itemView.findViewById(R.id.productManufacturerTextView);
        }
    }
}