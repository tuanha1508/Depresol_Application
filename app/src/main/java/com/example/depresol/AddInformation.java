package com.example.depresol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
public class AddInformation extends ArrayAdapter<Information> {
    public AddInformation(Context context, ArrayList<Information> userArrayList){

        //super(context,R.layout.item_play_video,userArrayList);
        super(context,R.layout.item_play_video,userArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Information user = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_play_video,parent,false);

        }

        ImageView imageView = convertView.findViewById(R.id.img_play_video);
        TextView descrip = convertView.findViewById(R.id.description);
        TextView title = convertView.findViewById(R.id.title_information);

        imageView.setImageResource(user.img);
        title.setText(user.title);
        descrip.setText(user.description);


        return convertView;
    }
}
