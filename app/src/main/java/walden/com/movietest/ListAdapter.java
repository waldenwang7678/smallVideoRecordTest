package walden.com.movietest;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/28 0028.
 */

public class ListAdapter extends BaseAdapter {
    ArrayList<DataBean> mData;
    Context mContext;

    ListAdapter(Context context, ArrayList<DataBean> data) {
        mData = data;
        mContext = context;
        if (mData == null) {
            mData = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListHolder holder;
        if (convertView == null) {
            holder = new ListHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.file_img);
            holder.time = (TextView) convertView.findViewById(R.id.file_time);
            holder.size = (TextView) convertView.findViewById(R.id.file_size);
            holder.play = (TextView) convertView.findViewById(R.id.play);
            holder.del = (TextView) convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        } else {
            holder = (ListHolder) convertView.getTag();
        }
        final DataBean data = mData.get(position);

        holder.time.setText(Util.formatDate(data.getCreateTime()));
        holder.size.setText(Util.formatSize(data.getMp4Size()));
        String path = data.getImgPath();
        if (!TextUtils.isEmpty(path)) {
            holder.iv.setImageBitmap(BitmapFactory.decodeFile(path));
        }
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, VideoActivity.class);
                intent.putExtra("video", data.getMp4Path());
                intent.putExtra("image", data.getImgPath());
                mContext.startActivity(intent);
            }
        });
        Util.setDoit(new Util.DosomeThing() {
            @Override
            public void doit() {
                ((MainActivity) mContext).reflashData();
            }
        });
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除文件夹
                File file = new File(data.getMp4Path()).getParentFile();  //父目录
                Util.showDialog(mContext, file, ListAdapter.this);
            }
        });
        return convertView;
    }

    class ListHolder {
        private ImageView iv;
        private TextView time;
        private TextView size;
        private TextView play;
        private TextView del;

    }
}
