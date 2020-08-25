package com.benoitthore.enamel.android

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.*
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.TypefaceCompatUtil
import androidx.recyclerview.widget.RecyclerView
import com.benoitthore.enamel.R
import com.benoitthore.enamel.android.demos.BaseDemoFragment
import com.benoitthore.enamel.databinding.ActivityMainBinding
import com.benoitthore.enamel.databinding.DemoItemBinding
import com.benoitthore.enamel.android.demos.views.*
import com.benoitthore.enamel.core.withAlpha
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.functions.scale
import com.benoitthore.enamel.geometry.functions.toRect
import com.benoitthore.enamel.layout.android.getViewBounds
import com.benoitthore.visualentity.DemoDrawer
import com.benoitthore.enamel.visualentity.android.draw
import com.benoitthore.enamel.visualentity.android.toAndroid
import com.benoitthore.enamel.visualentity.android.utils.dp
import com.benoitthore.visualentity.style.style
import com.benoitthore.visualentity.toVisualEntity

class MainActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(object : View(this) {
            val drawer = DemoDrawer { toAndroid() }

            override fun onDraw(canvas: Canvas) {
                canvas.drawColor(BLACK)

                drawer.getDrawables(getViewBounds()).forEach {
                    it.style = it.style.copy(
                        border = it.style.border?.copy(
                            width = it.style.border?.width?.dp ?: 0f
                        )
                    )
                    canvas.draw(it)
                }
                invalidate()
            }
        })
        return

        binding = ActivityMainBinding.inflate(layoutInflater)


        binding.mainRecyclerView.adapter = DemoAdapter(
            DemoItem.values().toList()
        ) { item ->

            while (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStackImmediate()
            }
            val fragment = BaseDemoFragment.newInstance(item)
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }


        setContentView(binding.root)
    }

}

enum class DemoItem(private val buildView: (Context) -> View) {
    Item0({ MyProgressBar(it) });
//    Item1({ TextView(it).apply { text = "12312312341321321312312312" } }),
//    Item2({ View(it).apply { setBackgroundColor(RED) } });

    operator fun invoke(context: Context): View = buildView(context)
}

class DemoAdapter(val demos: List<DemoItem>, private val onClick: (DemoItem) -> Unit) :
    RecyclerView.Adapter<DemoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoItemViewHolder =
        DemoItemViewHolder(DemoItemBinding.inflate(LayoutInflater.from(parent.context)), onClick)

    override fun getItemCount(): Int = demos.size

    override fun onBindViewHolder(holder: DemoItemViewHolder, position: Int) {
        holder.bind(demos[position])
    }

}

class DemoItemViewHolder(
    private val binding: DemoItemBinding,
    private val onClick: (DemoItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(demoItem: DemoItem) {

        binding.demoItemText.text = demoItem.name

        itemView.setOnClickListener {
            onClick(demoItem)
        }
    }
}











