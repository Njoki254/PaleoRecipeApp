package com.moringaschool.paleorecipeapp.util;

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}

    //onItemMove() will be called each time the user moves an item by dragging it across the touch screen.
        //The argument fromPosition represents the location the item originally resided at. toPosition represents the location the user moved the item to.
//list of methods to ovewrriden
//onItemDismiss() is called when an item has been dismissed with a swipe motion.
// The parameter position represents the location of the dismissed item.
   // FirebaseRecyclerAdapter to tell our adapter what to do when an item is moved or dismissed via the touchscreen.