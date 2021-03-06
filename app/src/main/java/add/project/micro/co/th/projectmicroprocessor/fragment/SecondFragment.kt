package add.project.micro.co.th.projectmicroprocessor.fragment


import add.project.micro.co.th.projectmicroprocessor.R
import add.project.micro.co.th.projectmicroprocessor.activity.MainActivity
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SecondFragment : Fragment() {
    private var baseR = FirebaseDatabase.getInstance().reference
    private var logR = baseR.child("log")
    private var statusR = baseR.child("status")
    @Nullable @BindView(R.id.image_washing) lateinit var imageView  : ImageView
    @Nullable @BindView(R.id.tv_real_time) lateinit var leftTime: TextView
    @Nullable @BindView(R.id.tv_real_status) lateinit var status: TextView
    @Nullable @BindView(R.id.edt_e_mail) lateinit var edtMail : EditText
    @Nullable @BindView(R.id.btn_summit) lateinit var btnSummit : Button
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)!!
        ButterKnife.bind(this, view)
        getMainActivity().supportActionBar?.show()
        dataLog()
        dataStatus()
        setEmail()
        return view

    }

    private fun setEmail() {
        btnSummit.setOnClickListener {
            val isnull = 0
            if (edtMail.length() == isnull) {
                Toast.makeText(context, getString(R.string.please_put_email), Toast.LENGTH_LONG).show()
            } else if (edtMail.length() != isnull){
                baseR.child("Noti").child("Email").setValue(edtMail.text.toString())
                Toast.makeText(context, getString(R.string.save_sucess), Toast.LENGTH_LONG).show()
            }
            edtMail.setText(getString(R.string.isBlank))
        }
    }


    private fun dataLog() {
        logR.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val count = dataSnapshot.child("countDown").getValue(Int::class.java)
                leftTime.text = count.toString()

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    private fun dataStatus() {
        statusR.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val statusData = dataSnapshot.child("running").getValue(Int::class.java)
                val powerData = dataSnapshot.child("power").getValue(Int::class.java)
                val turnOff = 0
                val turnOn = 1
                val idle = 0
                val working = 1
                statusShow(powerData, turnOff, turnOn, statusData, idle, working)

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        }

    private fun statusShow(powerData: Int?, turnOff: Int, turnOn: Int, statusData: Int?, idle: Int, working: Int) {
        if (powerData == turnOff) {
            try {
                imageView.setColorFilter(ContextCompat.getColor(context!!, R.color.colorBlueGray))
                status.text = getString(R.string.turn_off)
            } catch (e: NullPointerException) {

            }
        } else if (powerData == turnOn && statusData == idle) {
            try {
                imageView.setColorFilter(ContextCompat.getColor(context!!, R.color.green))
                status.text = getString(R.string.Blank)
            } catch (e: NullPointerException) {

            }

        } else if (powerData == turnOn && statusData == working) {
            try {
                imageView.setColorFilter(ContextCompat.getColor(context!!, R.color.red))
                status.text = getString(R.string.isRunning)

            } catch (e: NullPointerException) {

            }
        }
    }

    private fun getMainActivity(): MainActivity { return activity as MainActivity }

}