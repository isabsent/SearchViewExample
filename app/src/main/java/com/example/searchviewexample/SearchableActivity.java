package com.example.searchviewexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.searchviewexample.fragment.SearchListFragment;

//https://github.com/edsilfer/custom-searchable
//https://github.com/stephenbaidu/android-place-picker - Fully customized SearchView example
//http://www.zoftino.com/android-search-dialog-with-search-suggestions-example
public class SearchableActivity extends AppCompatActivity{

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent); //https://stackoverflow.com/a/4787666/753575
        handleRequest();
    }

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        handleRequest();  // Handle the search request.
	}

    private void handleRequest() {
        SearchListFragment fragment = (SearchListFragment) getSupportFragmentManager().findFragmentByTag("FRAGMENT_TAG");
        if (fragment == null){
            fragment = new SearchListFragment();
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment, "FRAGMENT_TAG").commit();
        }
    }

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
            case android.R.id.home:
                showHome();
                break;
		}
		return super.onOptionsItemSelected(item);
	}

    public void showHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}