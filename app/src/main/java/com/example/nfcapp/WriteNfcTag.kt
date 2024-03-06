import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nfcapp.Record_Type
import com.example.nfcapp.R
import com.google.android.material.card.MaterialCardView


class WriteNfcTag : Fragment() {

    // Define a variable to hold the reference to the MaterialCardView
    private lateinit var addRecordsCardView: MaterialCardView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write_nfc_tag, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the MaterialCardView using findViewById
        addRecordsCardView = view.findViewById(R.id.AddrecordsCardview)




        addRecordsCardView.setOnClickListener {

            val intent = Intent(activity, Record_Type::class.java)
            startActivity(intent)
        }


    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WriteNfcTag().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
