package net.q1cc.stefan.momogame01

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import com.pawegio.kandroid.d
import com.pawegio.kandroid.runAsync
import kotlinx.android.synthetic.main.activity_main_screen.*
import net.q1cc.stefan.babygame01.R

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MainScreen : AppCompatActivity(), View.OnTouchListener {

    private val backgroundPaint = Paint()
    private var running = true
    private val frameTime = 1000/30
    private val colors = intArrayOf(
            Color.BLUE, Color.rgb(255,0,255),
            Color.RED, Color.rgb(255,255,0),
            Color.GREEN, Color.rgb(0,255,255)
    )
    private var nextColorIdx = 0
    private var lastColor = Color.BLACK
    private val fadeTime = 200
    private var fadeStartTime : Long = 0

    // activity lifecycle

    override fun onResume() {
        super.onResume()
        supportActionBar?.hide()
        actionBar?.hide()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_screen)
        surf.setOnTouchListener(this)
        surf.fullScreen()
    }

    override fun onStart() {
        super.onStart()
        surf.fullScreen()
        // main rendering loop
        running = true
        runAsync {
            var waitTime = 500L
            initState()
            while (running){
                Thread.sleep(waitTime)
                val startTime=System.currentTimeMillis();
                computeState();
                val holder : SurfaceHolder = surf?.holder ?:continue;
                val canvas : Canvas? = holder.lockCanvas()
                if (canvas!=null){
                    drawState(canvas)
                    holder.unlockCanvasAndPost(canvas)
                }
                waitTime=Math.max(frameTime-System.currentTimeMillis()+startTime,5)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        running=false
    }

    // main loop callbacks

    private fun initState() {
        fadeStartTime = System.currentTimeMillis()
    }

    private fun computeState() {
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

    /** main rendering function **/
    fun drawState(canvas: Canvas) {
        canvas.drawRect(canvas.clipBounds,backgroundPaint)
    }

    // callback methods

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        if (event==null) return false
        if (event.action==MotionEvent.ACTION_DOWN){
            synchronized(this){
                lastColor = backgroundPaint.color
                fadeStartTime = System.currentTimeMillis()
                nextColorIdx = (nextColorIdx +1)%colors.size
            }
        }
        return true
    }

}
