package com.ninja.understandworkmanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class OneTimeWorker(context: Context, workerParams: WorkerParameters)
                            : Worker(context, workerParams) {
    val TAG = OneTimeWorker::class.java.name

    override fun doWork(): Result {
        var data = inputData
        val myData = data.getInt("number",-1)

        for (i in myData downTo 1){
            Log.d(TAG, "doWork: i is "+i)
            try{
                Thread.sleep(1000)
            }catch(e: Exception){
                e.printStackTrace()
                return Result.failure();
            }
        }
     //   return Result.success()

//return with out output Data
        val outputData =  Data.Builder()
            .putInt("number",15)
            .build()

        return Result.success(outputData)
    }
}
