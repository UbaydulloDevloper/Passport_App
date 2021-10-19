package com.Developer.passportapp

import DataBase.AppDatabase
import Entity.User
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.Developer.passportapp.databinding.FragmentAddPassportBinding
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.item_bottom_sheets_dialog.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddPassport : Fragment() {
    lateinit var binding: FragmentAddPassportBinding
    lateinit var appDatabase: AppDatabase
    lateinit var spiCountry: ArrayList<String>
    lateinit var spinner: ArrayList<String>

    var absolutePath: ByteArray? = null
    var seriaNumber = ""


    //
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPassportBinding.inflate(layoutInflater)
        appDatabase = AppDatabase.getInstance(binding.root.context)
        Spinner()
        seriaNumberGet()



        binding.imageUser.setOnClickListener {
            askPermission(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
            ) {
                val dialog = AlertDialog.Builder(binding.root.context)
                dialog.setMessage("Rasmni kameradan yoki gallereyadan yuklashingiz mumkin:")
                dialog.setPositiveButton("Camera") { _, _ ->
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, 0)
                }

                dialog.setNegativeButton("Gallery") { dialog, which ->
                    startActivityForResult(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "image/*"
                    }, 1)
                }
                dialog.show()
            }.onDeclined {
                if (it.hasDenied()) {
                    android.app.AlertDialog.Builder(context)
                        .setMessage("Please accept our permissions")
                        .setPositiveButton("yes") { dialog, which ->
                            it.askAgain();
                        } //ask again
                        .setNegativeButton("no") { dialog, which ->
                            dialog.dismiss();
                        }
                        .show();
                }
                if (it.hasForeverDenied()) {
                    //the list of forever denied permissions, user has check 'never ask again'

                    // you need to open setting manually if you really need it
                    it.goToSettings();
                }
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSave.setOnClickListener {
            val name = binding.fName.text.toString().trim()
            val surename = binding.fLastName.text.toString().trim()
            val fathername = binding.fOName.text.toString().trim()
            val spinCountry = binding.spinnerCountry.selectedItem.toString()
            val location = binding.location.text.toString().trim()
            val locationHome = binding.locationsHome.text.toString().trim()
            val podate = binding.pODate.text.toString().trim()
            val podateEnd = binding.pODateEnd.text.toString().trim()
            val spinner = binding.spinner2.selectedItem.toString()

            if (name != "") {
                if (surename != "") {
                    if (fathername != "") {
                        if (spinCountry != "Viloyat") {
                            if (location != "") {
                                if (locationHome != "") {
                                    if (podate != "") {
                                        if (podateEnd != "") {
                                            if (spinner != "") {
                                                if (absolutePath != null) {
                                                    val bottomSheetDialog = BottomSheetDialog(
                                                        binding.root.context,
                                                        R.style.MyBottomSheet
                                                    )
                                                    bottomSheetDialog.setContentView(
                                                        layoutInflater.inflate(
                                                            R.layout.item_bottom_sheets_dialog,
                                                            null,
                                                            false
                                                        )
                                                    )
                                                    bottomSheetDialog.show()
                                                    bottomSheetDialog.click_no.setOnClickListener {
                                                        bottomSheetDialog.dismiss()
                                                    }
                                                    bottomSheetDialog.click_yes.setOnClickListener {
                                                        appDatabase.userDao().addUser(
                                                            User(
                                                                name,
                                                                surename,
                                                                fathername,
                                                                spinCountry,
                                                                location,
                                                                locationHome,
                                                                podate,
                                                                podateEnd,
                                                                spinner,
                                                                absolutePath.toString(),
                                                                seriaNumber
                                                            )
                                                        )
                                                        Toast.makeText(
                                                            binding.root.context,
                                                            "Save",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                            .show()
                                                        bottomSheetDialog.dismiss()
                                                        findNavController().popBackStack()
                                                    }
                                                } else {
                                                    Toast.makeText(
                                                        binding.root.context,
                                                        "Rasmni qoymadingiz",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            } else {
                                                Toast.makeText(
                                                    binding.root.context,
                                                    "Insingizni tanlamadingiz",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        } else {
                                            Toast.makeText(
                                                binding.root.context,
                                                "Pasportni amal qilish muddatini kiritmadingiz",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    } else {
                                        Toast.makeText(
                                            binding.root.context,
                                            "Pasportni  berilgan  muddatini kiritmadingiz",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    Toast.makeText(
                                        binding.root.context,
                                        "Uy manzilingizni kiritmadingiz",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    binding.root.context,
                                    "Shaxar yoki tumaningizni kiritmadingiz",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                binding.root.context,
                                "Viloyatingizni kiritmadingiz",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            binding.root.context,
                            "Otangizning ismini kiritmadingiz",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        binding.root.context, "Familiyangizni kiritmadingiz", Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    binding.root.context, "Ismingizni kiritmadingiz", Toast.LENGTH_SHORT
                ).show()
            }

        }

        return binding.root
    }

    fun Spinner() {
        spiCountry = ArrayList()
        spinner = ArrayList()

        spiCountry.add(" Viloyat ")
        spiCountry.add("Andijon")
        spiCountry.add("Namangan")
        spiCountry.add("Farg'ona")
        spiCountry.add("Toshkent")
        spiCountry.add("Buxoro")
        spiCountry.add("Surxandaryo")
        spiCountry.add("Qashqadaryo")
        spiCountry.add("Sirdaryo")
        spiCountry.add("Samarqand")
        spiCountry.add("Xiva")
        spiCountry.add("Qoraqalpog'iston")
        spiCountry.add("Jizzax")
        spiCountry.add("Navoiy")

        spinner.add("Erkak")
        spinner.add("Ayol")

        val adapterS1 = ArrayAdapter(
            binding.root.context,
            android.R.layout.simple_expandable_list_item_1,
            spiCountry
        )
        val adapterS2 = ArrayAdapter(
            binding.root.context,
            android.R.layout.simple_expandable_list_item_1,
            spinner
        )

        binding.spinnerCountry.adapter = adapterS1
        binding.spinner2.adapter = adapterS2

        binding.spinnerCountry.setSelection(0)
        binding.spinner2.setSelection(0)

    }

    fun seriaNumberGet() {
        var passportSeria = ""
        val listdate = appDatabase.userDao().getAllUser()
        val characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        for (i in 0..1) {
            passportSeria += Random().nextInt(characterSet.length).toString()
        }
        for (i in 0 until 10) {
            passportSeria += Random().nextInt(7)
        }
        for (i in listdate.indices) {
            if (listdate[i].seriaNumber == passportSeria) {
                seriaNumberGet()
            }
        }
        seriaNumber = passportSeria
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
            val uri = data?.data ?: return
            binding.imageUser.setImageURI(uri)
            val format = SimpleDateFormat("yyyyMMdd_hhmmss").format(Date())
            val inputStream = activity?.contentResolver?.openInputStream(uri)
            val file = File(activity?.filesDir, "${format}_image.jpg")
            val fileOutputStream = FileOutputStream(file)
            inputStream?.copyTo(fileOutputStream)
            inputStream?.close()
            fileOutputStream.close()
            val fileInputStream = FileInputStream(file)
            val readBytes = fileInputStream.readBytes()
            absolutePath = readBytes
        } else if (requestCode == 0 && resultCode == AppCompatActivity.RESULT_OK) {
            val bitmap = data?.extras?.get("data") as Bitmap
            binding.imageUser.setImageBitmap(bitmap)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            absolutePath = byteArray
        }
    }


}