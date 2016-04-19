package net.q1cc.stefan.babygame01

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import com.pawegio.kandroid.d
import com.pawegio.kandroid.runAsync
import kotlinx.android.synthetic.main.activity_main_screen.*
import net.q1cc.stefan.babygame01.ColorFader
import net.q1cc.stefan.babygame01.R
import net.q1cc.stefan.babygame01.drawEngine.DrawEngine

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MainScreen : AppCompatActivity(), View.OnTouchListener {

    // activity lifecycle

    override fun onResume() {
        super.onResume()
        supportActionBar?.hide()
        actionBar?.hide()
    }

    private var drawEngine: ColorFader? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_screen)
        surf.setOnTouchListener(this)
        surf.fullScreen()
        drawEngine = ColorFader(surf)
    }

    override fun onStart() {
        super.onStart()
        surf.fullScreen()
        drawEngine?.start()
    }

    override fun onStop() {
        super.onStop()
        drawEngine?.stop()
    }

    // callback methods

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        if (event==null) return false
        if (event.action==MotionEvent.ACTION_DOWN){
            drawEngine?.changeColor()
        }
        return true
    }

}
