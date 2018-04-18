package com.example.suelliton.mcpd_android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Quaresma on 18/09/2017.
 */

public class MeuRecyclerViewClickListener implements RecyclerView.OnItemTouchListener {

    OnItemClickListener myListener;
    GestureDetector myGestureDetector;

    public interface OnItemClickListener {
        void OnItemClick(View view, int i);
        void OnItemLongClick(View view, int i);

        void onItemClick(View view, int position);
    }

    public MeuRecyclerViewClickListener(Context c, final RecyclerView view, final OnItemClickListener myListener) {
        this.myListener = myListener;
        myGestureDetector = new GestureDetector(c, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                super.onSingleTapUp(motionEvent);
                View childView = view.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (childView != null && myListener !=null)
                    myListener.OnItemClick(childView, view.getChildAdapterPosition(childView));

                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
                super.onLongPress(motionEvent);
                View childView = view.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (childView != null && childView != null)
                    myListener.OnItemLongClick(childView, view.getChildAdapterPosition(childView));
            }

        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        myGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}