package com.codeattitude.mainframeautorequest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codeattitude.mainframeautorequest.R;
import com.codeattitude.mainframeautorequest.model.User;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by xaviermaximin on 12/10/2015.
 */
public class ResquestAdapter extends ArrayAdapter {

        private Context context;
        private List<User> mListU;

        public ResquestAdapter(Context c, int textViewResourceId, List<User> i) {
            super(c, textViewResourceId, i);
            context = c;
            this.mListU = i;

        }

        @Override
        public int getCount() {
            return mListU.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_row_request, null);
        }

        TextView tv_Status = (TextView)v.findViewById(R.id.textViewStatus);
        TextView tv_Url = (TextView)v.findViewById(R.id.textViewUrl);
        TextView tv_Token = (TextView)v.findViewById(R.id.textViewToken);
        TextView tv_Response = (TextView)v.findViewById(R.id.textViewResponse);
        TextView tv_ReceivedDate = (TextView)v.findViewById(R.id.textViewReceivedDate);

        tv_Status.setText(mListU.get(position).getStatus());
        tv_Url.setText(mListU.get(position).getUrl());
        tv_Token.setText(mListU.get(position).getToken());
        tv_Response.setText(mListU.get(position).getResponse());
        tv_ReceivedDate.setText(mListU.get(position).getDateReceived());

        return v;
    }
}
