package com.example.uberapp_tim13.items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uberapp_tim13.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context context;
    private List<MessageItem> messages;

    public ChatAdapter(Context context, List<MessageItem> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        MessageItem message = (MessageItem) messages.get(position);

        if (message.getSender() != null) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_me_item, parent, false);
            return new SendHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_you_item, parent, false);
            return new RecieveHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageItem message = (MessageItem) messages.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SendHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((SendHolder) holder).bind(message);
        }
    }



    private class RecieveHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        private RecieveHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.textViewMessageYou);
            timeText = (TextView) itemView.findViewById(R.id.textViewTimestampYou);
        }

        void bind(MessageItem message) {
            messageText.setText(message.getMessage());
            //timeText.setText(Utils.formatDateTime(message.getTime()));
        }
    }


    private class SendHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        private SendHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.textViewMessageMe);
            timeText = (TextView) itemView.findViewById(R.id.textViewTimestampMe);
        }

        void bind(MessageItem message) {
            messageText.setText(message.getMessage());

            //timeText.setText(Utils.formatDateTime(message.getTime()));
        }
    }
}