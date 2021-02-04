Navigation View
============

![Logo](logo.png)

This is a navigation view for Android Applications. It contains 4-direction buttons which can be disabled in different situations.

Layout xml:
```xml
<com.mohsenoid.navigationview.NavigationView
        android:id="@+id/navigationView"
        android:layout_width="200dp"
        android:layout_height="200dp"
        app:downButton="false" />
```  

Code:
```java
  navigationView = (NavigationView) findViewById(R.id.navigationView);

  // set navigation disabled color
  navigationView.setFillDisabledColor(Color.BLUE);

  // set navigation fill color
  navigationView.setFillColor(Color.RED);

  // set on Navigation Listener
  navigationView.setOnNavigationListener(this);

  // you can disable any navigation button
  navigationView.setButtonsEnabled(false, true, true, true);
```

USAGE
--------

Grab via Maven:
```xml
<dependency>
  <groupId>com.mohsenoid.navigationview</groupId>
  <artifactId>navigationview</artifactId>
  <version>1.0.3</version>
  <type>pom</type>
</dependency>
```
or Gradle:
```groovy
compile 'com.mohsenoid.navigation-view:navigationview:1.0.3'
```

![Screenshot](/Screenshot.png)

License
-------

    Copyright (c) 2014 Mohsen Mirhoseini Argi

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
