package com.pasotti.matteo.wikiheroes.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.pasotti.matteo.wikiheroes.R;
import com.pasotti.matteo.wikiheroes.databinding.ItemCharacterBinding;
import com.pasotti.matteo.wikiheroes.models.Character;
import com.pasotti.matteo.wikiheroes.models.CharacterResponse;
import com.pasotti.matteo.wikiheroes.view.ui.home.ItemCharacterViewModel;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {

    private CharacterResponse characterResponse;

    private List<Character> items;

    private int lastPosition = -1;

    public CharacterAdapter( CharacterResponse response) {
        this.items = response.getData().getResults();
        this.characterResponse = response;

    }


    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemCharacterBinding itemBinding = ItemCharacterBinding.inflate(layoutInflater, parent, false);
        return new CharacterViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Character item = getItemForPosition(position);
        holder.bind(item);

        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;
    }

    public Character getItemForPosition(int position) {
        return characterResponse.getData().getResults().get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull CharacterViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder {

        private final ItemCharacterBinding binding;

        public CharacterViewHolder(ItemCharacterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Character character) {
            binding.setVModel(new ItemCharacterViewModel(character));
            binding.executePendingBindings();
        }
    }
}
