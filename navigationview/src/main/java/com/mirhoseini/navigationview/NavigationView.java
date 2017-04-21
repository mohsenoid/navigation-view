package com.mirhoseini.navigationview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

//import com.mirhoseini.navigationview.R;

public class NavigationView extends View implements SurfaceHolder.Callback {
    static final int sweepAngle = 90;

    static final int[] xFactor = {0, 1, 0, -1};
    static final int[] yFactor = {-1, 0, 1, 0};

    static final int[] hFactorX1 = {-1, -1, -1, 1};
    static final int[] hFactorX2 = {1, -1, 1, 1};
    static final int[] hFactorY1 = {1, -1, -1, -1};
    static final int[] hFactorY2 = {1, 1, -1, 1};

    int arcsPadding = 20;
    int backCirclePadding = 40;
    int strokeWidth = 5;
    boolean backCircleVisible = true;
    boolean[] buttonsEnabled = {true, true, true, true};
    boolean[] buttonsSelected = {false, false, false, false};
    int centerX;
    int centerY;
    int radiusInner, radiusOuter;
    private OnNavigationListener navigationListener = null;
    private int fillColor = Color.CYAN;
    private int fillDisabledColor = Color.GRAY;

    public NavigationView(Context context) {
        super(context);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavigationView);

        try {
            buttonsEnabled[0] = a.getBoolean(R.styleable.NavigationView_downButton, true);
            buttonsEnabled[1] = a.getBoolean(R.styleable.NavigationView_leftButton, true);
            buttonsEnabled[2] = a.getBoolean(R.styleable.NavigationView_upButton, true);
            buttonsEnabled[3] = a.getBoolean(R.styleable.NavigationView_rightButton, true);
            arcsPadding = a.getInt(R.styleable.NavigationView_arcsPadding, 20);
            backCirclePadding = a.getInt(R.styleable.NavigationView_backCirclePadding, 40);
            strokeWidth = a.getInt(R.styleable.NavigationView_strokeWidth, 5);
            backCircleVisible = a.getBoolean(R.styleable.NavigationView_backCircleVisible, true);
            fillColor = a.getColor(R.styleable.NavigationView_selectedColor, Color.CYAN);
        } finally {
            a.recycle();
        }
    }

    public NavigationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context, attrs);
    }

    public void setOnNavigationListener(OnNavigationListener l) {
        this.navigationListener = l;
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    public int getFillDisabledColor() {
        return fillDisabledColor;
    }

    public void setFillDisabledColor(int fillDisabledColor) {
        this.fillDisabledColor = fillDisabledColor;
    }

    public void setButtonsEnabled(boolean down, boolean left, boolean up,
                                  boolean right) {
        buttonsEnabled[0] = down;
        buttonsEnabled[1] = left;
        buttonsEnabled[2] = up;
        buttonsEnabled[3] = right;

        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {

        centerX = (int) (getWidth() / 2);
        centerY = (int) (getHeight() / 2);

        Paint paintFillWhite = new Paint();
        paintFillWhite.setColor(Color.WHITE);
        paintFillWhite.setStyle(Paint.Style.FILL);
        paintFillWhite.setAntiAlias(true);

        Paint paintFillGray = new Paint();
        paintFillGray.setColor(Color.rgb(176, 172, 157));
        paintFillGray.setStyle(Paint.Style.FILL);
        paintFillGray.setAntiAlias(true);

        Paint paintStroke = new Paint();
        paintStroke.setColor(Color.rgb(176, 172, 157));// #b0ac9d
        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setStrokeJoin(Paint.Join.MITER);
        paintStroke.setStrokeWidth(strokeWidth);
        paintStroke.setAntiAlias(true);

        Paint paintFillDisabled = new Paint();
        paintFillDisabled.setColor(fillDisabledColor);
        paintFillDisabled.setStyle(Paint.Style.FILL);
        paintFillDisabled.setAntiAlias(true);

        Paint paintFillSelected = new Paint();
        paintFillSelected.setColor(fillColor);
        paintFillSelected.setStyle(Paint.Style.FILL);
        paintFillSelected.setAntiAlias(true);

        // Draw Inner Circle

        if (backCircleVisible) {
            radiusInner = centerX < centerY ? centerX - backCirclePadding : centerY
                    - backCirclePadding;
            canvas.drawCircle(centerX, centerY, radiusInner, paintFillWhite);
            canvas.drawCircle(centerX, centerY, radiusInner, paintStroke);
        }

        // Draw Outer Circle Arcs

        radiusOuter = centerX < centerY ? centerX - strokeWidth - arcsPadding
                : centerY - strokeWidth - arcsPadding;

        RectF oval = new RectF();

        for (int i = 0; i < 4; i++) {
            oval.set(centerX - radiusOuter - arcsPadding * xFactor[i], centerY
                    - radiusOuter - arcsPadding * yFactor[i], centerX + radiusOuter
                    - arcsPadding * xFactor[i], centerY + radiusOuter - arcsPadding
                    * yFactor[i]);

            int startAngle = i * 90 + 45;

            if (buttonsEnabled[i]) {
                if (buttonsSelected[i]) {
                    canvas.drawArc(oval, startAngle, sweepAngle, true,
                            paintFillSelected);
                } else {
                    canvas.drawArc(oval, startAngle, sweepAngle, true,
                            paintFillWhite);
                }
            } else {
                canvas.drawArc(oval, startAngle, sweepAngle, true,
                        paintFillDisabled);
            }
            canvas.drawArc(oval, startAngle, sweepAngle, true, paintStroke);
        }

        // Draw Triangles

        int a = radiusOuter;
        int h = radiusOuter / 4;

        for (int i = 0; i < 4; i++) {
            int topX = centerX + a * xFactor[i];
            int topY = centerY + a * yFactor[i];

            Point point1_draw = new Point(topX, topY);
            Point point2_draw = new Point(topX + h * hFactorX1[i], topY + h
                    * hFactorY1[i]);
            Point point3_draw = new Point(topX + h * hFactorX2[i], topY + h
                    * hFactorY2[i]);

            Path path = new Path();
            path.setFillType(Path.FillType.EVEN_ODD);
            path.moveTo(point1_draw.x, point1_draw.y);
            path.lineTo(point2_draw.x, point2_draw.y);
            path.lineTo(point3_draw.x, point3_draw.y);
            path.lineTo(point1_draw.x, point1_draw.y);
            path.close();

            // if (buttonsEnabled[i])
            canvas.drawPath(path, paintFillGray);
            // else
            // canvas.drawPath(path, paintFillWhite);
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setWillNotDraw(false); // Allows us to use invalidate() to call
        // onDraw()
        postInvalidate();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    private void touch_start(float x, float y) {
        // mPath.reset();
        // mPath.moveTo(x, y);
        // mX = x;
        // mY = y;
    }

    private void touch_move(float x, float y) {
        // float dx = Math.abs(x - mX);
        // float dy = Math.abs(y - mY);
        // if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
        // mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
        // mX = x;
        // mY = y;
        //
        // circlePath.reset();
        // circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
        // }
    }

    private void touch_up() {
        // mPath.lineTo(mX, mY);
        // circlePath.reset();
        // // commit the path to our offscreen
        // mCanvas.drawPath(mPath, mPaint);
        // // kill this so we don't double draw
        // mPath.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = centerX - event.getX();
        float y = centerY - event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (Math.pow(x, 2) + Math.pow(y, 2) < Math.pow(radiusOuter, 2)) {
                    makeAllFalse();
                    if (Math.abs(x) < y)
                        buttonsSelected[2] = true;
                    else if (Math.abs(y) < x)
                        buttonsSelected[1] = true;
                    else if (Math.abs(y) < Math.abs(x))
                        buttonsSelected[3] = true;
                    else if (Math.abs(x) < Math.abs(y))
                        buttonsSelected[0] = true;
                    invalidate();
                    return true;
                } else {
                    makeAllFalse();
                }
                invalidate();
                return super.onTouchEvent(event);

            case MotionEvent.ACTION_UP:
                if (Math.pow(x, 2) + Math.pow(y, 2) < Math.pow(radiusOuter, 2)) {
                    if (navigationListener != null) {
                        if (buttonsSelected[0] && buttonsEnabled[0])
                            navigationListener.onDownClick(this);
                        else if (buttonsSelected[1] && buttonsEnabled[1])
                            navigationListener.onLeftClick(this);
                        else if (buttonsSelected[2] && buttonsEnabled[2])
                            navigationListener.onUpClick(this);
                        else if (buttonsSelected[3] && buttonsEnabled[3])
                            navigationListener.onRightClick(this);

                        makeAllFalse();
                        invalidate();
                        return true;
                    }
                } else {
                    makeAllFalse();
                    invalidate();
                    return super.onTouchEvent(event);
                }

        }
        return super.onTouchEvent(event);

    }

    private void makeAllFalse() {
        for (int i = 0; i < 4; i++)
            buttonsSelected[i] = false;
    }

    public interface OnNavigationListener {

        public void onDownClick(View v);

        public void onLeftClick(View v);

        public void onUpClick(View v);

        public void onRightClick(View v);

    }
}
