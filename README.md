Android AppMsg (Crouton) Library
==============================

Implementation of in-layout notifications. Based on [Toast](http://developer.android.com/reference/android/widget/Toast.html) notifications and article by [Cyril Mottier](http://android.cyrilmottier.com/?p=773).


Description
-----------

Toast is far from being perfect and I am not entirely satisfied with it. 
Toast can be un-accurate in some cases. Indeed, Toast has one major drawback: it completely breaks contexts. 
This issue can be reproduced effortless. Let’s say a user is currently in an app firing a Toast and wants to switch to another application using the dedicated “multitask” button. 
The Toast will remain on screen even if the brought-to-front application has nothing do to with the previously shown app as described on the following figure:
![Example Image][1]

As you can easily notice, the problem with Toasts is they are persistent. 
Once a Toast has been fired, it is displayed on top of any screen and remains visible for the duration specified at its creation.

In order to bypass the Toast persistence problem and ensure information is displayed in the correct context, we decided to create a new notification system: 
Activity-bound notifications. This is what it looks like in the current version of Prixing:
![Example Image][2]

Crouton overcomes the main issue of having a Toast being shown while the menu is open. 
It sticks to the current screen sliding with it and leaving the menu completely free of any information that would have not been related to it.

Copyright (C) by [Cyril Mottier](http://android.cyrilmottier.com/?p=773)

Sample
------

![Example Image][3]

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

* Show AppMsg:

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

[1]: http://android.cyrilmottier.com/medias/making_of_prixing/4/toast_user_flow_fail.png
[2]: http://android.cyrilmottier.com/medias/making_of_prixing/4/in_layout_notification_large.png
[3]: http://i46.tinypic.com/21kywit.png