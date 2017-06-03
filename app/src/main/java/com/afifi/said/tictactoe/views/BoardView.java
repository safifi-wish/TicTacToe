package com.afifi.said.tictactoe.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;

import com.afifi.said.tictactoe.R;
import com.afifi.said.tictactoe.controller.GameEngine;
import com.afifi.said.tictactoe.model.Tile;
import com.afifi.said.tictactoe.utility.Constants;

/*
    This class is responsible for drawing the board and all the tiles
 */
public class BoardView extends View {
    private Paint separatorPaint;
    private Paint xPaint;
    private Paint oPaint;
    private GameEngine gameEngine;

    public BoardView(Context context) {
        super(context);
        init();
    }

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        separatorPaint = new Paint();
        xPaint = new Paint();
        oPaint = new Paint();

        int color = ResourcesCompat.getColor(getResources(), R.color.colorBlueLight2, null);
        float strokeWidth = getResources().getDimension(R.dimen.boardSeparatorWidth);
        separatorPaint.setColor(color);
        separatorPaint.setStrokeCap(Paint.Cap.ROUND);
        separatorPaint.setStrokeWidth(strokeWidth);

        strokeWidth = getResources().getDimension(R.dimen.tileStrokeWidth);
        color = ResourcesCompat.getColor(getResources(), R.color.colorGreen, null);
        xPaint.setColor(color);
        xPaint.setStrokeCap(Paint.Cap.ROUND);
        xPaint.setStrokeWidth(strokeWidth);
        xPaint.setAntiAlias(true);
        xPaint.setDither(true);
        xPaint.setFilterBitmap(true);

        color = ResourcesCompat.getColor(getResources(), R.color.colorRed, null);
        oPaint.setColor(color);
        oPaint.setStrokeCap(Paint.Cap.ROUND);
        oPaint.setStrokeWidth(strokeWidth);
        oPaint.setStyle(Paint.Style.STROKE);
        oPaint.setAntiAlias(true);
        oPaint.setDither(true);
        oPaint.setFilterBitmap(true);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int dimen = Math.min(width, height);
        setMeasuredDimension(dimen, dimen);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the background
        float gap = getMeasuredWidth() / Constants.BOARD_SIZE;
        float padding = getResources().getDimension(R.dimen.smallPadding);
        for (int i = 0; i < Constants.BOARD_SIZE - 1; ++i) {
            canvas.drawLine(
                    i * gap + gap, padding,
                    i * gap + gap, getMeasuredWidth() - padding,
                    separatorPaint);
        }

        for (int i = 0; i < Constants.BOARD_SIZE - 1; ++i) {
            canvas.drawLine(
                    padding, i * gap + gap,
                    getMeasuredWidth() - padding, i * gap + gap,
                    separatorPaint);
        }

        // Draw Current Tiles
        if (gameEngine == null) {
            return;
        }
        padding = getResources().getDimension(R.dimen.largePadding);
        for (int x = 0; x < Constants.BOARD_SIZE; ++x) {
            for (int y = 0; y < Constants.BOARD_SIZE; ++y) {
                if (gameEngine.getBoard()[x][y] == Tile.X) drawX(canvas, gap, padding, x, y);
                else if (gameEngine.getBoard()[x][y] == Tile.O) drawO(canvas, gap, padding, x, y);
            }
        }

    }

    private void drawO(Canvas canvas, float gap, float padding, int x, int y) {
        float gapHalf = gap / 2;
        canvas.drawCircle(gap * x + gapHalf, gap * y + gapHalf, gapHalf - padding, oPaint);
    }

    private void drawX(Canvas canvas, float gap, float padding, int x, int y) {
        canvas.drawLine(
                x * gap + padding, y * gap + padding,
                (x + 1) * gap - padding, (y + 1) * gap - padding,
                xPaint);
        canvas.drawLine(
                (x + 1) * gap - padding, y * gap + padding,
                x * gap + padding, (y + 1) * gap - padding,
                xPaint);
    }

    public void setEngineReference(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }
}
