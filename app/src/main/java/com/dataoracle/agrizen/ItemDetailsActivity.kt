package com.dataoracle.agrizen

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.cloudinary.android.MediaManager
import kotlinx.android.synthetic.main.activity_item_details.*


class ItemDetailsActivity : AppCompatActivity() {
    val mImagesList = arrayListOf<Int>(0, 1, 2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        id_item_pictures_viewpager.adapter = SlidingImage_Adapter(this, mImagesList)
    }

    class SlidingImage_Adapter(context: Context, IMAGES: ArrayList<Int>) :
        PagerAdapter() {
        private val IMAGES: ArrayList<Int>
        private val inflater: LayoutInflater
        private val context: Context
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun getCount(): Int {
            return IMAGES.size
        }

        override fun instantiateItem(view: ViewGroup, position: Int): Any {
            val imageLayout: View = inflater.inflate(R.layout.image_detail, view, false)!!
            val imageView = imageLayout.findViewById<View>(R.id.image) as ImageView
            Glide.with(context).load(MediaManager.get().url().generate("sample.jpg")).into(imageView)
            view.addView(imageLayout, 0)
            return imageLayout
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}
        override fun saveState(): Parcelable? {
            return null
        }

        init {
            this.context = context
            this.IMAGES = IMAGES
            inflater = LayoutInflater.from(context)
        }
    }
}