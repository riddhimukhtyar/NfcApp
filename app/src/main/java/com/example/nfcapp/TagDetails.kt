package com.example.nfcapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nfcapp.databinding.FragmentTagDetailsBinding

class TagDetails : Fragment() {
    private var _binding: FragmentTagDetailsBinding? = null
    private val binding get() = _binding!!

    private var tagContent: String? = null
    private lateinit var databaseHelper: DatabaseHelper // Declare the DatabaseHelper for saving data

    companion object {
        private const val ARG_TAG_CONTENT = "tag_content"

        fun newInstance(tagContent: String): TagDetails =
            TagDetails().apply {
                arguments = Bundle().apply {
                    putString(ARG_TAG_CONTENT, tagContent)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tagContent = it.getString(ARG_TAG_CONTENT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTagDetailsBinding.inflate(inflater, container, false)
        databaseHelper = DatabaseHelper(requireContext()) // Initialize the DatabaseHelper
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayTagDetails()
        setupListeners()
    }

    private fun displayTagDetails() {
        tagContent?.let {
            val formattedText = it.split(".").joinToString(separator = "\n \n") { part ->
                "• $part".trim()
            }
            binding.readNfcTagMessage.text = formattedText
        }
    }

    private fun setupListeners() {
        binding.idBtnShare.setOnClickListener { shareTagContent() }
        binding.idBtnCopy.setOnClickListener { copyTagContent() }
        binding.topMenu.backButton.setOnClickListener {
            parentFragmentManager.popBackStack() // Implement back button functionality
        }
        binding.topMenu.saveButton.setOnClickListener {
            tagContent?.let { content ->
                databaseHelper.saveMessage(content) // Save NFC tag content to database
                Toast.makeText(requireContext(), "NFC tag content saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun shareTagContent() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, tagContent)
        }
        startActivity(Intent.createChooser(shareIntent, "Share NFC Tag Content"))
    }

    private fun copyTagContent() {
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("NFC Tag Content", tagContent)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Tag content copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
