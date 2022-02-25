package io.nacular.doodle.theme.native

import io.nacular.doodle.controls.range.Slider
import io.nacular.doodle.controls.theme.range.SliderBehavior
import io.nacular.doodle.drawing.Canvas
import io.nacular.doodle.drawing.impl.NativeSliderFactory
import io.nacular.doodle.event.PointerEvent
import io.nacular.doodle.event.PointerListener
import io.nacular.doodle.event.PointerMotionListener
import io.nacular.doodle.system.Cursor.Companion.Default

/**
 * Created by Nicholas Eddy on 11/20/18.
 */
internal class NativeSliderBehavior<T>(
        nativeSliderFactory: NativeSliderFactory,
        slider             : Slider<T>
): SliderBehavior<T>, PointerListener, PointerMotionListener where T: Number, T: Comparable<T> {
    private val nativePeer by lazy { nativeSliderFactory(slider) { slider, value -> slider.set(value) } }

    override fun render(view: Slider<T>, canvas: Canvas) {
        nativePeer.render(canvas)
    }

    override fun install(view: Slider<T>) {
        super.install(view)

        view.cursor              = Default
        view.pointerChanged       += this
        view.pointerMotionChanged += this

        view.rerender()
    }

    override fun uninstall(view: Slider<T>) {
        super.uninstall(view)

        nativePeer.discard()

        view.cursor              = null
        view.pointerChanged       -= this
        view.pointerMotionChanged -= this
    }

    override fun pressed(event: PointerEvent) {
        event.consume()
    }

    override fun dragged(event: PointerEvent) {
        event.consume()
    }

    override fun clipCanvasToBounds(view: Slider<T>) = false
}