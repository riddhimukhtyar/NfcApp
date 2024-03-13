
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nfcapp.MoreOptions
import com.example.nfcapp.R
import com.example.nfcapp.Record_Type
import com.google.android.material.card.MaterialCardView


class WriteNfcTag : Fragment() {

    // Define a variable to hold the reference to the MaterialCardView
    private lateinit var addRecordsCardView: MaterialCardView
    private lateinit var addMoreOptionsCardView: MaterialCardView



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
        addMoreOptionsCardView = view.findViewById(R.id.moreoptioncardview)



        addRecordsCardView.setOnClickListener {

            val intent = Intent(activity, Record_Type::class.java)
            startActivity(intent)
        }
        addMoreOptionsCardView.setOnClickListener{

            val intent = Intent(activity, MoreOptions::class.java)
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
