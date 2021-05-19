package awais.instagrabber.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import awais.instagrabber.adapters.viewholder.StoryArchiveListItemViewHolder;
import awais.instagrabber.databinding.ItemNotificationBinding;
import awais.instagrabber.repositories.responses.story.StoryArchiveResponse.ArchiveResponseItem;

public final class StoryArchiveListAdapter extends ListAdapter<ArchiveResponseItem, StoryArchiveListItemViewHolder> {
    private final OnArchiveClickListener listener;

    private static final DiffUtil.ItemCallback<ArchiveResponseItem> diffCallback = new DiffUtil.ItemCallback<ArchiveResponseItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull final ArchiveResponseItem oldItem, @NonNull final ArchiveResponseItem newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull final ArchiveResponseItem oldItem, @NonNull final ArchiveResponseItem newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    };

    public StoryArchiveListAdapter(final OnArchiveClickListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoryArchiveListItemViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final ItemNotificationBinding binding = ItemNotificationBinding.inflate(layoutInflater, parent, false);
        return new StoryArchiveListItemViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull final StoryArchiveListItemViewHolder holder, final int position) {
        final ArchiveResponseItem item = getItem(position);
        holder.bind(item);
    }

    public interface OnArchiveClickListener {
        void onArchiveClick(@NonNull final ArchiveResponseItem item);
    }
}
