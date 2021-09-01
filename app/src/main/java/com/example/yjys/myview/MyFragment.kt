package com.example.yjys.myview

import android.util.Log
import androidx.fragment.app.Fragment

abstract class MyFragment : Fragment() {

    private var isLoaded = false

    override fun onResume() {
        super.onResume()
        if (!isLoaded) {
            lazyInit()
            Log.d("MyFragmnet", "lazyInit:!!!!!!!")
            isLoaded = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }

    abstract fun lazyInit()

}