package awais.instagrabber.repositories.responses.story;

import androidx.annotation.NonNull;

public class StorySlider extends StorySticker {
    private final SliderSticker sliderSticker;

    public StorySlider(final float x,
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
                       final SliderSticker sliderSticker) {
        super(x, y, z, width, height, rotation, isPinned, isHidden, isSticker, isFbSticker, displayType);
        this.sliderSticker = sliderSticker;
    }

    public SliderSticker getSliderSticker() {
        return sliderSticker;
    }

    @NonNull
    @Override
    public Type getType() {
        return Type.SLIDER;
    }

    public static class SliderSticker {
        private final long sliderId;
        private final String question;
        private final String emoji;
        private final String textColor;
        private final String backgroundColor;
        private final boolean viewerCanVote;
        private final float sliderVoteAverage;
        private final long sliderVoteCount;

        public SliderSticker(final long sliderId,
                             final String question,
                             final String emoji,
                             final String textColor,
                             final String backgroundColor,
                             final boolean viewerCanVote,
                             final float sliderVoteAverage, final long sliderVoteCount) {
            this.sliderId = sliderId;
            this.question = question;
            this.emoji = emoji;
            this.textColor = textColor;
            this.backgroundColor = backgroundColor;
            this.viewerCanVote = viewerCanVote;
            this.sliderVoteAverage = sliderVoteAverage;
            this.sliderVoteCount = sliderVoteCount;
        }

        public long getSliderId() {
            return sliderId;
        }

        public String getQuestion() {
            return question;
        }

        public String getEmoji() {
            return emoji;
        }

        public String getTextColor() {
            return textColor;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public boolean isViewerCanVote() {
            return viewerCanVote;
        }

        public float getSliderVoteAverage() {
            return sliderVoteAverage;
        }

        public long getSliderVoteCount() {
            return sliderVoteCount;
        }
    }
}
