// package awais.instagrabber.customviews;
//
// import android.content.Context;
// import android.graphics.Canvas;
// import android.graphics.Color;
// import android.graphics.Paint;
// import android.util.AttributeSet;
// import android.view.View;
//
// import androidx.annotation.NonNull;
// import androidx.annotation.Nullable;
//
// import java.util.List;
//
// import awais.instagrabber.fragments.StoryViewerFragment.StoryStickerRect;
//
// public class DrawView extends View {
//     private final Paint paint = new Paint();
//
//     private List<StoryStickerRect> rects;
//
//     public DrawView(final Context context) {
//         super(context);
//     }
//
//     public DrawView(final Context context, @Nullable final AttributeSet attrs) {
//         super(context, attrs);
//     }
//
//     public DrawView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
//         super(context, attrs, defStyleAttr);
//     }
//
//     public DrawView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
//         super(context, attrs, defStyleAttr, defStyleRes);
//     }
//
//     public void setRects(@NonNull final List<StoryStickerRect> rects) {
//         this.rects = rects;
//         invalidate();
//     }
//
//     @Override
//     public void onDraw(@NonNull final Canvas canvas) {
//         if (rects == null || rects.isEmpty()) return;
//         paint.setStyle(Paint.Style.STROKE);
//         paint.setColor(Color.YELLOW);
//         paint.setStrokeWidth(10);
//         for (final StoryStickerRect rect : rects) {
//             canvas.rotate(rect.getSticker().getRotation() * 360, rect.centerX(), rect.centerY());
//             canvas.drawRect(rect, paint);
//         }
//     }
// }
