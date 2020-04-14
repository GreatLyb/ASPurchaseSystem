package com.lyb.purchasesystem.test

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.ScreenUtils
import com.lyb.purchasesystem.R
import java.util.*

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-14 17:28
 */
class LatterIndexView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    val mViewHight: Float
    var mletters = mutableListOf<String>()
    lateinit var defaultPaint: Paint

    //{ 'A',"B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z" }
    init {
        this.mViewHight = ScreenUtils.getScreenHeight().toFloat()
        Log.i("Lyb", "mViewHight==" + mViewHight)
        //
        this.mletters = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
        defaultPaint = Paint()
        defaultPaint.textSize = 18f
        defaultPaint.color = (resources.getColor(R.color.black_dim))

    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        //计算出每个字母的高度
        val singleHeight = mViewHight / mletters.size
        Log.i("Lyb", "singleHeight==" + singleHeight)
        for (index in mletters.indices) {
            //获取字体的宽度
            val mletter = mletters[index]
            val measureTextWidth = defaultPaint.measureText(mletter)
            Log.i("Lyb", "measureTextWidth==" + measureTextWidth)
            Log.i("Lyb", "mletter==" + mletter)
            val contentWidth = width - paddingLeft - paddingRight
            Log.i("Lyb", "contentWidth==" + contentWidth)
            val x = paddingLeft + (contentWidth - measureTextWidth) / 2
            // 计算基线位置
            // 计算基线位置
            val fontMetrics: Paint.FontMetrics = defaultPaint.getFontMetrics()
            val baseLine: Float = singleHeight / 2 + singleHeight * index + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
            canvas?.drawText(mletter, x, baseLine, defaultPaint)
        }
    }
}