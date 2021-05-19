package awais.instagrabber.customviews.helpers;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int spacePx;
    private final int spacePxHalf;

    public VerticalSpaceItemDecoration(int spacePx) {
        this.spacePx = spacePx;
        spacePxHalf = spacePx / 2;
    }

    @Override
    public void getItemOffsets(@NonNull final Rect outRect,
                               @NonNull View view,
                               @NonNull final RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = spacePxHalf;
        }
        outRect.right = spacePx;
    }
}
