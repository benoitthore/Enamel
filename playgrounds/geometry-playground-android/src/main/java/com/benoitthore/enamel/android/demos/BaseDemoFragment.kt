package com.benoitthore.enamel.android.demos

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.benoitthore.enamel.android.DemoItem
import java.lang.IllegalArgumentException

class BaseDemoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val item = (arguments?.getSerializable(KEY_ITEM) as? DemoItem)
            ?: throw IllegalArgumentException("Invalid argument $KEY_ITEM, needs to be of type ${DemoItem::class}")

        val view = item(requireContext())

        return FrameLayout(requireContext()).apply {
            setOnClickListener {} // Avoids click through
            setBackgroundColor(Color.WHITE)
            addView(view)
        }
    }

    companion object {
        fun newInstance(item: DemoItem): BaseDemoFragment = BaseDemoFragment().apply {
            arguments = Bundle().apply { putSerializable(KEY_ITEM, item) }
        }

        private const val KEY_ITEM = "ITEM"
    }
}