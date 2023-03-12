package com.ninja.understandworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.work.*
import com.ninja.understandworkmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        val data = Data.Builder().putString("DATA","This is my work data").build()

        val constrain = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        val  requests =  OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(data)
            .setConstraints(constrain)
            .build()
        WorkManager.getInstance().enqueue(requests)

        WorkManager.getInstance(applicationContext)
            .getWorkInfoByIdLiveData(requests.id)
            .observe(this, Observer{
                workInfo ->
                if (workInfo != null)
                {
                    if (workInfo.state.isFinished){
                        val data = workInfo.outputData;
                        val output = data.getString("OUT_DATA")
                        binding.workrequeststatus.append(output+"\n")
                    }
                    binding.workrequeststatus.append(workInfo.state.name+"\n")
                }
            })

        setContentView(view)
    }
}