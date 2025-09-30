package com.example.mpesademo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mpesademo.R;
import com.example.mpesademo.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> messages;
    private float textSize = 16f; // default text size in sp
    private ScaleGestureDetector scaleGestureDetector;

    public MessageAdapter(Context context, List<Message> messages, RecyclerView recyclerView) {
        this.messages = messages;

        // Gesture detector for pinch zoom
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());

        // Attach touch listener to recyclerView
        recyclerView.setOnTouchListener((v, event) -> {
            scaleGestureDetector.onTouchEvent(event);
            return false; // allow normal scroll too
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtMessage.setText(messages.get(position).getText());
        holder.txtMessage.setTextSize(textSize); // apply current zoom size
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMessage;
        public ViewHolder(View itemView) {
            super(itemView);
            txtMessage = itemView.findViewById(R.id.txt_message);
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            textSize *= detector.getScaleFactor();

            // Limit text size range
            textSize = Math.max(12f, Math.min(textSize, 32f));

            notifyDataSetChanged(); // refresh all items with new size
            return true;
        }
    }
}
