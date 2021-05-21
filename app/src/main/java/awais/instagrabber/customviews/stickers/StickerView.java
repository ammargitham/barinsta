package awais.instagrabber.customviews.stickers;

import awais.instagrabber.repositories.responses.story.StorySticker;
import awais.instagrabber.utils.StickerFactory;

/**
 * An interface all sticker view should implement
 */
public interface StickerView {
    StorySticker getSticker();

    StickerFactory.StickerBounds getBounds();
}
