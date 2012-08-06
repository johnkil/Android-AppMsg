Android SideNavigation Library
==============================

Implementation of "Side Navigation" or "Fly-in app menu" pattern for Android (based on Google+ app).


Description
-----------

The Google+ app slides the navigation on top of the UI while the others move the UI to the side. 
Google+ also has the up caret icon and the action bar present when the menu is opened while other apps don't.

There was a interesting discussion about this pattern in the blog's Google+ page some time ago. 
You can find the post & discussion here: [Google+](https://plus.google.com/115177579026138386092/posts/AvXiTF7LqDK).

Sample
------

A sample application is available on Google Play:

<a href="http://play.google.com/store/apps/details?id=com.devspark.sidenavigation.sample">
  <img alt="Get it on Google Play"
       src="http://www.android.com/images/brand/get_it_on_play_logo_small.png" />
</a>

![Example Image][1]

The source code is available in this repository.

Compatibility
-------------

This library is compatible from API 4 (Android 1.6).

Installation
------------

The sample project requires:

* The library project
* [ActionBarSherlock](https://github.com/JakeWharton/ActionBarSherlock)

Usage
-----

To display the item you need the following code:

* Add SideNavigationView to the end of the layout. Example:

``` xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical" >

    <ImageView
        android:id="@android:id/icon"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:src="@drawable/ic_android_logo" />

    <com.devspark.sidenavigation.SideNavigationView
        android:id="@+id/side_navigation_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</RelativeLayout>
```

* Create '.xml' description of the menu for the sideNavigationView. Example:

``` xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android" >

    <item
        android:id="@+id/side_navigation_menu_item1"
        android:icon="@drawable/ic_android1"
        android:title="@string/title1"/>
    <item
        android:id="@+id/side_navigation_menu_item2"
        android:icon="@drawable/ic_android2"
        android:title="@string/title2"/>
    <item
        android:id="@+id/side_navigation_menu_item3"
        android:icon="@drawable/ic_android3"
        android:title="@string/title3"/>
    <item
        android:id="@+id/side_navigation_menu_item4"
        android:icon="@drawable/ic_android4"
        android:title="@string/title4"/>
    <item
        android:id="@+id/side_navigation_menu_item5"
        android:icon="@drawable/ic_android5"
        android:title="@string/title5"/>

</menu>
```

* Set home should be displayed as an "up" and initialize the sideNavigationView:

``` java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
    // other code
    
    sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
    sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
	sideNavigationView.setMenuClickCallback(/*ISideNavigationCallback*/);
        
    getActionBar().setDisplayHomeAsUpEnabled(true);
}
```

* Override onOptionsItemSelected() method for show/hide teh sideNavigationView:

``` java
@Override
public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case android.R.id.home:
		sideNavigationView.toggleMenu();
		break;
	default:
		return super.onOptionsItemSelected(item);
	}
	return true;
}
```

* Implementation of ISideNavigationCallback:

``` java
ISideNavigationCallback sideNavigationCallback = new ISideNavigationCallback() {
    	
	@Override
	public void onSideNavigationItemClick(int itemId) {
		// Validation clicking on side navigation item
	}
};
```

Developed By
------------
* Evgeny Shishkin - <johnkil78@gmail.com>

License
-------

    Copyright 2012 Evgeny Shishkin
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
    http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: http://i45.tinypic.com/f8jzn.png