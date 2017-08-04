package com.example.mypc.stores.ui.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.mypc.stores.MyApplication;
import com.example.mypc.stores.R;
import com.example.mypc.stores.data.model.Post;
import com.example.mypc.stores.di.module.ViewModule;
import com.example.mypc.stores.ui.Adapter.PostAdapter;
import com.example.mypc.stores.ui.home.Fragment.cmt.CmtFragment;
import com.example.mypc.stores.ui.base.BaseActivity;
import com.example.mypc.stores.ui.home.Fragment.newpost.NewPostFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomeView, NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fbt_new_post)
    FloatingActionButton fbtNewPost;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.rcv_post)
    RecyclerView rcvPost;
    @BindView(R.id.layout_cmt)
    FrameLayout layoutFragment;
    private PostAdapter postAdapter;
    boolean isOpenFragment=false;

    @Inject
    HomePresenter mainPresenter;

    @Override
    protected void injectDependence() {
        MyApplication.get().getAppComponent().plus(new ViewModule(this)).InjectTo(this);
    }

    @Override
    protected int getContentLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mainPresenter.getPost();
    }

    @Override
    protected void initView() {
        layoutFragment.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        rcvPost.setLayoutManager(new GridLayoutManager(this, 1));
        rcvPost.setHasFixedSize(true);

    }

    @Override
    public void onBackPressed() {
        if(isOpenFragment){
            layoutFragment.setVisibility(View.GONE);
        } else if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_topStore) {
            // Handle the camera action
        } else if (id == R.id.nav_love) {

        } else if (id == R.id.nav_note) {

        } else if (id == R.id.nav_account) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_email) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_share) {

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.fbt_new_post)
    public void onViewClicked() {
        setView();
        setNewFragment(new NewPostFragment());

    }
    public void onLoadCmtFragment(){
        setView();
        setNewFragment(new CmtFragment());

    }

    private void setView() {
        layoutFragment.setVisibility(View.VISIBLE);
        rcvPost.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        fbtNewPost.setVisibility(View.GONE);
    }



    private void setNewFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.add(R.id.layout_cmt, fragment);
//        transaction.addToBackStack(tagFragment);
        transaction.commit();
    }

    @Override
    public void onUpdatePostsSuccess(ArrayList<Post> posts) {

        postAdapter = new PostAdapter(posts);
        rcvPost.setAdapter(postAdapter);
    }

    @Override
    public void onFail(String msg) {
        onShowBuider(msg);
    }



}
