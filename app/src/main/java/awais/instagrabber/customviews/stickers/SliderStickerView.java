package awais.instagrabber.customviews.stickers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.res.ResourcesCompat;

import awais.instagrabber.R;
import awais.instagrabber.customviews.emoji.GoogleCompatEmojiDrawable;
import awais.instagrabber.repositories.responses.story.StorySlider;
import awais.instagrabber.repositories.responses.story.StorySlider.SliderSticker;
import awais.instagrabber.utils.StickerFactory;
import awais.instagrabber.utils.TextUtils;
import awais.instagrabber.utils.Utils;
import awais.instagrabber.utils.ViewUtils;

public class SliderStickerView extends LinearLayoutCompat implements StickerView {
    private static final String TAG = SliderStickerView.class.getSimpleName();

    private StorySlider sticker;
    private StickerFactory.StickerBounds bounds;
    private AppCompatTextView question;
    private SeekBar seekBar;

    public SliderStickerView(@NonNull final Context context) {
        this(context, null);
    }

    public SliderStickerView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SliderStickerView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(@NonNull final Context context) {
        question = new AppCompatTextView(context);
        seekBar = new SeekBar(context);
        final int paddingVertical = Utils.convertDpToPx(10);
        final int paddingHorizontal = Utils.convertDpToPx(22);
        final LayoutParams questionLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        question.setPadding(paddingHorizontal, paddingVertical * 2, paddingHorizontal, 0);
        addView(question, questionLayoutParams);
        final LayoutParams seekBarLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        seekBar.setPadding(paddingHorizontal, 0, paddingHorizontal, 0);
        addView(seekBar, seekBarLayoutParams);
    }

    public void setSticker(@NonNull final StorySlider sticker,
                           @NonNull final StickerFactory.StickerBounds bounds) {
        this.sticker = sticker;
        this.bounds = bounds;
        update();
    }

    public StorySlider getSticker() {
        return sticker;
    }

    public StickerFactory.StickerBounds getBounds() {
        return bounds;
    }

    private void update() {
        if (sticker == null || sticker.getSliderSticker() == null) return;
        updateQuestion(sticker.getSliderSticker());
        updateSlider(sticker.getSliderSticker());
        updateParent(sticker.getSliderSticker());
    }

    private void updateQuestion(@NonNull final SliderSticker sliderSticker) {
        if (TextUtils.isEmpty(sliderSticker.getQuestion())) {
            question.setVisibility(GONE);
            return;
        } else {
            question.setVisibility(VISIBLE);
        }
        question.setText(sliderSticker.getQuestion());
        question.setGravity(Gravity.CENTER);
        question.setTextSize(TypedValue.COMPLEX_UNIT_PX, bounds.width * 0.065f);
        question.setTextColor(Color.parseColor(sliderSticker.getTextColor()));
    }

    private void updateSlider(@NonNull final SliderSticker sliderSticker) {
        final int size = Math.min((int) (bounds.height * 0.4), Utils.convertDpToPx(36));
        final GoogleCompatEmojiDrawable thumb = new GoogleCompatEmojiDrawable(sliderSticker.getEmoji(), size);
        seekBar.setThumb(thumb);
        seekBar.setSplitTrack(false);
        final Context context = getContext();
        if (context == null) return;
        final LayerDrawable drawable = (LayerDrawable) ResourcesCompat.getDrawable(getResources(),
                                                                                   R.drawable.story_slider_default_bg,
                                                                                   context.getTheme());
        if (drawable != null) {
            final Drawable backgroundDrawable = drawable.findDrawableByLayerId(R.id.background);
            final float scaleFactor = 0.08f;
            final int finalTrackHeight = Math.min((int) (bounds.height * scaleFactor), Utils.convertDpToPx(10));
            if (backgroundDrawable instanceof GradientDrawable) {
                backgroundDrawable.mutate();
                ((GradientDrawable) backgroundDrawable).setSize(backgroundDrawable.getIntrinsicWidth(), finalTrackHeight);
            }
            final Drawable progressDrawable = drawable.findDrawableByLayerId(R.id.progress);
            if (progressDrawable instanceof ScaleDrawable) {
                progressDrawable.mutate();
                final Drawable drawable1 = ((ScaleDrawable) progressDrawable).getDrawable();
                if (drawable1 instanceof GradientDrawable) {
                    ((GradientDrawable) drawable1).setSize(backgroundDrawable.getIntrinsicWidth(), finalTrackHeight);
                }
            }
        }
        seekBar.setProgressDrawable(drawable);
        seekBar.setMax(100);
        seekBar.setIndeterminate(false);
        // seekBar.setProgress(20);
    }

    private void updateParent(@NonNull final SliderSticker sliderSticker) {
        ViewGroup.LayoutParams parentLayoutParams = getLayoutParams();
        if (parentLayoutParams == null) {
            parentLayoutParams = new ViewGroup.LayoutParams((int) bounds.width, (int) bounds.height);
            setLayoutParams(parentLayoutParams);
        } else {
            parentLayoutParams.width = (int) bounds.width;
            parentLayoutParams.height = (int) bounds.height;
        }
        setOrientation(VERTICAL);
        final double diagonal = Math.sqrt(bounds.height * bounds.height + bounds.width * bounds.width);
        final Drawable drawable = ViewUtils.createRoundRectDrawable((int) (diagonal * 0.04),
                                                                    Color.parseColor(sliderSticker.getBackgroundColor()));
        setBackground(drawable);
        resetPosition();
    }

    public void resetPosition() {
        setTranslationX(bounds.left);
        setTranslationY(bounds.top);
        setRotation(sticker.getRotation() * 360);
        // setAlpha(0.5f);
    }
}
