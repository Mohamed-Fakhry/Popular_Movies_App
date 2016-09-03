package com.example.eastsound.popularmoviesapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Setting extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.type);
            Preference preference =           findPreference(getString(R.string.list_type_key));
            preference.setOnPreferenceChangeListener(this);
            preference  .setSummary(PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .getString(getString(R.string.list_type_key), "popular"));
        }

        @Override
        public void onStart() {
            super.onStart();

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    preference.setSummary(listPreference.getEntry());
                }
            }
            preference.setSummary(stringValue);



            return true;
        }
    }
}
