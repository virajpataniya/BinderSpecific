// ILoginInterface.aidl
package com.viraj.binderspecific;

// Declare any non-default types here with import statements

interface ILoginInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void login();//Login
    void loginCallback(boolean loginStatus,String loginUser);
}