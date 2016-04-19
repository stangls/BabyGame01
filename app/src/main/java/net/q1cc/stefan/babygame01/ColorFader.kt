package net.q1cc.stefan.babygame01

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import net.q1cc.stefan.babygame01.drawEngine.*

/**
 * Created by stefan on 19.04.16.
 */
class ColorFader(surface: Surface) : DrawEngine(surface) {

    private var fadeStartTime : Long = 0
    private val backgroundPaint = Paint()
    private val colors = intArrayOf(
            Color.BLUE, Color.rgb(255,0,255),
            Color.RED, Color.rgb(255,255,0),
            Color.GREEN, Color.rgb(0,255,255)
    )
    private var nextColorIdx = 0
    private var lastColor = Color.BLACK
    private val fadeTime = 200

    override fun initState() {
        fadeStartTime = System.currentTimeMillis()
    }

    override fun computeState() {
        val nextColor = synchronized(this){
            colors[nextColorIdx];
        }
        val p = synchronized(this){
            Math.min(1.0f,(System.currentTimeMillis()-fadeStartTime).toFloat()/fadeTime)
        }
        if (p==1.0f){
            backgroundPaint.color=nextColor
        }else{
            val pN = (1-p)
            val r=(Color.red(lastColor)*pN+Color.red(nextColor)*p).toInt()
            val g=(Color.green(lastColor)*pN+Color.green(nextColor)*p).toInt()
            val b=(Color.blue(lastColor)*pN+Color.blue(nextColor)*p).toInt()
            synchronized(this){
                backgroundPaint.color = Color.rgb(r,g,b)
            }
        }
    }

    override fun drawState(canvas: Canvas) {
        canvas.drawRect(canvas.clipBounds,backgroundPaint)
    }

    fun changeColor() {
        synchronized(this){
            lastColor = backgroundPaint.color
            fadeStartTime = System.currentTimeMillis()
            nextColorIdx = (nextColorIdx +1)%colors.size
        }
    }
}