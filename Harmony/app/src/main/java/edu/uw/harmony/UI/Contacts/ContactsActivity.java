package edu.uw.harmony.UI.Contacts;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import edu.uw.harmony.MainActivity;
import edu.uw.harmony.R;
import edu.uw.harmony.UI.Chat.page.ChatsFragment;
import edu.uw.harmony.UI.Home.HomeFragment;
import edu.uw.harmony.UI.Weather.WeatherFragment;

/**
 * This class represents the Activity that holds the different fragments for contacts
 *
 * @author  Hermie Baylon
 * @version 1.0
 */
public class ContactsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ContactListFragment contactList;
    private ContactsPendingRequestList requestList;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        toolbar = findViewById(R.id.contacts_tool_bar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        contactList = new ContactListFragment();
        requestList = new ContactsPendingRequestList();

        tabLayout.setupWithViewPager(viewPager);

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), 0);
        viewPageAdapter.addFragment(contactList, "Contact List");
        viewPageAdapter.addFragment(requestList, "Pending Requests");
        viewPager.setAdapter(viewPageAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_contact_list_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_contact_requests_24dp);

        bottomNav = findViewById(R.id.bottom_nav_view);
        bottomNav.setOnNavigationItemSelectedListener(bottomNavListener);

        //tabLayout.setVisibility(View.GONE);
    }

    /**
     * Block of code that determines what happens when the bottom navigation is clicked
     */
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch(item.getItemId()) {
                        case R.id.navigation_home:
                            Log.d("myTag", "Home");
                            FragmentManager fm =getSupportFragmentManager();
                            fm.popBackStackImmediate("fragB", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            finish();
                            break;
                        case R.id.navigation_contacts_activity:
                            Log.d("myTag", "Contacts");
                            break;
                        case R.id.navigation_chat_list:
                            Log.d("myTag", "Chats");
                            FragmentManager fm3 =getSupportFragmentManager();
                            fm3.popBackStackImmediate("fragB", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            finish();
                            break;
                        case R.id.navigation_weather:
                            FragmentManager fm4 =getSupportFragmentManager();
                            fm4.popBackStackImmediate("fragB", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            finish();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + item.getItemId());
                    }
                    return true;
                }
            };

    /**
     *
     * This class handles the constructs the tool bar
     *
     * @author  Hermie Baylon
     * @version 1.0
     */
    private class ViewPageAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}