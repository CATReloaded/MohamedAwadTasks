package com.andalus.mohamedawadtasks.preference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import com.andalus.mohamedawadtasks.R;

public class FragmentPreference extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.xml);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen =getPreferenceScreen();

        Preference p = preferenceScreen.getPreference(0);
        String val = sharedPreferences.getString(p.getKey(),"");
        p.setSummary(val);

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference p = findPreference(key);
        if(p != null){
            String val = sharedPreferences.getString(p.getKey() ,"");
            ListPreference listPreference = (ListPreference) p;
            int index = listPreference.findIndexOfValue(val);
            listPreference.setSummary(listPreference.getEntries()[index]);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
