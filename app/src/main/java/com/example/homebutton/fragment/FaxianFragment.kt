package com.example.homebutton.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homebutton.R
import com.example.homebutton.myview.MyFragment

class FaxianFragment() : Fragment() {

    var inflate : View? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(inflate == null){
            inflate = inflater.inflate(R.layout.faxian_fragment, container, false)
        }

        return inflate
    }
}