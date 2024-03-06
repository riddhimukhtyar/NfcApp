package com.example.nfcapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nfcapp.databinding.ScanItemDataBinding

class ScanItemAdapter(
    private var items: MutableList<ScanItem>,
    private val onItemClick: (ScanItem) -> Unit
) : RecyclerView.Adapter<ScanItemAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ScanItemDataBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(items[position])
                }
            }
        }

        fun bind(scanItem: ScanItem) {
            binding.itemImage.setImageResource(scanItem.imageResId)
            binding.itemText.text = scanItem.text
            binding.itemTextSize.text = scanItem.textSize
            binding.itemMenu.setOnClickListener { view ->
                showPopupMenu(view, scanItem)
            }
        }

        private fun showPopupMenu(view: View, scanItem: ScanItem) {
            val wrapper = ContextThemeWrapper(view.context, R.style.CustomPopupMenu)
            val popup = PopupMenu(wrapper, view)
            popup.menuInflater.inflate(R.menu.item_options_menu, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.EraseTag -> {
                        showEraseConfirmationDialog(view.context, scanItem) // Corrected the method call
                        true
                    }
                    R.id.EditTag -> {
                        // Implement edit action
                        true
                    }
                    R.id.LockTag -> {
                        // Implement lock action
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }

        private fun showEraseConfirmationDialog(context: Context, scanItem: ScanItem) {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null)
            val tvMessage: TextView = dialogView.findViewById(R.id.tvErasing)
            // Correctly fetching the string resource with placeholder
            tvMessage.text = context.getString(R.string.erase_confirmation, scanItem.text)

            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)
                .create()

            dialogView.findViewById<Button>(R.id.btnYes).setOnClickListener {
                dialog.dismiss()
                Toast.makeText(context, "Erased: ${scanItem.text}", Toast.LENGTH_SHORT).show()
            }

            dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }


        private fun showErasingDialog(context: Context) {
            val erasingDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_erasing, null)
            val erasingDialog = AlertDialog.Builder(context).setView(erasingDialogView).create()

            val btnCancel = erasingDialogView.findViewById<Button>(R.id.btnCancel)
            val progressBar = erasingDialogView.findViewById<ProgressBar>(R.id.progressBar)
            val tvProgress = erasingDialogView.findViewById<TextView>(R.id.tvProgress)
            var progress = 0
            val progressUpdater = object : Runnable {
                @SuppressLint("SetTextI18n")
                override fun run() {
                    if (progress < 100) {
                        progress += 1
                        progressBar.progress = progress
                        tvProgress.text = "$progress%"
                        progressBar.postDelayed(this, 100)
                    } else {
                        erasingDialog.dismiss()
                    }
                }
            }
            progressBar.post(progressUpdater)

            btnCancel.setOnClickListener {
                progressBar.removeCallbacks(progressUpdater)
                erasingDialog.dismiss()
            }

            erasingDialog.setCancelable(false)
            erasingDialog.show()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ScanItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<ScanItem>) {
        val diffCallback = ScanItemDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

}
