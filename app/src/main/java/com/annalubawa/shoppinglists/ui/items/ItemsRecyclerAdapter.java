package com.annalubawa.shoppinglists.ui.items;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.annalubawa.shoppinglists.databinding.ItemElementBinding;
import com.annalubawa.shoppinglists.domain.model.Item;

import java.util.List;

public class ItemsRecyclerAdapter extends RecyclerView.Adapter<ItemsRecyclerAdapter.ViewHolder> {

    private List<Item> items;
    private ItemClickListener itemListener;

    public ItemsRecyclerAdapter(List<Item> items, ItemClickListener itemListener) {
        this.items = items;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ItemsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemElementBinding binding = ItemElementBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsRecyclerAdapter.ViewHolder holder, int position) {
        holder.binding.setItem(items.get(position));
        holder.binding.getRoot().setOnLongClickListener(v -> {
            itemListener.onLongClick(items.get(position));
            return true;
        });
        holder.binding.checkIconView.setOnClickListener(v ->
                itemListener.onCheckIconClick(items.get(position))
        );
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemElementBinding binding;

        public ViewHolder(@NonNull ItemElementBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }

    public interface ItemClickListener {
        void onLongClick(Item item);
        void onCheckIconClick(Item item);
    }
}
