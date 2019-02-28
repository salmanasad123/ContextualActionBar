package com.example.teamas.contextualactionbar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ModelViewHolder> {

    private MainActivity mainActivity;
    private RecyclerClickListener listener;
    private List<Model> modelList;
    private List<Model> selectedItems = new ArrayList<>();


    public ModelAdapter(MainActivity mainActivity, List<Model> modelList, RecyclerClickListener listener) {
        this.mainActivity = mainActivity;
        this.modelList = modelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ModelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_single_row, viewGroup, false);
        return new ModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ModelViewHolder modelViewHolder, final int position) {
        final Model model = modelList.get(position);
        modelViewHolder.title.setText(model.getTitle());
        if (model.isSelected()) {
            modelViewHolder.constraintLayout.setBackgroundColor(mainActivity.getResources().getColor(R.color.colorAccent));
        } else {
            modelViewHolder.constraintLayout.setBackgroundColor(mainActivity.getResources().getColor(android.R.color.white));
        }

        modelViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setSelected(!model.isSelected());
                if (model.isSelected()) {
                    modelViewHolder.constraintLayout.setBackgroundColor(mainActivity.getResources().getColor(R.color.colorAccent));
                } else {
                    modelViewHolder.constraintLayout.setBackgroundColor(mainActivity.getResources().getColor(android.R.color.white));
                }
                if (model.isSelected()) {
                    selectedItems.add(model);
                } else {
                    selectedItems.remove(model);
                }
                listener.onClick(model, modelList, selectedItems);




                /*if (model.isSelected()) {
                    mainActivity.startActionMode(new android.view.ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                            MenuInflater menuInflater = mode.getMenuInflater();
                            menuInflater.inflate(R.menu.recycler_menu, menu);
                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                            return false;
                        }

                        @Override
                        public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete:
                                    deleteSelectedItems();
                                    mode.finish();
                                    return true;
                                default:
                                    return false;
                            }
                        }

                        @Override
                        public void onDestroyActionMode(android.view.ActionMode mode) {
                            mode = null;
                        }
                    });
                    selectedItems.add(model);
                } else {
                    selectedItems.remove(model);
                }*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    private void deleteSelectedItems() {
        for (int i = 0; i < selectedItems.size(); i++) {
            if (modelList.contains(selectedItems.get(i))) {
                modelList.remove(selectedItems.get(i));
            }
            notifyDataSetChanged();
        }

    }


    public class ModelViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ConstraintLayout constraintLayout;

        public ModelViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.items_container);
            title = itemView.findViewById(R.id.item_title);

        }
    }
}
