package com.viraj.binderspecific

import android.app.ProgressDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import android.os.IBinder
import android.os.SystemClock
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private const val NAME = "android"
private const val PWD = "123456"

class BLoginActivity : AppCompatActivity() {

    private lateinit var et_account: EditText
    private lateinit var et_password: EditText
    private lateinit var btn_login: Button
    private var isStartRemote = false
    private lateinit var iLogin: ILoginInterface
    private var conn = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iLogin = ILoginInterface.Stub.asInterface(service)
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_b_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Copied
        btn_login.setOnClickListener {
            var account = et_account.text.toString()
            var pwd = et_password.text.toString()
            if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
                Toast.makeText(this, "Account or password is empty!", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }
            val progress = ProgressDialog(this)
            progress.setTitle("Login")
            progress.setMessage("Logging in")
            progress.show()
            Thread {
                SystemClock.sleep(2000)
                runOnUiThread {
                    var loginStatus = false
                    if (NAME == account && PWD == pwd) {
                        Toast.makeText(this, "Login successful！", Toast.LENGTH_SHORT).show()
                        loginStatus = true
                        finish()
                    } else {
                        Toast.makeText(this, "Login failed！", Toast.LENGTH_SHORT).show()
                    }
                    iLogin.loginCallback(loginStatus, account)
                    progress.dismiss()
                }
            }.start()
        }
        initBindService()
    }

    private fun initBindService() {
    val intent = Intent()
    intent.action = "BinderA_Action"
    intent.`package` = "com.viraj.binderspecific" //Package name of the client application
    bindService(intent, conn, Context.BIND_AUTO_CREATE)
    isStartRemote = true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isStartRemote) {
            unbindService(conn)
        }
    }

}