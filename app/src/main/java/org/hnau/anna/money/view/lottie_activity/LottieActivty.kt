package org.hnau.anna.money.view.lottie_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import ru.hnau.androidutils.animations.AnimationMetronome
import ru.hnau.jutils.TimeValue
import ru.hnau.jutils.producer.detacher.ProducerDetachers


class LottieActivty : AppCompatActivity() {

    companion object {

        private val ANIMATION_PERIOD = TimeValue.SECOND * 15

    }

    private val lottieAnimationView: LottieAnimationView by lazy {
        LottieAnimationView(this).apply {
            setAnimation("animations/lottie.json")
        }
    }

    private var animationStarted = TimeValue.ZERO
    private val detachers = ProducerDetachers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(lottieAnimationView)
    }

    private fun updateAnimation() {
        val period = ANIMATION_PERIOD.milliseconds.toDouble()
        val percentage = (TimeValue.now() - animationStarted).milliseconds.toDouble() % period / period
        lottieAnimationView.progress = percentage.toFloat()
    }

    override fun onStart() {
        super.onStart()
        animationStarted = TimeValue.now()
        AnimationMetronome.attach(detachers) { updateAnimation() }
    }

    override fun onStop() {
        detachers.detachAllAndClear()
        super.onStop()
    }


}