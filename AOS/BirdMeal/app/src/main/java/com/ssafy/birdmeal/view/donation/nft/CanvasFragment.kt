package com.ssafy.birdmeal.view.donation.nft

import android.graphics.Color
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentCanvasBinding

class CanvasFragment : BaseFragment<FragmentCanvasBinding>(R.layout.fragment_canvas) {

    private val canvasViewModel by viewModels<CanvasViewModel>()

    override fun init() {

        initViewModelCallBack()

        initClickListener()
    }

    private fun initViewModelCallBack() {
        canvasViewModel.apply {
            color.observe(viewLifecycleOwner) {
                binding.canvas.setPaintColor(it)
            }
        }
    }

    private fun initClickListener() = with(binding) {
        // 취소 버튼
        ivCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        // NFT생성 버튼
        ivMakeNft.setOnClickListener {

        }

        // undo 버튼
        ivUndo.setOnClickListener {
            canvas.undo()
        }

        // redo 버튼
        ivRedo.setOnClickListener {
            canvas.redo()
        }

        // palette 버튼
        ivPalette.setOnClickListener {
            showPaletteDialog()
        }

        // text 버튼
        ivText.setOnClickListener {

        }

        // photo 버튼
        ivPhoto.setOnClickListener {

        }

        // gallery 버튼
        ivGallery.setOnClickListener {

        }
    }

    private fun showPaletteDialog() {
        ColorPickerDialog.Builder(requireContext())
            .setPreferenceName("platteDialog")
            .setPositiveButton("선택",
                object : ColorEnvelopeListener {
                    override fun onColorSelected(envelope: ColorEnvelope?, fromUser: Boolean) {
                        canvasViewModel.setColor(envelope?.color ?: Color.parseColor("#000000"))
                    }
                }
            )
            .setNegativeButton("취소") { dialogInterface, _ -> dialogInterface?.dismiss() }
            .attachAlphaSlideBar(true) // the default value is true.
            .attachBrightnessSlideBar(true)  // the default value is true.
            .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
            .show()
    }
}