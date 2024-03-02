import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.nfcapp.R
import com.example.nfcapp.ScreenItem

class ViewpagerAdapter(private val context: Context, private val mListScreen: List<ScreenItem>) : PagerAdapter() {

    override fun getCount(): Int {
        return mListScreen.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_screen, container, false)

        val imageView: ImageView = view.findViewById(R.id.intro_img)
        val titleTextView: TextView = view.findViewById(R.id.intro_title)
        val descriptionTextView: TextView = view.findViewById(R.id.intro_description)

        imageView.setImageResource(mListScreen[position].screenImg)
        titleTextView.text = mListScreen[position].title
        descriptionTextView.text = mListScreen[position].description

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
