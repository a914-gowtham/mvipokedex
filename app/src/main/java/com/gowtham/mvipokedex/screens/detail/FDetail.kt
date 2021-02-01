package com.gowtham.mvipokedex.screens.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexboxLayoutManager
import com.gowtham.mvipokedex.R
import com.gowtham.mvipokedex.adpters.AdPokemonForm
import com.gowtham.mvipokedex.adpters.AdPokemonType
import com.gowtham.mvipokedex.databinding.FDetailBinding
import com.gowtham.mvipokedex.models.Pokemon
import com.gowtham.mvipokedex.models.PokemonDetail
import com.gowtham.mvipokedex.util.MainState
import com.gowtham.mvipokedex.util.assistedViewModel
import com.gowtham.mvipokedex.util.gone
import com.gowtham.mvipokedex.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class FDetail : Fragment(R.layout.f_detail) {

    private val args by navArgs<FDetailArgs>()

    @Inject
    lateinit var viewModelFactory: DetailViewModel.Factory

    private lateinit var binding: FDetailBinding

    private lateinit var pokenon: Pokemon

    private val viewModel by assistedViewModel { viewModelFactory.create(args.pokemon.name, it) }

    private val adType: AdPokemonType by lazy {
        AdPokemonType()
    }

    private val adForm: AdPokemonForm by lazy {
        AdPokemonForm()
    }

    private val adAbility: AdPokemonForm by lazy {
        AdPokemonForm()
    }

    private val adMove: AdPokemonForm by lazy {
        AdPokemonForm()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          /*    val transition =
                  TransitionInflater.from(context).inflateTransition(android.R.transition.move)
              sharedElementEnterTransition = transition
              sharedElementReturnTransition = transition*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FDetailBinding.bind(view)
        setHasOptionsMenu(true)
        binding.pokemon = args.pokemon.apply {
            pokenon = this
        }
        binding.buttonRetry.setOnClickListener {
            viewModel.fetchPokemonInfo()
        }
        setDataInView()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    MainState.Idle -> {

                    }
                    MainState.Loading -> {
                        binding.progress.show()
                        binding.viewRetry.gone()
                    }
                    is MainState.Success -> {
                        updateSuccessData(it.data as PokemonDetail)
                        binding.viewRetry.gone()
                    }
                    is MainState.Failure -> {
                        binding.progress.gone()
                        binding.viewRetry.show()
                    }
                }
            }
        }
    }

    private fun updateSuccessData(detail: PokemonDetail) {
        binding.progress.gone()
        binding.apply {
            val height = "${detail.height} Meter"
            val weight = "${detail.weight} Kg"
            txtHeight.text = height
            txtWeight.text = weight
        }
        val typeList = detail.types.map { type ->
            type.type.name
        }
        val formList = detail.forms.map { form ->
            form.name
        }
        val abilityList = detail.abilities.map { ability ->
            ability.type.name
        }
        val moveList = detail.moves.map { move ->
            move.move.name
        }
//        binding.viewDetail.show()
        adType.submitList(typeList)
        adForm.submitList(formList)
        adAbility.submitList(abilityList)
        adMove.submitList(moveList)

        // get the center for the clipping circle
        // get the center for the clipping circle
/*        val myView=binding.viewDetail
        val cx: Int = (myView.getLeft() + myView.getRight()) / 2
        val cy: Int = (myView.getTop() + myView.getBottom()) / 2

        // get the final radius for the clipping circle

        // get the final radius for the clipping circle
        val dx = Math.max(cx, myView.getWidth() - cx)
        val dy = Math.max(cy, myView.getHeight() - cy)
        val finalRadius = Math.hypot(dx.toDouble(), dy.toDouble()).toFloat()*/
        var x = binding.viewContent.right
        var y = binding.viewContent.bottom

        var startRadius = 0
        val endRadius =
            Math.hypot(
                binding.viewContent.width.toDouble(),
                binding.viewContent.height.toDouble()
            ).toInt()
        val myView = binding.viewContent
        try {
            val anim = ViewAnimationUtils.createCircularReveal(
                myView,
                x/2,
                600,
                0f,
                endRadius.toFloat()
            )
            binding.viewDetail.show()
            anim.duration = 600
            anim.start()
        } catch (e: Exception) {
            binding.viewDetail.show()
            e.printStackTrace()
        }
    }

    private fun setDataInView() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        val menu = binding.toolbar.menu.findItem(R.id.menu_count)
        menu.title = "#" + pokenon.getIndex()
        binding.listType.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adType
        }
        binding.listForms.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adForm
        }
        binding.listAbility.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adAbility
        }
        binding.listMoves.apply {
            isNestedScrollingEnabled = false
            layoutManager = FlexboxLayoutManager(context)
            adapter = adMove
        }
    }





    val globalListener=object : ViewTreeObserver.OnGlobalLayoutListener{
        override fun onGlobalLayout() {
            val myView=binding.viewContent
            val w = myView.width
            val h = myView.height
            val endRadius =
                Math.hypot(
                    w.toDouble(),
                    h.toDouble()
                ).toInt()
            Log.d("TAG", "updateSuccessData: $w heigth $h")
            if (h==0)
                return
            try {
                val anim = ViewAnimationUtils.createCircularReveal(
                    myView,
                    w/2,
                    h/2,
                    0f,
                    endRadius.toFloat()
                )
                binding.viewDetail.show()
                anim.duration = 4000
                anim.start()
                binding.viewContent.viewTreeObserver.removeOnGlobalLayoutListener(this)
            } catch (e: Exception) {
                binding.viewDetail.show()
                e.printStackTrace()
            }
        }
    }

}