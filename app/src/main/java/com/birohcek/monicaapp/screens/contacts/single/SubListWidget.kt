//package com.birohcek.monicaapp.screens.contacts.single
//
//import android.content.Context
//import android.util.AttributeSet
//import android.widget.TextView
//import androidx.core.content.res.use
//import com.birohcek.monicaapp.R
//import com.squareup.contour.ContourLayout
//import com.squareup.contour.YInt
//
//
//class SubListWidget constructor(context: Context, attrs: AttributeSet) :
//    ContourLayout(context, attrs) {
//
//    private var contentViews: List<TextView> = listOf()
//    fun showList(contents: List<String>) {
//
//        contentViews.forEach {
//            removeView(it)
//        }
//
//        val k = contents.fold(listOf()) { list: List<TextView>, s ->
//            val t = TextView(context).apply {
//                text = s
//                setTextAppearance(context, R.style.TextAppearance_AppCompat_Body1)
//
//                layoutBy(
//                    leftTo { parent.left() },
//                    topTo { list.lastOrNull()?.bottom() ?: titleView.bottom() }
//                )
//            }
//
//            list + t
//        }
//
//        contentViews = k
//        requestLayout()
//    }
//
//
//    private val title = context.obtainStyledAttributes(attrs, R.styleable.SubListWidget)
//        .use {
//            it.getString(R.styleable.SubListWidget_title)
//        }
//
//    private val titleView = TextView(context).apply {
//        setTextAppearance(context, R.style.TextAppearance_MaterialComponents_Headline4)
//        text = title
//
//        layoutBy(
//            leftTo { parent.left() },
//            topTo { parent.top() }
//        )
//    }
//
//    init {
//        contourHeightOf { titleView.height() + contentViews.fold(YInt.ZERO) { acc, t -> acc + t.height() } }
//    }
//
//    private fun setTitle(title: String) {
//        titleView.text = title
//        titleView.requestLayout()
//    }
//
//}