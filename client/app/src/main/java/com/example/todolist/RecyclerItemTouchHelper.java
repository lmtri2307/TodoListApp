package com.example.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Adapter.ToDoAdapter;
import com.example.todolist.Utils.Converter;

import java.io.Console;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private final ToDoAdapter toDoAdapter;
    private final float MAX_SWIPE_DISTANCE_RATE = 0.4f;
    private final  float ACTIVE_SWIPED_CHANGE_DISTANCE_RATE =0.2f;

    private boolean isAdjustedByUser = false;
    public RecyclerItemTouchHelper(ToDoAdapter toDoAdapter) {
        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.toDoAdapter = toDoAdapter;
    }
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        float dx= viewHolder.itemView.getTranslationX();
        float activeSwipedChangeDistance = viewHolder.itemView.getWidth()* ACTIVE_SWIPED_CHANGE_DISTANCE_RATE;
        float newDx = dx > 0
                ? activeSwipedChangeDistance
                : -activeSwipedChangeDistance;
        viewHolder.itemView.animate()
                .translationX(newDx)
                .setDuration(200)
                .start();
        isAdjustedByUser = true;
        final int position = viewHolder.getAbsoluteAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(toDoAdapter.getContext());
            builder.setTitle("Delete Task");
            builder.setMessage("Are you sure to delete this task ?");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    toDoAdapter.removeItem(position);
                }
            });
            builder.setNegativeButton(android.R.string.cancel, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    toDoAdapter.notifyItemChanged(position);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            toDoAdapter.editItem(position);
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        isAdjustedByUser =false;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {

        View itemView = viewHolder.itemView;
        dX = adjustSwipeDistance(itemView, dX);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        Drawable icon;
        GradientDrawable background = new GradientDrawable();
        background.setCornerRadius(Converter.dpToPixel(toDoAdapter.getContext(), 8));



        int backgroundCornerOffset = 20;

        if (dX > 0) {
            icon = ContextCompat.getDrawable(toDoAdapter.getContext(), R.drawable.baseline_edit_24);
            background.setColor(ContextCompat.getColor(toDoAdapter.getContext(), R.color.teal_700));
        } else {
            icon = ContextCompat.getDrawable(toDoAdapter.getContext(), R.drawable.baseline_delete_24);
            background.setColor(Color.RED);
        }


        assert icon != null;
        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX > 0) { // Swiping to the right
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());
        } else if (dX < 0) { // Swiping to the left
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0);
        }

        background.draw(c);
        icon.draw(c);
    }

    private float adjustSwipeDistance(View view, float dx){
        float newDx = dx;
        if(dx == 0)
            return 0;
        if( isAdjustedByUser){
            float activeSwipedChangeDistance =
                    view.getWidth()* ACTIVE_SWIPED_CHANGE_DISTANCE_RATE;
            if (dx > 0)
                newDx = activeSwipedChangeDistance;
            else
                newDx = 0;
        } else {
            float maxSwipeDistance = view.getWidth()* MAX_SWIPE_DISTANCE_RATE;
            newDx = dx > 0 ? Math.min(dx,maxSwipeDistance) : Math.max(dx, -maxSwipeDistance);
        }
        return newDx;
    }

}
