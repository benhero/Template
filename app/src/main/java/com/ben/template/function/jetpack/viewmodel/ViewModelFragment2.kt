package com.ben.template.function.jetpack.viewmodel


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ben.template.R
import kotlinx.android.synthetic.main.fragment_view_model_fragment_2.*

/**
 * ViewModel
 *
 * @author Benhero
 * @date   2019/10/29
 */
class ViewModelFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_model_fragment_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(activity!!).get(MyViewModel::class.java)

        viewModel.num.observe(viewLifecycleOwner, {
            text.text = "Fragment 2: $it"
        })
        button.setOnClickListener {
            val num = viewModel.num
            val value = num.value ?: 0
            num.value = value - 1
        }
    }
}
