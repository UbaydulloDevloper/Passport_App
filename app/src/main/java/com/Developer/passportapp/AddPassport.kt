package com.Developer.passportapp

import DataBase.AppDatabase
import Entity.User
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
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
    lateinit var currentImagePath: String
    lateinit var photoUri: Uri

    var absolutePath: ByteArray? = null
    var seriaNumber = ""
    var image: String = ""
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
                    getImageContent.launch("image/*")
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
                                                if (image != "") {
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
                                                                image,
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


    @SuppressLint("SimpleDateFormat")
    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri ->
            uri ?: return@registerForActivityResult
            binding.imageUser.setImageURI(uri)

            val inputStream = activity?.contentResolver?.openInputStream(uri)
            val simpleDateFormat = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val file = File(activity?.filesDir, "${simpleDateFormat}rasm.jpg")
            val fileOutputStream = FileOutputStream(file)
            inputStream?.copyTo(fileOutputStream)

            inputStream?.close()
            fileOutputStream.close()

            image = file.absolutePath
        }

    private fun createImageFile(): File {
        val format = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val externalFilesDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        println("createImageFile ishlayapti")
        return File.createTempFile("JPEG_$format", ".jpg", externalFilesDir).apply {
            currentImagePath = absolutePath
        }
    }

    fun seriaNumberGet() {
        /* var passportSeria = ""
         val listdate = appDatabase.userDao().getAllUser()
         val characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
         for (i in 0..1) {
             passportSeria += Random().nextInt(characterSet.length).toString()
         }
         for (i in 0 until 7) {
             passportSeria += Random().nextInt(10)
         }
         for (i in listdate.indices) {
             if (listdate[i].seriaNumber == passportSeria) {
                 seriaNumberGet()
             }
         }
         seriaNumber = passportSeria*/

        val rand = Random()

        val str = "ABCDEFGHIJKLMNOPQRSTUVXYZ"
        var matn = ""
        for (i in 0..1) {
            val randomNumber = rand.nextInt(9)
            matn += str[randomNumber]
        }
        for (i in 0..6) {
            val randomNumber = rand.nextInt(9)
            matn += randomNumber
        }

        seriaNumber = matn
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