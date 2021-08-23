package com.novi.launcher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {

    private static final class AppInfo {
        CharSequence label, packageName;
        Drawable icon;
    }

    private final List<AppInfo> appsList;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            img = itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            v.getContext().startActivity(v.getContext().getPackageManager().getLaunchIntentForPackage(appsList.get(getAdapterPosition()).packageName.toString()));
            Toast.makeText(v.getContext(), appsList.get(getAdapterPosition()).label.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public AppListAdapter(Context c) {
        appsList = new ArrayList<>();
        PackageManager pm = c.getPackageManager();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> allApps = pm.queryIntentActivities(i, 0);
        for (ResolveInfo ri : allApps) {
            AppInfo app = new AppInfo();
            app.label = ri.loadLabel(pm);
            app.packageName = ri.activityInfo.packageName;
            app.icon = ri.activityInfo.loadIcon(pm);
            appsList.add(app);
        }
    }

    @Override
    public void onBindViewHolder(AppListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(appsList.get(i).label.toString());
        viewHolder.img.setImageDrawable(appsList.get(i).icon);
    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }


    @NonNull
    @Override
    public AppListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false)
        );
    }
}