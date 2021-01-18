package com.keennhoward.firebaseauthdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.keennhoward.firebaseauthdemo.databinding.ActivityRegister2Binding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegister2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityRegister2Binding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        binding.registerLoginTextView.setOnClickListener {
            onBackPressed()
        }

        binding.reButton.setOnClickListener {
            when{
                TextUtils.isEmpty(binding.reEmailEditText.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this@RegisterActivity,
                    "Please enter email",
                    Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(binding.rePasswordEditText.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this@RegisterActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = binding.reEmailEditText.text.toString().trim{it <= ' '}
                    val password: String = binding.rePasswordEditText.text.toString().trim{it <= ' '}


                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener { task ->

                                if(task.isSuccessful){
                                    //Firebase registered user
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "You are registered successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)

                                    //get rids of extra layers of activity
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                                    intent.putExtra("user_id", firebaseUser.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()

                                } else{
                                    Toast.makeText(this@RegisterActivity,
                                    task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                }
            }
        }
    }
}