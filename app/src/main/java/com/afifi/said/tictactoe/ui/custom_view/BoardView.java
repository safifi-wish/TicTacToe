package com.afifi.said.tictactoe.ui.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;

import com.afifi.said.tictactoe.R;
import com.afifi.said.tictactoe.controller.GameEngine;
import com.afifi.said.tictactoe.model.Result;
import com.afifi.said.tictactoe.model.Tile;
import com.afifi.said.tictactoe.utility.Constants;

/*
    This class is responsible for drawing the board and all the tiles
 */
public class BoardView extends View {
    private Paint separatorPaint;
    private Paint winnerPaint;
    private Paint tilePaint;
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
        winnerPaint = new Paint();
        tilePaint = new Paint();

        int color = ResourcesCompat.getColor(getResources(), R.color.colorBlueLight2, null);
        float strokeWidth = getResources().getDimension(R.dimen.boardSeparatorWidth);
        separatorPaint.setColor(color);
        separatorPaint.setStrokeCap(Paint.Cap.ROUND);
        separatorPaint.setStrokeWidth(strokeWidth);

        color = ResourcesCompat.getColor(getResources(), R.color.colorSkyBlue2, null);
        strokeWidth = getResources().getDimension(R.dimen.boardSeparatorWidth);
        winnerPaint.setColor(color);
        winnerPaint.setStrokeCap(Paint.Cap.ROUND);
        winnerPaint.setStrokeWidth(strokeWidth);

        strokeWidth = getResources().getDimension(R.dimen.tileStrokeWidth);
        tilePaint.setStrokeCap(Paint.Cap.ROUND);
        tilePaint.setStrokeWidth(strokeWidth);
        tilePaint.setStyle(Paint.Style.STROKE);
        tilePaint.setAntiAlias(true);
        tilePaint.setDither(true);
        tilePaint.setFilterBitmap(true);

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

        // Game engine needs to be initialized
        if (gameEngine == null) {
            return;
        }

        // Draw Current Tiles
        padding = getResources().getDimension(R.dimen.largePadding);
        for (int x = 0; x < Constants.BOARD_SIZE; ++x) {
            for (int y = 0; y < Constants.BOARD_SIZE; ++y) {
                if (gameEngine.getBoard()[x][y] == Tile.X) drawX(canvas, gap, padding, x, y);
                else if (gameEngine.getBoard()[x][y] == Tile.O) drawO(canvas, gap, padding, x, y);
            }
        }

        //Draw winning line
        Result currentResult = gameEngine.getCurrentResult();
        if( currentResult.getWinningCoordinates() != null ){
            Point point1 = currentResult.getWinningCoordinates().first;
            Point point2 = currentResult.getWinningCoordinates().second;
            float gapMid = (gap / 2);
            canvas.drawLine(
                    point1.x * gap + gapMid, point1.y * gap + gapMid,
                    point2.x * gap + gapMid, point2.y * gap + gapMid,
                    winnerPaint);
        }
    }

    private void drawO(Canvas canvas, float gap, float padding, int x, int y) {
        float gapMid = gap / 2;
        int color = ResourcesCompat.getColor(getResources(), Tile.O.getColorId(), null);
        tilePaint.setColor(color);
        canvas.drawCircle(gap * x + gapMid, gap * y + gapMid, gapMid - padding, tilePaint);
    }

    private void drawX(Canvas canvas, float gap, float padding, int x, int y) {
        int color = ResourcesCompat.getColor(getResources(), Tile.X.getColorId(), null);
        tilePaint.setColor(color);
        canvas.drawLine(
                x * gap + padding, y * gap + padding,
                (x + 1) * gap - padding, (y + 1) * gap - padding,
                tilePaint);
        canvas.drawLine(
                (x + 1) * gap - padding, y * gap + padding,
                x * gap + padding, (y + 1) * gap - padding,
                tilePaint);
    }

    public void setEngineReference(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void resetGame() {
        this.invalidate();
        this.setEnabled(true);
    }
}
