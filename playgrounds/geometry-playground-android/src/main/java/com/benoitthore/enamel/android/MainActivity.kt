package com.benoitthore.enamel.android

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.*
import android.widget.*
import android.graphics.Color.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.benoitthore.enamel.R
import com.benoitthore.enamel.android.demos.BaseDemoFragment
import com.benoitthore.enamel.databinding.ActivityMainBinding
import com.benoitthore.enamel.databinding.DemoItemBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)


        binding.mainRecyclerView.adapter = DemoAdapter(
            listOf(DemoItem.Item1, DemoItem.Item2)
        ) { item ->

            while(supportFragmentManager.backStackEntryCount > 0){
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
    Item1({ TextView(it).apply { text = "12312312341321321312312312" } }),
    Item2({ View(it).apply { setBackgroundColor(RED) } });

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











