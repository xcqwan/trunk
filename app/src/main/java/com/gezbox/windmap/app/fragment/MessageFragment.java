package com.gezbox.windmap.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.activity.StandardWorkInterface;
import com.gezbox.windmap.app.activity.WaitAuditListActivity;

/**
 * Created by zombie on 14-11-3.
 */
public class MessageFragment extends BaseFragment implements StandardWorkInterface, View.OnClickListener {
    private LinearLayout ll_wait_audit;
    private ImageView iv_wait_audit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCustom();
        initListener();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == ll_wait_audit.getId()) {
            startActivity(new Intent(getActivity(), WaitAuditListActivity.class));
        }
    }

    @Override
    public void initCustom() {
        ll_wait_audit = (LinearLayout) getView().findViewById(R.id.ll_wait_audit);
        iv_wait_audit = (ImageView) getView().findViewById(R.id.iv_wait_audit);

        iv_wait_audit.setImageResource(R.drawable.ic_audit_new);
    }

    @Override
    public void initListener() {
        ll_wait_audit.setOnClickListener(this);
    }
}
