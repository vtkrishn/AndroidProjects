package pingpong.vtkrishn.com.pingpong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;


/**
 * Created by vtkrishn on 5/23/2017.
 */

public class AnimateView extends android.support.v7.widget.AppCompatImageView {
    private Context mContext;

    public AnimateView(Context context) {
        super(context);
        mContext = context;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BitmapDrawable bm = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.ball,null);
    }
}
