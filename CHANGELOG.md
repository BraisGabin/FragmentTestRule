Change Log
==========

Version 2.0.1 *(2019-01-09)*
----------------------------

* No changes, just upload again to maven central.

Version 2.0.0 *(2019-01-04)*
----------------------------

Migrate to AndroidX

Version 1.1.0 *(2017-11-21)*
----------------------------

Split the library in two components. To import this version change this:


```gradle
debugImplementation 'com.21buttons:fragment-test-rule:1.0.0'
```

to this:


```gradle
androidTestImplementation 'com.21buttons:fragment-test-rule:1.1.0'
debugImplementation 'com.21buttons:fragment-test-rule-extras:1.1.0'
```

Version 1.0.0 *(2017-08-03)*
----------------------------

Add FragmentTestRule Factory methods to create a FragmentTestRule with a default activity container. 


Version 0.1.0 *(2017-07-24)*
----------------------------

Initial release.
