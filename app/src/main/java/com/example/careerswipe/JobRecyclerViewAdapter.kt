package com.example.careerswipe
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import androidx.core.content.ContextCompat
import com.example.jobreview.R

class JobAdapter(private val jobList: List<Job>, private val context: Context) : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val companyTextView: TextView = itemView.findViewById(R.id.company)
        val payTextView: TextView = itemView.findViewById(R.id.pay)
        val rootLayout: View = itemView.findViewById(R.id.root) // Root layout from layout_job.xml
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        // Inflate the layout_job.xml for each item in the RecyclerView
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_job, parent, false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobList[position]

        // Set data in the corresponding TextViews
        holder.titleTextView.text = job.title
        holder.companyTextView.text = job.company
        holder.payTextView.text = "$${job.annual_pay}" // Format pay to look like currency
        // Set the background color based on the job status
        when (job.status) {
            0 -> holder.rootLayout.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white)) // Neutral
            1 -> holder.rootLayout.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_green_light)) // Approved
            2 -> holder.rootLayout.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_light)) // Declined
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, JobOverviewActivity::class.java).apply {
                putExtra("JOB_ID", job.id)
                putExtra("JOB_TITLE", job.title)
                putExtra("JOB_COMPANY", job.company)
                putExtra("JOB_DESCRIPTION", job.description)
                putExtra("JOB_PAY", job.annual_pay)
                putExtra("JOB_LEVEL", job.level)
                putExtra("JOB_LINK", job.link)
                putExtra("JOB_STATUS", job.status)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return jobList.size
    }
}
