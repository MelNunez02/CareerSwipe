package com.example.careerswipe

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.jobreview.R
import com.google.firebase.database.FirebaseDatabase

class JobOverviewActivity : AppCompatActivity() {

    private lateinit var jobId: String // Assume we have a unique job ID
    private lateinit var jobLink: String // Job link for redirection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_overview)

        // Retrieve intent extras
        val jobTitle = intent.getStringExtra("JOB_TITLE") ?: "N/A"
        val jobCompany = intent.getStringExtra("JOB_COMPANY") ?: "N/A"
        val jobDescription = intent.getStringExtra("JOB_DESCRIPTION") ?: "N/A"
        val jobPay = intent.getIntExtra("JOB_PAY", 0)
        val jobLevel = intent.getStringExtra("JOB_LEVEL") ?: "N/A"
        jobId = intent.getStringExtra("JOB_ID") ?: "N/A" // Ensure you pass the job ID from the previous activity
        jobLink = intent.getStringExtra("JOB_LINK") ?: "N/A" // Retrieve the job link


        // Find views by ID
        val jobTitleTextView: TextView = findViewById(R.id.jobTitle)
        val jobCompanyTextView: TextView = findViewById(R.id.jobCompany)
        val jobDescriptionTextView: TextView = findViewById(R.id.jobDescription)
        val jobPayTextView: TextView = findViewById(R.id.jobPay)
        val jobLevelTextView: TextView = findViewById(R.id.jobLevel)

        // Populate views with intent data
        jobTitleTextView.text = jobTitle
        jobCompanyTextView.text = jobCompany
        jobDescriptionTextView.text = jobDescription
        jobPayTextView.text = "$$jobPay"
        jobLevelTextView.text = jobLevel

        // Handle Decline Button Click
        val denyButton: Button = findViewById(R.id.denyBtn)
        denyButton.setOnClickListener {
            updateJobStatus(jobId, 2) // 2 = Declined
        }

        // Handle Approve Button Click
        val approveButton: Button = findViewById(R.id.saveBtn)
        approveButton.setOnClickListener {
            updateJobStatus(jobId, 1)// 1 = Approved
        }

        // Handle Approve Button Click
        val applyBtn: Button = findViewById(R.id.applyBtn)
        applyBtn.setOnClickListener {
            redirectToLink(jobLink)
        }

    }

    private fun updateJobStatus(jobId: String, status: Int) {
        val database = FirebaseDatabase.getInstance()
        val jobRef = database.getReference("Jobs").child(jobId)

        jobRef.child("status").setValue(status)
            .addOnSuccessListener {
                val message = when (status) {
                    1 -> "Job Approved Successfully!"
                    2 -> "Job Declined Successfully!"
                    else -> "Job Updated!"
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                finish() // Navigate back to the previous screen
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to update job status.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun redirectToLink(link: String) {
        if (link.isNotEmpty()) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(browserIntent) // Open the link in the default browser
        } else {
            Toast.makeText(this, "No valid link provided.", Toast.LENGTH_SHORT).show()
        }
        finish() // Close the current activity
    }
}
