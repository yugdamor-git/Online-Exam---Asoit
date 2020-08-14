package com.asoit.test.student.home.questions

import android.animation.Animator
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.recyclerview.widget.*
import com.asoit.test.R
import com.asoit.test.firestoremodel.ResultInfoClass
import com.asoit.test.firestoremodel.ResultQuestionClass
import com.asoit.test.firestoremodel.TestQuestionModel
import com.asoit.test.student.StudentHomeActivity
import com.asoit.test.utils.setupWithViewPager

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kiwimob.firestore.livedata.livedata
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager
import com.lsjwzh.widget.recyclerviewpager.TabLayoutSupport
import com.minibugdev.sheetselection.SheetSelection
import com.minibugdev.sheetselection.SheetSelectionItem
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.activity_questions.*
import java.util.*
import kotlin.collections.ArrayList

class QuestionsActivity : AppCompatActivity() {

    //lateinit var recycler:RecyclerViewPager
  //  lateinit var auth:FirebaseAuth
    var list = ArrayList<TestQuestionModel>()
    var time_left_timer=0L
    lateinit var db:FirebaseFirestore
    lateinit var timer:CountDownTimer
    lateinit var madapter:QuestionAdapter
    lateinit var docid:String
    var textcolor = false
    var count =0
    override fun onPause() {
        super.onPause()
        db.collection("test")
            .document(docid)
            .update("time_left",time_left_timer)
            .addOnSuccessListener {  }
        timer.cancel()
        Toast.makeText(this,"onPause",Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        db.collection("test")
            .document(docid)
            .get()
            .addOnSuccessListener {
                val result = it.toObject(ResultInfoClass::class.java)
                timer = object :CountDownTimer(result?.time_left!!,1000)
                {
                    override fun onFinish() {
                        //redirect user to test complete page
                        calculateScore()
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        //  update text view
                        val min =  (millisUntilFinished/1000)/60
                        val second = (millisUntilFinished/1000) % 60
                        val time_string = String.format(Locale.getDefault(),"%02d:%02d",min,second)
                        if (min < 10)
                        {
                            if(!textcolor)
                            {
                                time_left_tv.setTextColor(Color.parseColor("#c9485b"))
                                textcolor = true
                            }
                        }
                        time_left_tv.text = time_string

                        time_left_timer = millisUntilFinished


                    }

                }.start()
            }


        Toast.makeText(this,"onResume",Toast.LENGTH_SHORT).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseFirestore.getInstance()
        setContentView(R.layout.activity_questions)


        var testid = "0EllamK8fcNLN09hpJ6L"

        testid = intent.getStringExtra("test_id")
        val time_left = intent.getLongExtra("time_left",0)
        docid = intent.getStringExtra("doc_id")

            madapter = QuestionAdapter(db, testid, docid)
      viewpager2_questions.adapter = madapter
      //  tabLayout2.setupWithViewPager(viewpager2_questions,madapter.currentList)

         //   TabLayoutSupport.setupWithViewPager(tabLayout2 as TabLayout,recycler,madapter)
        val lManager = LinearLayoutManager(this@QuestionsActivity)





        db.collection("test")
            .document(docid)
            .collection("questions")
            .livedata(TestQuestionModel::class.java)
            .observe(this, androidx.lifecycle.Observer {
                madapter.submitList(it)
                TabLayoutMediator(tabLayout2,viewpager2_questions,TabLayoutMediator.TabConfigurationStrategy{tab,pos->



                    if (madapter.currentList[pos].selected_ans == "0")
                    {
                        tab.setIcon(R.drawable.ic_button_cross)
                       // tab.icon?.current?.setTint(getColor(R.color.colorAccent))



                            //.view.background.setTint(Color.BLUE)

                    }
                    else{
                        tab.setIcon(R.drawable.ic_right)
                          //  tab.icon?.current?.setTint(getColor(R.color.green))
                        //tab.view.setBackgroundColor(Color.MAGENTA)
                    }
                    tab.text = (pos+1).toString()



                }).attach()

                list.clear()
                list.addAll(it)
            })

        submit.setOnClickListener {

            val items = listOf<SheetSelectionItem>(SheetSelectionItem("1","Yes"),
                SheetSelectionItem("2","No"))
            SheetSelection.Builder(this)
                .title("Are you sure ?")
                .items(items)
                .selectedPosition(2)
                .showDraggedIndicator(true)
                .searchEnabled(false)
                .onItemClickListener { item, position ->
                    if (item.key =="1")
                    {
                        //show result
                       calculateScore()


                    }
                    if (item.key =="2")
                    {
                        //dismiss

                    }
                }
                .show()






        }


      //  recycler = findViewById(R.id.question_recycler)
        //auth = FirebaseAuth.getInstance()
        //db = FirebaseFirestore.getInstance()
      //  madapter = QuestionAdapter(db,testid,docid)

            //db.collection("all_test").whereEqualTo("id","")
               // .live
        /**
        questions_next_btn.setOnClickListener {
        count =0
        playAnim(question_tv,0)

        }
        db.collection("test").document(docid).collection("questions")
            .livedata(TestQuestionModel::class.java)
            .observe(this, androidx.lifecycle.Observer {
                madapter.submitList(it)
            })

        val lManager = LinearLayoutManager(this@QuestionsActivity)
        recycler.apply {

            lManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = lManager
            adapter = madapter

        }

            val a = PagerSnapHelper()
        a.attachToRecyclerView(recycler)


         *
         *  db.collection("all_test").add(testDetailsModel(Timestamp.now(), Timestamp.now(),60,"6","ce","aj")).addOnSuccessListener { doc->
        // Toast.makeText(this,it.id,Toast.LENGTH_SHORT).show()
        listOfQue.forEach {q->
        db.collection("question_data").document(doc.id).collection("questions").add(q).addOnSuccessListener{
        Toast.makeText(this,it.id,Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
        Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
        }
        }

        }
        .addOnFailureListener {
        //   Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
        }

        val da =   db.collection("test_question_data").document("hxSMtDyc11CuD2e850uE").collection("questions").get() as LiveData<ArrayList<QuestionModel>>
              da.observe(this, Observer {
                  recycler.adapter = QuestionsRecyclerAdapter(this,it)
              })
 .addOnSuccessListener {

        val questions = it.toObjects(QuestionModel::class.java)
        recycler.adapter = QuestionsRecyclerAdapter(this, questions as ArrayList<QuestionModel>)
        }
         */

    }

    /**
     *   fun playAnim(view: View, value:Int)
    {
    view.animate().alpha(value.toFloat()).scaleX(value.toFloat()).scaleY(value.toFloat()).setDuration(500).setStartDelay(100)
    .setInterpolator(DecelerateInterpolator())
    .setListener(object:Animator.AnimatorListener{
    override fun onAnimationRepeat(animation: Animator?) {

    }

    override fun onAnimationEnd(animation: Animator?) {

    if(value ==0)
    {
    playAnim(view,1)

    }

    questions_next_btn.isClickable = true
    }

    override fun onAnimationCancel(animation: Animator?) {

    }

    override fun onAnimationStart(animation: Animator?) {
    questions_next_btn.isClickable = false
    if(value == 0 && count < 4)
    {
    playAnim(option_root.getChildAt(count),0)
    count++
    }
    }

    })
    }

     */


 fun calculateScore()
    {
        var count =0
        var total = 0
        db.collection("test")
            .document(docid)
            .collection("questions")
            .get()
            .addOnSuccessListener {
                val que = it.toObjects(TestQuestionModel::class.java)
                total = que.size
                for (q in que) {

                    if (q.correct_ans == q.selected_ans)
                    {
                        count = count +1
                    }

                }



                Alerter.create(this)
                    .setTitle("Result")
                    .setDuration(3000)
                    .setIcon(R.drawable.ic_happy_smiley)
                    .setBackgroundColor(R.color.success_color)
                    .setText("you have score : " + count+" out of "+total)
                    .show()

                db.collection("test")
                    .document(docid)
                    .update("submited",true,"score",count)
                    .addOnSuccessListener {
                        val intent = Intent(this,StudentHomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }


            }


    }


}
