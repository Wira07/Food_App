package com.wira_fkom.food_app.about

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.wira_fkom.food_app.databinding.ActivityProfileBinding
import com.wira_fkom.food_app.data.Profile

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)  // Ensure Firebase is initialized
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        binding.btnCreate.setOnClickListener { createUser() }
        binding.btnRead.setOnClickListener { readUser() }
        binding.btnUpdate.setOnClickListener { updateUser() }
        binding.btnDelete.setOnClickListener { deleteUser() }
    }

    private fun createUser() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhone.text.toString()

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val user = Profile(name = name, email = email, phone = phone)

        firestore.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("CreateUser", "DocumentSnapshot added with ID: ${documentReference.id}")
                Toast.makeText(this, "User created successfully!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("CreateUser", "Error adding document", e)
                Toast.makeText(this, "Failed to create user!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun readUser() {
        val id = binding.etId.text.toString()
        if (id.isEmpty()) {
            Toast.makeText(this, "Please enter a valid ID", Toast.LENGTH_SHORT).show()
            return
        }

        firestore.collection("users")
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val user = document.toObject(Profile::class.java)
                    if (user != null) {
                        binding.etName.setText(user.name)
                        binding.etEmail.setText(user.email)
                        binding.etPhone.setText(user.phone)
                    } else {
                        Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Log.e("ReadUser", "Error getting document", e)
                Toast.makeText(this, "Failed to fetch user!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUser() {
        val id = binding.etId.text.toString()
        if (id.isEmpty()) {
            Toast.makeText(this, "Please enter a valid ID", Toast.LENGTH_SHORT).show()
            return
        }

        val user = Profile(
            name = binding.etName.text.toString(),
            email = binding.etEmail.text.toString(),
            phone = binding.etPhone.text.toString()
        )

        if (user.name.isEmpty() || user.email.isEmpty() || user.phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        firestore.collection("users")
            .document(id)
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(this, "User updated successfully!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e("UpdateUser", "Error updating document", e)
                Toast.makeText(this, "Failed to update user!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteUser() {
        val id = binding.etId.text.toString()
        if (id.isEmpty()) {
            Toast.makeText(this, "Please enter a valid ID", Toast.LENGTH_SHORT).show()
            return
        }

        firestore.collection("users")
            .document(id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "User deleted successfully!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e("DeleteUser", "Error deleting document", e)
                Toast.makeText(this, "Failed to delete user!", Toast.LENGTH_SHORT).show()
            }
    }
}
