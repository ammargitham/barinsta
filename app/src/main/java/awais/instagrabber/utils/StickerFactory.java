package awais.instagrabber.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import awais.instagrabber.customviews.stickers.QuestionStickerView;
import awais.instagrabber.customviews.stickers.SliderStickerView;
import awais.instagrabber.customviews.stickers.StickerView;
import awais.instagrabber.repositories.responses.story.StoryMedia;
import awais.instagrabber.repositories.responses.story.StoryQuestion;
import awais.instagrabber.repositories.responses.story.StorySlider;
import awais.instagrabber.repositories.responses.story.StorySticker;

public class StickerFactory {
    @Nullable
    public static StickerView createSticker(@NonNull final Context context,
                                            @NonNull final StorySticker sticker,
                                            @NonNull final StoryMedia storyMedia,
                                            final int containerWidth,
                                            final int containerHeight) {
        switch (sticker.getType()) {
            case QUESTION:
                return createQuestionSticker(context, sticker, storyMedia, containerWidth, containerHeight);
            case POLL:
                break;
            case SLIDER:
                return createSliderSticker(context, sticker, storyMedia, containerWidth, containerHeight);
            case MENTION:
                break;
            case QUIZ:
                break;
            default:
                return null;
        }
        return null;
    }

    @NonNull
    private static StickerView createQuestionSticker(@NonNull final Context context,
                                                     @NonNull final StorySticker sticker,
                                                     @NonNull final StoryMedia storyMedia,
                                                     final int containerWidth,
                                                     final int containerHeight) {
        final QuestionStickerView view = new QuestionStickerView(context);
        view.setSticker((StoryQuestion) sticker, getBounds(storyMedia, sticker, containerWidth, containerHeight));
        return view;
    }

    private static StickerView createSliderSticker(@NonNull final Context context,
                                                   @NonNull final StorySticker sticker,
                                                   @NonNull final StoryMedia storyMedia,
                                                   final int containerWidth,
                                                   final int containerHeight) {
        final SliderStickerView view = new SliderStickerView(context);
        view.setSticker((StorySlider) sticker, getBounds(storyMedia, sticker, containerWidth, containerHeight));
        return view;
    }

    @NonNull
    private static StickerBounds getBounds(@NonNull final StoryMedia storyMedia,
                                           @NonNull final StorySticker sticker,
                                           final int containerWidth,
                                           final int containerHeight) {
        final int originalWidth = storyMedia.getOriginalWidth();
        final int originalHeight = storyMedia.getOriginalHeight();
        final NullSafePair<Integer, Integer> result = NumberUtils
                .calculateWidthHeight(originalHeight, originalWidth, containerHeight, containerWidth);
        final float horizontalGap = (containerWidth - result.first) / 2f;
        final float verticalGap = (containerHeight - result.second) / 2f;
        final float left = sticker.getX() * result.first;
        final float top = sticker.getY() * result.second;
        final float width = sticker.getWidth() * result.first;
        final float height = sticker.getHeight() * result.second;
        final StickerBounds bounds = new StickerBounds();
        bounds.left = left - width / 2 + horizontalGap;
        bounds.right = left + width / 2;
        bounds.top = top - height / 2 + verticalGap;
        bounds.bottom = top + height / 2 + verticalGap;
        bounds.width = width;
        bounds.height = height;
        return bounds;
    }

    public static class StickerBounds {
        public float left;
        float right;
        public float top;
        float bottom;
        public float width;
        public float height;
    }
}
