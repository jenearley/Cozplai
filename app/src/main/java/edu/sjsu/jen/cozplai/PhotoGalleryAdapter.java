package edu.sjsu.jen.cozplai;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class PhotoGalleryAdapter extends RecyclerView.Adapter<PhotoGalleryAdapter.PhotoViewHolder> {

    private ArrayList<Uri> imageUris;
    private final Context context;

    public PhotoGalleryAdapter(Context inContext) {
        context = inContext;
        imageUris = new ArrayList<>();
        regenPhotos();
    }

    public void regenPhotos() {
        imageUris.clear();
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("captures", Context.MODE_PRIVATE);
        File[] photos = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.contains(".jpg");
            }
        });
        for (int i = 0; i < photos.length; i++) {
            imageUris.add(Uri.fromFile(photos[i]));
        }
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.photo_gallery_item_layout, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        holder.imageView.setImageURI(imageUris.get(position));
    }

    @Override
    public int getItemCount() {
        return imageUris.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.photo_gallery_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // TODO: Show the photo in a larger view?????
        }
    }
}
