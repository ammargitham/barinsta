// package awais.instagrabber.customviews;
//
// import android.annotation.SuppressLint;
// import android.content.Context;
// import android.graphics.RectF;
// import android.util.AttributeSet;
// import android.view.GestureDetector;
// import android.view.MotionEvent;
//
// import androidx.annotation.NonNull;
// import androidx.annotation.Nullable;
// import androidx.constraintlayout.widget.ConstraintLayout;
// import androidx.core.view.GestureDetectorCompat;
//
// import java.util.List;
//
// import awais.instagrabber.fragments.StoryViewerFragment.StoryStickerRect;
//
// public class StoryViewerConstraintLayout extends ConstraintLayout {
//     private static final String TAG = StoryViewerConstraintLayout.class.getSimpleName();
//
//     private GestureDetectorCompat gestureDetector;
//     private List<StoryStickerRect> stickerRects;
//     private boolean shouldIntercept = true;
//     private OnStickerClickListener onStickerClickListener;
//
//     public StoryViewerConstraintLayout(@NonNull final Context context) {
//         super(context);
//         init();
//     }
//
//     public StoryViewerConstraintLayout(@NonNull final Context context, @Nullable final AttributeSet attrs) {
//         super(context, attrs);
//         init();
//     }
//
//     public StoryViewerConstraintLayout(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
//         super(context, attrs, defStyleAttr);
//         init();
//     }
//
//     public StoryViewerConstraintLayout(@NonNull final Context context,
//                                        @Nullable final AttributeSet attrs,
//                                        final int defStyleAttr,
//                                        final int defStyleRes) {
//         super(context, attrs, defStyleAttr, defStyleRes);
//         init();
//     }
//
//     private void init() {
//         final Context context = getContext();
//         if (context == null) return;
//         gestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
//             @Override
//             public boolean onDown(final MotionEvent e) {
//                 return true;
//             }
//
//             @Override
//             public boolean onSingleTapConfirmed(final MotionEvent event) {
//                 // Log.d(TAG, "onSingleTapConfirmed");
//                 final StoryStickerRect sticker = findTargetSticker(event.getX(), event.getY());
//                 if (onStickerClickListener != null && sticker != null) {
//                     onStickerClickListener.onStickerClick(sticker);
//                     return true;
//                 }
//                 return false;
//             }
//         });
//     }
//
//     @Override
//     public boolean onInterceptTouchEvent(final MotionEvent event) {
//         if (!shouldIntercept || stickerRects == null || stickerRects.isEmpty()) {
//             return super.onInterceptTouchEvent(event);
//         }
//         final int action = event.getActionMasked();
//         final float x = event.getX();
//         final float y = event.getY();
//         if (action == MotionEvent.ACTION_DOWN) {
//             return findTargetSticker(x, y) != null;
//         }
//         return super.onInterceptTouchEvent(event);
//     }
//
//     @SuppressLint("ClickableViewAccessibility")
//     @Override
//     public boolean onTouchEvent(final MotionEvent event) {
//         if (gestureDetector != null) {
//             return gestureDetector.onTouchEvent(event);
//         }
//         return super.onTouchEvent(event);
//     }
//
//     public void setStickers(final List<StoryStickerRect> stickerRects) {
//         this.stickerRects = stickerRects;
//     }
//
//     public void shouldInterceptTouch(final boolean shouldIntercept) {
//         this.shouldIntercept = shouldIntercept;
//     }
//
//     public void setOnStickerClickListener(final OnStickerClickListener onStickerClickListener) {
//         this.onStickerClickListener = onStickerClickListener;
//     }
//
//     public interface OnStickerClickListener {
//         void onStickerClick(@NonNull StoryStickerRect sticker);
//     }
//
//     @Nullable
//     private StoryStickerRect findTargetSticker(final float pointX,
//                                                final float pointY) {
//         for (final StoryStickerRect stickerRect : stickerRects) {
//             if (contains(stickerRect, pointX, pointY)) {
//                 return stickerRect;
//             }
//         }
//         return null;
//     }
//
//     private boolean contains(@NonNull final StoryStickerRect stickerRect,
//                              final float pointX,
//                              final float pointY) {
//         return contains(stickerRect, stickerRect.getSticker().getRotation() * 360, pointX, pointY);
//     }
//
//     private boolean contains(@NonNull final RectF rect,
//                              final float rotDeg,
//                              final float pointX,
//                              final float pointY) {
//         final double rotRadians = Math.toRadians(rotDeg);
//         // rotate around rectangle center by -rotRadians
//         final double s = Math.sin(-rotRadians);
//         final double c = Math.cos(-rotRadians);
//
//         // set origin to rect center
//         float newPointX = pointX - rect.centerX();
//         float newPointY = pointY - rect.centerY();
//
//         final float newPointXCopy = newPointX;
//         final float newPointYCopy = newPointY;
//         // rotate
//         newPointX = (float) (newPointXCopy * c - newPointYCopy * s);
//         newPointY = (float) (newPointXCopy * s + newPointYCopy * c);
//
//         // put origin back
//         newPointX = newPointX + rect.centerX();
//         newPointY = newPointY + rect.centerY();
//
//         // check if our transformed point is in the rectangle, which is no longer
//         // rotated relative to the point
//         return rect.contains(newPointX, newPointY);
//     }
// }
