package com.ninja.understandworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.work.*
import com.ninja.understandworkmanager.databinding.ActivityMainBinding
import com.ninja.understandworkmanager.databinding.ActivityWorkRequestsBinding
import java.util.concurrent.TimeUnit

class WorkRequestsActivity : AppCompatActivity() {
    private val TAG = WorkRequestsActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityWorkRequestsBinding.inflate(layoutInflater)
        val view = binding.root



        setContentView(view)
    }

    fun oneTimeWorkRequest(view: View) {
        val data = Data.Builder().putInt("number",5).build()
        val constaints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val workRequest =  OneTimeWorkRequest.Builder(OneTimeWorker::class.java)
            .setInputData(data)
            .setConstraints(constaints)
            .setInitialDelay(5,TimeUnit.SECONDS)
            .addTag("onetimeworkrequest")
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }

    fun periodicWorkRequest(view: View) {
        val data = Data.Builder().putInt("number",5).build()
        val constaints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val peiodicWorkRequest = PeriodicWorkRequest
            .Builder(OneTimeWorker::class.java,15,TimeUnit.MINUTES)
            .setInputData(data)
            .setConstraints(constaints)
            .addTag("periodicworkrequest")
            .build()

        WorkManager.getInstance(this).enqueue(peiodicWorkRequest)

        WorkManager.getInstance(applicationContext)
            .getWorkInfoByIdLiveData(peiodicWorkRequest.id)
            .observe(this, Observer{
                    workInfo ->
                if (workInfo != null)
                {
                    Log.i(TAG, "periodicWorkRequest: "+workInfo.state.name+"\n")
                }
            })

        WorkManager.getInstance(this).cancelWorkById(peiodicWorkRequest.id)
    }
}