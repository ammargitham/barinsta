package awais.instagrabber.repositories.responses.story;

import androidx.annotation.NonNull;

import awais.instagrabber.repositories.responses.User;

public class ReelMention extends StorySticker {
    private final User user;

    public ReelMention(final float x,
                       final float y,
                       final int z,
                       final float width,
                       final float height,
                       final float rotation,
                       final int isPinned,
                       final int isHidden,
                       final int isSticker,
                       final int isFbSticker,
                       final String displayType,
                       final User user) {
        super(x, y, z, width, height, rotation, isPinned, isHidden, isSticker, isFbSticker, displayType);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @NonNull
    @Override
    public Type getType() {
        return Type.MENTION;
    }
}
