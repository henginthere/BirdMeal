package com.ssafy.birdmeal.view.donation.nft

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import com.ssafy.birdmeal.R
import com.ssafy.birdmeal.base.BaseFragment
import com.ssafy.birdmeal.databinding.FragmentCanvasBinding
import com.ssafy.birdmeal.utils.FileUtil
import com.ssafy.birdmeal.utils.TAG
import com.ssafy.birdmeal.utils.fileToBitmap
import com.ssafy.birdmeal.view.home.LoadingDialog
import com.ssafy.birdmeal.view.home.YesOrNoDialog


private val PERMISSIONS_REQUIRED = Manifest.permission.READ_EXTERNAL_STORAGE

class CanvasFragment : BaseFragment<FragmentCanvasBinding>(R.layout.fragment_canvas) {

    private val canvasViewModel by activityViewModels<CanvasViewModel>()
    private val loadingDialog by lazy { LoadingDialog("포토카드 제작중...") }

    override fun init() {
        canvasViewModel.setColor(Color.parseColor("#000000"))

        initViewModelCallBack()

        initClickListener()
    }

    private fun initViewModelCallBack() = with(binding) {
        canvasViewModel.apply {
            color.observe(viewLifecycleOwner) {
                canvas.setPaintColor(it)
            }

            textMsgEvent.observe(viewLifecycleOwner) {
                canvas.addTextSticker(text?.value!!, color?.value!!, null)
            }

            fileMsgEvent.observe(viewLifecycleOwner) {
                loadingDialog.dismiss()
                showToast(it)
                findNavController().popBackStack()
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
            showYesOrNoDialog()
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
            canvasViewModel.text.postValue("")
            TextDialog().show(childFragmentManager, "textDialog")
        }

        // photo 버튼
//        ivPhoto.setOnClickListener {
//
//        }

        // gallery 버튼
        ivGallery.setOnClickListener {
            requestPermissionLauncher.launch(PERMISSIONS_REQUIRED)
        }
    }

    private fun showYesOrNoDialog() {
        val positiveListener = {
            loadingDialog.show(parentFragmentManager, "loadingDialog")
            val bitmap = binding.canvas.downloadBitmap()
            val multipartBody = FileUtil.bitmapToMultiPart(bitmap)
            canvasViewModel.saveFile(multipartBody)
            Unit
        }
        val negativeListener = {
            Unit
        }
        YesOrNoDialog(
            title = "마음을 담은 포토카드를\n생성하시겠습니까?",
            positive = "생성하기",
            negative = "취소",
            positiveListener,
            negativeListener
        ).show(childFragmentManager, "YesOrNoDialog")
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

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // PERMISSION GRANTED
            pick()
        } else {
            // PERMISSION NOT GRANTED
            showToast("권한이 거부됨")
        }
    }

    // 사진 선택
    private fun pick() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        filterActivityLauncher.launch(intent)
    }

    // 사진 골라서 가져온 결과
    private val filterActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {

                val imagePath = it.data!!.data
                Log.d(TAG, "imagePath: $imagePath")

                val file = FileUtil.toFile(requireContext(), imagePath!!)
                Log.d(TAG, "file: $file")

                val bm = fileToBitmap(file)
                Log.d(TAG, "bm: $bm")

                binding.canvas.addBitmapSticker(bm!!)

            } else if (it.resultCode == Activity.RESULT_CANCELED) {
                showToast("사진 선택 취소")
            } else {
                Log.d(TAG, "filterActivityLauncher wrong")
            }
        }
}