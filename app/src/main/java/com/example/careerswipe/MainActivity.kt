package com.example.careerswipe
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.example.jobreview.R

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var jobAdapter: JobAdapter
    private lateinit var database: DatabaseReference
    private val jobList = mutableListOf<Job>() // Mutable list to hold jobs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize Firebase Database reference
        database = FirebaseDatabase.getInstance().getReference("Jobs")

        // Set up adapter with an empty list initially
        jobAdapter = JobAdapter(jobList, this)
        recyclerView.adapter = jobAdapter

        // Fetch jobs from Firebase
        fetchJobsFromFirebase()
    }

    private fun fetchJobsFromFirebase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear the existing list to avoid duplicates
                jobList.clear()

                // Iterate through all jobs in the database
                for (jobSnapshot in snapshot.children) {
                    val job = jobSnapshot.getValue(Job::class.java)
                    if (job != null) {
                        // Add the job to the list
                        jobList.add(job)
                    }
                }

                // Notify the adapter about the data change
                jobAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
                println("Failed to fetch jobs: ${error.message}")
            }
        })
    }
}