package awais.instagrabber.adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import awais.instagrabber.R;
import awais.instagrabber.databinding.ItemStoryBinding;
import awais.instagrabber.repositories.responses.story.StoryMedia;
import awais.instagrabber.utils.ResponseBodyUtils;

public final class StoriesAdapter extends ListAdapter<StoryMedia, StoriesAdapter.StoryViewHolder> {
    private final OnItemClickListener onItemClickListener;

    private static final DiffUtil.ItemCallback<StoryMedia> diffCallback = new DiffUtil.ItemCallback<StoryMedia>() {
        @Override
        public boolean areItemsTheSame(@NonNull final StoryMedia oldItem, @NonNull final StoryMedia newItem) {
            return Objects.equals(oldItem.getPk(), newItem.getPk());
        }

        @Override
        public boolean areContentsTheSame(@NonNull final StoryMedia oldItem, @NonNull final StoryMedia newItem) {
            return Objects.equals(oldItem.getPk(), newItem.getPk());
        }
    };
    private int activeIndex;

    public StoriesAdapter(final OnItemClickListener onItemClickListener) {
        super(diffCallback);
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final ItemStoryBinding binding = ItemStoryBinding.inflate(layoutInflater, parent, false);
        return new StoryViewHolder(binding, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final StoryViewHolder holder, final int position) {
        final StoryMedia storyModel = getItem(position);
        holder.bind(storyModel, position, activeIndex == position);
    }

    public void setActiveIndex(final int activeIndex) {
        final int prevActiveIndex = this.activeIndex;
        this.activeIndex = activeIndex;
        // notify prev and current
        notifyItemChanged(prevActiveIndex);
        notifyItemChanged(activeIndex);
    }

    public final static class StoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemStoryBinding binding;
        private final OnItemClickListener clickListener;
        @DrawableRes
        private int selectableItemBackground;

        public StoryViewHolder(@NonNull final ItemStoryBinding binding,
                               @Nullable final OnItemClickListener clickListener) {
            super(binding.getRoot());
            final TypedValue typedValue = new TypedValue();
            final Context context = itemView.getContext();
            if (context != null) {
                context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
                selectableItemBackground = typedValue.resourceId;
            }
            this.binding = binding;
            this.clickListener = clickListener;
            binding.getRoot().setClipToOutline(true);
        }

        public void bind(final StoryMedia media,
                         final int position,
                         final boolean isActive) {
            if (media == null) return;

            itemView.setTag(media);
            itemView.setOnClickListener(v -> {
                if (clickListener == null) return;
                clickListener.onItemClick(media, position);
            });

            binding.getRoot().setForeground(ContextCompat.getDrawable(
                    itemView.getContext(),
                    isActive ? selectableItemBackground : R.drawable.fg_semi_black_with_ripple
            ));
            // binding.selectedView.setVisibility(isActive ? View.VISIBLE : View.GONE);
            final String imageUrl = ResponseBodyUtils.getImageUrl(media);
            binding.icon.setImageURI(imageUrl);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull StoryMedia storyModel, int position);
    }
}