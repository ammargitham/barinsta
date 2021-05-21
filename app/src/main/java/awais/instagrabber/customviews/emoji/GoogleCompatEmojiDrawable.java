package awais.instagrabber.customviews.emoji;

/*
 * Copyright (C) 2016 - Niklas Baudy, Ruben Gees, Mario Đanić and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.text.TextPaint;

import androidx.annotation.NonNull;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.text.EmojiSpan;

/**
 * An emoji drawable backed by a span generated by the Google emoji support library.
 */
public final class GoogleCompatEmojiDrawable extends Drawable {
    private static final String TAG = GoogleCompatEmojiDrawable.class.getSimpleName();
    private static final float TEXT_SIZE_FACTOR = 0.8f;
    private static final float BASELINE_OFFSET_FACTOR = 0.225f;

    private EmojiSpan emojiSpan;
    private boolean processed;
    private CharSequence emojiCharSequence;
    private final int height;
    private final TextPaint textPaint = new TextPaint();

    public GoogleCompatEmojiDrawable(@NonNull final String unicode) {
        this(unicode, 0);
    }

    public GoogleCompatEmojiDrawable(@NonNull final String unicode,
                                     final int height) {
        emojiCharSequence = unicode;
        this.height = height;
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setColor(0x0ffffffff);
        textPaint.setAntiAlias(true);
        setBounds(0, 0, height, height);
    }

    private void process() {
        emojiCharSequence = EmojiCompat.get().process(emojiCharSequence);
        if (emojiCharSequence instanceof Spanned) {
            final Object[] spans = ((Spanned) emojiCharSequence).getSpans(0, emojiCharSequence.length(), EmojiSpan.class);
            if (spans.length > 0) {
                emojiSpan = (EmojiSpan) spans[0];
            }
        }
    }

    @Override
    public void draw(@NonNull final Canvas canvas) {
        final Rect bounds = getBounds();
        final int height = this.height > 0 ? this.height : bounds.height();
        textPaint.setTextSize(height * TEXT_SIZE_FACTOR);
        final int y = Math.round(bounds.bottom - height * BASELINE_OFFSET_FACTOR);

        if (!processed && EmojiCompat.get().getLoadState() != EmojiCompat.LOAD_STATE_LOADING) {
            processed = true;
            if (EmojiCompat.get().getLoadState() != EmojiCompat.LOAD_STATE_FAILED) {
                process();
            }
        }

        if (emojiSpan == null) {
            canvas.drawText(emojiCharSequence, 0, emojiCharSequence.length(), bounds.left, y, textPaint);
        } else {
            emojiSpan.draw(canvas, emojiCharSequence, 0, emojiCharSequence.length(), bounds.left, bounds.top, y, bounds.bottom, textPaint);
        }
    }

    @Override
    public void setAlpha(final int alpha) {
        textPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(final ColorFilter colorFilter) {
        textPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    @Override
    public int getIntrinsicHeight() {
        return height > 0 ? height : super.getIntrinsicHeight();
    }

    @Override
    public int getIntrinsicWidth() {
        return height > 0 ? height : super.getIntrinsicWidth();
    }
}

