package awais.instagrabber.repositories.responses.story;

import androidx.annotation.NonNull;

public abstract class StorySticker {
    private final float x;
    private final float y;
    private final int z;
    private final float width;
    private final float height;
    private final float rotation;
    private final int isPinned;
    private final int isHidden;
    private final int isSticker;
    private final int isFbSticker;
    private final String displayType;

    public StorySticker(final float x,
                        final float y,
                        final int z,
                        final float width,
                        final float height,
                        final float rotation,
                        final int isPinned,
                        final int isHidden,
                        final int isSticker,
                        final int isFbSticker,
                        final String displayType) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.isPinned = isPinned;
        this.isHidden = isHidden;
        this.isSticker = isSticker;
        this.isFbSticker = isFbSticker;
        this.displayType = displayType;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public boolean isPinned() {
        return isPinned == 1;
    }

    public boolean isHidden() {
        return isHidden == 1;
    }

    public boolean isSticker() {
        return isSticker == 1;
    }

    public boolean isFbSticker() {
        return isFbSticker == 1;
    }

    public String getDisplayType() {
        return displayType;
    }

    @NonNull
    public abstract Type getType();

    public enum Type {
        QUESTION,
        POLL,
        SLIDER,
        MENTION,
        QUIZ;
    }
}
