package org.ucomplex.ucomplex.Modules.Messenger;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.ucomplex.ucomplex.Common.FacadeMedia;
import org.ucomplex.ucomplex.Common.base.BaseAdapter;
import org.ucomplex.ucomplex.Common.interfaces.IntentCallback;
import org.ucomplex.ucomplex.Common.interfaces.OnListItemClicked;
import org.ucomplex.ucomplex.Modules.Messenger.model.MessageFile;
import org.ucomplex.ucomplex.Modules.Messenger.model.MessageFileType;
import org.ucomplex.ucomplex.R;

import java.util.List;

import static org.ucomplex.ucomplex.Common.Constants.imageFormats;
import static org.ucomplex.ucomplex.Common.base.UCApplication.MESSAGE_FILES_URL;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 05/06/2017.
 * Project: UComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">www.ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public class MessengerMessageFilesAdapter extends BaseAdapter<MessengerMessageFilesAdapter.MessengerMessageFilesViewHolder, List<MessageFile>> {

    private final static int TYPE_FILE_IN = 0;
    private final static int TYPE_FILE_OUT = 1;
    private final static int TYPE_IMAGE_IN = 2;
    private final static int TYPE_IMAGE_OUT = 3;

    static class MessengerMessageFilesViewHolder extends RecyclerView.ViewHolder {

        TextView fileName;
        ImageView attachment;
        ProgressBar progressBar;

        public MessengerMessageFilesViewHolder(View itemView) {
            super(itemView);
            fileName = (TextView) itemView.findViewById(R.id.file_name);
            attachment = (ImageView) itemView.findViewById(R.id.attachment);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    private OnListItemClicked<String, String> onListItemClicked;
    private IntentCallback<String> intentCallback;
    private int myId;

    public MessengerMessageFilesAdapter(OnListItemClicked<String, String> onListItemClicked,
                                        IntentCallback<String> intentCallback,
                                        int myId) {
        this.onListItemClicked = onListItemClicked;
        this.intentCallback = intentCallback;
        this.myId = myId;
    }

    @Override
    public MessengerMessageFilesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        int layout = -1;
        if (viewType == TYPE_FILE_OUT) {
            layout = R.layout.item_message_file_out;
        } else if (viewType == TYPE_FILE_IN) {
            layout = R.layout.item_message_file_in;
        } else if (viewType == TYPE_IMAGE_OUT) {
            layout = R.layout.item_message_image_out;
        } else if (viewType == TYPE_IMAGE_IN) {
            layout = R.layout.item_message_image_in;
        }
        View view = inflater.inflate(layout, parent, false);
        return new MessengerMessageFilesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessengerMessageFilesViewHolder holder, int position) {
        MessageFile item = mItems.get(position);
        String address = item.getFrom() + "/" + item.getAddress();

        if (getItemViewType(position) == TYPE_FILE_IN || getItemViewType(position) == TYPE_FILE_OUT) {
            holder.fileName.setText(item.getName());
            holder.attachment.setImageResource(R.drawable.ic_file);
            holder.fileName.setOnClickListener(v -> onListItemClicked.onClick(address, item.getName()));
        } else {
            Context context = holder.attachment.getContext();
            downloadImage(holder.attachment, holder.progressBar, item, holder.attachment.getContext());
            holder.attachment.setOnClickListener(v -> {
                Bitmap bitmap = FacadeMedia.drawableToBitmap(holder.attachment.getDrawable());
                String bitmapUri = FacadeMedia.saveBitmapToStorage(
                        context.getContentResolver(),
                        bitmap,
                        item.getAddress(),
                        "");
                intentCallback.startIntent(bitmapUri);
            });
        }
    }

    private String extractExtension(String address) {
        String[] nameComponents = address.split("\\.");
        if (nameComponents.length > 1) {
            return nameComponents[1];
        }
        return "";
    }

    private void downloadImage(ImageView attachment, ProgressBar progressBar, MessageFile item, Context context) {
        progressBar.setVisibility(View.VISIBLE);
        String url = MESSAGE_FILES_URL + item.getFrom() + "/" + item.getAddress();
        Glide.with(context)
                .load(url)
                .asBitmap()
                .priority(Priority.HIGH).listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(attachment);
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems.size() == 0) {
            return TYPE_EMPTY;
        }
        String extension = extractExtension(mItems.get(position).getAddress());
        if (imageFormats.contains(extension)) {
            return mItems.get(position).getFrom() == myId ? TYPE_IMAGE_OUT : TYPE_IMAGE_IN;
        }
        return mItems.get(position).getFrom() == myId ? TYPE_FILE_OUT : TYPE_FILE_IN;
    }
}
