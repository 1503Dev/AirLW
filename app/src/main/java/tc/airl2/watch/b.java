package tc.airl2.watch;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

class b extends ViewGroup {
    int H;
    int V;
    int max;
    int n = 2;

    b(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft = getPaddingLeft();
        getPaddingTop();
        int i5 = 1;
        int i6 = paddingLeft;
        for (int i7 = 0; i7 < getChildCount(); i7++) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8) {
                int i8 = ((this.max - this.H) / this.n) - this.H;
                int measuredHeight = childAt.getMeasuredHeight();
                int i9 = (this.H + i8) + i6;
                i6 = (this.V + measuredHeight) * i5;
                if (i9 > this.max + paddingLeft) {
                    i9 = (this.H + paddingLeft) + i8;
                    i5++;
                    i6 = (this.V + measuredHeight) * i5;
                }
                childAt.measure(MeasureSpec.makeMeasureSpec(i8, 1073741824), MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
                childAt.layout(i9 - i8, i6 - measuredHeight, i9, i6);
                i6 = i9;
            }
        }
    }

    @Override
    protected void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        this.max = (size - getPaddingLeft()) - getPaddingRight();
        int i3 = 0;
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int i4 = 0;
        for (int i5 = 0; i5 < getChildCount(); i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                childAt.measure(0, 0);
                int measuredHeight = childAt.getMeasuredHeight();
                i4++;
                paddingTop = (i3 * measuredHeight) + measuredHeight;
                if (i4 > this.n) {
                    i4 = 1;
                    i3++;
                    paddingTop = (i3 * measuredHeight) + measuredHeight;
                }
            }
        }
        setMeasuredDimension(size, ((i3 + 2) * this.V) + paddingTop);
    }

    void set(int i, int i2, int i3) {
        this.n = i;
        this.H = i2;
        this.V = i3;
    }
}
