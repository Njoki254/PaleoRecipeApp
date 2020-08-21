package com.moringaschool.paleorecipeapp.util;

import androidx.recyclerview.widget.RecyclerView;

public interface OnStartDragListener {
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}

//onStartDrag() will be called when the user begins a 'drag-and-drop' interaction with the touchscreen.
// viewHolder represents the RecyclerView holder corresponding to the object being dragged.
