package com.eason.marker.login_register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.eason.marker.BaseFragment;
import com.eason.marker.R;
import com.eason.marker.model.Constellation;
import com.eason.marker.util.CommonUtil;
import com.eason.marker.util.LogUtil;

import java.util.Calendar;

/**
 * Created by Eason on 9/17/15.
 */
public class RegisterBirthdayFragment extends BaseFragment {

    private RegisterActivity registerActivity;
    private TextView constellationTextView;
    private TextView ageTextView;
    private DatePicker datePicker;
    private Button nextButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_birthday,container,false);
        constellationTextView = (TextView)rootView.findViewById(R.id.register_constellation_text_view);
        ageTextView = (TextView)rootView.findViewById(R.id.register_age_text_view);
        datePicker = (DatePicker)rootView.findViewById(R.id.register_date_picker);
        nextButton = (Button)rootView.findViewById(R.id.register_birthday_next_button);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerActivity = (RegisterActivity)getActivity();

        //获取当前系统年份
        Calendar calendar = Calendar.getInstance();
        final int currentYear = calendar.get(Calendar.YEAR);
        //在页面中显示初始化的信息
        ageTextView.setText(currentYear-1995);
        constellationTextView.setText(Constellation.getConstellation(getActivity(),6,15));

        //生日格式统一用 year-month-day
        RegisterActivity.birthday = "1995-6-15";
        RegisterActivity.userConsellation = Constellation.ShuangZiZuo;

        //初始化日期控件，并且监听用户输入的日期来显示相应的信息
        datePicker.init(1995, 6, 15, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int age = currentYear - year;
                int validMonth = monthOfYear+1;
                ageTextView.setText(age);
                RegisterActivity.birthday = String.valueOf(year+"-"+validMonth+"-"+dayOfMonth);
                constellationTextView.setText(Constellation.getConstellation(getActivity(),validMonth,dayOfMonth));
                RegisterActivity.userConsellation = Constellation.getConstellation(getActivity(),validMonth,dayOfMonth);

                LogUtil.d("RegisterBirthdayFragment", "year : " + year + " monthOfYear : " + validMonth + " dayOfMonth : " + dayOfMonth);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //如果用户快速点击则返回
                if (CommonUtil.isFastDoubleClick(500))return;

                //注册
                registerActivity.register();
            }
        });
    }
}
