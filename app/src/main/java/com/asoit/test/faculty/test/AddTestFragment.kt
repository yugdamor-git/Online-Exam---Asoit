package com.asoit.test.faculty.test

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DownloadManager
import android.app.TimePickerDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.asoit.test.R
import com.asoit.test.firestoremodel.*
import com.asoit.test.student.models.test2
import com.asoit.test.user.StudentInfoModel
import com.asoit.test.user.students
import com.developer.filepicker.view.FilePickerDialog
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.minibugdev.sheetselection.SheetSelection
import com.minibugdev.sheetselection.SheetSelectionItem
import com.snatik.storage.Storage
import com.tapadoo.alerter.Alerter
import droidninja.filepicker.FilePickerActivity
import io.grpc.internal.IoUtils
import kotlinx.android.synthetic.main.fragment_add_test.*
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class AddTestFragment : Fragment() {



     var branch = ArrayList<SheetSelectionItem>()
     var subject=ArrayList<SheetSelectionItem>()
    var testData:ResultInfoClass = ResultInfoClass()

    lateinit var adapter:ArrayAdapter<String>
    lateinit var dialog:FilePickerDialog

    var testQuestionData = ArrayList<TestQuestionModel>()
    lateinit var storage:Storage
     var firestore:FirebaseFirestore = FirebaseFirestore.getInstance()


     var selectedSemPos:Int =0
    var time_left=0L
    var selectedDurationPos=0
    var selectedSubjectPos=0

    var selectedBranchPos=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add_test, container, false)

        firestore = FirebaseFirestore.getInstance()
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testData.published = false
        //get all semesters from database
        firestore.collection("subject").get().addOnSuccessListener {
            val branches= it.toObjects(SubjectInfoModel::class.java)
            branches.forEach {
                subject.add(SheetSelectionItem(it.subject_code.toString(),it.name.toString()))
            }
        }

        //get all branch from database
        firestore.collection("branch").get().addOnSuccessListener {
            val branches= it.toObjects(BranchInfoModel::class.java)
            branches.forEach {
                branch.add(SheetSelectionItem(it.code.toString(),it.branch_name.toString()))
            }
        }










// Create a reference with an initial file path and name
        /**
         *    val pathReference = storageRef.child("asoit/Book1.xlsx")

        pathReference.downloadUrl.addOnSuccessListener {

        download(this.requireContext(),"book",".xlsx",DIRECTORY_DOWNLOADS,it.toString())
        }
        .addOnFailureListener{
        Toast.makeText(this.requireContext(),it.message,Toast.LENGTH_SHORT).show()
        }
         */


upload_btn.setOnClickListener {

    if (test_title_et.text.isNullOrEmpty())
    {
        Alerter.create(this.requireActivity())
            .setTitle("Empty title")
            .setIcon(R.drawable.ic_sad)
            .setBackgroundColor(R.color.warning_color)
            .setText("Please Enter test title !!")
            .show()
      //  Toast.makeText(this.requireContext(),"Please Enter test title",Toast.LENGTH_SHORT).show()
        return@setOnClickListener
    }
    if (testData.duration == null )

    {    Alerter.create(this.requireActivity())
        .setTitle("Empty duration")
        .setIcon(R.drawable.ic_sad)
        .setBackgroundColor(R.color.warning_color)
        .setText("Please Select Duration !!")
        .show()
      //  Toast.makeText(this.requireContext(),"Please Select Duration",Toast.LENGTH_SHORT).show()
        return@setOnClickListener
    }

    if (testData.semester == null)
    {
        Alerter.create(this.requireActivity())
            .setTitle("Empty semester")
            .setIcon(R.drawable.ic_sad)
            .setBackgroundColor(R.color.warning_color)
            .setText("Please Select Semester !!")
            .show()
       // Toast.makeText(this.requireContext(),"Please Select Semester",Toast.LENGTH_SHORT).show()
        return@setOnClickListener
    }

    if (testData.test_date == null)
    {
        Alerter.create(this.requireActivity())
            .setTitle("Empty test date")
            .setIcon(R.drawable.ic_sad)
            .setBackgroundColor(R.color.warning_color)
            .setText("Please Select test date !!")
            .show()
      //  Toast.makeText(this.requireContext(),"Please Select test date",Toast.LENGTH_SHORT).show()
        return@setOnClickListener
    }
    if (testData.valid_till == null)
    {
        Alerter.create(this.requireActivity())
            .setTitle("Empty valid till date")
            .setIcon(R.drawable.ic_sad)
            .setBackgroundColor(R.color.warning_color)
            .setText("Please Select valid till date !!")
            .show()
       // Toast.makeText(this.requireContext(),"Please Select valid till date",Toast.LENGTH_SHORT).show()
        return@setOnClickListener
    }
    if (testData.branch == null)
    {
        Alerter.create(this.requireActivity())
            .setTitle("Empty Branch")
            .setIcon(R.drawable.ic_sad)
            .setBackgroundColor(R.color.warning_color)
            .setText("Please Select Branch !!")
            .show()
       // Toast.makeText(this.requireContext(),"Please Select Branch",Toast.LENGTH_SHORT).show()
        return@setOnClickListener
    }
    if (testData.subject == null)
    {
        Alerter.create(this.requireActivity())
            .setTitle("Empty Subject")
            .setIcon(R.drawable.ic_sad)
            .setBackgroundColor(R.color.warning_color)
            .setText("Please Select Subject !!")
            .show()
       // Toast.makeText(this.requireContext(),"Please Select Subject",Toast.LENGTH_SHORT).show()
        return@setOnClickListener
    }
    if (testQuestionData.size <= 0 || testQuestionData.isNullOrEmpty())
    {
        Alerter.create(this.requireActivity())
            .setTitle("Empty Excel file")
            .setIcon(R.drawable.ic_sad)
            .setBackgroundColor(R.color.warning_color)
            .setText("Please Select Excel file !!")
            .show()
       // Toast.makeText(this.requireContext(),"Please Select Excel file",Toast.LENGTH_SHORT).show()
        return@setOnClickListener
    }

    add_test_constrain.visibility = View.VISIBLE
    val now = Timestamp(Calendar.getInstance().time)




                val reference = firestore.collection("all_test")
                    .document()
                val id = reference.id
                testData.test_title = test_title_et.text.toString()
                testData.test_id = id
                testData.test_active = false
                testData.created_date = now
                testData.user_id = "null"
                testData.score =0
                testData.time_left = time_left
                testData.started = false
                testData.submited = false
                testData.user_enrollment = "null"
                testData.user_name = "null"
                testData.document_id = id
                reference.set(testData).addOnSuccessListener {

                    testQuestionData.forEach {
                        val questionReference = firestore.collection("all_test").document(reference.id).collection("questions").document()
                        it.id = questionReference.id
                        it.selected_ans ="0"
                        questionReference.set(it)
                       // Toast.makeText(this.requireContext(),"Please Select Semester",Toast.LENGTH_SHORT).show()

                    }
                }
                    .addOnFailureListener {
                        add_test_constrain.visibility = View.GONE
                        Alerter.create(this.requireActivity())
                            .setTitle("Firestore error")
                            .setIcon(R.drawable.ic_sad)
                            .setBackgroundColor(R.color.error_color)
                            .setText(it.message)
                            .show()

                    }
                    .addOnSuccessListener {
                        Alerter.create(this.requireActivity())
                            .setTitle("Successfully uploaded")
                            .setIcon(R.drawable.ic_happy_smiley)
                            .setBackgroundColor(R.color.success_color)
                            .setText("Added in unpublished list")
                            .show()
                     val action =   AddTestFragmentDirections.actionAddTestFragmentToFacultyHomeFragment()
                        findNavController().navigate(action)
                    }





}


        sem_btn.setOnClickListener {
            semesterDialog()
        }
        duration_btn.setOnClickListener {
            durationDialog()
        }
        file_btn.setOnClickListener {
            chooseFile(it)
        }
        test_date.setOnClickListener {
           showDatePickerDailog()
        }
        branch_btn.setOnClickListener {
            branchDialog()
        }
        subject_btn.setOnClickListener {
            showSubjectDialog()
        }


        valid_till_date.setOnClickListener {
            showDatePickerDailogValidTill()
        }
        /**
         * SheetSelection.Builder(this.requireContext()    )
        .title("Sheet Selection")
        .items(items)
        .selectedPosition(2)
        .showDraggedIndicator(true)
        .searchEnabled(false)
        .onItemClickListener { item, position ->
        // DO SOMETHING
        }
        .show()
         */
    }

    private fun showDatePickerDailogValidTill() {
        val now = Calendar.getInstance()
        val datePicker = DatePickerDialog(this.requireContext(),DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            val selectedDate = Calendar.getInstance()
            selectedDate.set(Calendar.YEAR,year)
            selectedDate.set(Calendar.MONTH,month)
            selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)

            val timePicker = TimePickerDialog(this.requireContext(),TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                selectedDate.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedDate.set(Calendar.MINUTE,minute)
                testData?.valid_till = Timestamp(selectedDate.time)

                // Timestamp(selectedDate.)
            }
                ,
                now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false).show()

        },

            now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))

        datePicker.show()
    }

    private fun chooseFile(view:View) {
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

    private fun showSubjectDialog() {


        SheetSelection.Builder(this.requireContext()    )
            .title("Select Subject")
            .items(subject)
            .selectedPosition(selectedSubjectPos)
            .showDraggedIndicator(true)
            .searchEnabled(false)
            .onItemClickListener { item, position ->
                // DO SOMETHING
                selectedSubjectPos = position
                testData?.subject = item.value
                testData?.subject_code = item.key.toInt()
                subject_btn.text = item.value
            }
            .show()
    }

    private fun branchDialog() {

        SheetSelection.Builder(this.requireContext()    )
            .title("Select Branch")
            .items(branch)
            .selectedPosition(selectedBranchPos)
            .showDraggedIndicator(true)
            .searchEnabled(false)
            .onItemClickListener { item, position ->
                // DO SOMETHING
                selectedBranchPos = position
                testData?.branch = item.value
                testData?.branch_code = item.key.toInt()
                branch_btn.text = item.value
            }
            .show()
    }

    private fun durationDialog() {

        var duration_item = ArrayList<SheetSelectionItem>()
        var count = 0
        var index = 0
        for(i in 0..100)
        {

            count = count +1
            if (count == 5)
            {
                index = index +1
                duration_item.add(SheetSelectionItem(index.toString(),(i+1).toString()))
                count = 0
            }

        }

        SheetSelection.Builder(this.requireContext()    )
            .title("Select Duration")
            .items(duration_item)
            .selectedPosition(selectedDurationPos)
            .showDraggedIndicator(true)
            .searchEnabled(false)
            .onItemClickListener { item, position ->
                // DO SOMETHING
               selectedDurationPos = position
                testData?.duration = item.value.toInt()
                time_left = (item.value.toInt() * 60 * 1000).toLong()
                duration_btn.text = item.value
            }
            .show()
    }

    fun semesterDialog()
    {
        val sem_item = listOf(
            SheetSelectionItem("1", "1"),
            SheetSelectionItem("2", "2"),
            SheetSelectionItem("3", "3"),
            SheetSelectionItem("4", "4"),
            SheetSelectionItem("5", "5"),
            SheetSelectionItem("6", "6"),
            SheetSelectionItem("7", "7"),
            SheetSelectionItem("8", "8")
        )

        SheetSelection.Builder(this.requireContext()    )
            .title("Select Semester")
            .items(sem_item)
            .selectedPosition(selectedSemPos)
            .showDraggedIndicator(true)
            .searchEnabled(false)
            .onItemClickListener { item, position ->
                // DO SOMETHING
                selectedSemPos = position
                testData?.semester = item.value.toInt()
                sem_btn.text = item.value
            }
            .show()
    }

    fun download(context:Context,fileName:String,extension:String,destinationDir:String,url:String)
    {
        val dm = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val uri = Uri.parse(url)
        val req = DownloadManager.Request(uri)
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        req.setDestinationInExternalPublicDir(destinationDir,fileName+extension)
        dm.enqueue(req)


    }



    private fun showFileDialog() {
        val intent = Intent()
            .setType("*/*")
            .setAction(Intent.ACTION_GET_CONTENT)

        startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)


        /**
         *  val properties = DialogProperties()
        properties.selection_mode = DialogConfigs.SINGLE_MODE
        properties.selection_type = DialogConfigs.FILE_SELECT
        properties.root =  File(DialogConfigs.STORAGE_DIR)
        properties.error_dir =  File(DialogConfigs.DEFAULT_DIR)
        properties.offset =  File(DialogConfigs.DEFAULT_DIR)
        properties.extensions = null

        dialog = FilePickerDialog(this.requireContext(), properties)
        dialog.setTitle("Select a File")

        dialog.setDialogSelectionListener {
        //files is the array of the paths of files selected by the Application User.
        // val file = context.contentResolver.openInputStream(Uri.parse(it[0].toString()))
        val listofq = Poiji.fromExcel(Uri.parse("file:"+it[0].toString()).toFile(),QuestionModelE::class.java)

        Toast.makeText(this.requireContext(),listofq[0].question,Toast.LENGTH_SHORT).show()
        }

        dialog.show()

         */
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

                        val a =  Gson().fromJson(it.reader(),test2::class.java)
                        testQuestionData?.clear()
                            testQuestionData.addAll(a.Sheet1)
                            testData.total_questions = a.Sheet1.size

                    //   val wb =  jxl.Workbook.getWorkbook(it)
                      //  val sheet = wb.getSheet(0)
                       // val rows = sheet.rows
                        //val cols = sheet.columns

                  //   val question=   Poiji.fromExcel(it.,PoijiExcelType.XLSX,QuestionModelE::class.java)

                   //  Toast.makeText(this.requireContext(),sheetrows.toString()+" : "+cols.toString(),Toast.LENGTH_SHORT).show()
                        /**
                         * val a =  Gson().fromJson(it.reader(),students::class.java)
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
    fun getFilename(contentResolver: ContentResolver, uri: Uri): String? {
        return when(uri.scheme) {
            ContentResolver.SCHEME_CONTENT -> {
                contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    cursor.getString(nameIndex);
                }
            }
            ContentResolver.SCHEME_FILE-> {
                uri.path?.let { path ->
                    File(path).name
                }
            }
            else -> null
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (dialog != null) { //Show dialog if the read permission has been granted.
                        dialog.show()
                    }
                } else { //Permission has not been granted. Notify the user.
                    Toast.makeText(
                        this.requireContext(),
                        "Permission is Required for getting list of files",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showDatePickerDailog() {
        val now = Calendar.getInstance()
        val datePicker = DatePickerDialog(this.requireContext(),DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            val selectedDate = Calendar.getInstance()
            selectedDate.set(Calendar.YEAR,year)
            selectedDate.set(Calendar.MONTH,month)
            selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)

            val timePicker = TimePickerDialog(this.requireContext(),TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                selectedDate.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedDate.set(Calendar.MINUTE,minute)
                testData?.test_date = Timestamp(selectedDate.time)
               // Timestamp(selectedDate.)
            }
                ,
                now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false).show()

        },

            now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))

        datePicker.show()
    }




}
