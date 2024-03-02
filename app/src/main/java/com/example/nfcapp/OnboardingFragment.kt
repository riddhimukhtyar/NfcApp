package com.example.nfcapp

import ViewpagerAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class OnboardingFragment : Fragment() {
    private lateinit var screenPager: ViewPager
    private lateinit var introViewPagerAdapter: ViewpagerAdapter
    private lateinit var tabIndicator: TabLayout
    private lateinit var btnNext: ImageView
    private lateinit var btnGetStarted: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var cardview: View
    private lateinit var cardview2: View
    private lateinit var rootView: View

    private var position = 0
    companion object {
        // Static method to create a new instance of OnboardingFragment
        fun newInstance(): OnboardingFragment {
            return OnboardingFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_onboarding, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        btnGetStarted = rootView.findViewById(R.id.btn_get_started)
        tabIndicator = rootView.findViewById(R.id.tab_indicator)
        btnNext = rootView.findViewById(R.id.btn_next)
        cardview = rootView.findViewById(R.id.cardview)
        cardview2 = rootView.findViewById(R.id.cardview2)
        screenPager = rootView.findViewById(R.id.screen_viewpager)

        // Make cardview2 invisible on the first screen
        cardview2.visibility = View.GONE

        val mList = ArrayList<ScreenItem>()
        mList.add(ScreenItem("Welcome To NFC Tools!", "Unlock The Power Of NFC Technology With Our User-Friendly Tools.", R.drawable.img_onboarding))
        mList.add(ScreenItem("Near Field Communication", "NFC Enables Short-Range Wireless Communication Between Devices.", R.drawable.img_onboarding2))
        mList.add(ScreenItem("Let's Get Started", "Simply Tap Your Device To Another Equipped With NFC To Initiate NFC Tools.It's That Easy!.", R.drawable.img_onboarding3))

        introViewPagerAdapter = ViewpagerAdapter(requireContext(), mList)
        screenPager.adapter = introViewPagerAdapter

        screenPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                // Check if it's the second screen
                if (position == 1) {
                    cardview2.visibility = View.VISIBLE
                } else {
                    cardview2.visibility = View.GONE
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        btnGetStarted.setOnClickListener {
            // Navigate to the HomeFragment activity
            navigateToHomeFragment()
        }

        btnNext.setOnClickListener {
            position = screenPager.currentItem
            if (position < mList.size) {
                position++
                screenPager.currentItem = position
            }
            if (position == mList.size - 1) {
                loaddLastScreen()
            }
        }

        tabIndicator.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == mList.size - 1) {
                    loaddLastScreen()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        if (restorePrefData()) {
            // Navigate to the HomeFragment activity if onboarding is already completed
            navigateToHomeFragment()
        }

        return rootView
    }

    private fun savePrefsData() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isIntroOpnend", true)
        editor.apply()
    }

    private fun restorePrefData(): Boolean {
        return sharedPreferences.getBoolean("isIntroOpnend", false)
    }

    private fun loaddLastScreen() {
        btnNext.visibility = View.INVISIBLE
        btnGetStarted.visibility = View.VISIBLE
        tabIndicator.visibility = View.GONE
        tabIndicator.isClickable = false
        cardview.visibility = View.GONE
        cardview2.visibility = View.GONE
    }

    private fun navigateToHomeFragment() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)

        // Save preferences to indicate that the user has completed onboarding
        savePrefsData()

        // Finish the current activity
        requireActivity().finish()
    }
}
