package edu.uw.harmony.UI.Contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import edu.uw.harmony.R;
import edu.uw.harmony.UI.Contacts.ContactRequestListFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactContainerFragment extends Fragment {

    /** Current Fragment to be displayed */
    View myFragment;

    /** View pager that is used to display chosen fragments*/
    ViewPager viewPager;

    /** menu that contains the tabs */
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_contact_container, container, false);
        viewPager = myFragment.findViewById(R.id.view_pager);
        tabLayout = myFragment.findViewById(R.id.tabs);
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Creates a view pager for the fragments to use to navigate to
     * @param viewPager - The body of the UI
     */
    private void setUpViewPager(ViewPager viewPager) {
        ViewPageAdapter adapter = new ViewPageAdapter(getChildFragmentManager());

        adapter.addFragment(new ContactListFragment(), "My Contacts");
        adapter.addFragment(new ContactRequestListFragment(), "Requests");

        viewPager.setAdapter(adapter);
    }

    /**
     * Inflates toolbar menu
     * @param menu - Resource file that contains toolbar
     * @param inflater - inflater parameter
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contact_tool_bar, menu);
    }

    /**
     * Makes tool bar menu visible
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * handles when tool bar menu's item is clicked
     * @param item - Selected Item
     * @return true if item selected add new contacts
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_new_contact:
                Navigation.findNavController(getView()).
                        navigate(ContactContainerFragmentDirections.actionNavigationContactContainerToAddNewContactFragment());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *
     * This class handles the constructs the tool bar and allows to add class in toolbar
     *
     * @author  Hermie Baylon
     * @version 1.0
     */
    private class ViewPageAdapter extends FragmentPagerAdapter {

        /** List that stores all needed fragments for tabs */
        private List<Fragment> fragments = new ArrayList<>();

        /** List of Fragment titles for fragments */
        private List<String> fragmentTitle = new ArrayList<>();

        /** Constructor for Page adapter with behaviour paramenter */
        public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        /** Constructor for page adapter */
        public ViewPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        /** Adds fragment and title to list */
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