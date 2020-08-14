package com.asoit.test.faculty.home.addstudents

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.asoit.test.R
import com.asoit.test.user.students
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_add_students.*

/**
 * A simple [Fragment] subclass.
 */
lateinit var firestore: FirebaseFirestore
lateinit var firebaseAuth: FirebaseAuth
class AddStudentsFragment : Fragment() {
    var studentJson = students()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        firestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.fragment_add_students, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        excel_file_btn.setOnClickListener {
            chooseFile()
        }


        upload_student_btn.setOnClickListener {
            studentJson.Sheet1?.forEach {user->

                firebaseAuth.createUserWithEmailAndPassword(user.email!!,"whymee007")
                    .addOnSuccessListener {
                       val uidofuser = it.user!!.uid
                        user.user_id = uidofuser
                        firestore.collection("users").document(uidofuser).set(user).addOnFailureListener {
                            Toast.makeText(this.requireContext(),it.message,Toast.LENGTH_SHORT).show()
                        }

                    }
                firebaseAuth.signOut()

            }
            firebaseAuth.signInWithEmailAndPassword("faculty@gmail.com","whymee007")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) { // Step 1: When a result has been received, check if it is the result for READ_IN_FILE
            if (resultCode == Activity.RESULT_OK) { // Step 2: Check if the operation to retrieve thea ctivity's result is successful
                // Attempt to retrieve the file


                try {
                    data?.data?.let {

                        context?.contentResolver?.openInputStream(it)
                    }?.let {
                        val a =  Gson().fromJson(it.reader(), students::class.java)
                        studentJson.Sheet1 = a.Sheet1
                        Toast.makeText(this.requireContext(),"click on upload",Toast.LENGTH_SHORT).show()

                        //   val wb =  jxl.Workbook.getWorkbook(it)
                        //  val sheet = wb.getSheet(0)
                        // val rows = sheet.rows
                        //val cols = sheet.columns

                        //   val question=   Poiji.fromExcel(it.,PoijiExcelType.XLSX,QuestionModelE::class.java)

                        //  Toast.makeText(this.requireContext(),sheetrows.toString()+" : "+cols.toString(),Toast.LENGTH_SHORT).show()
                        /**
                         *
                        a.Sheet1?.forEach {

                        val ref =  firestore.collection("users").document()
                        it.user_id = ref.id

                        ref.set(it).addOnSuccessListener {

                        }
                        }
                         */

                        //Log.d("xls",a.Sheet1.size.toString())
                        //  testQuestionData?.clear()
                        //  testQuestionData.addAll(a.Sheet1)
                        //    testData.total_questions = a.Sheet1.size
                        ///val r = BufferedReader(InputStreamReader(it))

                        //   while (true) {
                        //     val line: String? = r.readLine() ?: break
                        //Log.d("xls",line)
                        // }
                    }

                } catch (e: Exception) { // If the app failed to attempt to retrieve the error file, throw an error alert
                    Toast.makeText(
                        this.requireContext(),
                        e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun chooseFile() {
        //only the below specified mime type is allowed in the picker
        val READ_IN_FILE = 101
        val mimeTypes = arrayOf(

            "application/json",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            ,"application/vnd.ms-excel"
        )
        println("chooseFile activated!");
        var selectFile = Intent(Intent.ACTION_GET_CONTENT)
        selectFile.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
        if (mimeTypes.isNotEmpty()) {
            selectFile.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
        selectFile = Intent.createChooser(selectFile, "Choose a file")
        startActivityForResult(selectFile, READ_IN_FILE)

        // startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
    }

}
