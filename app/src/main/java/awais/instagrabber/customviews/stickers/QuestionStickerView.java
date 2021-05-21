package awais.instagrabber.customviews.stickers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.ColorUtils;

import awais.instagrabber.customviews.CircularImageView;
import awais.instagrabber.repositories.responses.story.StoryQuestion;
import awais.instagrabber.utils.StickerFactory;
import awais.instagrabber.utils.ViewUtils;

import static awais.instagrabber.utils.ColorUtils.darken;

public class QuestionStickerView extends FrameLayout implements StickerView {
    private static final float PROFILE_PIC_RATIO = 0.165f;

    private CircularImageView profilePic;
    private AppCompatTextView question;
    private AppCompatEditText input;
    private StoryQuestion sticker;
    private StickerFactory.StickerBounds bounds;
    private LinearLayout questionInputWrapper;
    // private OnInputFocusChangeListener onInputFocusChangeListener;
    private boolean inputEnabled;
    private OnQuestionStickerClickListener clickListener;

    public QuestionStickerView(@NonNull final Context context) {
        this(context, null);
    }

    public QuestionStickerView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuestionStickerView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public QuestionStickerView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(final Context context) {
        profilePic = new CircularImageView(context);
        questionInputWrapper = new LinearLayout(context);
        questionInputWrapper.setOrientation(LinearLayout.VERTICAL);
        question = new AppCompatTextView(context);
        initInput(context);
        questionInputWrapper.addView(question, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                             ViewGroup.LayoutParams.WRAP_CONTENT));
        questionInputWrapper.addView(input, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        addView(questionInputWrapper, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(profilePic);
        setInputEnabled(false);
        setOnClickListener(v -> {
            if (clickListener == null) return;
            clickListener.onQuestionStickerClick(this, sticker);
        });
    }

    private void initInput(final Context context) {
        input = new AppCompatEditText(context);
        // input.setOnFocusChangeListener((v, hasFocus) -> {
        //     // if (onInputFocusChangeListener == null) return;
        //     // onInputFocusChangeListener.onFocusChange(v, hasFocus);
        // });
        input.setOnClickListener(v -> {
            if (clickListener == null) return;
            clickListener.onQuestionStickerClick(this, sticker);
        });
    }

    public void setSticker(@NonNull final StoryQuestion sticker,
                           @NonNull final StickerFactory.StickerBounds bounds) {
        this.sticker = sticker;
        this.bounds = bounds;
        update();
    }

    public StoryQuestion getSticker() {
        return sticker;
    }

    public StickerFactory.StickerBounds getBounds() {
        return bounds;
    }

    private void update() {
        if (sticker == null) return;
        final StoryQuestion.QuestionSticker questionSticker = sticker.getQuestionSticker();
        updateProfilePic(questionSticker);
        updateQuestionInputWrapper(questionSticker);
        updateQuestion(questionSticker);
        updateInput(questionSticker);
        updateParent();
    }

    private void updateProfilePic(@NonNull final StoryQuestion.QuestionSticker questionSticker) {
        profilePic.setImageURI(questionSticker.getProfilePicUrl());
        profilePic.setBorder(Color.parseColor(questionSticker.getBackgroundColor()), 1);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) profilePic.getLayoutParams();
        final int width = (int) (bounds.width * PROFILE_PIC_RATIO);
        if (layoutParams == null) {
            //noinspection SuspiciousNameCombination
            layoutParams = new FrameLayout.LayoutParams(width, width);
            profilePic.setLayoutParams(layoutParams);
            return;
        }
        layoutParams.width = width;
        //noinspection SuspiciousNameCombination
        layoutParams.height = width;
        layoutParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        // profilePic.setForeground(new ColorDrawable(Color.argb(128, 0, 0, 0)));
    }

    private void updateQuestionInputWrapper(@NonNull final StoryQuestion.QuestionSticker questionSticker) {
        final double diag = Math.sqrt(bounds.height * bounds.height + bounds.width * bounds.width);
        final Drawable drawable = ViewUtils.createRoundRectDrawable((int) (diag * 0.04),
                                                                    Color.parseColor(questionSticker.getBackgroundColor()));
        questionInputWrapper.setBackground(drawable);
        final FrameLayout.LayoutParams wrapperLayoutParams = (FrameLayout.LayoutParams) questionInputWrapper.getLayoutParams();
        wrapperLayoutParams.setMargins(0, ((int) (bounds.width * PROFILE_PIC_RATIO)) / 2, 0, 0);
    }

    private void updateQuestion(@NonNull final StoryQuestion.QuestionSticker questionSticker) {
        final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) question.getLayoutParams();
        final int top = (int) (bounds.height * 0.1);
        layoutParams.topMargin = ((int) (bounds.width * PROFILE_PIC_RATIO)) / 2 + top;
        question.setText(questionSticker.getQuestion());
        question.setGravity(Gravity.CENTER);
        question.setTextSize(TypedValue.COMPLEX_UNIT_PX, bounds.width * 0.07f);
        question.setTextColor(Color.parseColor(questionSticker.getTextColor()));
    }

    private void updateInput(@NonNull final StoryQuestion.QuestionSticker questionSticker) {
        final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) input.getLayoutParams();
        final int margin = (int) (bounds.height * 0.1);
        layoutParams.setMargins(margin, margin, margin, margin);
        final String questionType = questionSticker.getQuestionType();
        final String hint;
        final double multiplier;
        switch (questionType) {
            case "text":
                hint = "Type something...";
                multiplier = 0.03;
                break;
            case "music":
                hint = "\uD83C\uDFB5 Choose a song";
                multiplier = 0.1;
                break;
            default:
                hint = "Unknown";
                multiplier = 0.03;
                break;
        }
        final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(
                darken(Color.parseColor(questionSticker.getBackgroundColor())),
                PorterDuff.Mode.SRC
        );
        final double diagonal = Math.sqrt(bounds.height * bounds.height + bounds.width * bounds.width);
        final Drawable drawable = ViewUtils.createRoundRectDrawable(
                (int) (diagonal * multiplier),
                Color.parseColor(questionSticker.getBackgroundColor()),
                colorFilter
        );
        input.setBackground(drawable);
        input.setHint(hint);
        final int textColor = Color.parseColor(questionSticker.getTextColor());
        input.setTextColor(textColor);
        input.setHintTextColor(ColorUtils.setAlphaComponent(textColor, 128));
        input.setGravity(Gravity.CENTER);
        input.setTextSize(TypedValue.COMPLEX_UNIT_PX, bounds.width * 0.05f);
    }

    private void updateParent() {
        ViewGroup.LayoutParams parentLayoutParams = getLayoutParams();
        if (parentLayoutParams == null) {
            parentLayoutParams = new ViewGroup.LayoutParams((int) bounds.width, (int) bounds.height);
            setLayoutParams(parentLayoutParams);
        } else {
            parentLayoutParams.width = (int) bounds.width;
            parentLayoutParams.height = (int) bounds.height;
        }
        resetPosition();
    }

    public void resetPosition() {
        setTranslationX(bounds.left);
        setTranslationY(bounds.top);
        setRotation(sticker.getRotation() * 360);
        // setAlpha(0.5f);
    }

    // public void setOnInputFocusChangeListener(final OnInputFocusChangeListener onInputFocusChangeListener) {
    //     this.onInputFocusChangeListener = onInputFocusChangeListener;
    // }

    public void setInputEnabled(final boolean inputEnabled) {
        this.inputEnabled = inputEnabled;
        input.setFocusable(inputEnabled);
        input.setCursorVisible(inputEnabled);
        input.setLongClickable(inputEnabled);
    }

    public void setOnQuestionStickerClickListener(final OnQuestionStickerClickListener clickListener) {
        this.clickListener = clickListener;
    }

    // public interface OnInputFocusChangeListener {
    //     void onFocusChange(final View v, final boolean hasFocus);
    // }

    public interface OnQuestionStickerClickListener {
        void onQuestionStickerClick(@NonNull QuestionStickerView view,
                                    @NonNull StoryQuestion storyQuestion);
    }
}
