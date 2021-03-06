package com.example.harsha.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by harsha on 09/02/17.
 */
public class CustomAdapter extends ArrayAdapter<ArticlesData>{

    ArrayList<ArticlesData> articelsDataSet;
    Context mContext;

    public CustomAdapter(ArrayList<ArticlesData> articlesData, Context applicationContext) {
        super(applicationContext, R.layout.articles_list, articlesData);
        this.articelsDataSet = articlesData;
        this.mContext=applicationContext;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ArticlesData articlesData = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (CommonUtils.isNull(convertView)) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.articles_list, parent, false);
            viewHolder.titleTV = (TextView) convertView.findViewById(R.id.title_tv);
            viewHolder.descriptionTV = (TextView) convertView.findViewById(R.id.description_tv);
            viewHolder.imageURL = (ImageView) convertView.findViewById(R.id.imageView);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        String titleText = "<a href=" + articlesData.getUrl() + ">" + articlesData.getTitle() + "</a>";
        if (Build.VERSION.SDK_INT > 24) {
            viewHolder.titleTV.setText("Title : " + Html.fromHtml(titleText, Html.FROM_HTML_MODE_LEGACY));
        } else {
            viewHolder.titleTV.setText("Title : " + Html.fromHtml(titleText));
        }
        viewHolder.titleTV.setAutoLinkMask(Linkify.WEB_URLS);
        Picasso.with(getContext()).load(articlesData.getUrlImage()).into(viewHolder.imageURL);
        viewHolder.descriptionTV.setText("Description : \n"+ articlesData.getDescription());
        return convertView;
    }

    private static class ViewHolder {
        TextView titleTV;
        TextView descriptionTV;
        TextView urlTV;
        ImageView imageURL;
    }
}
