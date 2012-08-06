Android AppMsg (Crouton) Library
==============================

Implementation of in-layout notifications. Based on Toast notifications and article by [Cyril Mottier](http://android.cyrilmottier.com/?p=773).


Description
-----------

The Google+ app slides the navigation on top of the UI while the others move the UI to the side. 
Google+ also has the up caret icon and the action bar present when the menu is opened while other apps don't.

There was a interesting discussion about this pattern in the blog's Google+ page some time ago. 
You can find the post & discussion here: [Google+](https://plus.google.com/115177579026138386092/posts/AvXiTF7LqDK).

Sample
------

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

* Create AppMsg:

``` java
AppMsg.makeText(/*Activity*/, /*CharSequence*/, /*AppMsg.Style*/).show();
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

[1]: http://android.cyrilmottier.com/medias/making_of_prixing/4/in_layout_notification_large.png