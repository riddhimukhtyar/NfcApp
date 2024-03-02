// ScreenFragment.kt
package com.example.nfcapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class ScreenFragment : Fragment() {

    private lateinit var screenItem: ScreenItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            screenItem = it.getParcelable(SCREEN_ITEM_KEY)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.layout_screen, container, false)
        // Set data to views
        view.findViewById<ImageView>(R.id.intro_img).setImageResource(screenItem.screenImg)
        view.findViewById<TextView>(R.id.intro_title).text = screenItem.title
        view.findViewById<TextView>(R.id.intro_description).text = screenItem.description
        return view
    }

    companion object {
        private const val SCREEN_ITEM_KEY = "screenItemKey"

        @JvmStatic
        fun newInstance(screenItem: ScreenItem) =
            ScreenFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(SCREEN_ITEM_KEY, screenItem)
                }
            }
    }
}
