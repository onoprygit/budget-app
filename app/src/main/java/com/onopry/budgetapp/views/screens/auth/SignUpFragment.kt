package com.onopry.budgetapp.views.screens.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import com.onopry.budgetapp.R
import com.onopry.budgetapp.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    private var dbRef: DatabaseReference? = null
    private lateinit var db : FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        db = FirebaseDatabase.getInstance("https://budget-app-a4a96-default-rtdb.europe-west1.firebasedatabase.app")
        dbRef = db.getReference("message")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater,container,false)

        //write
        binding.registrationBtnRegister.setOnClickListener {
            dbRef?.setValue("Hello!!!!!!!")
        }
        //read
        binding.testRead.setOnClickListener {
            println("Read value from firebase")

            dbRef?.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.getValue(String::class.java)
                    binding.registerScreenTitle.text = value as String
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Failed to read value, ${error.toException()}")
                }

            })
        }

        return binding.root
    }

}