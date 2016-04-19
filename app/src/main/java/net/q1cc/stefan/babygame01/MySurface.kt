package net.q1cc.stefan.momogame01

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.pawegio.kandroid.d

/**
 * Created by stefan on 18.04.16.
 */

class MySurface(ctx: Context, attrs: AttributeSet?, defStyleAttr:Int, defStyleRes:Int )
    : SurfaceView( ctx, attrs, defStyleAttr, defStyleRes )
    , SurfaceHolder.Callback
{
    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        d("surfaceDestroyed")
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        d("surfaceCreated")
    }

    override fun surfaceChanged(p0: SurfaceHolder?, format: Int, width: Int, height: Int) {
        d("surfaceChanged: "+width+"x"+height )
    }

    constructor(ctx: Context) : this(ctx,null,0,0)
    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx,attrs,0,0)
    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr:Int) : this(ctx,attrs,defStyleAttr,0)

    init{
        holder?.addCallback(this)
    }

    fun fullScreen() {
        systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

}
