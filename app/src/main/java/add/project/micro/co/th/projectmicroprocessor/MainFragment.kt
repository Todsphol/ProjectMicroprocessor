package add.project.micro.co.th.projectmicroprocessor


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*


class MainFragment : Fragment() {
    var baseR = FirebaseDatabase.getInstance().getReference()
    var logR = baseR.child("log")
    var positionR = baseR.child("position")
    var statusR = baseR.child("status")
    @Nullable @BindView(R.id.image_washing) lateinit var imageView : ImageView
    @Nullable @BindView(R.id.tv_real_time) lateinit var LeftTime : TextView
    @Nullable @BindView(R.id.tv_real_status) lateinit var Status : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)!!
        ButterKnife.bind(this, view)
        dataLog()
        dataStatus()
        return view
    }

    private fun dataLog() {
        logR.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val datetime  = dataSnapshot.child("dateStart").getValue(Long::class.java)
                val calendar = Calendar.getInstance()
                val calendar2 = Calendar.getInstance()
                calendar.timeInMillis = datetime!! + 3600000
                val mHourAfter = calendar2.get(Calendar.HOUR)
                val mMinuteAfter = calendar2.get(Calendar.MINUTE)
                val mSecondAfter = calendar2.get(Calendar.SECOND)
                val mHourBefore = calendar.get(Calendar.HOUR)
                val mMinuteBefore = calendar.get(Calendar.MINUTE)
                val mSecondBefore = calendar.get(Calendar.SECOND)
                val timeMi = calendar2.timeInMillis
               val xxxx= (calendar.timeInMillis-timeMi)
                calendar.timeInMillis=xxxx
                 LeftTime.text = calendar.get(Calendar.MINUTE).toString()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    fun dataStatus() {
        statusR.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val status = dataSnapshot.child("running").value
                if (status.toString().equals("0") ) {
                    imageView.setColorFilter(ContextCompat.getColor(context, R.color.green))
                    Status.text = "free"
                }else {
                    imageView.setColorFilter(ContextCompat.getColor(context, R.color.red))
                    Status.text = "running"

                }

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        }

    companion object {
        fun newInstance(): MainFragment {
            val bundle = Bundle()
            val fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}
