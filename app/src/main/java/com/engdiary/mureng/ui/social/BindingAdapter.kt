package com.engdiary.mureng.ui.social

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.engdiary.mureng.R
import com.engdiary.mureng.data.ItemWriteDiaryImage
import com.google.android.material.tabs.TabLayout
import jp.wasabeef.blurry.Blurry


object BindingAdapter {
    @BindingAdapter("setTapContents", "setVm")
    @JvmStatic
    fun setTapContents(tabLayout: TabLayout, items: List<String>?, mainVm: SocialViewModel?) {
        items?.forEach {
            with(tabLayout) {
                newTab().let { tab ->
                    tab.text = it
                    addTab(tab)
                }
                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabReselected(tab: TabLayout.Tab?) {
                        //Nothing.
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                        //Nothing.
                    }

                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        tab?.position?.let { position ->
                            mainVm?.selectPosition(position)
                        }
                    }
                })
            }
        }
    }

    @BindingAdapter("setPagerCount", "setFsm", "setVm")
    @JvmStatic
    fun setViewPager(
        viewPager: ViewPager,
        items: List<String>?,
        fragmentManager: FragmentManager?,
        mainVm: SocialViewModel?
    ) {
        if (!items.isNullOrEmpty())
            viewPager.adapter?.run {
                if (this is ViewPagerAdapter) {
                    setItems(items)
                }
            } ?: kotlin.run {
                if (fragmentManager != null)
                    viewPager.adapter = ViewPagerAdapter(fragmentManager, items)
                viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {
                        //Nothing.
                    }

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        //Nothing.
                    }

                    override fun onPageSelected(position: Int) {
                        mainVm?.selectPosition(position)
                    }
                })
            }
    }

    @BindingAdapter("setViewPosition")
    @JvmStatic
    fun setViewPosition(view: View, position: Int?) {
        if (position != null)
            when (view) {
                is ViewPager -> {
                    view.currentItem = position
                }
                is TabLayout -> {
                    view.run {
                        getTabAt(position)?.let { tab ->
                            selectTab(tab)
                        }

                    }
                }
            }
    }

    @BindingAdapter("hintBulb")
    @JvmStatic
    fun setHintBulb(imageView: ImageView, isHintOpen: Boolean) {
        val drawable = if (isHintOpen) {
            imageView.context
                .run { ResourcesCompat.getDrawable(resources, R.drawable.icons_bulb_checked, null) }
        } else {
            imageView.context
                .run { ResourcesCompat.getDrawable(resources, R.drawable.icons_bulb, null) }
        }


        Glide.with(imageView.context)
            .load(drawable)
            .into(imageView)
    }

    @BindingAdapter("hintArrow")
    @JvmStatic
    fun setHintArrow(imageView: ImageView, isHintOpen: Boolean) {
        val drawable = if (isHintOpen) {
            imageView.context
                .run {
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.writingdiary_hintarrow_up,
                        null
                    )
                }
        } else {
            imageView.context
                .run {
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.writingdiary_hintarrow,
                        null
                    )
                }
        }

        Glide.with(imageView.context)
            .load(drawable)
            .into(imageView)
    }

    @BindingAdapter("diaryImageSelected")
    @JvmStatic
    fun setDiaryImageSelected(imageView: ImageView, diaryImageSelected: Boolean) {
        if (diaryImageSelected) {
            val diaryimageSelectedTint = ResourcesCompat.getColor(
                imageView.resources,
                R.color.diaryimage_selected_tint,
                null
            )
            imageView.imageTintList = ColorStateList.valueOf(diaryimageSelectedTint)
            imageView.imageTintMode = PorterDuff.Mode.SRC_OVER
        } else {
            imageView.imageTintList = null
        }
    }

    @BindingAdapter("diaryImagePreview", "galleryImageUri")
    @JvmStatic
    fun setDiaryImagePreview(
        imageView: ImageView,
        diaryWriteImage: ItemWriteDiaryImage?,
        galleryImageUri: Uri?
    ) {
        val image = when (diaryWriteImage) {
            is ItemWriteDiaryImage.DiaryImage -> diaryWriteImage.url
            is ItemWriteDiaryImage.Gallery -> galleryImageUri
            null -> return
        }

        Glide.with(imageView.context)
            .asBitmap()
            .load(image)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    loadBlurBitmap(imageView, resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    @BindingAdapter("diaryImage")
    @JvmStatic
    fun setDiaryImage(imageView: ImageView, diaryImage: String?) {
        diaryImage?.let {
            Glide.with(imageView.context)
                .load(it)
                .into(imageView)
        }
    }
}

private fun loadBlurBitmap(imageView: ImageView, bitmap: Bitmap) {
    Blurry.with(imageView.context)
        .sampling(1)
        .from(bitmap)
        .into(imageView)
}
