package awais.instagrabber.adapters.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import awais.instagrabber.R;
import awais.instagrabber.adapters.StoryArchiveListAdapter.OnArchiveClickListener;
import awais.instagrabber.databinding.ItemNotificationBinding;
import awais.instagrabber.repositories.responses.MediaCandidate;
import awais.instagrabber.repositories.responses.story.StoryArchiveResponse.ArchiveResponseItem;

public final class StoryArchiveListItemViewHolder extends RecyclerView.ViewHolder {
    private final ItemNotificationBinding binding;
    private final OnArchiveClickListener listener;

    public StoryArchiveListItemViewHolder(@NonNull final ItemNotificationBinding binding,
                                          final OnArchiveClickListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }

    public void bind(final ArchiveResponseItem item) {
        if (item == null) return;
        final int storiesCount = item.getMediaCount();
        binding.tvComment.setVisibility(View.VISIBLE);
        binding.tvComment.setText(itemView.getResources().getQuantityString(R.plurals.stories_count, storiesCount, storiesCount));

        binding.tvSubComment.setVisibility(View.GONE);

        binding.tvUsername.setText(item.getFormattedLocalDateTime());

        binding.ivProfilePic.setVisibility(View.GONE);

        binding.ivPreviewPic.setVisibility(View.VISIBLE);
        final MediaCandidate coverImageVersion = item.getCoverImageVersion();
        binding.ivPreviewPic.setImageURI(coverImageVersion != null ? coverImageVersion.getUrl() : null);

        itemView.setOnClickListener(v -> {
            if (listener == null) return;
            listener.onArchiveClick(item);
        });
    }
}