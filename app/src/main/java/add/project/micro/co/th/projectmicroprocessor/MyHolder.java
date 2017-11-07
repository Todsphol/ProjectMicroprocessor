package add.project.micro.co.th.projectmicroprocessor;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
     ImageView image ;
    TextView statusView;
    TextView statusShow;
    TextView dateView;
    TextView dateShow;
    ImageView arrowRight;
    ItemClickListener itemClickListener;
    public MyHolder(View itemView) {
        super(itemView);
        image =  itemView.findViewById(R.id.im_machine);
        statusView =  itemView.findViewById(R.id.tv_status_first);
        statusShow =  itemView.findViewById(R.id.tv_real_status);
        dateView =  itemView.findViewById(R.id.tv_date);
        dateShow =  itemView.findViewById(R.id.tv_time_real);
        arrowRight =  itemView.findViewById(R.id.im_ic_arrow_right);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());

    }
    public void setItemClickListener(ItemClickListener ic) {
        this.itemClickListener = ic;
    }
}
