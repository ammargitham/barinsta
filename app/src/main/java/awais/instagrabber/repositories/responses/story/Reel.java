package awais.instagrabber.repositories.responses.story;

import java.util.List;

import awais.instagrabber.repositories.responses.User;

public class Reel {
    private final String id;
    private final long expiringAt;
    private final int seen;
    private final boolean canReply;
    private final boolean canGifQuickReply;
    private final boolean canReshare;
    private final String reelType;
    private final User user;
    private final List<StoryMedia> items;
    private final int mediaCount;
    private final List<Long> mediaIds;
    private final CoverMedia coverMedia;
    private final List<User> viewers;
    private final boolean storyIsSavedToArchive;

    public Reel(final String id,
                final long expiringAt,
                final int seen,
                final boolean canReply,
                final boolean canGifQuickReply,
                final boolean canReshare,
                final String reelType,
                final User user,
                final List<StoryMedia> items,
                final int mediaCount,
                final List<Long> mediaIds,
                final CoverMedia coverMedia,
                final List<User> viewers,
                final boolean storyIsSavedToArchive) {
        this.id = id;
        this.expiringAt = expiringAt;
        this.seen = seen;
        this.canReply = canReply;
        this.canGifQuickReply = canGifQuickReply;
        this.canReshare = canReshare;
        this.reelType = reelType;
        this.user = user;
        this.items = items;
        this.mediaCount = mediaCount;
        this.mediaIds = mediaIds;
        this.coverMedia = coverMedia;
        this.viewers = viewers;
        this.storyIsSavedToArchive = storyIsSavedToArchive;
    }

    public String getId() {
        return id;
    }

    public long getExpiringAt() {
        return expiringAt;
    }

    public int getSeen() {
        return seen;
    }

    public boolean isCanReply() {
        return canReply;
    }

    public boolean isCanGifQuickReply() {
        return canGifQuickReply;
    }

    public boolean isCanReshare() {
        return canReshare;
    }

    public String getReelType() {
        return reelType;
    }

    public User getUser() {
        return user;
    }

    public List<StoryMedia> getItems() {
        return items;
    }

    public int getMediaCount() {
        return mediaCount;
    }

    public List<Long> getMediaIds() {
        return mediaIds;
    }

    public CoverMedia getCoverMedia() {
        return coverMedia;
    }

    public List<User> getViewers() {
        return viewers;
    }

    public boolean isStoryIsSavedToArchive() {
        return storyIsSavedToArchive;
    }
}
