package com.example.teamas.contextualactionbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerClickListener {

    private RecyclerView recyclerView;
    private ModelAdapter modelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        List<Model> modelList = new ArrayList<>();
        modelList.add(new Model("Schindler's List"));
        modelList.add(new Model("Pulp Fiction"));
        modelList.add(new Model("No Country for Old Men"));
        modelList.add(new Model("Léon: The Professional"));
        modelList.add(new Model("Fight Club"));
        modelList.add(new Model("Forrest Gump"));
        modelList.add(new Model("The Shawshank Redemption"));
        modelList.add(new Model("The Godfather"));
        modelList.add(new Model("A Beautiful Mind"));
        modelList.add(new Model("Good Will Hunting"));

        modelAdapter = new ModelAdapter(this, modelList, this);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(modelAdapter);
        recyclerView.setHasFixedSize(true);
    }


    @Override
    public void onClick(Model model, final List<Model> modelList, final List<Model> selectedItems) {
        if (model.isSelected()) {
            startActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater menuInflater = mode.getMenuInflater();
                    menuInflater.inflate(R.menu.recycler_menu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.delete:
                            deleteSelectedItems(modelList, selectedItems);
                            mode.finish();
                            return true;
                        default:
                            return false;
                    }
                }


                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });

        }
    }


    private void deleteSelectedItems(List<Model> modelList, List<Model> selectedItems) {
        for (int i = 0; i < selectedItems.size(); i++) {
            if (modelList.contains(selectedItems.get(i))) {
                modelList.remove(selectedItems.get(i));
            }
            modelAdapter.notifyDataSetChanged();
        }

    }

}
