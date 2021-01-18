package com.keennhoward.firebaseauthdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.keennhoward.firebaseauthdemo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityLoginBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        binding.loginTextView.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            when{
                TextUtils.isEmpty(binding.loginEmailEditText.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this@LoginActivity,
                        "Please enter email",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(binding.loginPasswordEditText.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this@LoginActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = binding.loginEmailEditText.text.toString().trim{it <= ' '}
                    val password: String = binding.loginPasswordEditText.text.toString().trim{it <= ' '}


                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener { task ->

                                if(task.isSuccessful){

                                    Toast.makeText(
                                        this@LoginActivity,
                                        "You are logged in successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)

                                    //get rids of extra layers of activity
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                                    intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()

                                } else{
                                    Toast.makeText(this@LoginActivity,
                                        task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                }
            }
        }
    }
}