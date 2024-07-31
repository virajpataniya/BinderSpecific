package com.viraj.binderspecific

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log


class LoginService : Service() {
    companion object {
        private const val TAG = "LoginService"
    }
    override fun onBind(intent: Intent?): IBinder?{

        return object : ILoginInterface.Stub() {
            override fun login() {
                Log.d(TAG,"BinderB_LoginService")
                // One-way communication; in real projects, interprocess communication is usually bidirectional, with bidirectional service binding.
                // When implementing QQ login or QQ sharing, you need to enter your own package name, which is the reason for this.
                serviceStartActivity()
            }

            override fun loginCallback(loginStatus: Boolean, loginUser: String?) {
                TODO("Not yet implemented")
            }
        }
    }

    /**
     * Launch Page
     */

    private fun serviceStartActivity() {

        val intent = Intent(this, BLoginActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        startActivity(intent)

    }
}