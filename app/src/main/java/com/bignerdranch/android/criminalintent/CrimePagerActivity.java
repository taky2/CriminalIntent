package com.bignerdranch.android.criminalintent;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import java.util.List;
import java.util.UUID;


public class CrimePagerActivity extends FragmentActivity {

    // extra for crime id
    private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        // find ViewPager in activity's view
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);
        // get data set from CrimeLab - list of crimes
        mCrimes = CrimeLab.get(this).getCrimes();
        // get activity's instance of FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // set adapter to be unnamed instance of FragmentStatePagerAdapter
        // -> FragmentStatePagerAdapter manages conversation with ViewPager
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
        // find index of the crime to display by looping through and comparing each crime id
        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals((crimeId))) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
